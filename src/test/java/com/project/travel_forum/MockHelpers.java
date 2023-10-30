package com.project.travel_forum;

import com.project.travel_forum.models.*;

import java.util.ArrayList;
import java.util.List;

public class MockHelpers {
    public static Post createMockPost() {
        var mockPost = new Post();
        mockPost.setId(1);
        mockPost.setContent("MockPostContent");
        mockPost.setTitle("MockPostTitle");
        mockPost.setCreatedBy(createMockUser());
        return mockPost;
    }
    public static Comment createMockComment(){
        var mockComment= new Comment();
        mockComment.setId(1);
        mockComment.setComment("Hello, this is a test comment!");
        mockComment.setCreatedBy(createMockUser());
        mockComment.setPost(createMockPost());
        return mockComment;
    }
    public static User createMockAdmin() {
        User mockUser = createMockUser();
        mockUser.setAdmin(true);
        return mockUser;
    }

    public static User createMockUser() {
        var mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("MockUsername");
        mockUser.setPassword("MockPassword");
        mockUser.setLastName("MockLastName");
        mockUser.setFirstName("MockFirstName");
        mockUser.setEmail("mock@user.com");
        return mockUser;
    }
    public static PhoneNumber createMockPhoneNumber(){
        var mockNumber=new PhoneNumber();
        mockNumber.setPhoneNumber("+359888888888");
        mockNumber.setUser(createMockAdmin());
        mockNumber.setId(1);
        return mockNumber;
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

    public static List<Comment> createListMockComment() {
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
    public static FilterUserOptions createMockFilterUserOptions() {
        return new FilterUserOptions(
                "User",
                "user@test.com",
                "username",
                "username",
                "asc");
    }


}
