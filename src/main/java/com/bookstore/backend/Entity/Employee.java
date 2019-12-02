package com.bookstore.backend.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "db_employee")
public class Employee {

   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "EmployeeID")
   private Integer EmployeeID;

   @Column(name = "last_name")
   private String LastName;

   @Column(name = "first_name")
   private String FirstName;
}

