package com.sini.doneit.repository;

import com.sini.doneit.model.Proposal;
import com.sini.doneit.model.Todo;

import java.util.List;

import com.sini.doneit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProposalJpaRepository extends JpaRepository<Proposal, Long> {


    @Query("select p.todo from Proposal p where p.user = :user and p.state = 'in progress'")
    List<Todo> getJoinedTodo(@Param("user") User user);

    @Query("select p.todo from Proposal p where p.user = :user and p.state = 'accepted'")
    List<Todo> getJoinedAcceptedTodo(@Param("user") User user);

    Proposal findByUserAndTodo(Long user, Long todo);



    @Query("select p from Proposal p where p.user = :user and p.todo = :todo")
    Proposal findByUserAndTodo(@Param("user") User user, @Param("todo") Todo todo);
}
