
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
public interface DescriptorBuilder_enableCondensedClassNames_setReturnType_setStartingMethodName<_ReturnType >{


    DescriptorBuilder_setReturnType_setStartingMethodName<_ReturnType> enableCondensedClassNames();

    MethodBuilder_addBlockChain<BlockBuilder<DescriptorBuilder_enableCondensedClassNames_setReturnType_setStartingMethodName<_ReturnType>>> startBlock(String blockName, String methodSignature);

    DescriptorBuilder_enableCondensedClassNames_setReturnType<_ReturnType> setStartingMethodName(String methodName);

    MethodBuilder_addBlockChain<DescriptorBuilder_enableCondensedClassNames_setReturnType_setStartingMethodName<_ReturnType>> addMethod(String methodSignature);

    Descriptor build();

    MethodBuilder_addBlockChain<DescriptorBuilder_enableCondensedClassNames_setReturnType_setStartingMethodName<_ReturnType>> addBlockReference(String blockName, String methodSignature);

    MethodBuilder_addBlockChain<BlockBuilder<DescriptorBuilder_enableCondensedClassNames_setReturnType_setStartingMethodName<_ReturnType>>> startBlock(String methodSignature);

    DescriptorBuilder_enableCondensedClassNames_setStartingMethodName<_ReturnType> setReturnType(Class returnType);

}
