package com.web.demo.service;

import com.web.demo.entities.Member;
import com.web.demo.repositories.UserRepository;
import com.web.demo.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = userRepository.findById(memberId);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        if (memberId.equals("admin")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.ADMIN.getValue()));
        } else {
            grantedAuthorities.add(new SimpleGrantedAuthority(Role.MEMBER.getValue()));
        }

        return new User(member.getUserId(), member.getPassword(), grantedAuthorities);
    }
}
