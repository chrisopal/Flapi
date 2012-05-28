/*******************************************************************************
 Copyright 2012 Benjamin Fagin

     Licensed under the Apache License, Version 2.0 (the "License");
     you may not use this file except in compliance with the License.
     You may obtain a copy of the License at

         http://www.apache.org/licenses/LICENSE-2.0

     Unless required by applicable law or agreed to in writing, software
     distributed under the License is distributed on an "AS IS" BASIS,
     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     See the License for the specific language governing permissions and
     limitations under the License.


     Read the included LICENSE.TXT for more information.
 ******************************************************************************/

package unquietcode.tools.flapi.generator;

import com.sun.codemodel.*;
import unquietcode.tools.flapi.Constants;
import unquietcode.tools.flapi.MinimumInvocationsException;
import unquietcode.tools.flapi.ObjectWrapper;
import unquietcode.tools.flapi.outline.BlockOutline;
import unquietcode.tools.flapi.outline.MethodOutline;

import java.lang.reflect.Field;
import java.util.*;

/**
 * @author Ben Fagin
 * @version 03-11-2012
 */
public class BlockGenerator extends AbstractGenerator<BlockOutline, Void> {

	public BlockGenerator(BlockOutline outline, GeneratorContext context) {
		super(outline, context);
	}

	@Override
	public Void generate() {
		JDefinedClass iHelper = generateHelper();
		generateSubsets();

		for (BlockOutline child : outline.getBlocks()) {
			BlockGenerator childGenerator = new BlockGenerator(child, ctx);
			childGenerator.generate();
		}

		return null;

	}

	//==o==o==o==o==o==o==| helper |==o==o==o==o==o==o==//

	public JDefinedClass generateHelper() {
		JDefinedClass iHelper = getHelperInterface(outline);

		for (MethodOutline method : outline.getAllMethods()) {
			JMethod _method = addMethod(iHelper, ctx.model.VOID, JMod.NONE, method);

			// for every block in the chain, add a wrapped helper parameter
			int i=1;
			for (BlockOutline block : method.getBlockChain()) {
				JDefinedClass blockHelper = getHelperInterface(block);
				_method.param(ref(ObjectWrapper.class).narrow(blockHelper), Constants.HELPER_VALUE_NAME+(i++));
			}
		}

		return iHelper;
	}



	//==o==o==o==o==o==o==| subsets |==o==o==o==o==o==o==//

	public void generateSubsets() {
		for (Set<MethodOutline> combination : makeCombinations(outline.getDynamicMethods())) {

			// make the interface (the empty one should be the only already created one)
			JDefinedClass iSubset = getSubsetInterface(outline, combination);
			iSubset.generify(Constants.RETURN_TYPE_NAME);

			// make the class
			JDefinedClass cSubset = createSubsetImpl(combination);
			addInvocationTracking(cSubset, outline.getAllMethods());

			// add the required methods
			for (MethodOutline method : outline.getRequiredMethods()) {

				// add to interface
				addMethod(iSubset, getDynamicReturnType(outline, combination, method), JMod.NONE, method);

				// add to implementation
				JExpression initialReturnValue = method.isTerminal() ? JExpr.ref(Constants.RETURN_VALUE_NAME) : JExpr._this();
				addMethod(cSubset, computeImplementationReturnType(iSubset, combination, method), initialReturnValue, method);
			}

			// add the dynamic methods
			for (MethodOutline method : combination) {
				JType returnType = getDynamicReturnType(outline, combination, method);

				// add to interface
				addMethod(iSubset, returnType, JMod.NONE, method);

				// add to implementation
				addMethod(
					cSubset,
					computeImplementationReturnType(iSubset, combination, method),
					computeInitialReturnValue(combination, method),
					method
				);
			}
		}
	}

	private JDefinedClass createSubsetImpl(Set<MethodOutline> methodCombination) {
		JDefinedClass cSubset = getSubsetImplementation(outline, methodCombination);
		JDefinedClass iSubset = getSubsetInterface(outline, methodCombination);
		cSubset._implements(iSubset);

		JFieldVar _helper = cSubset.field(JMod.PRIVATE+JMod.FINAL, getHelperInterface(outline), Constants.HELPER_VALUE_NAME);
		JFieldVar _returnValue = cSubset.field(JMod.PRIVATE+JMod.FINAL, ref(Object.class), Constants.RETURN_VALUE_NAME);

		// constructor
		JMethod constructor = cSubset.constructor(JMod.NONE);
		JVar pHelper = constructor.param(getHelperInterface(outline), "helper");
		JVar pReturnValue = constructor.param(ref(Object.class), "returnValue");

		constructor.body().assign(_helper, pHelper);
		constructor.body().assign(_returnValue, pReturnValue);

		return cSubset;
	}

