package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.FilterUserOptions;
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
public class UserRepositoryImpl implements UserRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public long getUserCount() {
        try (Session session = sessionFactory.openSession()) {
            String hql = "SELECT COUNT(*) FROM User";

            Query<Long> query = session.createQuery(hql, Long.class);

            List<Long> resultList = query.list();

            return resultList.get(0);
        }
    }

    @Override
    public List<User> get(FilterUserOptions filterUserOptions) {
        try (
                Session session = sessionFactory.openSession()) {
            List<String> filters = new ArrayList<>();
            Map<String, Object> params = new HashMap<>();

            filterUserOptions.getFirstName().ifPresent(value -> {
                filters.add(" firstName like :firstName ");
                params.put("firstName", String.format("%%%s%%", value));
            });
            filterUserOptions.getEmail().ifPresent(value -> {
                filters.add(" email = :email ");
                params.put("email", value);
            });
            filterUserOptions.getUsername().ifPresent(value -> {
                filters.add(" username like :username ");
                params.put("username", String.format("%%%s%%", value));
            });
            StringBuilder queryString = new StringBuilder("from User ");
            if (!filters.isEmpty()) {
                queryString.append(" where ")
                        .append(String.join(" and ", filters));
            }

            Query<User> query = session.createQuery(queryString.toString(), User.class);
            query.setProperties(params);
            return query.list();
        }
    }

    @Override
    public List<User> getAllBlockUser() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where isBlocked = :isBlocked", User.class);
            query.setParameter("isBlocked", true);
            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "status", "block");
            }
            return result;
        }
    }

    @Override
    public List<User> getAllAdmins() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where isAdmin = :isAdmin", User.class);
            query.setParameter("isAdmin", true);
            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "status", "admin");
            }
            return result;
        }
    }

    @Override
    public User getById(int id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            User user = session.get(User.class, id);
            if (user == null) {
                throw new EntityNotFoundException("User", id);
            }
            return user;
        }
    }

    @Override
    public User getByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where email = :email", User.class);
            query.setParameter("email", email);
            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "email", email);
            }
            return result.get(0);
        }
    }

    @Override
    public User getByUsername(String username) {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User where username = :username", User.class);
            query.setParameter("username", username);
            List<User> result = query.list();
            if (result.isEmpty()) {
                throw new EntityNotFoundException("User", "username", username);
            }
            return result.get(0);
        }
    }

    @Override
    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public void deleteUser(int id) {
        User userToDelete = getById(id);
        User deletedUser = getByUsername("deletedUser");
        int deletedUserId = deletedUser.getId();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            deleteLikes(id, session);

            updatePostAndComment(id, session, deletedUserId);

            session.remove(userToDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public void modifyPermissions(User userToModify) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(userToModify);
            session.getTransaction().commit();
        }
    }

    @Override
    public void modifyBlock(User userToModify) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(userToModify);
            session.getTransaction().commit();
        }
    }


    private static void deleteLikes(int id, Session session) {
        Query<User> deleteLikesQuery = session.createNativeQuery("DELETE FROM likes WHERE user_id = :userId",
                User.class);
        deleteLikesQuery.setParameter("userId", id);
        deleteLikesQuery.executeUpdate();
    }

    private static void updatePostAndComment(int id, Session session, int deletedUserId) {
        Query<User> updatePostsAndCommentsQuery = session.createNativeQuery(
                "UPDATE posts SET user_id = :deletedUserId WHERE user_id = :userId", User.class);
        updatePostsAndCommentsQuery.setParameter("deletedUserId", deletedUserId);
        updatePostsAndCommentsQuery.setParameter("userId", id);
        updatePostsAndCommentsQuery.executeUpdate();
    }

}













