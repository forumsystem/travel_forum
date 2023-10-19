package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.Post;
import com.project.travel_forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PostRepositoryImpl implements PostRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public PostRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Post> get(FilterOptions filterOptions) {
        try (
                Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterOptions.getTitle().ifPresent(value -> {
                filters.add(" title like :title ");
                params.put("title", String.format("%%%s%%", value));
            });

            filterOptions.getContent().ifPresent(value -> {
                filters.add(" content like :content ");
                params.put("content", String.format("%%%s%%", value));
            });

            filterOptions.getCreatedBy().ifPresent(value -> {
                filters.add(" createdBy.username = :createdBy ");
                params.put("createdBy", value);
            });

            StringBuilder queryString = new StringBuilder("from Post ");
            if (!filters.isEmpty()) {
                queryString.append(" where ")
                        .append(String.join(" and ", filters));
            }


            //todo: comments after Marian

            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            query.setProperties(params);
            return query.list();
        }

    }

    @Override
    public Post getById(int id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            Post post = session.get(Post.class, id);
            if (post == null) {
                throw new EntityNotFoundException("Post", id);
            }
            return post;
        }
    }

    @Override
    public void createPost(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updatePost(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deletePost(int id) {
        Post postToDelete = getById(id);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(postToDelete);
            session.getTransaction().commit();
        }
    }
}
