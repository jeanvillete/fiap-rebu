package org.fiap.homework.fiap.rebu.userdetails.domain.usecase;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.User;
import org.fiap.homework.fiap.rebu.userdetails.domain.UserService;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserNicknameConflictException;
import org.springframework.stereotype.Component;

@Component
public class UserUseCase {

    public static class UserPayload {
        String nickname;

        public UserPayload() {
        }

        public String getNickname() {
            return nickname;
        }
    }

    private final UserService userService;

    public UserUseCase(UserService userService) {
        this.userService = userService;
    }

    public void saveUser(UserPayload userPayload) throws InvalidSuppliedDataException, UserNicknameConflictException {
        userService.validNickname(
                userPayload.nickname
        );
        userService.nicknameAvailable(
                userPayload.nickname
        );

        userService.save(
                new User(
                        userPayload.nickname
                )
        );
    }

}
