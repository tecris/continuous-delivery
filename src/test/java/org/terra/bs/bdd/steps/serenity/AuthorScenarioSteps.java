package org.terra.bs.bdd.steps.serenity;

import org.terra.bs.bdd.page.CreateAuthorPage;

import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class AuthorScenarioSteps extends ScenarioSteps {

    private CreateAuthorPage createAuthorPage;

    @Step
    public void openPage() {
        this.createAuthorPage.openPage();
    }

    @Step
    public void checkCreateAuthorForm() {
        this.createAuthorPage.checkCreateAuthorForm();
    }

    @Step
    public void createAuthor(String firstName, String lastName, String email) {
        this.createAuthorPage.createAuthor(firstName, lastName, email);
    }

}
