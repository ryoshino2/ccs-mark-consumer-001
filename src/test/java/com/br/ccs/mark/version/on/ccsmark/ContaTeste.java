package com.br.ccs.mark.version.on.ccsmark;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:caracteristicas", tags = "@ContaTeste",
        glue = "com.br.ccs.mark.version.on.ccsmark.steps", monochrome = true, dryRun = false)
public class ContaTeste {


}
