package com.project.travel_forum.repositories;

import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class PostRepositoryImpl implements PostRepository {
    private final SessionFactory sessionFactory;
    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> get() {
        //todo: implement
        return null;
    }

    @Override
    public Post getById(int id) {
        //todo: implement
        return null;
    }

    @Override
    public void createPost(Post post) {
        //todo: implement
    }

    @Override
    public void updatePost(Post post) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
        //todo: implement
    }

    @Override
    public void deletePost(int id) {
        Post postToDelete = getById(id);
        try(Session session =sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(postToDelete);
            session.getTransaction().commit();
        }
      //todo: implement
    }
}
