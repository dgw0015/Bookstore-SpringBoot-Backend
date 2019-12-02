package com.bookstore.backend.Projections;


import org.springframework.beans.factory.annotation.Value;

public interface SubjectCategoryName {

   @Value("#{target.category_name}")
   String getCategoryName();

}
