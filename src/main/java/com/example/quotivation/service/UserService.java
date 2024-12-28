package com.example.quotivation.service;

import com.example.quotivation.dto.user.request.UserSignUpReq;
import com.example.quotivation.dto.user.response.UserInfoResponse;
import com.example.quotivation.exception.response.EmailConflictException;
import com.example.quotivation.security.AuthenticationService;
import com.example.quotivation.security.PrincipalDetails;
import com.example.quotivation.entity.User;
import com.example.quotivation.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationService authenticationService;

    public void signup(UserSignUpReq request) {
        Optional<User> getEmail = userRepository.findByEmail(request.getEmail());
        if(getEmail.isPresent()) {
            throw new EmailConflictException("이미 가입한 이메일 입니다.", HttpStatus.CONFLICT);
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        User user = User.createUser(request, passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public UserInfoResponse getUserInfo() {
        User user = authenticationService.getUser();

        return UserInfoResponse.make(user);
    }
}
