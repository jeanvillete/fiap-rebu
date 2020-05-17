package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserHasOpenTrip;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserNicknameConflictException;

import java.util.Optional;

public interface UserService {
    void validNickname(String nickname) throws InvalidSuppliedDataException;

    void nicknameAvailable(String nickname) throws UserNicknameConflictException;

    void save(User user);

    Optional<User> findUserByNickname(String nickname);

    void ensureThereIsNoOpenTripForTheUser(User user) throws UserHasOpenTrip;
}
