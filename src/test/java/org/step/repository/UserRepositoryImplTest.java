package org.step.repository;

import org.hibernate.Session;
import org.hibernate.graph.RootGraph;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.step.entity.Course;
import org.step.entity.Message;
import org.step.entity.Profile;
import org.step.entity.User;
import org.step.repository.impl.UserRepositoryImpl;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryImplTest {

    private final UserRepository userRepositoryImpl = new UserRepositoryImpl();
    private final List<Long> userIdList = new ArrayList<>();

    @Before
    public void setup() {
        System.out.println("******************SETUP*******************");

        EntityManager entityManager = SessionFactoryCreator.getEntityManager();

        entityManager.getTransaction().begin();

        for (int i = 0; i < 10; i++) {
            ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

            Validator validator = validatorFactory.getValidator();

            User first = User.builder()
                    .username("first " + i)
                    .age(25)
                    .build();

            Set<ConstraintViolation<User>> constraintViolationSet = validator.validate(first);

            constraintViolationSet.forEach(cv -> System.out.println(cv.getMessage()));

            Profile profile = Profile.builder()
                    .fullName("first full name " + i)
                    .graduation("KBTU")
                    .abilities("good speaking")
                    .workExperience("student")
                    .build();

            Message message = Message.builder()
                    .id(null)
                    .message("message " + i)
                    .build();

            first.addMessage(message);
            first.addProfile(profile);

            entityManager.persist(first);

//        session.flush();

        /*
        flush - сохранение в БД

        1. Вызываем на прямую flush()
        2. При вызове commit()

        Можно настроить
        1. Перед каждой Query
        2. AUTO - переодически флашит изменения
         */
            entityManager.flush();

            userIdList.add(first.getId());

        }
        entityManager.getTransaction().commit();

        System.out.println("******************SETUP*******************");
    }

    @After
    public void clean() {
        Session session = SessionFactoryCreator.getSession();

        session.getTransaction().begin();

        session.createQuery("delete from Message m")
                .executeUpdate();

        session.createQuery("delete from Profile p")
                .executeUpdate();

        session.createQuery("delete from User u")
                .executeUpdate();

        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void saveUserTest() {
        final String username = "second";

        User user = User.builder()
                .username(username)
                .build();

        User savedUser = userRepositoryImpl.save(user);

        Session session = SessionFactoryCreator.getSession();

        User userFromDB = session.find(User.class, savedUser.getId());

        session.close();

        Assert.assertNotNull(userFromDB); // not null
        Assert.assertEquals(userFromDB.getId(), savedUser.getId()); // id equals
        Assert.assertEquals(username, user.getUsername()); // username equals
    }

    @Test
    public void updateUsernameTest() {
        final String newUsername = "new username";

        userRepositoryImpl.updateUsername(newUsername, userIdList.get(0));

        Session session = SessionFactoryCreator.getSession();

        User user = session.find(User.class, userIdList.get(0));

        session.close();

        Assert.assertNotNull(user);
        Assert.assertEquals(userIdList.get(0), user.getId());
        Assert.assertEquals(newUsername, user.getUsername());
    }

    @Test
    public void findAllTest() {
        List<User> allUsers = userRepositoryImpl.findAll();

        Assert.assertNotNull(allUsers);
        Assert.assertFalse(allUsers.isEmpty());
        Assert.assertTrue(allUsers.contains(User.builder().id(userIdList.get(0)).build()));
    }

    @Test
    public void deleteUser() {
        final int zero = 0;
        final Long id = userIdList.get(zero);

        userRepositoryImpl.delete(id);

        Session session = SessionFactoryCreator.getSession();

        BigInteger userSingleResult = (BigInteger) session.createNativeQuery("SELECT count(1) FROM USERS WHERE ID=:id")
                .setParameter("id", id)
                .getSingleResult();

        BigInteger profileSingleResult = (BigInteger) session.createNativeQuery("SELECT count(1) FROM PROFILES WHERE USER_ID=:id")
                .setParameter("id", id)
                .getSingleResult();

        BigInteger messageSingleResult = (BigInteger) session.createNativeQuery("SELECT count(1) FROM MESSAGES WHERE USER_ID=:id")
                .setParameter("id", id)
                .getSingleResult();

        Assert.assertEquals(zero, userSingleResult.intValue());
        Assert.assertEquals(zero, profileSingleResult.intValue());
        Assert.assertEquals(zero, messageSingleResult.intValue());
    }

    @Test
    /*
    Задание: Достать из Базы Данных всех пользователей с профилями
     */
    public void findAllUsingSessionTest() {
//        Session session = SessionFactoryCreator.getSession();
//
//        session.beginTransaction();
//
//        List<User> users = session.createQuery("select u from User u join fetch u.profile", User.class)
//                .setReadOnly(true)
//                .getResultList();
//
//        users.forEach(usr -> System.out.println(usr.getMessageList().get(0).getMessage()));
//
//        Assert.assertNotNull(users);
//        Assert.assertFalse(users.isEmpty());
//
//        session.getTransaction().commit();
//
//        session.close();

        List<User> users = userRepositoryImpl.findAllUsingSession();

        users.forEach(usr -> System.out.println(usr.getProfile().getGraduation()));

        users.forEach(usr -> System.out.println(usr.getMessages().get(0).getMessage()));
    }

    @Test
    public void shouldAddCourseToUser() {
        Course course = Course.builder()
                .id()
                .topic("topic")
                .courseDescription("course description")
                .build();

        User user = userRepositoryImpl.findAll().get(0);

        Session session = SessionFactoryCreator.getSession();

        session.beginTransaction();

        session.persist(course);

        session.flush();

        user.addCourse(course);

        session.getTransaction().commit();

        session.close();
    }

    @Test
    public void getAllCoursesByUser() {
        List<User> all = userRepositoryImpl.findAll();

        User user = all.get(0);

        Course build = Course.builder().id().topic("dasd").courseDescription("dsad").build();

        Session session = SessionFactoryCreator.getSession();

        session.beginTransaction();

        session.persist(build);

        session.flush();

        user.addCourse(build);

        session.flush();

//        User singleResult = session.createQuery("select u from User u join fetch u.courseSet where u.id=:id and u.courseSet.size > 0", User.class)
//                .setParameter("id", all.get(0).getId())
//                .getSingleResult();

        RootGraph<User> entityGraph = session.createEntityGraph(User.class);

        entityGraph.addAttributeNode("courseSet");

        User singleResult1 = session.createQuery("select u from User u where u.id=:id", User.class)
                .setParameter("id", all.get(0).getId())
                .applyFetchGraph(entityGraph)
                .getSingleResult();

        List<Object> objectList = session.createNativeQuery("SELECT COURSE_ID FROM USER_COURSE WHERE USER_ID=?")
                .setParameter(1, all.get(0).getId())
                .getResultList();

        session.doWork(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM COURSES WHERE ID IN (SELECT COURSE_ID FROM USER_COURSE WHERE USER_ID = ?)"
            );
            preparedStatement.setLong(1, user.getId());

            ResultSet resultSet = preparedStatement.getResultSet();
        });

        System.out.println(objectList.toString());

        List<Course> resultList = session.createQuery("select c from Course c where c.id in :id", Course.class)
                .setParameter("id", objectList)
                .getResultList();

        System.out.println(resultList.toString());

        session.getTransaction().commit();

        session.close();
    }
}
