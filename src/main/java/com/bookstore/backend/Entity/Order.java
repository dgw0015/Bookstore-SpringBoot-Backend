package com.bookstore.backend.Entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.annotations.Tuplizer;

import javax.persistence.*;
import javax.validation.valueextraction.ExtractedValue;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "db_order")
public class Order {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "OrderID")
   private Integer OrderID;

   @Column(name = "CustomerID")
   private Integer CustomerID;

   @Column(name = "EmployeeID")
   private Integer EmployeeID;

   @Column(name = "order_date")
   private String OrderDate;

   @Column(name = "shipped_date")
   private String ShippedDate;

   @Column(name = "ShipperID")
   private Integer ShipperID;

}


