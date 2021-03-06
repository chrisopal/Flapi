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

import com.sun.codemodel.JCodeModel;
import unquietcode.tools.flapi.generator.DescriptorGenerator;
import unquietcode.tools.flapi.outline.DescriptorOutline;

import java.io.File;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Map;

/**
 * @author Ben Fagin
 * @version 03-04-2012
 */
public class Descriptor {
	private final JCodeModel model;


	public Descriptor(DescriptorOutline outline) {
		DescriptorGenerator generator = new DescriptorGenerator(outline);
		model = generator.generate();
	}

	/**
	 * Write the generated classes to a stream. The order is generally alphabetic
	 * but is not guaranteed. Several lines of separator are written out between
	 * each file.
	 *
	 * @see com.sun.codemodel.writer.SingleStreamCodeWriter
	 * @param stream to write generated files
	 * @return this descriptor, for chaining
	 */
	public Descriptor writeToStream(OutputStream stream) {
		CodeWriter.writeToStream(model, stream);
		return this;
	}

	/**
	 * Write the generated classes out to the specified folder.
	 * Classes in other packages will be written to folders relative
	 * to this one.
	 *
	 * Existing files will be overwritten!
	 *
	 * @see com.sun.codemodel.writer.FileCodeWriter
	 * @param folder where files are to be written
	 * @return this descriptor, for chaining
	 */
	public Descriptor writeToFolder(String folder) {
		File f = new File(folder);
		if (!f.exists()) {
			throw new DescriptorBuilderException("Folder '"+folder+"' does not exist.");
		}

		if (!f.isDirectory()) {
			throw new DescriptorBuilderException("Folder '"+folder+"' is not a folder!");
		}

		if (!f.canWrite()) {
			throw new DescriptorBuilderException("Cannot write to folder '"+folder+"'.");
		}

		// write out the support classes, if instructed
		if (Flapi.shouldOutputRuntime()) {
			try {
				ExtractRuntime.main(new String[]{folder});
			} catch (Exception ex) {
				throw new DescriptorBuilderException(ex);
			}
		}

		// always do this last so we don't unnecessarily write files
		CodeWriter.writeToDirectory(model, f);

		return this;
	}

	/**
	 * Writes individual files to individual streams.
	 *
	 * @param streams stream iterator, should always return another!
	 * @return this descriptor, for chaining
	 */
	public Map<String, OutputStream> writeToStreams(Iterator<OutputStream> streams) {
		if (streams == null) {
			throw new IllegalArgumentException("streams should not be null");
		}
		return CodeWriter.writeToStreams(model, streams);
	}
}
