package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface TitleAndShipperName {

   @Value("#{target.Title}")
   String getBookTitle();

   @Value("#{target.shipper_name}")
   String getShipperName();
}
