package com.soebes.tools.project.cli;

import com.beust.jcommander.Parameter;

public class CLIBaseCommand {
    @Parameter(names = {"--help", "-help", "-?", "-h"}, description = "Get help for the particular command.")
    private boolean help;


    public boolean isHelp() {
        return help;
    }

}
