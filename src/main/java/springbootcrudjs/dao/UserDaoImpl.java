package springbootcrudjs.dao;

import org.springframework.stereotype.Repository;
import springbootcrudjs.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        return entityManager.createQuery("select u from User u").getResultList();
    }

    @Override
    public User findById(int id) {
        return entityManager.find(User.class,id);
    }

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteById(int id) {
//        Query q = entityManager.createQuery("delete from User User where userId = :id");
//        q.setParameter("id",id);
//        q.executeUpdate();
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }
//
//    @Override
//    public void update(int id, User updatedUser) {
//        entityManager.merge(updatedUser);
//    }

    @Override
    public User findByUsername(String username) {
        TypedQuery<User> q = (entityManager.createQuery("select u from User u " +
                "join fetch u.userRoles where u.username = :username",User.class));
        q.setParameter("username",username);
        return q.getResultList().stream().findFirst().orElse(null);
    }
}