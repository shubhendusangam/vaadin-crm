package com.vaadin.tutorials.ui.listview;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.tutorials.backend.entity.Contact;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ListViewTest {

   @Autowired
   private ListView listView;

   @Test
   public void formShownWhenContactSelected() {
      Grid<Contact> grid = listView.contactGrid;
      Contact firstContact = getFirstItem(grid);

      ContactForm form = listView.contactForm;

      Assert.assertFalse(form.isVisible());
      grid.asSingleSelect().setValue(firstContact);
      Assert.assertTrue(form.isVisible());
      Assert.assertEquals(firstContact.getFirstName(), form.firstName.getValue());
   }
   private Contact getFirstItem(Grid<Contact> grid) {
      return( (ListDataProvider<Contact>) grid.getDataProvider()).getItems().iterator().next();
   }
}