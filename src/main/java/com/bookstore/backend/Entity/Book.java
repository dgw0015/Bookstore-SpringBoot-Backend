package com.bookstore.backend.Entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "db_book")
public class Book {


   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)
   @Column(name = "BookID")
   private Integer BookID;

   @Column(name = "Title")
   private String Title;

   @Column(name = "unit_price")
   private String UnitPrice;

   @Column(name = "Author")
   private String Author;

   @Column(name = "Quantity")
   private Integer Quantity;

   @Column(name = "SupplierID")
   private Integer SupplierID;

   @Column(name = "SubjectID")
   private Integer SubjectID;
}
