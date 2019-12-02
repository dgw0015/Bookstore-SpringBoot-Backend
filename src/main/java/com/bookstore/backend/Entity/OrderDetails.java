package com.bookstore.backend.Entity;

import lombok.Data;
import javax.persistence.*;

@Data
@Entity
@Table(name = "db_order_detail")
public class OrderDetails {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "BookID")
   private Integer BookID;

   @Column(name = "OrderID")
   private Integer OrderID;

   @Column(name = "Quantity")
   private Integer Quantity;
}

