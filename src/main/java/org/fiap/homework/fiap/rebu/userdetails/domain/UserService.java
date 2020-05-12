package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserNicknameConflictException;

public interface UserService {
    void validNickname(String nickname) throws InvalidSuppliedDataException;

    void nicknameAvailable(String nickname) throws UserNicknameConflictException;

    void save(User user);
}
