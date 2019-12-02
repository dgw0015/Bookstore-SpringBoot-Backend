package com.bookstore.backend.Projections;

import org.springframework.beans.factory.annotation.Value;

public interface TitleOnly {

   @Value("#{target.Title}")
   String getBookTitles();
}
