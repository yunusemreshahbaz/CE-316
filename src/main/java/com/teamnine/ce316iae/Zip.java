package com.teamnine.ce316iae;

//imports

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Zip{

    private String zipDirectory;

    public void setZipDirectory(String zipDirectory){
        this.zipDirectory = zipDirectory;
        
    }

    public String getZipDirectory(){
        return zipDirectory;
    }

    // Methods
    public static void extractZipFiles(String zipDirectory) {
        File zip = new File(zipDirectory);
        File[] zFiles = zip.listFiles((dir, name) -> name.toLowerCase().endsWith(".zip"));
        byte[] size = new byte[1024];

        if(zFiles != null) {
            for(File zFile : zFiles) {
                try{
                    ZipInputStream zInputStream = new ZipInputStream(new FileInputStream(zFile));
                    ZipEntry zEntry = zInputStream.getNextEntry();

                    while(zEntry != null){
                        File fileOfOutput = new File(zip, zEntry.getName());
                        
                        if(zEntry.isDirectory()){
                            if(!fileOfOutput.exists()){
                                fileOfOutput.mkdirs();
                            }
                        } else{
                            File parentDirectory = fileOfOutput.getParentFile();

                            if(!parentDirectory.exists()){
                                parentDirectory.mkdirs();
                            }

                            FileOutputStream fileOutputStream = new FileOutputStream(fileOfOutput);
                            int zipLength;

                            while((zipLength = zInputStream.read(size)) > 0){
                                fileOutputStream.write(size, 0, zipLength);
                            }
                            fileOutputStream.close();
                        }
                        zEntry = zInputStream.getNextEntry();
                    }
                    zInputStream.closeEntry();
                    zInputStream.close();
                }
                catch(IOException e){
                    e.getMessage();
                }
            }
        } else{
            System.out.println("There is currently no Zip");
        }
    }
}