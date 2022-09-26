package springbootcrudjs.service;

import springbootcrudjs.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.SortedSet;

public interface AppService extends UserDetailsService {

    List<User> findAllUsers();

     User findUserById(int id);

     User findUserByUsername(String email);

     void saveUser(User user, SortedSet<String> rolesList);

     void updateUser(User user, SortedSet<String> rolesList);

     void deleteUserById(int id);

     User passwordCoder(User user);

}
