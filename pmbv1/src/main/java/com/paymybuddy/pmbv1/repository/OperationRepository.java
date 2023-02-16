package com.paymybuddy.pmbv1.repository;

import com.paymybuddy.pmbv1.model.Operation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationRepository extends CrudRepository<Operation, Integer> {

}

