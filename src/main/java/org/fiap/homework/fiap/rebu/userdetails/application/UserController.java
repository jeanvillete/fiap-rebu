package org.fiap.homework.fiap.rebu.userdetails.application;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserNicknameConflictException;
import org.fiap.homework.fiap.rebu.userdetails.domain.usecase.UserUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    private final UserUseCase userUseCase;

    public UserController(UserUseCase userUseCase) {
        this.userUseCase = userUseCase;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void insertNewUser(@RequestBody UserUseCase.UserPayload userPayload) throws UserNicknameConflictException, InvalidSuppliedDataException {
        this.userUseCase.saveUser(userPayload);
    }

}
