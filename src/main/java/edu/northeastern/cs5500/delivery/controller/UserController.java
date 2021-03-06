package edu.northeastern.cs5500.delivery.controller;

import edu.northeastern.cs5500.delivery.model.User;
import edu.northeastern.cs5500.delivery.repository.GenericRepository;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

@Singleton
@Slf4j
public class UserController {
    private final GenericRepository<User> users;

    @Inject
    UserController(GenericRepository<User> userRepository) {
        users = userRepository;
        log.info("UserController > construct");

        if (users.count() > 0) {
            return;
        }

        log.info("UserController > construct > adding default users");

        final User defaultUser1 = new User();

        defaultUser1.setFirstName("Shaun");
        defaultUser1.setLastName("Ho");
        defaultUser1.setEmail("shaun@hotmail.com");
        defaultUser1.setAddress("A street");
        defaultUser1.setCity("Seattle");
        defaultUser1.setUsername("shaunho");
        defaultUser1.setPhoneNumber(1234567891L);
        defaultUser1.setState("WA");
        defaultUser1.setZip("12345");
        defaultUser1.setPassword("AABABABA");

        final User defaultUser2 = new User();

        defaultUser2.setFirstName("Emily");
        defaultUser2.setLastName("Chiang");
        defaultUser2.setEmail("emily@hotmail.com");
        defaultUser2.setAddress("D street");
        defaultUser2.setCity("Seattle");
        defaultUser2.setUsername("emilychiang");
        defaultUser2.setPhoneNumber(1234567891L);
        defaultUser2.setState("WA");
        defaultUser2.setZip("12345");
        defaultUser2.setPassword("ABABA");

        try {
            addUser(defaultUser1);
            addUser(defaultUser2);
        } catch (Exception e) {
            log.error("UserController > construct > adding default User > failure?");
            e.printStackTrace();
        }
    }

    @Nullable
    public User getUser(@Nonnull ObjectId uuid) {
        log.debug("UserController > getUser({})", uuid);
        return users.get(uuid);
    }

    @Nonnull
    public Collection<User> getUsers() {
        log.debug("UserController > getUsers()");
        return users.getAll();
    }

    @Nonnull
    public User addUser(@Nonnull User user) throws ExceptionClass {
        log.debug("UserController > addUser(...)");
        if (!user.isValid()) {
            throw new ExceptionClass("InvalidUserException");
        }
        Collection<User> allUsers = users.getAll();
        ObjectId id = user.getId();
        if (id != null && users.get(id) != null) {
            throw new ExceptionClass("DuplicateKeyExecption");
        }

        if (!checkPhoneLength(user)) {
            throw new ExceptionClass("PhoneNumberException");
        }

        if (checkDuplicateUsername(allUsers, user)) {
            throw new ExceptionClass("DuplicateUsernameException");
        }

        if (!checkZipCodeLength(user)) {
            throw new ExceptionClass("InvalidZipcodeException");
        }

        return users.add(user);
    }

    private boolean checkDuplicateUsername(Collection<User> users, User userToCheck) {
        for (User user : users) {
            if (user.getUsername().equals(userToCheck.getUsername())) {
                return true;
            }
        }
        return false;
    }

    private boolean checkPhoneLength(User user) {
        if (Long.toString(user.getPhoneNumber()).length() != 10) {
            return false;
        }
        return true;
    }

    private boolean checkZipCodeLength(User user) {
        if (user.getZip().length() != 5) {
            return false;
        }
        return true;
    }

    public void updateUser(@Nonnull User user) throws ExceptionClass {
        log.debug("UserController > updateUser(...)");

        if (!checkPhoneLength(user)) {
            throw new ExceptionClass("PhoneNumberException");
        }

        if (!checkZipCodeLength(user)) {
            throw new ExceptionClass("InvalidZipcodeException");
        }

        users.update(user);
    }

    public void deleteUser(@Nonnull ObjectId id) throws ExceptionClass {
        log.debug("UserController > deleteUser(...)");
        users.delete(id);
    }
}
