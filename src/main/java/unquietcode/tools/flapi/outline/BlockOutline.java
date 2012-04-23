package unquietcode.tools.flapi.outline;

import unquietcode.tools.flapi.BlockReference;
import unquietcode.tools.flapi.generator.AbstractGenerator;

import java.util.*;

/**
 * @author Ben Fagin
 * @version 03-09-2012
 */
public class BlockOutline implements Outline {
	// name of the block
	public String name;

	// nested blocks
	public List<BlockOutline> blocks = new ArrayList<BlockOutline>();
	
	// constructor, used by parent to create this block
	public MethodOutline constructor;
	
	// block references
	public final List<BlockReference> blockReferences = new ArrayList<BlockReference>();

	// methods
	public final Set<MethodOutline> methods = new HashSet<MethodOutline>();

	public String getTopLevelImplementation() {
		return "Impl"+ AbstractGenerator.getGeneratedName(name + "Builder", getDynamicMethods());
	}

	public String getTopLevelInterface() {
		return AbstractGenerator.getGeneratedName(name + "Builder", getDynamicMethods());
	}
	
	public String getBaseImplementation() {
		return "Impl"+name+"Builder";
	}
	
	public String getBaseInterface() {
		return name+"Builder";
	}
	
	public String getHelperInterface() {
		return name+"Helper";
	}
	
	public String getGeneratorImplmentation() {
		return name+"Generator";
	}
	

	public Set<MethodOutline> getRequiredMethods() {
		Set<MethodOutline> required = new TreeSet<MethodOutline>();
		
		for (MethodOutline method : methods) {
			if (method.isRequired()) {
				required.add(method);
			}
		}
		
		return required;
	}

	public Set<MethodOutline> getDynamicMethods() {
		Set<MethodOutline> dynamic = new TreeSet<MethodOutline>();

		for (MethodOutline method : methods) {
			if (!method.isRequired()) {
				dynamic.add(method);
			}
		}

		return dynamic;
	}
}