package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.FilterOptions;
import com.project.travel_forum.models.Post;
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

    //can add how many posts to show (as count) OR offset - from which index on? - good to have to be RESTful

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


            queryString.append(generateOrderBy(filterOptions));
            Query<Post> query = session.createQuery(queryString.toString(), Post.class);
            query.setProperties(params);
            return query.list();
        }

    }

    private String generateOrderBy(FilterOptions filterOptions) {
        if (filterOptions.getSortBy().isEmpty()) {
            return "";
        }
        String orderBy = "";
        switch (filterOptions.getSortBy().get()) {
            case "title":
                orderBy = "title";
                break;
            case "content":
                orderBy = "content";
                break;
            case "createdBy":
                orderBy = "createdBy.username";
                break;
            default:
                return "";
        }
        orderBy = String.format(" order by %s", orderBy);
        if (filterOptions.getSortOrder().isPresent() && filterOptions.getSortOrder().get().equalsIgnoreCase("desc")) {
            orderBy = String.format("%s desc", orderBy);
        }
        return orderBy;
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
    public List<Post> getTop10MostCommented() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT posts, COUNT(comments.id) AS comment_count " +
                    "FROM Post posts " +
                    "LEFT JOIN Comment comments ON posts.id = comments.post.id " +
                    "GROUP BY posts " +
                    "ORDER BY comment_count DESC";

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setMaxResults(10);

            List<Post> result = new ArrayList<>();
            List<Object[]> resultList = query.list();

            for (Object[] row : resultList) {
                Post post = (Post) row[0];
                result.add(post);
            }
            return result;
        }
    }

    @Override
    public List<Post> getTop10MostRecent() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT posts " +
                    "FROM Post posts " +
                    "ORDER BY posts.timestamp DESC";

            Query<Post> query = session.createQuery(hql, Post.class);
            query.setMaxResults(10);

            return query.list();
        }
    }

    @Override
    public List<Post> getTop10MostLiked() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT posts, " +
                    "(SELECT COUNT(*) FROM posts.likes l WHERE l.id = posts.id) AS likes_count " +
                    "FROM Post posts";
//                    "ORDER BY likes_count DESC" todo: add the ORDER BY feature

            Query<Object[]> query = session.createQuery(hql, Object[].class);
            query.setMaxResults(10);

            List<Post> result = new ArrayList<>();
            List<Object[]> resultList = query.list();

            for (Object[] row : resultList) {
                Post post = (Post) row[0];
                result.add(post);
            }
            return result;
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

    @Override
    public void modifyLike(Post post) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(post);
            session.getTransaction().commit();
        }
    }


}
