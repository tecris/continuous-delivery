package org.terra.bs.bdd.steps;

import org.terra.bs.bdd.steps.serenity.AuthorScenarioSteps;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;

public class AuthorStepDefinitions {

    @Steps
    private AuthorScenarioSteps authorScenarioSteps;

    @Given("^a bookstore application administrator navigates to create author page$")
    public void a_bookstore_application_administrator_navigates_to_create_author_page() {
        this.authorScenarioSteps.openPage();
    }

    @When("^a new author with first name (.*), last name (.*), email (.*) is created$")
    public void a_new_author_with_Peter_Abelard_peter_abelard_bookstore_org_gets_created(String firstName,
            String lastName, String email) {
        this.authorScenarioSteps.createAuthor(firstName, lastName, email);
    }

    @Then("^author list shows newly created author with first name (.*), last name (.*), email (.*)$")
    public void author_list_shows_newly_created_author_Peter_Abelard_peter_abelard_bookstore_org() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

}
