package ua.nix.akolovych.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nix.akolovych.dao.CategoryDao;
import ua.nix.akolovych.dao.implementation.CategoryDaoImpl;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.service.CategoryService;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {

    private CategoryDao categoryDao;
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public void create(Category entity) {
        categoryDao.create(entity);
    }

    @Override
    public void update(Category entity) {
        if(categoryDao.existById(entity.getId())){
            categoryDao.update(entity);
            LOGGER_INFO.info("Category " + entity.getName() + " updated");
        }
        else {
            LOGGER_WARN.warn("Category " + entity.getName() + " not updated");
            throw new RuntimeException();
        }
    }

    @Override
    public void delete(Long Id) {
        if(categoryDao.existById(Id)){
            categoryDao.delete(Id);
            LOGGER_INFO.info("Category with id = " + Id + " deleted");
        }
        else{
            LOGGER_WARN.warn("Category with id = " + Id + " not deleted");
            throw new RuntimeException();
        }
    }

    @Override
    public Category findById(Long id) {
        Category category = categoryDao.findById(id);
        if(category==null){
            LOGGER_WARN.warn("Category with id = " + id + " not found");
            throw new RuntimeException();
        }
        LOGGER_INFO.info("Category with id = " + id + " found");
        return category;
    }

    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }
}
