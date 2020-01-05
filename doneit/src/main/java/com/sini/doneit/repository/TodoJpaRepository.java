package com.sini.doneit.repository;

import com.sini.doneit.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoJpaRepository extends JpaRepository<Todo, Long> {

}
