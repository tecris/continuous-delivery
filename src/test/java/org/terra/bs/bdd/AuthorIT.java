package org.terra.bs.bdd;

import cucumber.api.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

// mvn clean verify -Dtest=org.terra.bs.bdd.AuthorIT

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(features = "src/test/resources/features/author")
// to run only one feature, tag the feature with custom tag (@run_me) and use bellow CucumberOptions
//@CucumberOptions(features = "src/test/resources/features/author", tags = {"@run_US123"})
public class AuthorIT {
}