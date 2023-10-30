package com.project.travel_forum.services;

import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.CommentRepository;
import com.project.travel_forum.repositories.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.project.travel_forum.MockHelpers.*;
import static com.project.travel_forum.MockHelpers.createMockComment;
import static com.project.travel_forum.MockHelpers.createMockPost;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @Mock
    CommentRepository mockRepository;
    @Mock
    PostRepository postRepository;
    @InjectMocks
    CommentServiceImpl commentService;

    @Test
    public void getByCommentId_Should_ReturnComment_When_MatchExists(){
        //Arrange
        Comment mockComment =createMockComment();
        when(mockRepository.getByCommentId(1))
                .thenReturn(mockComment);

        //Act
        Comment result= commentService.getByCommentId(1);

        //Assert
        Assertions.assertEquals(1,result.getId());
        Assertions.assertEquals("Hello, this is a test comment!",result.getComment());
    }
    @Test
    public void testGetByCommentId() {
        //Arrange
        Comment expectedComment = createMockComment();
        Mockito.when(mockRepository.getByCommentId(1)).thenReturn(expectedComment);

        //Act
        Comment result = commentService.getByCommentId(1);

        //Assert
        Assertions.assertEquals(expectedComment, result);
    }

    @Test
    public void testCreateComment() {
        //Arrange
        User mockUser = createMockUser();
        Comment comment = createMockComment();
        Post post = createMockPost();

        //Act
        Mockito.when(postRepository.getById(1)).thenReturn(post);

        commentService.create(1, mockUser, comment);

        //Assert
        Mockito.verify(mockRepository).create(comment);
    }

    @Test
    void getById_Should_ReturnComment_When_UserIsAdminOrSameUser() {
        // Arrange
        Comment comment = createMockComment();

        Mockito.when(mockRepository.getByCommentId(Mockito.anyInt()))
                .thenReturn(comment);

        // Act
        Comment result=commentService.getByCommentId(1);

        // Assert
        Assertions.assertEquals(comment, result);
        Mockito.verify(mockRepository, Mockito.times(1))
                .getByCommentId(1);
    }
        @Test
    public void testUpdateComment() {
        //Arrange
        User mockUser = createMockUser();
        Comment comment = createMockComment();
        comment.setCreatedBy(mockUser);

        //Act
        commentService.update(comment, mockUser);

        //Assert
        Mockito.verify(mockRepository).update(comment);
    }

    @Test
    public void testDeleteComment() {
        //Arrange
        User mockAdmin = createMockAdmin();

        //Act
        commentService.delete(1, mockAdmin);

        //Assert
        Mockito.verify(mockRepository).delete(1);
    }

}
