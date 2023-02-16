package com.paymybuddy.pmbv1.repository;

import com.paymybuddy.pmbv1.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
}
