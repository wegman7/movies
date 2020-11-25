package com.example.polls.repository;

import com.example.polls.model.UserList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserListRepository extends JpaRepository<UserList, Long> {

    List<UserList> findAll();

    Optional<UserList> findById(Long id);

    List<UserList> findByUser_Id(Long id);
}
