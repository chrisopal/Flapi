/*********************************************************************
 Flapi, the fluent API builder for Java.
 Visit the project page at https://github.com/UnquietCode/Flapi

 Flapi is free and open software provided without a license.
 ********************************************************************/

package unquietcode.tools.flapi.examples.calculator;

import org.junit.Test;
import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;
import unquietcode.tools.flapi.Supplier;
import unquietcode.tools.flapi.examples.calculator.builder.Calculation.CalculationBuilder;
import unquietcode.tools.flapi.examples.calculator.builder.Calculator.CalculatorBuilder;
import unquietcode.tools.flapi.examples.calculator.builder.Calculator.CalculatorFactory;
import unquietcode.tools.flapi.examples.calculator.builder.Calculator.CalculatorGenerator;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.assertEquals;


/**
 * @author Ben Fagin
 * @version 05-11-2012
 */
public class CalculatorBuilderExample implements DescriptorMaker {

	@Override
	public Descriptor descriptor() {
		return Flapi.create(Calculator.class)
			.setPackage("unquietcode.tools.flapi.examples.calculator.builder")
			.setStartingMethodName("begin")
		.build();
	}

	@Test
	public void basicUsage() {
		Result result = CalculatorGenerator.begin(new CalculatorHelperImpl())
			.$(0)
			.plus(1)
			.plus(1)
			.power(5)
			.divide(2)
		.equals();

		assertEquals(16, result.get().intValue());
	}

	// ------- - - ------- -------- -  --     -- - -   ---- - -   -   -----------

	public static class Result extends AtomicReference<BigInteger> { }

	static CalculationBuilder.Start<Void> begin(int startingValue) {
		CalculatorBuilder.Start<Void> begin = CalculatorGenerator.begin(new CalculatorHelperImpl());
		CalculationBuilder.Start<Void> start = begin.$(startingValue);

		return start;
	}

	@Test
	public void cleanedUpUsage() {
		Result result = begin(0)
			.plus(1)
			.plus(1)
			.power(5)
			.divide(2)
		.equals();

		assertEquals(16, result.get().intValue());
	}

	@Test
	public void factoryUsage() {
		CalculatorFactory factory = CalculatorGenerator.factory(new Supplier<Calculator>() {
			public @Override Calculator get() {
				return new CalculatorHelperImpl();
			}
		});

		Result result = factory.begin()
			.$(0)
			.plus(10)
			.minus(5)
		.equals();

		assertEquals(5, result.get().intValue());
	}
}
