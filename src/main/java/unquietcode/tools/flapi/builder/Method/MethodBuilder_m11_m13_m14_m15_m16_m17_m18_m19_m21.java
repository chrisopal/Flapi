
package unquietcode.tools.flapi.builder.Method;

import javax.annotation.Generated;
import unquietcode.tools.flapi.builder.Documentation.DocumentationBuilder_m25_m26;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit https://github.com/UnquietCode/Flapi for more information.
 * 
 * 
 * Generated on January 30, 2013 1:01:45 CST using version 0.3
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "January 30, 2013 1:01:45 CST", comments = "generated using Flapi, the fluent API generator for Java")
public interface MethodBuilder_m11_m13_m14_m15_m16_m17_m18_m19_m21 <_ReturnType >{


    MethodBuilder_m11_m13_m14_m15_m16_m17_m18_m19_m21 <_ReturnType> addAlias(String methodSignature);

    _ReturnType any();

    _ReturnType atLeast(int num);

    _ReturnType atMost(int num);

    _ReturnType between(int atLeast, int atMost);

    _ReturnType exactly(int num);

    _ReturnType last();

    _ReturnType last(Class returnType);

    /**
     * Add javadoc style documentation to the method.
     * 
     */
    DocumentationBuilder_m25_m26 <MethodBuilder_m11_m13_m14_m15_m16_m17_m18_m19 <_ReturnType>> withDocumentation();

}