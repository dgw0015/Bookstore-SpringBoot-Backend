package com.bookstore.backend.Exception;

public class ResourceNotFoundException extends RuntimeException{

   public ResourceNotFoundException(String message)   {
      super(message);
   }
}
