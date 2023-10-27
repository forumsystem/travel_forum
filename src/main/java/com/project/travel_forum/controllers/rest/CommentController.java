package com.project.travel_forum.controllers.rest;

import com.project.travel_forum.controllers.AuthenticationHelper;
import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.EntityDuplicateException;
import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.helpers.CommentMapper;
import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.CommentDto;
import com.project.travel_forum.models.User;
import com.project.travel_forum.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;
    private final AuthenticationHelper authenticationHelper;
    private final CommentMapper commentMapper;
    @Autowired
    public CommentController(CommentService commentService, AuthenticationHelper authenticationHelper, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.authenticationHelper = authenticationHelper;
        this.commentMapper = commentMapper;
    }

    @GetMapping("/{id}")
    public Comment getByCommentId(@PathVariable int id) {
        try {
            return commentService.getByCommentId(id);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }

    }
    @PostMapping("/{id}")
    public Comment create(@RequestHeader HttpHeaders headers, @Valid @RequestBody CommentDto commentDto,@PathVariable int id) {
        try {
            User user = authenticationHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromDto(commentDto);
            commentService.create(id, user, comment);
            return comment;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UnauthorizedOperationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public Comment update(@RequestHeader HttpHeaders headers, @Valid @RequestBody CommentDto commentDto,@PathVariable int id){
        try{
            User user = authenticationHelper.tryGetUser(headers);
            Comment comment = commentMapper.fromDto(id,commentDto);
            commentService.update(comment,user);
            return comment;
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (EntityDuplicateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public void delete(@RequestHeader HttpHeaders headers, @PathVariable int id){
        try{
            User user = authenticationHelper.tryGetUser(headers);
            commentService.delete(id,user);
        }catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (AuthorizationException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

}
