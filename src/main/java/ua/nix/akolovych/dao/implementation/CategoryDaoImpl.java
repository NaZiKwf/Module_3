package ua.nix.akolovych.dao.implementation;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.nix.akolovych.dao.CategoryDao;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.utils.HibernateSessionFactoryUtil;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();

    @Override
    public void create(Category entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void update(Category entity) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.merge(entity);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void delete(Long id) {
        Category category = new Category();
        category.setId(id);
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(category);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public boolean existById(Long id) {
        Category category = findById(id);
        if(category == null){
            return false;
        }
        return true;
    }

    @Override
    public Category findById(Long id) {
        Category category;
        Session session = sessionFactory.openSession();
        category = session.find(Category.class, id);
        session.close();
        return category;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories;
        Session session = sessionFactory.openSession();
        categories =  session.createQuery("select c from Category c", Category.class).getResultList();
        session.close();
        return categories;
    }
}
