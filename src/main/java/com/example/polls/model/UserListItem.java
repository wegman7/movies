package com.example.polls.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "user_list_item")
public class UserListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_list_item_id")
    private Long userListItemId;

    @Size(max = 100)
    private String content;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_list_id", nullable = false)
    private UserList userList;

    public UserListItem() {
    }

    public UserListItem(@Size(max = 100) String content, UserList userList) {
        this.content = content;
        this.userList = userList;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserList getUserList() {
        return userList;
    }

    public void setUserList(UserList userList) {
        this.userList = userList;
    }
}
