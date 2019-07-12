package org.openredstone.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileArrayProvider {

    public static String[] readLines(File file) throws IOException {
        Scanner scan = new Scanner(file);
        List<String> lines = new ArrayList<String>();
        while (scan.hasNextLine()) {
            lines.add(scan.nextLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

}
