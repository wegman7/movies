package com.example.polls.repository;

import com.example.polls.model.UserList;
import com.example.polls.model.UserListItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserListItemRepository extends JpaRepository<UserListItem, Long> {

    Optional<UserListItem> findByUserList_UserListId(Long id);
}
