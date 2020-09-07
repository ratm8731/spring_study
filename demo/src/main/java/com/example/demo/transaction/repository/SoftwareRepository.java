package com.example.demo.transaction.repository;

import com.example.demo.transaction.entity.SoftwareEntity;
import org.springframework.data.repository.CrudRepository;

public interface SoftwareRepository extends CrudRepository<SoftwareEntity, Long> {
}
