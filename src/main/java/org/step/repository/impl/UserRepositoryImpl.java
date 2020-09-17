package org.step.repository.impl;

import org.hibernate.Session;
import org.hibernate.annotations.QueryHints;
import org.step.entity.User;
import org.step.repository.SessionFactoryCreator;
import org.step.repository.UserRepository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.step.entity.User.USER_MESSAGE_GRAPH;

public class UserRepositoryImpl implements UserRepository {

    private final EntityManager entityManager = SessionFactoryCreator.getEntityManager();

    @Override
    public void updateUsername(String username, Long id) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        User user = session.find(User.class, id);

        user.setUsername(username);

        session.getTransaction().commit();

        session.close();
    }

    @Override
    public User save(User user) {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.persist(user);

        session.getTransaction().commit();

        session.close();

        return user;
    }

    @Override
    public List<User> findAll() {
        Session session = SessionFactoryCreator.getSession();

//        EntityGraph<User> graph = session.createEntityGraph(User.class);
//
//        graph.addAttributeNodes("profile");

        List<User> users = new ArrayList<>();

        session.doWork(connection -> {
//            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM USERS WHERE NAME LIKE %?%");
//
//            preparedStatement.setString(1, "irs");

            Statement statement = connection.createStatement();

//            statement.addBatch("INSERT INTO USERS() values ()");
//
//            int[] ints = statement.executeBatch();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS");

            while (resultSet.next()) {
                users.add(
                        User.builder()
                        .id(resultSet.getLong("id"))
                        .username(resultSet.getString("username"))
                        .password(resultSet.getString("password"))
                        .age(resultSet.getInt("age"))
                        .build()
                );
            }
        });

//        List<User> users = session.createQuery("select u from User u", User.class)
//                .setHint("javax.persistence.fetchgraph", graph)
//                .getResultList();

        session.close();

        return users;
    }

    @Override
    public List<User> findAllUsingSession() {
//        Session session = SessionFactoryCreator.getSession();
//
//        session.beginTransaction();
//
//        List<User> userList = session.createQuery("select u from User u join fetch u.messageList join fetch u.profile", User.class)
//                .setReadOnly(true)
//                .getResultList();
//
//        session.getTransaction().commit();
//
//        session.close();

        /*
        1. join fetch
        2. Subselect @Fetch
        3. Entity Graph
         */

//        return entityManager.createQuery("select u from User u join fetch u.profile", User.class)
//                .setHint(QueryHints.READ_ONLY, true)
//                .getResultList();

        // First approach
        List<EntityGraph<? super User>> entityGraphs = entityManager.getEntityGraphs(User.class);

        EntityGraph<? super User> entityGraph = entityGraphs
                .stream()
                .filter(eg -> eg.getName().equalsIgnoreCase(USER_MESSAGE_GRAPH))
                .findAny().orElse(null);

        // Second approach
        EntityGraph<?> userMessageGraph = entityManager.getEntityGraph(USER_MESSAGE_GRAPH);

        // Third approach
        EntityGraph<User> userMessageDynamicGraph = entityManager.createEntityGraph(User.class);

        userMessageDynamicGraph.addAttributeNodes("messageList");

        // LoadGraph - загружает не только то, что мы прописали в графе, но так же все EAGER связи
        // FetchGraph - загружает только то, что мы прописали в графе

        return entityManager.createQuery("select u from User u", User.class)
                .setHint(QueryHints.READ_ONLY, true)
                .setHint("javax.persistence.loadgraph", userMessageDynamicGraph)
                .getResultList();
    }

    @Override
    public void delete(Long id) {
        Session session = SessionFactoryCreator.getSession();

        session.beginTransaction();

        session.createQuery("delete from Message m where m.user.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.createQuery("delete from Profile p where p.user.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.createQuery("delete from User u where u.id=:id")
                .setParameter("id", id)
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }
}
