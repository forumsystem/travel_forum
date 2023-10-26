package com.project.travel_forum;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class Helpers {
    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setFirstName("MockFirstName");
        mockUser.setLastName("MockLastName");
        mockUser.setEmail("mock@user.com");
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        return mockUser;
    }

    public static Post createMockPost() {
        var mockPost = new Post();
        mockPost.setId(1);
        mockPost.setContent("MockPostContent");
        mockPost.setTitle("MockPostTitle");
        mockPost.setCreatedBy(createMockUser());
        return mockPost;
    }

    public static FilterOptions createMockFilterOptions() {
        return new FilterOptions(
                "name",
                "ContentPost",
                "Dora",
                "sort",
                "order");
    }

    public static User createMockBlockedUser() {
        var mockUser = new User();
        mockUser.setBlocked(true);
        return mockUser;
    }

    public static List<Comment> createMockComment() {
        List<Comment> comments = new ArrayList<>();

        Comment mockComment1 = new Comment();
        mockComment1.setComment("Test comment 1");
        mockComment1.setId(1);
        mockComment1.setPost(createMockPost());
        mockComment1.setCreatedBy(createMockUser());

        Comment mockComment2 = new Comment();
        mockComment2.setComment("Test comment 2");
        mockComment2.setId(2);
        mockComment2.setPost(createMockPost());
        mockComment2.setCreatedBy(createMockUser());

        comments.add(mockComment1);
        comments.add(mockComment2);

        return comments;
    }


}
