package com.project.travel_forum.repositories;

import com.project.travel_forum.exceptions.EntityNotFoundException;
import com.project.travel_forum.models.PhoneNumber;
import com.project.travel_forum.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PhoneNumberRepositoryImpl implements PhoneNumberRepository{
    private final SessionFactory sessionFactory;

    @Autowired
    public PhoneNumberRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public PhoneNumber get(int id) {
        try (
                Session session = sessionFactory.openSession()
        ) {
            PhoneNumber phoneNumber = session.get(PhoneNumber.class, id);
            if (phoneNumber == null) {
                throw new EntityNotFoundException("Phone number", id);
            }
            return phoneNumber;
        }
    }

    @Override
    public void create(PhoneNumber phoneNumber) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(PhoneNumber phoneNumber) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(phoneNumber);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(User user) {
        PhoneNumber phoneNumberToDelete = get(user.getId());
        try(Session session =sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(phoneNumberToDelete);
        }
    }
}
