package com.web.demo.repositories;


import com.web.demo.entities.Member;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<Member, Long> {
    Member findById(String memberId);
}
