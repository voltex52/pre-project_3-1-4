package springbootcrudjs.service;

import springbootcrudjs.model.User;
import springbootcrudjs.repository.RoleRepository;
import springbootcrudjs.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AppServiceImpl implements AppService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findById(id).get();
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void saveUser(User user, SortedSet<String> rolesList) {
        for(String role : rolesList) {
            user.addRoleToUser(roleRepository.findAllByName(role).get(0));
        }
        userRepository.save(user);
    }

    @Override
    public void deleteUserById(int id) {
        userRepository.deleteById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return findUserByUsername(s);
    }
}
