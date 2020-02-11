package com.sini.doneit.repository;

import com.sini.doneit.model.Convalidation;
import com.sini.doneit.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConvalidationJpaRepository extends JpaRepository<Convalidation, Long> {

    Convalidation findByTodo(Long todoId);

    Convalidation findByKey(String key);
}
