package com.paymybuddy.pmbv1.repository;

import com.mg.paymybuddy.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

   Optional<User> findByEmail(String email);
}

