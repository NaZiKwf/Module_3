package ua.nix.akolovych.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.nix.akolovych.dao.UserDao;
import ua.nix.akolovych.entity.Category;
import ua.nix.akolovych.entity.User;
import ua.nix.akolovych.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private static final Logger LOGGER_WARN = LoggerFactory.getLogger("warn");
    private static final Logger LOGGER_INFO = LoggerFactory.getLogger("info");

    public UserServiceImpl (UserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public void create(User entity) {
        userDao.create(entity);
        LOGGER_INFO.info("User " + entity.getFirstName() + " " + entity.getLastName() + " created");
    }

    @Override
    public void update(User entity) {
        if(userDao.existById(entity.getId())){
            userDao.update(entity);
            LOGGER_INFO.info("User " + entity.getFirstName() + " " + entity.getLastName() + " updated");
        }
        else {
            LOGGER_WARN.warn("User " + entity.getFirstName() + " " + entity.getLastName() + " not found");
            throw  new RuntimeException();
        }
    }

    @Override
    public void delete(Long Id) {
        if(userDao.existById(Id)){
            userDao.delete(Id);
            LOGGER_INFO.info("User with id = " + Id + " deleted");
        }
        else{
            LOGGER_WARN.warn("User with id = " + Id + " not found");
            throw new RuntimeException();
        }
    }

    @Override
    public User findById(Long id) {
        User user = userDao.findById(id);
        if(user==null){
            LOGGER_WARN.warn("User with id = " + id + " not found");
            throw new RuntimeException();
        }
        LOGGER_INFO.info("User with id = " + id + " found");
        return user;
    }

    @Override
    public List<User> findAll() {
       return userDao.findAll();
    }
}
