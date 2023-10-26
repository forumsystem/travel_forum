package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.project.travel_forum.Helpers.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    //TODO: --- @Dora

    @Mock
    private PostRepository postRepository;


    @InjectMocks
    private PostServiceImpl postService;


    @Test
    void get_Should_CallRepository() {
        // Arrange
        FilterOptions mockFilterOptions = createMockFilterOptions();
        Mockito.when(postRepository.get(mockFilterOptions))
                .thenReturn(null);

        // Act
        postService.get(mockFilterOptions);

        // Assert
        Mockito.verify(postRepository, Mockito.times(1))
                .get(mockFilterOptions);
    }

    @Test
    public void get_Should_ReturnListOfPosts(){
        // Arrange
        List<Post> posts = new ArrayList<>();
        posts.add(createMockPost());
        FilterOptions mockFilterOptions = createMockFilterOptions();
        Mockito.when(postRepository.get(mockFilterOptions)).thenReturn(posts);

        // Act
        List<Post> result = postService.get(mockFilterOptions);

        // Assert
        Assertions.assertEquals(result.size(), 1);
    }
    @Test
    public void get_Should_ReturnSinglePost_When_PostExists(){
        // Arrange
        Post mockPost = createMockPost();
        Mockito.when(postRepository.getById(Mockito.anyInt())).thenReturn(mockPost);

        // Act
        Post expectPost = postService.getById(1);

        // Assert
        Assertions.assertEquals(mockPost, expectPost);
    }



    @Test
    public void create_Should_ThrowException_When_Post_CreatedBy_BlockedUser(){

        // Arrange
        User user = createMockUser();
        User blockUser = createMockBlockedUser();
        Post mockPost = createMockPost();

        // Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.createPost(mockPost, blockUser));
        Assertions.assertDoesNotThrow(
                () -> postService.createPost(mockPost, user));
    }

    @Test
    public void update_Should_ThrowException_When_Post_CreatedBy_BlockedUser(){
        // Arrange
        User user = createMockUser();
        User blockUser = createMockBlockedUser();
        Post mockPost = createMockPost();

        // Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.updatePost(mockPost, blockUser));
        Assertions.assertDoesNotThrow(
                () -> postService.updatePost(mockPost, user));
    }

    @Test
    public void update_Should_ThrowException_When_IsUserNotCreatedPost(){
        // Arrange
        Post postToUpdate = createMockPost();
        User mockUser = createMockUser();
        mockUser.setId(2);

        // Act, Assert
        Assertions.assertThrows(AuthorizationException.class,
                () -> postService.updatePost(postToUpdate, mockUser));
    }

    @Test
    public void update_Should_UpdatePost_WhenUserIsCreator(){
        Post postToUpdate = createMockPost();
        User mockUser = createMockUser();

        postService.updatePost(postToUpdate, mockUser);

        Mockito.verify(postRepository, Mockito.atLeastOnce()).updatePost(postToUpdate);
    }

    @Test
    void delete_Should_CallRepository_When_UserIsCreator() {
      //-- TODO --
    }
    @Test
    void delete_Should_CallRepository_When_UserIsAdmin() {
       //-- TODO --
    }
    @Test
    void delete_Should_ThrowException_When_UserIsNotAdminOrCreator() {
        //-- TODO --
    }

}
