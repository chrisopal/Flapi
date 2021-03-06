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

package unquietcode.tools.flapi.graph.components;

import unquietcode.tools.flapi.graph.TransitionVisitor;
import unquietcode.tools.flapi.java.JavaType;
import unquietcode.tools.flapi.runtime.TransitionType;

/**
 * @author Ben Fagin
 * @version 08-15-2012
 */
public class TerminalTransition extends Transition {
	private JavaType returnType;

	public TerminalTransition() {
		super(TransitionType.Terminal);
	}

	public TerminalTransition(JavaType returnType) {
		this();
		this.returnType = returnType;
	}

	public JavaType getReturnType() {
		return returnType;
	}

	public void setReturnType(JavaType returnType) {
		this.returnType = returnType;
	}

	@Override
	public void accept(TransitionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Transition basicCopy() {
		TerminalTransition copy = new TerminalTransition();
		copy(copy);
		copy.returnType = this.returnType;

		return copy;
	}
}
