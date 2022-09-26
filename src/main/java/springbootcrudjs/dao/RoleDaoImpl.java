package springbootcrudjs.dao;

import org.springframework.stereotype.Repository;
import springbootcrudjs.model.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class RoleDaoImpl implements RoleDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Role> findAllByName(String name) {
        TypedQuery<Role> q = entityManager.createQuery("select r from Role r where r.name in :role", Role.class);
        q.setParameter("role",name);
        return q.getResultList();
    }
}
