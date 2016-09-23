package compressor;

import java.util.ArrayList;

/**
 * Filename:    CommandLineParser.java
 * Purpose:     Parse the command-line arguments given to the ArrayCompressor
 *              application.
 * 
 * @author      Andrew Blackwood
 * @version     1.0, 22/09/2016
 */
class CommandLineParser {
        
    public CommandLineParser() {}
    
    public ArrayList<String> parse(String[] args) {
        
        ArrayList<String> parsedArgs = new ArrayList<>();
        
        for (int i = 0; i < args.length; i++) {
            if (args[i].matches("-[cd]")){    
                
                if (args[i].equals("-c")) {
                    parsedArgs.add("compress");
                } 
                else if (args[i].equals("-d")) {
                    parsedArgs.add("decompress");
                }  
          
                if (args.length > i + 2) {
                    parsedArgs.add(args[i+1]);
                    parsedArgs.add(args[i+2]);
                    return parsedArgs;
                }
                else {
                    ErrorHandler error = new ErrorHandler();
                    error.printAndExit("Error: You need to provide two"
                            + " arguments after the -[c/d] argument: the source"
                            + " filepath and the destination filepath.",
                            true, 1);
                }
            }
        }
        
        ErrorHandler error = new ErrorHandler();
        error.printAndExit("Error: You need to enter a compress -c or"
                + " decompress -d argument, followed by the source filepath"
                + " and the destination filepath.", true, 1);
       
        return parsedArgs;
    }
}
