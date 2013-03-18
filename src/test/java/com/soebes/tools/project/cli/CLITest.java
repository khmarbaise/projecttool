package com.soebes.tools.project.cli;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

public class CLITest {

    @Test
    public void shouldAcceptGlobalHelpOption() {
        String[] args = { "--help" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
    }

    @Test
    public void CheckoutFullCommandNameTest() {
        String[] args = { "checkout", "--repository", "first", "--repository", "second" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(2);
    }

    @Test
    public void CheckoutAliasCommandNameTest() {
        String[] args = { "co", "--repository", "first", "--repository", "second" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(2);
    }

    @Test
    public void CheckoutTest() {
        String[] args = { "checkout"};
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
    }

    @Test
    public void checkoutWithASingleRepository() {
        String[] args = { "checkout", "--repository", "first" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(1);
    }

    @Test
    public void checkoutZZ() {
        String[] args = { "checkout", "--repository", "first", "second", "third" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(3);
    }

    @Test
    public void checkoutShouldDeliverThreeRepositories() {
        String[] args = { "checkout", "--repository", "first", "second", "third" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(3);
    }
    
    @Test
    public void checkoutShouldDeliverDefaultRepositoriesIfNotDefinedOnCommandLine() {
        String[] args = { "checkout" };
        CLI cli = CLI.execute(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CLICheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(4);
        assertThat(checkoutCommand.getRepositories()).isEqualTo(CLICheckoutCommand.DEFAULT_REPOSITORIES);
    }
}
