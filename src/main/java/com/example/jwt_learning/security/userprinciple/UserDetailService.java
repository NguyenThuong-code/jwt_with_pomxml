package com.example.jwt_learning.security.userprinciple;

import com.example.jwt_learning.models.Users;
import com.example.jwt_learning.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
@Autowired
    IUserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found -> username or password"+username));

        return UserPrinciple.build(users);
    }

}
