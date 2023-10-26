package com.project.travel_forum;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;

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

    public static User createMockBlockedUser(){
        var mockUser = new User();
        mockUser.setBlocked(true);
        return mockUser;
    }


}
