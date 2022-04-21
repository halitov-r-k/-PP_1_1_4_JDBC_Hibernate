package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.closeSessionFactory;

public class UserDaoHibernateImpl implements UserDao {

    private final SessionFactory sessionFactory = Util.getConnection();

    public UserDaoHibernateImpl() { }
    private void rollback(Transaction transaction){
        if (transaction != null) {
            transaction.rollback();
        }
    }
    @Override
    public void createUsersTable() {
        String request = "CREATE TABLE IF NOT EXISTS db.users" +
                " (id mediumint not null auto_increment, name VARCHAR(50), " +
                "lastname VARCHAR(50), " +
                "age tinyint, " +
                "PRIMARY KEY (id))";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(request).executeUpdate();
            transaction.commit();
            System.out.println("Table created");
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback(transaction);
        }
    }

    @Override
    public void dropUsersTable() {
        String request = "Drop table if exists db.users";
        Transaction transaction = null;
        try ( Session session = sessionFactory.openSession()){
            session.createSQLQuery(request).executeUpdate();
            transaction = session.beginTransaction();
            transaction.commit();
            System.out.println("Table  dropped");
        } catch (Exception e) {
            e.printStackTrace();
            rollback(transaction);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()){
            session.save(new User(name, lastName, age));
            transaction = session.beginTransaction();
            transaction.commit();
            System.out.println("User с именем – " + name + " добавлен в базу данных");
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback(transaction);
        }
    }

    @Override
    public void removeUserById(long id) {

        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            transaction = session.beginTransaction();
            session.delete(session.get(User.class, id));
            transaction.commit();
            System.out.println("User deleted");
        } catch (Exception e) {
            e.printStackTrace();
            rollback(transaction);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = null;
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            CriteriaQuery<User> criteriaQuery = session.getCriteriaBuilder().createQuery(User.class);
            criteriaQuery.from(User.class);
            transaction.commit();
            userList = session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback(transaction);
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        String request = "TRUNCATE TABLE db.users;";
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.createNativeQuery(request).executeUpdate();

            transaction.commit();
            System.out.println("Table cleaned");
        } catch (HibernateException e) {
            e.printStackTrace();
            rollback(transaction);
        }
    }

    @Override
    public void closeConnect() {closeSessionFactory();}
}
