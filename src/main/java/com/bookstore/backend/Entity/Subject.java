package com.bookstore.backend.Entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "db_subject")
public class Subject {
   @Id
   @GeneratedValue(strategy = GenerationType.AUTO)

   @Column(name = "SubjectID")
   private Integer SubjectID;

   @Column(name = "category_name")
   private String category_name;

}
