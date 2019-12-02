package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface FirstAndLastName {

   @Value("#{target.firstName}")
   String getFirstName();

   @Value("#{target.lastName}")
   String getLastName();
}
