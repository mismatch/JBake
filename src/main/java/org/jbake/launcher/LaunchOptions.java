package org.jbake.launcher;

import java.io.File;
 
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.spi.SubCommandHandler;
import org.kohsuke.args4j.spi.SubCommands;
import org.kohsuke.args4j.spi.SubCommand;

class LaunchOptions {
	@Option(name = "-s", aliases = {"--source"}, 
            usage = "source of your blog posts (with templates and assets)", metaVar = "source_folder")
	private File source = new File(".");

	@Option(name = "-d", aliases = {"--dest"}, 
            usage = "destination folder for baked artifacts", metaVar = "destination_folder")
	private File destination = new File("baked");

	@Option(name = "-h", aliases = {"--help"}, usage="prints this message")
	private boolean isHelpNeeded;

    @Argument(handler = SubCommandHandler.class)
    @SubCommands({
        @SubCommand(name = "init", impl = InitCommand.class)
    })
    private Command command;

	
	LaunchOptions() {}

	File getSource() {
		return source;
	}

	File getDestination() {
		return destination;
	}

	boolean isHelpNeeded() {
		return isHelpNeeded;
	}

    Command getCommand() {
        return command;
    }
}
