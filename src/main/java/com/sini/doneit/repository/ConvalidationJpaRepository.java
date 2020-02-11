package com.sini.doneit.repository;

import com.sini.doneit.model.Convalidation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvalidationJpaRepository extends JpaRepository<Convalidation,Long> {
    Convalidation findByTodo(Long todoId);
}
