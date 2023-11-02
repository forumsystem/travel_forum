package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.Comment;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public CommentRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Comment> getByPost(Post post) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            Query<Comment> query = session.createQuery("from Comment c where c.post = :post", Comment.class);
            query.setParameter("post", post);
            return query.list();
        }
    }

    @Override
    public List<Comment> getByUser(User user) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            Query<Comment> query = session.createQuery("from Comment c where c.createdBy = :user", Comment.class);
            query.setParameter("user", user);
            return query.list();
        }
//        return null;
        //-- TODO --
    }

    @Override
    public Comment getByCommentId(int id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            Comment comment = session.get(Comment.class, id);
            if (comment == null) {
                throw new EntityNotFoundException("Comment", id);
            }
            return comment;
        }
    }


    @Override
    public void create(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Comment comment) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(comment);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(int id) {
        Comment commentToDelete = getByCommentId(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(commentToDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteAllCommentsByPost(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Query deleteQuery = session.createNativeQuery("DELETE FROM comments WHERE post_id = :id");
            deleteQuery.setParameter("id", post.getId());
            deleteQuery.executeUpdate();
            session.getTransaction().commit();
        }
    }


}
