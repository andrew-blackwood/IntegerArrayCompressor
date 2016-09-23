package compressor;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;
import me.lemire.integercompression.Composition;
import me.lemire.integercompression.FastPFOR;
import me.lemire.integercompression.IntWrapper;
import me.lemire.integercompression.IntegerCODEC;
import me.lemire.integercompression.VariableByte;
import me.lemire.integercompression.differential.IntegratedIntCompressor;

/**
 * Filename:    IntegerArrayCompressor.java
 * Purpose:     Compress or decompress an array of integers and write to a file.
 * 
 * @author      Andrew Blackwood
 * @version     1.0, 22/09/2016
 */
class IntegerArrayCompressor {
    
    public IntegerArrayCompressor() {}
    
    private boolean checkSort() {
        
        Scanner reader = new Scanner(System.in);

        while (true) {      
            
            System.out.println("Allow sorting of integers for"
                    + " smaller compression?");
            System.out.print("Enter 'Y' for yes or 'N' for no: ");
            
            String userSorting = reader.next().toLowerCase();
            
            switch (userSorting) {
                case "y": 
                    return true;
                case "n":
                    return false;
                default:
                    System.out.println("Error: Not a correct response.");
            }
        }
    }
    
    public void printToFile(int[] destArray, String destFilePath) {
        
        File destFile = new File(destFilePath);

        try {
            PrintWriter destPrintWriter = new PrintWriter(destFile);

            for (int i=0; i<destArray.length; i++) {
                destPrintWriter.print(destArray[i]);
                if (i != destArray.length - 1) {
                    destPrintWriter.print(",");
                }
            }

            destPrintWriter.flush();
            destPrintWriter.close();
        }
        catch (FileNotFoundException e) {
            ErrorHandler error = new ErrorHandler();
            error.printAndExit("Error: Could not write file to destination.",
                    true,1);
        }   
    }
    
    public void compress(int[] sourceArray, String destArrayFilePath) {
        
        int[] destArray = new int [sourceArray.length+1024];
        
        boolean sortArray = this.checkSort();

        if (sortArray) {

            Arrays.sort(sourceArray);

            try {
                IntegratedIntCompressor compressor 
                        = new IntegratedIntCompressor();
                destArray = compressor.compress(sourceArray);
            }
            catch (Exception e) {
                ErrorHandler error = new ErrorHandler();
                error.printAndExit("Error: could not compress sorted integers.",
                        true, 1);
            }     
        }
        else {

            // Try bit packing unsorted array
            try {
                IntegratedIntCompressor compressor 
                        = new IntegratedIntCompressor();
                destArray = compressor.compress(sourceArray);
            }
            catch (Exception e) {
                
                // Otherwise try without bit packing
                try {
                    IntegerCODEC codec 
                            =  new Composition(new FastPFOR(),
                                               new VariableByte());
                    IntWrapper inputoffset = new IntWrapper(0);
                    IntWrapper outputoffset = new IntWrapper(0);
                    codec.compress(sourceArray,inputoffset,
                            sourceArray.length,destArray,outputoffset);
                }
                catch (Exception e2) {
                    ErrorHandler error = new ErrorHandler();
                    error.printAndExit("Error: could not compress unsorted"
                            + " integers.", true, 1);
                }
            }  
        }

        this.printToFile(destArray, destArrayFilePath);   
    }
    
    public void decompress(int[] sourceArray, String destArrayFilePath) {
        
        int[] destArray = new int [sourceArray.length-1024];

        // First assume that the file was compressed using bit packing
        try {
            IntegratedIntCompressor compressor = new IntegratedIntCompressor();
            destArray = compressor.uncompress(sourceArray);
        }
        catch (Exception e) {

            // Otherwise decompress assuming that bit packing was not used
            try {
                IntegerCODEC codec 
                        =  new Composition(new FastPFOR(), new VariableByte());
                IntWrapper inputoffset = new IntWrapper(0);
                IntWrapper outputoffset = new IntWrapper(0);
                codec.uncompress(sourceArray,inputoffset,sourceArray.length,
                        destArray,outputoffset);
            }
            catch (Exception e2) {
                ErrorHandler error = new ErrorHandler();
                error.printAndExit("Error: could not decompress file."
                        , true, 1);
            }
        }  

        this.printToFile(destArray, destArrayFilePath);   
    }    
}
