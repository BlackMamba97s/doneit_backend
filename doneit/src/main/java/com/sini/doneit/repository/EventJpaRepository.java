package com.sini.doneit.repository;

import com.sini.doneit.model.Event;
import com.sini.doneit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EventJpaRepository extends JpaRepository<Event,Long> {
    @Query("select e from Event e ")
    List<Event> findEventList();
}
