package com.bookstore.backend.Projections;


import org.springframework.beans.factory.annotation.Value;

public interface TitleAndPrice {

   @Value("#{target.Title}")
   String getBookTitles();

   @Value("#{target.unit_price}")
   String getUnitMaxPrice();
}
