package com.example.polls.payload;

import com.example.polls.model.Post;

public class CreateCommentRequest extends CreatePostRequest {

    private Long postId;

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
