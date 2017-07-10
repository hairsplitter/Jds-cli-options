package de.dimitroff.lib.clioptions;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * @author Janko Dimitroff
 */
public class OptionsTest {

	@Rule
	public ExpectedException thrownException = ExpectedException.none();

	@Test
	public void testOptionsBuilder() {
		Options.Builder builder = new Options.Builder();
		assertNotNull(builder);
	}

	@Test
	public void testOptionsNoArgsNoParams() {
		Options.Builder builder = new Options.Builder();

		// noargs given, norargs expected
		Options options = builder.build(null);
		assertNotNull(options);
	}

	@Test
	public void testOptionsNoArgsWithParams() {
		Options.Builder builder = new Options.Builder();
		// empty options, ok
		// noargs given, but 1 expected, -> illegal argumentaexception when building options.
		String paramName = "output";
		builder.addParameter(paramName);

		thrownException.expect(IllegalArgumentException.class);
		Options options = builder.build(null);
		assertNull(options);
	}

	@Test
	public void testOptionsOneParamWithAndWithoutMinus() {
		Options.Builder builder = new Options.Builder();

		// mit minus vor dem Parameternamen
		String paramName = "output";
		String paramValue = "hello.html";
		String[] cliArguments = new String[]{"-" + paramName, paramValue};
		builder.addParameter(paramName);
		Options options = builder.build(cliArguments);
		assertNotNull(options);
		assertNotNull(options.get(paramName));
		assertEquals(paramValue, options.get(paramName));

		// ohne minus vorm parameternamen
		builder = new Options.Builder();
		cliArguments = new String[]{paramName, paramValue};
		builder.addParameter(paramName);
		options = builder.build(cliArguments);
		assertNotNull(options);
		assertNotNull(options.get(paramName));
		assertEquals(paramValue, options.get(paramName));
	}

	@Test
	public void testOptionsNormal() {
		Options.Builder builder = new Options.Builder();

		// mit minus vor dem Parameternamen
		String paramName1 = "output";
		String paramValue1 = "hello.html";
		String paramName2 = "input";
		String paramValue2 = "test*";
		String[] cliArguments = new String[]{"-" + paramName1, paramValue1, paramName2, paramValue2};
		builder.addParameter(paramName1);
		builder.addParameter(paramName2);
		Options options = builder.build(cliArguments);
		assertNotNull(options);
		assertNotNull(options.get(paramName1));
		assertNotNull(options.get(paramName2));
		assertEquals(paramValue1, options.get(paramName1));
		assertEquals(paramValue2, options.get(paramName2));

		// ohne minus vorm parameternamen
		builder = new Options.Builder();
		cliArguments = new String[]{paramName1, paramValue1, paramName2, paramValue2};
		builder.addParameter(paramName1);
		builder.addParameter(paramName2);
		options = builder.build(cliArguments);
		assertNotNull(options);
		assertNotNull(options.get(paramName1));
		assertNotNull(options.get(paramName2));
		assertEquals(paramValue1, options.get(paramName1));
		assertEquals(paramValue2, options.get(paramName2));

	}

	@Test
	public void testOptionsNormalSameParameterTwice() {
		Options.Builder builder = new Options.Builder();

		// mit minus vor dem Parameternamen
		String paramName1 = "output";
		String paramValue1 = "hello.html";
		String paramValue1B = "goodbye.html";
		String paramName2 = "input";
		String paramValue2 = "test*";

		// somecommand -A a B b A c 
		// ->  A:c, B:b
		String[] cliArguments = new String[]{"-" + paramName1, paramValue1, paramName2, paramValue2, paramName1,
			paramValue1B};
		builder.addParameter(paramName1);
		builder.addParameter(paramName2);
		Options options = builder.build(cliArguments);
		assertNotNull(options);
		assertNotNull(options.get(paramName1));
		assertNotNull(options.get(paramName2));
		assertEquals(paramValue1B, options.get(paramName1));
		assertEquals(paramValue2, options.get(paramName2));
	}

}
