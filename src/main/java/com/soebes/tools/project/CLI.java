package com.soebes.tools.project;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;

import com.beust.jcommander.MissingCommandException;
import com.beust.jcommander.ParameterException;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.soebes.tools.project.PTCommandLine.Commands;

public class CLI {
    private static Logger LOGGER = LogManager.getLogger(CLI.class.getName());

    private int returnCode = 0;

    public CLI() {
        LOGGER.info("Test message.");
        
    }

    private PTCommandLine commands;
    
    public void run(String[] args) {
        setReturnCode(0);
        try {
            commands = new PTCommandLine(args);
        } catch (MissingCommandException e) {
            LOGGER.warn("");
            LOGGER.warn("It looks like you used a wrong command .");
            LOGGER.warn("");
            LOGGER.warn("Message: " + e.getMessage());
            LOGGER.warn("");
            LOGGER.warn("To get help about all existing commands please type:");
            LOGGER.warn("");
            LOGGER.warn("    supose --help");
            LOGGER.warn("");
            LOGGER.warn("If you like to get help about a particular command:");
            LOGGER.warn("");
            LOGGER.warn("    supose command --help");
            LOGGER.warn("");
            return;
            
        } catch (ParameterException e) {
            LOGGER.warn("");
            LOGGER.warn("It looks like you used a wrong command or used wrong options or a combination of this.");
            LOGGER.warn("");
            LOGGER.warn("Message: " + e.getMessage());
            LOGGER.warn("");
            LOGGER.warn("To get help about all existing commands please type:");
            LOGGER.warn("");
            LOGGER.warn("    supose --help");
            LOGGER.warn("");
            LOGGER.warn("If you like to get help about a particular command:");
            LOGGER.warn("");
            LOGGER.warn("    supose command --help");
            LOGGER.warn("");
            return;
        }

        Commands command = commands.getCommand();
        if (commands.isHelpForCommand()) {
            commands.getCommander().usage(command.getCommandName());
            return;
        }

        if (commands.getMainCommand().isVersion()) {
            //printVersion();
            System.out.println("Version:" + "0.1");
            System.out.println("Build Number:" + "UNKNOWN");

            return;
        }

        if (command == null || commands.getMainCommand().isHelp()
                || (args.length == 0)) {
            commands.getCommander().usage();
            return;
        }


        switch (command) {
            case CHECKOUT:
                CheckoutCommand coCommand = commands.getCheckoutCommand();
                //??
                break;
            default:
                LOGGER.error("Unknown command in switch.");
                setReturnCode(1);
                break;
        }

    }

    /**
     * This will do the command argument extraction and give the parameter to
     * the scanRepository class which will do the real repository scan.
     * 
     * @param scanCommand
     *            The command line.
     * @throws SVNException
     */
    public void setReturnCode(int returnCode) {
        this.returnCode = returnCode;
    }

    public int getReturnCode() {
        return returnCode;
    }

    public PTCommandLine getCommands() {
        return commands;
    }
    
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CLIModule());
        CLI cli = injector.getInstance(CLI.class);
        cli.run(args);
        System.exit(cli.getReturnCode());
    }

    
}
