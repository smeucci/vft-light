
import static tool.VFT.*;

import java.util.Comparator;

import org.apache.commons.cli.*;

public class Menu {

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
	
	private static CommandLine parseArguments(String[] args) {
		
		Options opts = new Options();
		
		OptionGroup group = new OptionGroup();
		group.addOption(new Option("d", "draw", false, "draw a tree from an xml file."));
		group.addOption(new Option("p", "parse", false, "parse a video file container into a xml file."));
		group.addOption(new Option("h", "help", false, "print help message."));
		opts.addOptionGroup(group);
		
		Option input_opt = Option.builder("i")
				.longOpt("input")
				.argName("file")
				.hasArg()
				.desc("video file for --parse, xml file for --draw.")
				.build();
		opts.addOption(input_opt);
		
		Option output_opt = Option.builder("o")
				.longOpt("output")
				.argName("folder")
				.hasArg()
				.desc("output folder for the xml file, only for --parse.")
				.build();
		opts.addOption(output_opt);
		
		CommandLine cl = null;
		HelpFormatter formatter = buildHelpFormatter();
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
	    }
	    if (cl.getOptions().length == 0) {
	    	System.err.println("No options given.");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    }
	    if (cl.hasOption("d") && !cl.hasOption("i")) {
	    	System.err.println("Missing option: i.");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    }
	    if (cl.hasOption("p") && !cl.hasOption("i") | !cl.hasOption("o")) {
	    	System.err.println("Missing options: [i | o].");
	    	formatter.printHelp("vft", opts, true);
	        System.exit(0);
	    }
	    return cl;
	}
	
	private static HelpFormatter buildHelpFormatter() {
		HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(new Comparator<Option>() {
			
			@Override
			public int compare(Option o1, Option o2) {
				if (o1.hasArg() == false) {
					return o1.getId();
				}
				return o1.getLongOpt().compareToIgnoreCase(o2.getLongOpt());
			}
			
		});
		return formatter;
	}

}