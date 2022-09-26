package springbootcrudjs.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import springbootcrudjs.dao.RoleDao;
import springbootcrudjs.dao.UserDao;
import springbootcrudjs.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppServiceImpl implements AppService {

    private final RoleDao roleDao;
    private final UserDao userDao;

    private final PasswordEncoder passwordEncoder;


    public AppServiceImpl(RoleDao roleDao, UserDao userDao, PasswordEncoder passwordEncoder) {
        this.roleDao = roleDao;
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User passwordCoder(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return user;
    }

    @Override
    public List<User> findAllUsers() {
        return userDao.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userDao.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Transactional
    @Override
    public void saveUser(User user, SortedSet<String> rolesList) {
        for(String role : rolesList) {
            user.addRoleToUser(roleDao.findAllByName(role).get(0));
        }
        userDao.save(passwordCoder(user));
    }

    @Transactional
    @Override
    public void updateUser(User user, SortedSet<String> rolesList) {
        for(String role : rolesList) {
            user.addRoleToUser(roleDao.findAllByName(role).get(0));
        }
        userDao.updateUser(passwordCoder(user));
    }

    @Transactional
    @Override
    public void deleteUserById(int id) {
        userDao.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findUserByUsername(s);
    }
}
