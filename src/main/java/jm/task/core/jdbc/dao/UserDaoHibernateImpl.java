package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS `preprojectjm`.`user` (\n" +
                "  `id` INT(11) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(50) NULL,\n" +
                "  `lastName` VARCHAR(50) NULL,\n" +
                "  `age` INT(4) NULL,\n" +
                "  PRIMARY KEY (`id`));";
        session.createSQLQuery(sql).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createSQLQuery("DROP TABLE IF EXISTS user").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        User user = new User();
        user.setName(name);
        user.setLastName(lastName);
        user.setAge(age);
        session.save(user);
        transaction.commit();
        session.close();
    }


    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        User user = new User();
        user.setId(id);
        try {
            session.delete(user);
            session.getTransaction().commit();
        } catch (Throwable e) {
            System.out.printf("Пользователя с id = %d нет \n", id);
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAllUsers() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        List<User> users = session.createQuery("from User").list();
        session.getTransaction().commit();
        session.close();
        return users;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionFactory().openSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM User u").executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
