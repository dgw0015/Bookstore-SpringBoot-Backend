package com.bookstore.backend.Entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "db_customer")
public class Customer {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "CustomerID")
   private Integer CustomerID;

   @Column(name = "last_name")
   private String LastName;

   @Column(name = "first_name")
   private String FirstName;

   @Column(name = "Phone")
   private String Phone;

}
