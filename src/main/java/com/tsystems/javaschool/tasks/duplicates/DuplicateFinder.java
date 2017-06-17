package com.tsystems.javaschool.tasks.duplicates;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class DuplicateFinder {

    /**
     * Processes the specified file and puts into another sorted and unique
     * lines each followed by number of occurrences.
     *
     * @param sourceFile file to be processed
     * @param targetFile output file; append if file exist, create if not.
     * @return <code>false</code> if there were any errors, otherwise
     * <code>true</code>
     */
    public boolean process(File sourceFile, File targetFile){
        // TODO: Implement the logic here
        if (sourceFile == null || targetFile == null) throw new IllegalArgumentException();
        try {
            List<String> list = Files.readAllLines(Paths.get(sourceFile.getName()), StandardCharsets.UTF_8);
            FileWriter writer = new FileWriter(targetFile.getName(),true);
            Collections.sort(list);
            String indStr = list.get(0);
            int index = 1;
            for (int i = 1; i < list.size(); i++){
                if (!list.get(i).equals(indStr)) {writer.write(indStr + "["+index+"]"+"\n"); indStr = list.get(i); index = 1;}
                else {index += 1;}
            }
            writer.write(indStr + "["+index+"]"+"\n");
            writer.close();
            return true;
        } catch (Exception e){
            return false;
        }

    }


}
