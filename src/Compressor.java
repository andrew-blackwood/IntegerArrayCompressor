package compressor;

import java.io.File;
import java.util.ArrayList;

/**
 * Filename:    ArrayCompressor.java
 * Purpose:     Implements an application that compresses or decompresses
 *              a file containing an array of integers.
 * 
 * @author      Andrew Blackwood
 * @version     1.0, 22/09/2016
 */
public class Compressor {

    public static void main(String[] args) {
        
        ArrayList<String> parsedArgs;
        
        CommandLineParser commandLineParser = new CommandLineParser();
        parsedArgs = commandLineParser.parse(args);
        
        boolean compress = "compress".equals(parsedArgs.get(0));
        String sourceFilePath = parsedArgs.get(1);
        String destFilePath = parsedArgs.get(2);
        
        File sourceFile = new File(sourceFilePath);
        
        boolean toZipFile = destFilePath.substring(destFilePath.length() - 4, 
                            destFilePath.length()).toLowerCase().equals(".zip");
        
        boolean fromZipFile = sourceFilePath.substring(
                          sourceFilePath.length() - 4, 
                          sourceFilePath.length()).toLowerCase().equals(".zip");
          
        if (compress) {
            if (toZipFile) {
                Zipper zipper = new Zipper();
                zipper.zip(sourceFile, destFilePath);
            }
            else {
                ArrayFileParser sourceArrayFile = new ArrayFileParser();
                int[] sourceArray = sourceArrayFile.parse(sourceFile);
                
                IntegerArrayCompressor integerCompressor 
                        = new IntegerArrayCompressor();
                integerCompressor.compress(sourceArray, destFilePath);
            }
        } 
        else {
            if (fromZipFile) {
                Zipper zipper = new Zipper();
                zipper.unzip(sourceFilePath, destFilePath);
            }
            else {
                ArrayFileParser sourceArrayFile = new ArrayFileParser();
                int[] sourceArray = sourceArrayFile.parse(sourceFile);
                
                IntegerArrayCompressor integerCompressor 
                        = new IntegerArrayCompressor();
                integerCompressor.decompress(sourceArray, destFilePath);
            }
        }
        
        System.exit(0);
    }     
}
