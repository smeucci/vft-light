package util;

import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Util {

	public static String removeBrackets(String str, String bracket) {
		//remove square brackets from the input string
		return str.replaceAll(bracket, "");
	}
	
	public static boolean contains(String text, String token) {
		//check if text contains token
		text = text == null ? "" : text;
		token = token == null ? "" : token;
		return text.toLowerCase().contains(token.toLowerCase());
	}
	
	public static String sanitize(String str) {
		str = str.replaceAll("|©|", "");
		return str.trim();
	}
	
	public static String[] append(String[] arr, String[] elements) {
		if (arr != null) {
			final int N = arr.length;
			final int E = elements.length;
			arr = Arrays.copyOf(arr, N + E);
			for (int i = 0; i < E; i++) {
				arr[N + i] = elements[i];
			}
			return arr;
		} else {
			String[] new_arr = {""};
			final int N = new_arr.length;
			final int E = elements.length;
			new_arr = Arrays.copyOf(new_arr, E);
			for (int i = 0; i < E; i++) {
				new_arr[N - 1 + i] = elements[i];
			}
			return new_arr;
		}
	}
	
	public static int countOccurrences(String text, String token) {
		//return how many times the token is in text
        int n = 0;
        for (int i = 0; i <= text.length() - token.length(); i++) {
            String str = text.substring(i, i + token.length());
            if (str.equals(token)) {
                n++;
            }
        }
        return n;
    }
	
	public static CommandLine parseArguments(String[] args) {
		
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
	
	public static HelpFormatter buildHelpFormatter() {
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