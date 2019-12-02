package com.bookstore.backend.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "db_shipper")
public class Shipper {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "ShipperID")
   private Integer ShipperID;

   @Column(name = "shipper_name")
   private String shipper_name;
}
