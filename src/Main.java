
import static tool.VFT.*;
import static util.Util.*;

import org.apache.commons.cli.*;

public class Main {

	public static String parse;
	public static String draw;
	public static String input;
	public static String output;
	
	public static void main(String args[]) throws Exception {
		
		CommandLine cmd = parseArguments(args);
		if (cmd.hasOption("parse")) {
			input = cmd.getOptionValue("input");
			output = cmd.getOptionValue("output");
			parse(input, output);
		} else if (cmd.hasOption("draw")) {
			input = cmd.getOptionValue("input");
			draw(input, null, "Tree of: " + input + " - Press 0 to exit.");
		}
	}

}