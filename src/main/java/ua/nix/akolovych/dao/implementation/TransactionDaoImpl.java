package ua.nix.akolovych.dao.implementation;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.nix.akolovych.dao.TransactionDao;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.entity.Transaction;
import ua.nix.akolovych.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    public void create(Transaction entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Transaction entity) {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public boolean existById(Long id) {
        Transaction transaction = findById(id);
        if(transaction == null){
            return false;
        }
        return true;
    }

    @Override
    public Transaction findById(Long id) {
        Transaction transaction;
        Session session = sessionFactory.openSession();
        transaction = session.find(Transaction.class, id);
        session.close();
        return transaction;
    }

    @Override
    public List<Transaction> findAll() {
        List<Transaction> transactions;
        Session session = sessionFactory.openSession();
        transactions = session.createQuery("select t from Transaction t", Transaction.class).getResultList();
        session.close();
        return transactions;
    }

    @Override
    public List<Transaction> findAllByAccountId(Long accountId) {
        List<Transaction> transactions;
        Session session = sessionFactory.openSession();
        transactions = session.
                createQuery("select t from Transaction t where t.account.id = :accountId", Transaction.class)
                .setParameter("accountId", accountId).getResultList();
        session.close();
        return transactions;
    }


    @Override
    public List<Transaction> findAllByCategoryId(Long categoryId) {
        List<Transaction> transactions;
        Session session = sessionFactory.openSession();
        transactions = session.
                createQuery("select t from Transaction t where t.category.id = :categoryId", Transaction.class)
                .setParameter("categoryId", categoryId).getResultList();
        session.close();
        return transactions;
    }

    @Override
    public List<Transaction> findAllByUserId(Long userId) {
        List<Transaction> transactions;
        Session session = sessionFactory.openSession();
        transactions = session.
                createQuery("select t from Transaction t inner join Account a on t.account.id = a.id where a.user.id = :userId", Transaction.class).setParameter("userId", userId).getResultList();
        session.close();
        return transactions;
    }
}
