package com.sini.doneit.repository;

import com.sini.doneit.model.PersonalCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalCardJpaRepository extends JpaRepository<PersonalCard, Long> {

}
