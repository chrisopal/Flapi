
package unquietcode.tools.flapi.builder;

import unquietcode.tools.flapi.Descriptor;

import javax.annotation.Generated;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit http://www.unquietcode.com/flapi for more information.
 * 
 * 
 * Generated on November 24, 2012 14:26:52 CST using version 0.3
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "November 24, 2012 14:26:52 CST", comments = "generated using Flapi, the fluent API generator for Java")
public interface DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage_setReturnType<_ReturnType >{


    DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage<_ReturnType> setReturnType(Class returnType);

    DescriptorBuilder_setDescriptorName_setPackage_setReturnType<_ReturnType> enableCondensedClassNames();

    DescriptorBuilder_enableCondensedClassNames_setPackage_setReturnType<_ReturnType> setDescriptorName(String descriptorName);

    MethodBuilder_addBlockChain<BlockBuilder<DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage_setReturnType<_ReturnType>>> startBlock(String blockName, String methodSignature);

    Descriptor build();

    MethodBuilder_addBlockChain<DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage_setReturnType<_ReturnType>> addBlockReference(String blockName, String methodSignature);

    MethodBuilder_addBlockChain<BlockBuilder<DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage_setReturnType<_ReturnType>>> startBlock(String methodSignature);

    MethodBuilder_addBlockChain<DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setPackage_setReturnType<_ReturnType>> addMethod(String methodSignature);

    DescriptorBuilder_enableCondensedClassNames_setDescriptorName_setReturnType<_ReturnType> setPackage(String packageName);

}