	private void addInvocationTracking(JDefinedClass cBuilder, Set<MethodOutline> methods) {
		// filter the methods
		Set<MethodOutline> filtered = new TreeSet<MethodOutline>(methods);
		for (MethodOutline method : methods) {
			if (method.minOccurrences <= 0) {
				filtered.remove(method);
			}
		}
		methods = filtered;

		// fields
		for (MethodOutline method : methods) {
			cBuilder.field(JMod.NONE, ctx.model.INT, "ic_"+makeMethodKey(outline, method), JExpr.lit(method.minOccurrences));
		}

		// copy method
		JMethod _method = cBuilder.method(JMod.PRIVATE, ctx.model.VOID, "_transferInvocations");
		JVar _next = _method.param(ref(Object.class), "next");

		if (methods.isEmpty()) {
			_method.body().directStatement("// nothing");
		} else {
			JVar _clazz = _method.body().decl(ref(Class.class), "clazz", _next.invoke("getClass"));

			for (MethodOutline method : methods) {
				_method.body().directStatement(" ");
				String key = "ic_"+makeMethodKey(outline, method);
				JTryBlock block = _method.body()._try();
				JVar _field = block.body().decl(ref(Field.class), "field", _clazz.invoke("getDeclaredField").arg(key));

				// field.setInt(next, ref(id))
				block.body().add(_field.invoke("setInt").arg(_next).arg(JExpr.ref(key)));

				block._catch(ref(Exception.class)).body().directStatement("// nothing");
			}
		}

		// check method
		_method = cBuilder.method(JMod.PRIVATE, ctx.model.VOID, "_checkInvocations");

		if (methods.isEmpty()) {
			_method.body().directStatement("// nothing");
		} else {
			for (MethodOutline method : methods) {
				String key = "ic_"+makeMethodKey(outline, method);
				String message = "Expected at least "+method.minOccurrences+" invocations of method '"+method.getMethodSignature()+"'.";

				_method.body()._if(JExpr.ref(key).gt(JExpr.lit(0)))._then()
					._throw(JExpr._new(ref(MinimumInvocationsException.class)).arg(message));
			}
		}
	}

	// --------------------------------------------------------------------------- //

	protected JType computeImplementationReturnType(JDefinedClass iBuilder, Set<MethodOutline> allMethods, MethodOutline method) {
		JType returnType;

		if (method.getBlockChain().isEmpty() && method.isTerminal()) {
			returnType = ref(Object.class);
		} else {
			returnType = getDynamicReturnType(outline, allMethods, method).erasure();
		}

		return returnType;
	}

	protected JExpression computeInitialReturnValue(Set<MethodOutline> allMethods, MethodOutline method) {
		JExpression returnValue;

		// required method will return self
		if (method.isRequired()) {
			returnValue = JExpr._this();

		// terminal method exits the class (eventually)
		} else if (method.isTerminal()) {
			returnValue = JExpr.ref(Constants.RETURN_VALUE_NAME);

		// dynamic method moves laterally to a sibling class
		} else {
			JType returnType = getSubsetImplementation(outline, computeMinusMethod(allMethods, method));

			returnValue =
				JExpr._new(returnType)
					.arg(JExpr.ref(Constants.HELPER_VALUE_NAME))
					.arg(JExpr.ref(Constants.RETURN_VALUE_NAME))
			;
		}

		return returnValue;
	}

	protected void addMethod(JDefinedClass cBuilder, JType returnType, JExpression initialReturnValue, MethodOutline method) {
		JMethod _method = addMethod(cBuilder, returnType, JMod.PUBLIC, method);

		// for every block chain, add a new object wrapper declaration
		List<JVar> helpers = new ArrayList<JVar>();
		for (BlockOutline blockChain : method.getBlockChain()) {
			helpers.add(addHelper(getHelperInterface(blockChain), helpers.size() + 1, _method));
		}

		// invocation check before helper call
		if (method.isTerminal()) {
			_method.body().invoke("_checkInvocations");
		}

		// invoke the main helper
		JInvocation helperCall = makeHelperCall(_method, method);

		// add the wrapped helpers as parameters
		for (JVar helper : helpers) {
			helperCall.arg(helper);
		}

		// add to method body
		_method.body().add(helperCall);
		_method.body().directStatement(" ");

		JExpression returnValue = initialReturnValue;

		for (int i = method.getBlockChain().size()-1; i >=0; --i) {
			BlockOutline targetBlock = method.getBlockChain().get(i);
			JDefinedClass iTargetBuilder = getTopLevelInterface(targetBlock);
			JDefinedClass cTargetBuilder = getTopLevelImplementation(targetBlock);

			JVar invocation = _method.body().decl(
				iTargetBuilder, "step" + (i + 1),
				JExpr._new(cTargetBuilder)
					.arg(helpers.get(i).invoke("get"))
					.arg(returnValue)
			);

			returnValue = invocation;
		}

		JExpression _retval = _method.body().decl(returnType, "retval", returnValue);

		// TODO optimization to avoid unecessary variable declaration
		// FLAPI-45
//		JExpression _retval = returnValue != JExpr._this()
//				? _method.body().decl(returnType, "retval", returnValue)
//				: returnValue;

		// invocation tracking
		if (method.minOccurrences > 0) {
			_method.body().directStatement("--ic_"+makeMethodKey(outline, method)+";");
		}

		// No need to transfer if it is to ourself!
		if (!method.isTerminal() && returnValue != JExpr._this()) {
			_method.body().invoke("_transferInvocations").arg(_retval);
		}

		// return call
		_method.body()._return(_retval);
	}

	private JVar addHelper(JDefinedClass iHelper, int id, JMethod _method) {
		JType wrappedType = ref(ObjectWrapper.class).narrow(iHelper);
		JVar _helper = _method.body().decl(wrappedType, "helper"+id,JExpr._new(wrappedType));
		return _helper;
	}
}
