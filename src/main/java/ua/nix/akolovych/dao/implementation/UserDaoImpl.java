package ua.nix.akolovych.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.nix.akolovych.dao.UserDao;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.entity.User;
import ua.nix.akolovych.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    public void create(User entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(User entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        User user = new User();
        user.setId(id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean existById(Long id) {
        User user = findById(id);
        if(user == null){
            return false;
        }
        return true;
    }

    @Override
    public User findById(Long id) {
        User user;
        Session session = sessionFactory.openSession();
        user = session.find(User.class, id);
        session.close();
        return user;
    }

    @Override
    public List<User> findAll() {
        List<User> users;
        Session session = sessionFactory.openSession();
        users = session.createQuery("select u from User u", User.class).getResultList();
        session.close();
        return users;
    }
}
