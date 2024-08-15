package com.example.quotivation.security;

import com.example.quotivation.entity.User;
import com.example.quotivation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User validUser = user.get();

        return new PrincipalDetails(validUser);
    }

    public UserDetails loadUserById(Long userId)
            throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(userId);

        if(user.isEmpty()){
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User validUser = user.get();

        return new PrincipalDetails(validUser);
    }
}
