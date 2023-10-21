package com.project.travel_forum.services;

import com.project.travel_forum.exceptions.AuthorizationException;
import com.project.travel_forum.exceptions.UnauthorizedOperationException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import com.project.travel_forum.repositories.PostRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

import static com.project.travel_forum.helpers.CheckPermissions.*;

@Service
public class PostServiceImpl implements PostService {

    public static final String USER_IS_NOT_ADMIN = "This user is not admin";
    public static final String BLOCKED_USER_CREATE_ERR = "Blocked users cannot create new posts.";

    private final PostRepositoryImpl postRepository;

    @Autowired
    public PostServiceImpl(PostRepositoryImpl postRepository) {
        this.postRepository = postRepository;
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
    public void createPost(Post post, User user) {

        checkIfBlocked(user);

        post.setCreatedBy(user);
        postRepository.createPost(post);
    }

    @Override
    public void updatePost(Post post, User user) {
        checkIfBlocked(user);
        checkIfSameUser(user,post.getCreatedBy());
        postRepository.updatePost(post);
    }

    @Override
    public void deletePost(int id, User user) {
        // -- TODO -- check if admin or user - admin can delete every post --- @Simona
        checkIfAdmin(user);
        checkIfBlocked(user);
        postRepository.deletePost(id);
    }

    @Override
    public void modifyLike(int id, User user, boolean likeFlag) {
        checkIfBlocked(user);
        Post postToModify=postRepository.getById(id);

        if(likeFlag){
            postToModify.setLikes(user);
        }
        if(!likeFlag){
            postToModify.removeLikes(user);
        }
        postRepository.modifyLike(postToModify);
    }
}
