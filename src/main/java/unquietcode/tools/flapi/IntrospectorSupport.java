/*********************************************************************
 Copyright 2014 the Flapi authors

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ********************************************************************/

package unquietcode.tools.flapi;

import unquietcode.tools.flapi.annotations.BlockChain;
import unquietcode.tools.spring.generics.MethodParameter;
import unquietcode.tools.spring.generics.ResolvableType;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static com.google.common.base.Preconditions.checkState;

/**
 * @author Ben Fagin
 * @version 2014-09-07
 */
public class IntrospectorSupport {

	protected static <T extends Annotation> T getParameterAnnotation(Parameter parameter, Class<T> annotationClass) {
		for (Annotation annotation : parameter.getAnnotations()) {
			if (annotation.annotationType() == annotationClass) {
				@SuppressWarnings("unchecked") T annotation1 = (T) annotation;
				return annotation1;
			}
		}

		return null;
	}

	protected static <T extends Annotation> boolean hasParameterAnnotation(Parameter parameter, Class<T> annotationClass) {
		return getParameterAnnotation(parameter, annotationClass) != null;
	}

	protected static String getMethodSignature(Method method) {
		return getMethodSignature(method, null);
	}

	protected static String getMethodSignature(final Method method, final String normalizedName) {
		StringBuilder signature = new StringBuilder();

		String name = normalizedName != null ? normalizedName : method.getName();
		signature.append(name).append("(");

		boolean first = true;
		final Parameter[] parameters = method.getParameters();

		for (int i=0; i < parameters.length; ++i) {
			final Parameter parameter = parameters[i];
			final Class<?> parameterType = parameter.getType();

			// skip BlockChain parameters
			if (hasParameterAnnotation(parameter, BlockChain.class)) {
				continue;
			}

			if (!first) {
				signature.append(", ");
			} else {
				first = false;
			}

			final String typeName;

			// varargs
			if (parameter.isVarArgs()) {
				checkState(parameterType.isArray());
				typeName = parameterType.getComponentType().getName()+"...";
			}

			// arrays
			else if (parameterType.isArray()) {
				typeName = parameterType.getComponentType().getName()+"[]";
			}

			// normal
			else {
				typeName = parameterType.getName();
			}

			// type
			Class<?>[] generics = ResolvableType.forMethodParameter(MethodParameter.forMethodOrConstructor(method, i)).resolveGenerics();
			signature.append(makeTypeWithGenerics(typeName, generics));

			// parameter name (or argX by default)
			signature.append(" ").append(parameter.getName());
		}

		signature.append(")");
		return signature.toString();
	}

	protected static String makeTypeWithGenerics(Class<?> base, Class<?>[] generics) {
		return makeTypeWithGenerics(base.getName(), generics);
	}

	protected static String makeTypeWithGenerics(String base, Class<?>[] generics) {
		StringBuilder sb = new StringBuilder(base);

		if (generics != null && generics.length > 0) {
			sb.append("<");

			for (int p = 0; p < generics.length; ++p) {
				if (p != 0) { sb.append(", "); }
				Class<?> generic = generics[p];

				if (generic == null) {
					sb.append("?");
				} else {
					sb.append(generic.getName());
				}
			}

			sb.append("> ");
		}

		return sb.toString();
	}
}