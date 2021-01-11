package com.dolap.ecommerce.security;

import com.dolap.ecommerce.entity.User;
import com.dolap.ecommerce.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements CustomUserDetailsService {
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return user;
    }

    public UserDetails loadUserById(Long id) throws IllegalArgumentException {
        return userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);
    }
}
