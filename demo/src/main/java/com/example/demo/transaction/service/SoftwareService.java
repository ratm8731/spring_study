package com.example.demo.transaction.service;

import com.example.demo.transaction.entity.SoftwareEntity;
import com.example.demo.transaction.exception.AsyncXAResourceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("softwareService")
@Transactional
public interface SoftwareService {
    List<SoftwareEntity> findAll();
    SoftwareEntity save(SoftwareEntity softwareEntity) throws AsyncXAResourceException;
    long count();
}
