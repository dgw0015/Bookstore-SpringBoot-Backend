package com.bookstore.backend.Entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "db_supplier")
public class Supplier {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "SupplierID")
   private Integer SupplierID;

   @Column(name = "company_name")
   private String CompanyName;

   @Column(name = "contact_last_name")
   private String ContactLastName;

   @Column(name = "contact_first_name")
   private String ContactFirstName;

   @Column(name = "Phone")
   private String Phone;
}
