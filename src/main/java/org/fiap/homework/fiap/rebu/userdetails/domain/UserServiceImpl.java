package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.fiap.homework.fiap.rebu.common.exception.InvalidSuppliedDataException;
import org.fiap.homework.fiap.rebu.userdetails.domain.exception.UserNicknameConflictException;
import org.springframework.stereotype.Service;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void validNickname(String nickname) throws InvalidSuppliedDataException {
        if (nickname == null) {
            throw new InvalidSuppliedDataException("Nickname is mandatory for a user insertion.");
        }
        if (nickname.length() < 6 || nickname.length() > 10) {
            throw new InvalidSuppliedDataException("Nickname must be between 6 and 10 characters.");
        }
    }

    @Override
    public void nicknameAvailable(String nickname) throws UserNicknameConflictException {
        Integer countByNickname = this.userRepository.countByNickname(nickname)
                .orElse(0);

        if (countByNickname > 0) {
            throw new UserNicknameConflictException("Nickname \"" + nickname + "\" is already being used.");
        }
    }

    @Override
    public void save(User user) {
        this.userRepository.save(user);
    }
}
