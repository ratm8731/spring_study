package com.example.demo.jpa.repository;

import com.example.demo.jpa.entity.SoftwareEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AgentJpaRepository extends JpaRepository<SoftwareEntity, Long> {
    @Query("select software from SoftwareEntity software where software.agent.id = :id")
    List<SoftwareEntity> findAgentIdBySoftWare(@Param("id") Long id);
    @Query("select software from SoftwareEntity software where software.name = :name")
    List<SoftwareEntity> findBySoftWareName(@Param("name") String name);
}
