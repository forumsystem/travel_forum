package com.project.travel_forum;

import com.project.travel_forum.models.FilterUserOptions;
import com.project.travel_forum.models.User;

public class UserHelpers {

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

    public static FilterUserOptions createMockFilterUserOptions() {
        return new FilterUserOptions(
                "User",
                "user@test.com",
                "username",
                "username",
                "asc");
    }


}


