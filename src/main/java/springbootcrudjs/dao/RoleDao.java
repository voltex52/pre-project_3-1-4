package springbootcrudjs.dao;

import springbootcrudjs.model.Role;
import java.util.List;


public interface RoleDao {
    List<Role> findAllByName(String name);
}
