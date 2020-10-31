package com.vaadin.tutorials.it;

import org.junit.Assert;
import org.junit.Test;

import com.vaadin.flow.component.login.testbench.LoginFormElement;

public class LoginIT extends AbstractTest {
   public LoginIT() {
      super("");
   }

   @Test
   public void loginAsValidUserSucceeds() {
      // Find the LoginForm used on the page
      LoginFormElement form = $(LoginFormElement.class).first();
      // Enter the credentials and log in
      form.getUsernameField().setValue("user");
      form.getPasswordField().setValue("password");
      form.getSubmitButton().click();
      // Ensure the login form is no longer visible
      Assert.assertFalse($(LoginFormElement.class).exists());
   }


}