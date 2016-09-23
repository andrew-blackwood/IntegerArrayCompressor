package compressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Filename:    ArrayFileParser.java
 * Purpose:     Parse a file containing an array of integers.
 * 
 * @author      Andrew Blackwood
 * @version     1.0, 22/09/2016
 */
public class ArrayFileParser {
        
    public ArrayFileParser() {}
    
    public int[] parse(File file) {
        
        ArrayList<Integer> integerList = new ArrayList<>();
        
        try {
            Scanner fileReader = new Scanner(file);
            fileReader.useDelimiter("[\\s,]++");
            while (fileReader.hasNext()){
                try {
                    integerList.add(fileReader.nextInt());
                }
                catch (Exception e) {
                    ErrorHandler error = new ErrorHandler();
                    error.printAndExit("Error: File has an integer that cannot"
                            + " be read.", true, 1);
                }
            }
        }
        catch (FileNotFoundException e) {
            ErrorHandler error = new ErrorHandler();
            error.printAndExit("Error: Source file not found.", true, 1);
        } 
        
        int[] array = new int[integerList.size()];
        
        for (int i = 0; i < integerList.size(); i++) {
            array[i] = integerList.get(i);
        }
        
        return array;
    }   
}
