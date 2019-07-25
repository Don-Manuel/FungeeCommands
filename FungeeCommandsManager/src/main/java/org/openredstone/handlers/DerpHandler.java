package org.openredstone.handlers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DerpHandler  {
    private final Random rand = new Random();
    private final File derpsFile;
    private List<String> derps = Collections.emptyList();

    public DerpHandler(File derpsFile) {
        this.derpsFile = derpsFile;
    }

    public void loadDerps() throws LoadException {
        try {
            derps = Files.readAllLines(derpsFile.toPath());
        } catch (IOException e) {
            throw new LoadException("Failed to load derps from " + derpsFile, e);
        }
    }

    public int derpCount() {
        return derps.size();
    }

    public String randomDerp() {
        return derpByIndex(rand.nextInt(derps.size()));
    }

    public String derpByIndex(int index) {
        return derps.get(index);
    }
}