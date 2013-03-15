package com.soebes.tools.project;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.beust.jcommander.JCommander;

public class PTCommandLine {
    private static Logger LOGGER = Logger.getLogger(PTCommandLine.class);

    public static final String PROGRAMM_NAME = "pt";

    public enum Commands {
        CHECKOUT("checkout", "co");

        private String commandName;
        private String[] aliases;

        private Commands(String commandName, String... aliases) {
            this.commandName = commandName;
            this.aliases = aliases;
        }

        private Commands(String commandName) {
            this.commandName = commandName;
            this.aliases = null;
        }

        public String getCommandName() {
            return commandName;
        }

        public String[] getAliases() {
            return aliases;
        }
    };

    private Map<Commands, ICommand> commandList = null;

    private final MainCommand mainCommand;

    private final JCommander commander;

    public PTCommandLine(String[] args) {
        mainCommand = new MainCommand();
        commander = new JCommander(mainCommand);

        commandList = new HashMap<Commands, ICommand>();

        commandList.put(Commands.CHECKOUT, new CheckoutCommand());

        for (Commands item : Commands.values()) {
            getCommander().addCommand(item.getCommandName(), commandList.get(item), item.getAliases());
        }

        getCommander().setProgramName(PROGRAMM_NAME);
        getCommander().parse(args);

    }

    /**
     * Check to see if for one command a help option is given or not.
     *
     * @return true if a command is given with help option false otherwise.
     */
    public boolean isHelpForCommand() {
        boolean result = false;
        Commands command = getCommand();
        ICommand baseCommand = commandList.get(command);
        if (baseCommand == null) {
            result = false;
        } else {
            result = baseCommand.isHelp();
        }
        return result;
    }

    public Commands getCommand() {
        Commands command = null;
        for (Commands item : Commands.values()) {
            if (item.getCommandName().equalsIgnoreCase(getCommander().getParsedCommand())) {
                command = item;
            }
        }
        return command;
    }

    
    public JCommander getCommander() {
        return commander;
    }

    public MainCommand getMainCommand() {
        return this.mainCommand;
    }

    public CheckoutCommand  getCheckoutCommand() {
        return (CheckoutCommand) commandList.get(Commands.CHECKOUT);
    }
    
}
