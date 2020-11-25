package com.example.polls.model;

import com.example.polls.model.audit.DateAudit;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user_list")
public class UserList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_list_id")
    private Long userListId;

    @NotBlank
    @Size(max = 40)
    private String title;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    public UserList() {

    }

    public UserList(@NotBlank @Size(max = 40) String title, User user) {
        this.title = title;
        this.user = user;
    }

    public Long getUserListId() {
        return userListId;
    }

    public void setUserListId(Long userListId) {
        this.userListId = userListId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
