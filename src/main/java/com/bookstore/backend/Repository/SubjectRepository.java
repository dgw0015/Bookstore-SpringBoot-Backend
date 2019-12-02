package com.bookstore.backend.Repository;

import com.bookstore.backend.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SubjectRepository extends JpaRepository<Subject, Integer> {
}
