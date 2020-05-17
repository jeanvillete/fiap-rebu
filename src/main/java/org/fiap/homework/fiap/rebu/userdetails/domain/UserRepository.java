package org.fiap.homework.fiap.rebu.userdetails.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

interface UserRepository extends JpaRepository<User, Integer> {

    Optional<Integer> countByNickname(String nickname);

    Optional<User> findByNickname(String nickname);
}
