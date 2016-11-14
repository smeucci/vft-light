package com.vftlite.main;

import static com.vftlite.core.VFT.*;

import org.apache.commons.cli.*;

/**
 * <h1>VFT cli app</h1>
 * <p>The Menu class is the main entry point the app.</p>
 * @author saverio
 *
 */
public class Menu {
		
	/**
	 * Main functions of the app. It executes the selected functions given arguments as
	 * input.
	 * @param args Input arguments.
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {		
				
		CommandLine cmd = parseArguments(args);
		if (cmd.hasOption("parse")) {
			String input = cmd.getOptionValue("input");
			String output = cmd.getOptionValue("output");
			parse(input, output);
		} else if (cmd.hasOption("draw")) {
			String input = cmd.getOptionValue("input");
			draw(input, "Tree of: " + input + " - Press 0 to exit.");
		} else if (cmd.hasOption("batch")) {
			String input = cmd.getOptionValue("input");
			String output = cmd.getOptionValue("output");
			batch(input, output);
		} else if (cmd.hasOption("merge")) {
			String input1 = cmd.getOptionValue("input");			
			String input2 = cmd.getOptionValue("input2");
			String output = cmd.getOptionValue("output");
			if (cmd.hasOption("withAttributes")) {
				merge(input1, input2, output, true);
			} else {
				merge(input1, input2, output, false);
			}
		} else if (cmd.hasOption("update-config")) {
			String input = cmd.getOptionValue("input");
			String output = cmd.getOptionValue("output");
			if (cmd.hasOption("with-attributes")) {
				update(input, output, true);
			} else {
				update(input, output, false);
			}
		}
	}
	
	/**
	 * This method is used to parse the input arguments.
	 * @param args The imput arguments.
	 * @return A CommandLine object that contains the parsed input arguments.
	 */
	private static CommandLine parseArguments(String[] args) {
		
		Options opts = new Options();
		
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("d", "draw", false, "draw a tree from an xml file"));
		group.addOption(new Option("p", "parse", false, "parse a video file container into a xml file"));
		group.addOption(new Option("b", "batch", false, "batch parse a directory of video files; it recreates the same folder structure"));
		group.addOption(new Option("m", "merge", false, "merge two xml file into one"));
		group.addOption(new Option("u", "update-config", false, "merge all files in the dataset folder into a config.xml file"));
		group.addOption(new Option("h", "help", false, "print help message"));
		opts.addOptionGroup(group);
		
		Option input_opt = Option.builder("i")
				.longOpt("input")
				.argName("file|folder")
				.hasArg()
				.desc("video file for --parse, xml file for --draw, a folder for --batch and --update-config")
				.build();
		opts.addOption(input_opt);
		
		Option input_opt2 = Option.builder("i2")
				.longOpt("input2")
				.argName("file")
				.hasArg()
				.desc("second xml file for --merge")
				.build();
		opts.addOption(input_opt2);
		
		Option output_opt = Option.builder("o")
				.longOpt("output")
				.argName("folder")
				.hasArg()
				.desc("output folder for the xml file,for --parse, --merge and --update-config")
				.build();
		opts.addOption(output_opt);
		
		Option withAttributes_opt = Option.builder("wa")
				.longOpt("with-attributes")
				.desc("whether to consider attributes in --merge and --update-config or not. Default is false")
				.build();
		opts.addOption(withAttributes_opt);
		
		CommandLine cl = null;
		HelpFormatter formatter = new HelpFormatter();
	    try {
	        cl = new DefaultParser().parse(opts, args);
	    } catch (ParseException e) {
	        System.err.println(e.getMessage());
	        formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    }
	    if (cl.hasOption("h")) {
	        formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.getOptions().length == 0) {
	    	System.err.println("No options given");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.hasOption("d") && !cl.hasOption("i")) {
	    	System.err.println("Missing option: i");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.hasOption("parse") && (!cl.hasOption("i") || !cl.hasOption("o"))) {
	    	System.err.println("Missing options: [i | o]");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.hasOption("b") && (!cl.hasOption("i") || !cl.hasOption("o"))) {
	    	System.err.println("Missing options: [i | o]");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.hasOption("m") && (!cl.hasOption("i") ||!cl.hasOption("i2"))) {
	    	System.err.println("Missing options: [i | i2]");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    } else if (cl.hasOption("u") && (!cl.hasOption("i") || !cl.hasOption("o"))) {
	    	System.err.println("Missing options: [i | o]");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    }
	    return cl;
	}

}