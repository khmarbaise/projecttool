package com.soebes.tools.project;

import java.util.List;

import org.fest.util.Collections;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Checkout multiple modules from different repositories")
public class CheckoutCommand extends BaseCommand implements ICommand {

    public final static List<String> DEFAULT_REPOSITORIES = Collections.list("a", "b", "c", "d");

    @Parameter(names = { "--repository", "-r" }, variableArity = true, description = "The repositories which will be used.")
    private List<String> repositories;

    @Parameter(names = { "--branch", "-b" }, description="The name of the branch which will be checked out.")
    private String branch;

    public CheckoutCommand() {
        repositories = DEFAULT_REPOSITORIES;
    }

    public List<String> getRepositories() {
        return repositories;
    }

}
