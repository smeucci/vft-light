
import static tool.VFT.*;

import org.apache.commons.cli.*;

public class Menu {

	public static String input;
	public static String output;
	
	public static void main(String args[]) throws Exception {
		
		parse("/media/saverio/DATA/dataset-righini/videos/ipad2_giulia/outdoor/garden_move_1.MOV", "/home/saverio/");
		
		CommandLine cmd = parseArguments(args);
		if (cmd.hasOption("parse")) {
			input = cmd.getOptionValue("input");
			output = cmd.getOptionValue("output");
			parse(input, output);
		} else if (cmd.hasOption("draw")) {
			input = cmd.getOptionValue("input");
			draw(input, "Tree of: " + input + " - Press 0 to exit.");
		} else if (cmd.hasOption("batch")) {
			input = cmd.getOptionValue("input");
			output = cmd.getOptionValue("output");
			batch(input, output);
		}
	}
	
	private static CommandLine parseArguments(String[] args) {
		
		Options opts = new Options();
		
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("d", "draw", false, "draw a tree from an xml file"));
		group.addOption(new Option("p", "parse", false, "parse a video file container into a xml file"));
		group.addOption(new Option("b", "batch", false, "batch parse a directory of video files; it recreates the same folder structure"));
		group.addOption(new Option("h", "help", false, "print help message"));
		opts.addOptionGroup(group);
		
		Option input_opt = Option.builder("i")
				.longOpt("input")
				.argName("file|folder")
				.hasArg()
				.desc("video file for --parse, xml file for --draw, a folder for --batch")
				.build();
		opts.addOption(input_opt);
		
		Option output_opt = Option.builder("o")
				.longOpt("output")
				.argName("folder")
				.hasArg()
				.desc("output folder for the xml file, only for --parse")
				.build();
		opts.addOption(output_opt);
		
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
	    }
	    return cl;
	}

}