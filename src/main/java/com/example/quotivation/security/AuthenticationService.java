package com.example.quotivation.security;

import com.example.quotivation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.quotivation.entity.User;

import java.util.Optional;

@Component
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;

    public long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
                return Long.parseLong(userDetails.getUsername());
            }
        }

        return 0L;
    }

    public User getUser() {
        long userId = getUserId();
        Optional<User> user = userRepository.findById(userId);

        return user.orElse(null);
    }
}
