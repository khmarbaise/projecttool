package com.soebes.tools.project;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CLITest {

    private CLI cli;
    
    @BeforeMethod
    public void beforeMethod() {
        cli = new CLI();
    }

    @Test
    public void firstTest() {
        String[] args = { "--help" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
    }

    @Test
    public void CheckoutFullCommandNameTest() {
        String[] args = { "checkout", "--repository", "first", "--repository", "second" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(2);
    }

    @Test
    public void CheckoutAliasCommandNameTest() {
        String[] args = { "co", "--repository", "first", "--repository", "second" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(2);
    }

    @Test
    public void CheckoutTest() {
        String[] args = { "checkout"};
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
    }

    @Test
    public void checkoutXXX() {
        String[] args = { "checkout", "--repository", "first" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(1);
    }

    @Test
    public void checkoutZZ() {
        String[] args = { "checkout", "--repository", "first", "second", "third" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(3);
    }

    @Test
    public void checkoutShouldDeliverThreeRepositories() {
        String[] args = { "checkout", "--repository", "first", "second", "third" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(3);
    }
    
    @Test
    public void checkoutShouldDeliverDefaultRepositoriesIfNotDefinedOnCommandLine() {
        String[] args = { "checkout" };
        cli.run(args);
        assertThat(cli.getReturnCode()).isEqualTo(0);
        CheckoutCommand checkoutCommand = cli.getCommands().getCheckoutCommand();
        assertThat(checkoutCommand.getRepositories()).hasSize(4);
        assertThat(checkoutCommand.getRepositories()).isEqualTo(CheckoutCommand.DEFAULT_REPOSITORIES);
    }
}
