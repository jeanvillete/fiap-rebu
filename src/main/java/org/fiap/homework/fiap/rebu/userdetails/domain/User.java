package org.fiap.homework.fiap.rebu.userdetails.domain;


import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

@Entity(name = "USER_DETAILS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 10)
    private String nickname;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Trip> trips;

    public User() {
    }

    public User(String nickname) {
        this.nickname = nickname;
    }

    public String getNickname() {
        return nickname;
    }

    public Set<Trip> getTrips() {
        return Collections.unmodifiableSet(trips);
    }
}
