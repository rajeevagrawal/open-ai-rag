package com.raj.rag;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FindFiles {

    public static void main(String[] args) {
        File currentDir = new File("/Users/rajeevkumar/Documents/PersonalDocuments/273MagnetBuying"); // current directory
        getDirectoryContents(currentDir,"pdf").stream().forEach(file -> {
            try {
                System.out.println(file.getCanonicalPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
    public static List<File> getDirectoryContents(File dir,String extension) {
        ArrayList<File> rtnFiles = new ArrayList<File>();

        try {
            File[] files = dir.listFiles();

            for (File file : files) {

                if (file.isDirectory()) {
                    rtnFiles.addAll(getDirectoryContents(file,extension));

                } else {
                    if (file.getName().endsWith(("."+extension))) {
                        System.out.println("     file:" + file.getCanonicalPath());
                        rtnFiles.add(file);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rtnFiles;
    }
}
