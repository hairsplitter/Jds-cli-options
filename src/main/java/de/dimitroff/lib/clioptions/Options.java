package de.dimitroff.lib.clioptions;

import java.util.HashMap;
import java.util.Map;

/**
 * Class to analyze commandline parameters.
 *
 * Howto:
 *
 * <ol>
 * <li>
 * create a builder
 * </li>
 * <li>
 * add expected parameternames to it
 * </li>
 * <li>
 * build the Options object with the builders build() method, giving it the commandlineoptionsstring
 * </li>
 * <li>
 * access the Options object for paramters
 * </li>
 * </ol>
 *
 * Example:
 *
 * <pre>Options options = new Options.Builder()
 * .addParameter("output")
 * .addParameter("input")
 * .build(args);
 * </pre>
 *
 * <pre>
 * System.out.println("output is: " + options.get("output"));
 * System.out.println("input is: " + options.get("input"));
 * </pre>
 *
 * @author Janko Dimitroff
 */
public class Options extends HashMap {

	public static class Builder {

		HashMap<String, Class> paramMap = new HashMap();

		public Builder addParameter(String paramName) {
			paramMap.put(paramName, String.class);
			return this;
		}

		/**
		 * builds the Options object, that holds the
		 * @param args the commandline arguments, that the main() method get as input.
		 * @return the Options
		 * @throws IllegalArgumentException if expected parameters were not given in commandline
		 */
		public Options build(String[] args) throws IllegalArgumentException {
			Options options = new Options();
			try {
				for (Map.Entry<String, Class> entry : paramMap.entrySet()) {
					for (int i = 0; i < args.length - 1; i++) {
						String arg = args[i].trim();
						if (entry.getKey().equalsIgnoreCase(arg)
							|| ("-" + entry.getKey()).equalsIgnoreCase(arg)) {
							i++;
							options.put(entry.getKey(), args[i]);
						}
					}
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}
			return options;
		}
	}

}
