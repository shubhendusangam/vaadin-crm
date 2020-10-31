package com.vaadin.tutorials.it.elements.login;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.login.testbench.LoginFormElement;
import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;
import com.vaadin.testbench.annotations.Attribute;

@Attribute(name = "class", contains = "login-view")
public class LoginViewElement extends VerticalLayoutElement {

   public boolean login(String username, String password) {
      LoginFormElement form = $(LoginFormElement.class).first();
      form.getUsernameField().setValue(username);
      form.getPasswordField().setValue(password);
      form.getSubmitButton().click();

      // Return true if we end up on another page
      return !$(LoginViewElement.class).onPage().exists();
   }

   @Test
   public void loginAsValidUserSucceeds() {
      LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
      Assert.assertTrue(loginView.login("user", "password"));
   }

   @Test
   public void loginAsInvalidUserFails() {
      LoginViewElement loginView = $(LoginViewElement.class).onPage().first();
      Assert.assertFalse(loginView.login("user", "invalid"));
   }
}