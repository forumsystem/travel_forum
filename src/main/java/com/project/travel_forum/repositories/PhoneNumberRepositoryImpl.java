package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberRepositoryImpl implements PhoneNumberRepository {
    private final SessionFactory sessionFactory;

    @Autowired
    public PhoneNumberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber getByUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Query<PhoneNumber> getUserQuery = session.createQuery(
                    "FROM PhoneNumber p WHERE p.user.id = :userId", PhoneNumber.class);
            getUserQuery.setParameter("userId", user.getId());


            PhoneNumber phoneNumber = getUserQuery.uniqueResult();

            session.getTransaction().commit();

            if (phoneNumber == null) {
                throw new EntityNotFoundException("PhoneNumber", user.getId());
            }

            return phoneNumber;
        }
    }


    @Override
    public void create(PhoneNumber phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.merge(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        PhoneNumber phoneNumberToDelete = getByUser(user);
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(phoneNumberToDelete);
            session.getTransaction().commit();
        }
    }

}
