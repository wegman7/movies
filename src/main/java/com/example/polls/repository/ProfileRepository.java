package com.example.polls.repository;

import com.example.polls.model.Profile;
import com.example.polls.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    public Profile findByUser(User user);
}
