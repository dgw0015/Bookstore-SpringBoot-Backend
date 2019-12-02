package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface TitleAndTotalQuantity {

   @Value("#{target.Title}")
   String getBookTitle();

   @Value("#{target.TotalQuantity}")
   Integer getTotalQuantity();


}
