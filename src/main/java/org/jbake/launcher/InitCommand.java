package org.jbake.launcher;

import java.io.File;
import java.io.IOException;

import org.jbake.app.FileUtil;

public class InitCommand implements Command {
    public void execute() {
        try {
            FileUtil.copyJarResourcesRecursively(getClass().getResource("/sample"), new File("."));
        } catch (IOException ioe) {
            System.err.println("jbake cannot initialize current directory");
        }
    }
}
				
