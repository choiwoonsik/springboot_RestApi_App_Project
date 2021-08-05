package com.restApi.restApiSpringBootApp.service;

import com.restApi.restApiSpringBootApp.advice.exception.UserNotFoundCException;
import com.restApi.restApiSpringBootApp.dto.user.UserRequestDto;
import com.restApi.restApiSpringBootApp.entity.User;
import com.restApi.restApiSpringBootApp.repository.UserJpaRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {
    private UserJpaRepo userJpaRepo;

    @Transactional
    public Long save(UserRequestDto userDto) {
        userJpaRepo.save(userDto.toEntity());
        return userJpaRepo.findByEmail(userDto.getEmail()).getUserId();
    }

    @Transactional(readOnly = true)
    public UserRequestDto findById(Long id) {
        User user = userJpaRepo.findById(id)
                .orElseThrow(UserNotFoundCException::new);
        return UserRequestDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Transactional(readOnly = true)
    public UserRequestDto findByEmail(String email) {
        User user = userJpaRepo.findByEmail(email);
        if (user == null) throw new UserNotFoundCException();
        else return UserRequestDto
                .builder()
                .name(user.getName())
                .email(user.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public List<UserRequestDto> findAllUser() {
        return userJpaRepo.findAll()
                .stream()
                .map(u -> UserRequestDto.builder()
                        .name(u.getName())
                        .email(u.getEmail())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public UserRequestDto modify(Long id, String name) {
        User modifiedUser = userJpaRepo
                .findById(id).orElseThrow(UserNotFoundCException::new);
        modifiedUser.setName(name);
        return UserRequestDto.builder()
                .name(modifiedUser.getName())
                .email(modifiedUser.getEmail())
                .build();
    }

    @Transactional
    public void delete(Long id) {
        userJpaRepo.deleteById(id);
    }
}
