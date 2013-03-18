package com.soebes.tools.project.cli;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.Test;

public class RegularExpressionTest {

    @Test
    public void test() {
        String message = "shopping";
        
        String result = message.replaceAll("(.*)", "$1");
        
        assertThat(result).isEqualTo("SHOPPING");
    }
}
