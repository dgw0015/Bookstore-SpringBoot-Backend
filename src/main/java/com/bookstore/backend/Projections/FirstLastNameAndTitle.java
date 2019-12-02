package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface FirstLastNameAndTitle {

   @Value("#{target.first_name}")
   String getFirstName();

   @Value("#{target.last_name}")
   String getLastName();

   @Value("#{target.Title}")
   String getBookTitle();
}
