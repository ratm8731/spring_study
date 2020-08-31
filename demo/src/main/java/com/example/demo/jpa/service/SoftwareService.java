package com.example.demo.jpa.service;

import com.example.demo.jpa.entity.SoftwareEntity;

import java.util.List;

public interface SoftwareService {
    List<SoftwareEntity> findAgentIdBySoftWare(Long id);
    List<SoftwareEntity> findBySoftWareName(String name);
}
