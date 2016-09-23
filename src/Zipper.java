package compressor;

import java.io.File;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

/**
 * Filename:    Zipper.java
 * Purpose:     Zip or unzip a file/folder.
 * 
 * @author      Andrew Blackwood
 * @version     1.0, 22/09/2016
 */
public class Zipper {
    
    public Zipper() {}
    
    public void zip(File sourceFile, String destFilePath) {
        try {
            ZipFile zipFile = new ZipFile(destFilePath);
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipFile.addFile(sourceFile, parameters);
        }
        catch (ZipException e) {
            ErrorHandler error = new ErrorHandler();
            error.printAndExit("Error: Could not zip source file.", true, 1);
        }
    }
    
    public void unzip(String zipPath, String extractPath) {
       try {
        ZipFile zipFile = new ZipFile(zipPath);
        zipFile.extractAll(extractPath);
       }
       catch (ZipException e) {
           ErrorHandler error = new ErrorHandler();
           error.printAndExit("Error: Could not unzip source file.", true, 1);
       }    
    }   
}
