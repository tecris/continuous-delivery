package org.terra.bs.bdd.page;

import org.junit.Assert;
import org.openqa.selenium.By;

import net.thucydides.core.annotations.DefaultUrl;
import net.thucydides.core.pages.PageObject;

@DefaultUrl("http://localhost:8070/bookstore/app.html#/Authors/new")
public class CreateAuthorPage extends PageObject {

    public void openPage() {
        this.open();
    }

    public void createAuthor(String firstName, String lastName, String email) {
        find(By.id("firstName")).clear();
        find(By.id("firstName")).sendKeys(firstName);
        find(By.id("lastName")).clear();
        find(By.id("lastName")).sendKeys(lastName);
        find(By.id("email")).clear();
        find(By.id("email")).sendKeys(lastName);
        find(By.id("saveAuthor")).click();
    }

    public void checkCreateAuthorForm() {
        Assert.assertTrue("First name input field not found", this.isElementVisible(By.id("firstName")));
        Assert.assertTrue("Last name input field not found", this.isElementVisible(By.id("lastName")));
        Assert.assertTrue("Email input field not found", this.isElementVisible(By.id("email")));
    }
}
