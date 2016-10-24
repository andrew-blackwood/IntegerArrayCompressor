# IntegerArrayCompressor
An application that compresses a file containing an array of integers.

## Preparing your file
Your file should contain a list of integers, separated by commas.  
All integers should be between ***-2147483648*** and ***2147483647***.

## Running the Application
* Open a command line in the ***dist*** folder of the repository.
* Execute the following command: <br/>
  `Java –jar  compressor.jar –[c/d] file1 file2`
  * Use the parameter ***-c*** to compress ***file1*** into ***file2***, or the parameter ***-d*** to decompress ***file1*** into ***file2***.
  * ***file1*** is the target file.
  * ***file2*** is the resulting file.
  * Note: If the resulting file has a ***zip*** extension, the file will simply be zipped.
