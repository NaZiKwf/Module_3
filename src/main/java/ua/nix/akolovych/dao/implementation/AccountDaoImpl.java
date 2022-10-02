package ua.nix.akolovych.dao.implementation;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.nix.akolovych.dao.AccountDao;
import ua.nix.akolovych.entity.Account;
import ua.nix.akolovych.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class AccountDaoImpl implements AccountDao {

    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    public List<Account> findAllByUserId(Long userId) {
        List<Account> accounts;
        Session session = sessionFactory.openSession();
        accounts = session.createQuery("select a from Account a where a.user.id = :userId", Account.class).setParameter("userId", userId).getResultList();
        session.close();
        return accounts;
    }

    @Override
    public void create(Account entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Account entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Account account = new Account();
        account.setId(id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(account);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean existById(Long id) {
        Account account = findById(id);
        if(account==null){
            return false;
        }
        return true;
    }

    @Override
    public Account findById(Long id) {
        Account account;
        Session session = sessionFactory.openSession();
        account=session.find(Account.class,id);
        session.close();
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts;
        Session session = sessionFactory.openSession();
        accounts = session.createQuery("select a from Account a", Account.class).getResultList();
        session.close();
        return accounts;
    }
}
