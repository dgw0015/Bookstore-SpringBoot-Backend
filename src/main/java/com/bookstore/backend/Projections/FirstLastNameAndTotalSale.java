package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface FirstLastNameAndTotalSale {

   @Value("#{target.first_name}")
   String getFirstName();

   @Value("#{target.last_name}")
   String getLastName();

   @Value("#{target.TotalSales}")
   Double getTotalSales();

}
