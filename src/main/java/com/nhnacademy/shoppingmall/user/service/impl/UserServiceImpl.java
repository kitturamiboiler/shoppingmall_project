package com.nhnacademy.shoppingmall.user.service.impl;

import com.nhnacademy.shoppingmall.user.exception.NotEnoughPointException;
import com.nhnacademy.shoppingmall.user.exception.UserAlreadyExistsException;
import com.nhnacademy.shoppingmall.user.exception.UserNotFoundException;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String userId){
        //todo#4-1 회원조회
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public void saveUser(User user) {
        //todo#4-2 회원등록
        if (userRepository.countByUserId(user.getUserId()) >0) {
            throw new UserAlreadyExistsException(user.getUserId());
        }
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        //todo#4-3 회원수정
        if (userRepository.countByUserId(user.getUserId()) == 0 ){
            throw new UserNotFoundException(user.getUserId());
        }
        userRepository.update(user);
    }


    @Override
    public void deleteUser(String userId) {
        //todo#4-4 회원삭제
        if (userRepository.countByUserId(userId) ==0) {
            throw new UserNotFoundException(userId);
        }
        userRepository.deleteByUserId(userId);
    }

    @Override
    public User doLogin(String userId, String userPassword) {
        //todo#4-5 로그인 구현, userId, userPassword로 일치하는 회원 조회
        Optional<User> userOptional = userRepository.findByUserIdAndUserPassword(userId, userPassword);
        if (userOptional.isEmpty()) {
            throw new UserNotFoundException(userId);
        }
        userRepository.updateLatestLoginAtByUserId(userId, LocalDateTime.now());
        return userOptional.get();
    }
    @Override
    public void updateUserPoint(String userId, int pointDelta) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        if (user.getUserPoint() + pointDelta < 0) {
            throw new NotEnoughPointException();
        }
        user.setUserPoint(user.getUserPoint() + pointDelta);
        userRepository.update(user);
        log.info("포인트 변동 기록: 사용자={}, 변동액={}", userId, pointDelta);
    }
    //추가
    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }
}
