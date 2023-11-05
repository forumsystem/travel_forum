package com.project.travel_forum.services;

import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.CommentRepository;
import com.project.travel_forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.travel_forum.helpers.CheckPermissions.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public long getPostCount() {
        return postRepository.getPostCount();
    }

    @Override
    public List<Post> get(FilterOptions filterOptions) {
        return postRepository.get(filterOptions);
    }

    @Override
    public Post getById(int id) {
        return postRepository.getById(id);
    }

    @Override
    public List<Post> getTop10MostCommented() {
        return postRepository.getTop10MostCommented();
    }

    @Override
    public List<Post> getTop10MostLiked() {
        return postRepository.getTop10MostLiked();
    }

    @Override
    public List<Post> getTop10MostResent() {
        return postRepository.getTop10MostRecent();
    }

    @Override
    public void createPost(Post post, User user) {

        checkIfBlocked(user);

        post.setCreatedBy(user);
        postRepository.createPost(post);
    }

    @Override
    public void updatePost(Post post, User user) {
        checkIfBlocked(user);
        checkIfSameUserOrAdmin(user,post);
        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(int id, User user) {
        Post post = getById(id);
        checkIfSameUserOrAdmin(user, post);
        checkIfBlocked(user);
        if (!commentRepository.getByPost(post).isEmpty()) {
            commentRepository.deleteAllCommentsByPost(post);
        }
        postRepository.deletePost(id);
    }

    @Override
    public void modifyLike(int id, User user, boolean likeFlag) {
        checkIfBlocked(user);
        Post postToModify = postRepository.getById(id);

        if (likeFlag) {
            postToModify.setLikes(user);
        }
        if (!likeFlag) {
            postToModify.removeLikes(user);
        }
        postRepository.modifyLike(postToModify);
    }
}
