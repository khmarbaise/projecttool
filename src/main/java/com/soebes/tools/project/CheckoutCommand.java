package com.soebes.tools.project;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "Checkout multiple modules from different repositories")
public class CheckoutCommand extends BaseCommand implements ICommand {

     @Parameter(names = { "--repository", "-r" })
     private List<String> repositories;

    public List<String> getRepositories() {
        return repositories;
    }

}
