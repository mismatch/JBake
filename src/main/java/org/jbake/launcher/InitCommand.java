package org.jbake.launcher;

import java.io.File;

public class InitCommand implements Command {
    public void execute() {
        new File("assets").mkdir();
        new File("content").mkdir();
        new File("templates").mkdir();
    }
}
				
