package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;


public interface FirstLastNameTotalPurchases {

   @Value("#{target.first_name}")
   String getFirstName();

   @Value("#{target.last_name}")
   String getLastName();

   @Value("#{target.TotalPurchases}")
   Double getTotalPurchases();
}
