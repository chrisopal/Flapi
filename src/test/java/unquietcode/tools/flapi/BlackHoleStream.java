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

package unquietcode.tools.flapi;

import java.io.IOException;
import java.io.OutputStream;

public final class BlackHoleStream extends OutputStream {
	public static final BlackHoleStream $ = new BlackHoleStream();

	@Override
	public final void write(int b) throws IOException {
		// nothing
	}
}
