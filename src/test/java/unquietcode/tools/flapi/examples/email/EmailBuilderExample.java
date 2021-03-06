/*********************************************************************
 Copyright 2015 the Flapi authors

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

package unquietcode.tools.flapi.examples.email;

import org.junit.Test;
import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;
import unquietcode.tools.flapi.examples.email.builder.Email.EmailGenerator;

/**
 * @author Benjamin Fagin
 * @version 04-27-2012
 *
 * A test which builds an 'email builder' descriptor.
 */
public class EmailBuilderExample implements DescriptorMaker {

	@Override
	public Descriptor descriptor() {
		return Flapi.builder()
			.setPackage("unquietcode.tools.flapi.examples.email.builder")
			.setStartingMethodName("compose")
			.setDescriptorName("Email")

			.addMethod("subject(String subject)").atMost(1)
			.addMethod("addRecipient(String emailAddress)").atLeast(1)
			.addMethod("sender(String emailAddress)").exactly(1)
			.addMethod("addCC(String emailAddress)").any()
			.addMethod("addBCC(String emailAddress)").any()
			.addMethod("body(String text)").atMost(1)
			.addMethod("addAttachment(java.io.File file)").any()
			.addMethod("send()").last(EmailMessage.class)
		.build();
	}

//	@Test
//	public void annotated() {
//		Flapi.create(AnnotatedEmailHelper.class)
//			.setPackage("unquietcode.tools.flapi.examples.email.builder")
//			.setStartingMethodName("compose")
//		.build();
//	}

	@Test
	public void usage() {
		EmailMessage message = EmailGenerator.compose(new EmailHelperImpl())
			.sender("iamthewalrus@hotmail.com")
			.addRecipient("unclebob@unquietcode.com")
			.subject("Has you seen my bucket?")
			.body("Dear sir,\nI was wondering, have you seen my bucket? It is small, metallic, somewhat used, " +
				  "and slightly smells of fish. Please let me know if you have seen, or ever do see it.\n\nThanks!")
		.send();
	}
}