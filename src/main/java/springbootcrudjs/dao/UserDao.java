package springbootcrudjs.dao;

import springbootcrudjs.model.User;

import java.util.List;

public interface UserDao {
    List<User>findAll();
    User findById(int id);
    void save(User user);
    void deleteById(int id);
    void updateUser(User user);
    User findByUsername(String username);
//    void update(int id, User updateUser);
}
