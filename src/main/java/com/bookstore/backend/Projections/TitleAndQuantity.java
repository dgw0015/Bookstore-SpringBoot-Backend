package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface TitleAndQuantity {

   @Value("#{target.Title}")
   String getBookTitle();

   @Value("#{target.Quantity}")
   Integer getBookQuantity();

}
