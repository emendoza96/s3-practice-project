package com.s3practice.product.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.s3practice.product.dao.UserEntityRepository;
import com.s3practice.product.model.RoleEntity;
import com.s3practice.product.model.UserEntity;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserEntityRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity =  userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("The user " +  username + " doesn't exist")
        );

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = new HashSet<>();

        for(RoleEntity role : userEntity.getRoles()) {
            simpleGrantedAuthorities.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole())));
        }

        Collection<? extends GrantedAuthority> authorities = simpleGrantedAuthorities;

        return new User(
            userEntity.getUsername(),
            userEntity.getPassword(),
            true,
            true,
            true,
            true,
            authorities
        );
    }
}
