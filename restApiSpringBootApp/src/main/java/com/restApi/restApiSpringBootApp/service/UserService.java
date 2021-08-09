package com.restApi.restApiSpringBootApp.service;

import com.restApi.restApiSpringBootApp.advice.exception.CEmailLoginFailedException;
import com.restApi.restApiSpringBootApp.advice.exception.CEmailSignupFailedException;
import com.restApi.restApiSpringBootApp.advice.exception.CUserNotFoundException;
import com.restApi.restApiSpringBootApp.dto.user.UserLoginResponseDto;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.domain.user.User;
import com.restApi.restApiSpringBootApp.domain.user.UserJpaRepo;
import com.restApi.restApiSpringBootApp.dto.user.UserResponseDto;
import com.restApi.restApiSpringBootApp.dto.user.UserSignupRequestDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {
    private UserJpaRepo userJpaRepo;
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Long save(UserRequestDto userDto) {
        User saved = userJpaRepo.save(userDto.toEntity());
        return saved.getUserId();
    }

    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email)
                .orElseThrow(CUserNotFoundException::new);
        return new UserResponseDto(user);
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long update(Long id, UserRequestDto userRequestDto) {
        User modifiedUser = userJpaRepo
                .findById(id).orElseThrow(CUserNotFoundException::new);
        modifiedUser.updateNickName(userRequestDto.getNickName());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserLoginResponseDto login(String email, String password) {
        User user = userJpaRepo.findByEmail(email).orElseThrow(CEmailLoginFailedException::new);
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new CEmailLoginFailedException();
        return new UserLoginResponseDto(user);
    }

    @Transactional
    public Long signup(UserSignupRequestDto userSignupDto) {
        User user = userJpaRepo.findByEmail(userSignupDto.getEmail()).orElse(null);
        if (user == null) return userJpaRepo.save(userSignupDto.toEntity()).getUserId();
        else throw new CEmailSignupFailedException();
    }
}
