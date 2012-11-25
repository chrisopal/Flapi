
package unquietcode.tools.flapi.builder;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.support.v0_2.BuilderImplementation;
import unquietcode.tools.flapi.support.v0_2.ExpectedInvocationsException;
import unquietcode.tools.flapi.support.v0_2.ObjectWrapper;

import javax.annotation.Generated;
import java.lang.reflect.Field;


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
public class ImplDescriptorBuilder_setDescriptorName_setPackage_setReturnType_setStartingMethodName
    implements DescriptorBuilder_setDescriptorName_setPackage_setReturnType_setStartingMethodName, BuilderImplementation
{

    private final DescriptorHelper _helper;
    private final Object _returnValue;
    int ic_Descriptor_setDescriptorName$String_descriptorName = 1;
    int ic_Descriptor_setPackage$String_packageName = 1;

    ImplDescriptorBuilder_setDescriptorName_setPackage_setReturnType_setStartingMethodName(DescriptorHelper helper, Object returnValue) {
        _helper = helper;
        _returnValue = returnValue;
    }

    public BuilderImplementation _getParent() {
        if (_returnValue instanceof BuilderImplementation) {
            return ((BuilderImplementation) _returnValue);
        } else {
            return null;
        }
    }

    private void _transferInvocations(Object next) {
        Class clazz = next.getClass();
         
        try {
            Field field = clazz.getDeclaredField("ic_Descriptor_setDescriptorName$String_descriptorName");
            field.setInt(next, ic_Descriptor_setDescriptorName$String_descriptorName);
        } catch (Exception _x) {
            // nothing
        }
         
        try {
            Field field = clazz.getDeclaredField("ic_Descriptor_setPackage$String_packageName");
            field.setInt(next, ic_Descriptor_setPackage$String_packageName);
        } catch (Exception _x) {
            // nothing
        }
    }

    public void _checkInvocations() {
        if (ic_Descriptor_setDescriptorName$String_descriptorName > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'setDescriptorName(String descriptorName)'.");
        }
        if (ic_Descriptor_setPackage$String_packageName > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'setPackage(String packageName)'.");
        }
    }

    public Descriptor build() {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        Descriptor intermediateResult = _helper.build();
         
        return intermediateResult;
    }

    public DescriptorBuilder_setPackage_setReturnType_setStartingMethodName setDescriptorName(String descriptorName) {
        --ic_Descriptor_setDescriptorName$String_descriptorName;
        _helper.setDescriptorName(descriptorName);
        ImplDescriptorBuilder_setPackage_setReturnType_setStartingMethodName step1 = new ImplDescriptorBuilder_setPackage_setReturnType_setStartingMethodName(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public MethodBuilder_addBlockChain startBlock(String blockName, String methodSignature) {
        ObjectWrapper<MethodHelper> helper1 = new ObjectWrapper<MethodHelper>();
        ObjectWrapper<BlockHelper> helper2 = new ObjectWrapper<BlockHelper>();
        _helper.startBlock(blockName, methodSignature, helper1, helper2);
        ImplBlockBuilder step1 = new ImplBlockBuilder(helper2 .get(), this);
        ImplMethodBuilder_addBlockChain step2 = new ImplMethodBuilder_addBlockChain(helper1 .get(), step1);
         
        return step2;
    }

    public DescriptorBuilder_setDescriptorName_setPackage_setStartingMethodName setReturnType(Class returnType) {
        _helper.setReturnType(returnType);
        ImplDescriptorBuilder_setDescriptorName_setPackage_setStartingMethodName step1 = new ImplDescriptorBuilder_setDescriptorName_setPackage_setStartingMethodName(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public MethodBuilder_addBlockChain startBlock(String methodSignature) {
        ObjectWrapper<MethodHelper> helper1 = new ObjectWrapper<MethodHelper>();
        ObjectWrapper<BlockHelper> helper2 = new ObjectWrapper<BlockHelper>();
        _helper.startBlock(methodSignature, helper1, helper2);
        ImplBlockBuilder step1 = new ImplBlockBuilder(helper2 .get(), this);
        ImplMethodBuilder_addBlockChain step2 = new ImplMethodBuilder_addBlockChain(helper1 .get(), step1);
         
        return step2;
    }

    public MethodBuilder_addBlockChain addBlockReference(String blockName, String methodSignature) {
        ObjectWrapper<MethodHelper> helper1 = new ObjectWrapper<MethodHelper>();
        _helper.addBlockReference(blockName, methodSignature, helper1);
        ImplMethodBuilder_addBlockChain step1 = new ImplMethodBuilder_addBlockChain(helper1 .get(), this);
         
        return step1;
    }

    public MethodBuilder_addBlockChain addMethod(String methodSignature) {
        ObjectWrapper<MethodHelper> helper1 = new ObjectWrapper<MethodHelper>();
        _helper.addMethod(methodSignature, helper1);
        ImplMethodBuilder_addBlockChain step1 = new ImplMethodBuilder_addBlockChain(helper1 .get(), this);
         
        return step1;
    }

    public DescriptorBuilder_setDescriptorName_setReturnType_setStartingMethodName setPackage(String packageName) {
        --ic_Descriptor_setPackage$String_packageName;
        _helper.setPackage(packageName);
        ImplDescriptorBuilder_setDescriptorName_setReturnType_setStartingMethodName step1 = new ImplDescriptorBuilder_setDescriptorName_setReturnType_setStartingMethodName(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public DescriptorBuilder_setDescriptorName_setPackage_setReturnType setStartingMethodName(String methodName) {
        _helper.setStartingMethodName(methodName);
        ImplDescriptorBuilder_setDescriptorName_setPackage_setReturnType step1 = new ImplDescriptorBuilder_setDescriptorName_setPackage_setReturnType(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

}
