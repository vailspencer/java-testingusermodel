package com.lambdaschool.usermodel.services;

import com.lambdaschool.usermodel.UserModelApplication;
import com.lambdaschool.usermodel.models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserModelApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserServiceImplTest
{
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;

    @Before
    public void setUp() throws Exception
    {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findUserById()
    {
        assertEquals("cinnamon",
            userService.findUserById(7)
                .getUsername());
    }

    @Test
    public void findByNameContaining()
    {
        assertEquals(1,
            userService.findByNameContaining("cinna")
                .size());
    }

    @Test
    public void findAll()
    {
        assertEquals(5,
            userService.findAll()
                .size());
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteFailure()
    {
        userService.delete(777);
        assertEquals(4,
            userService.findAll()
                .size());
    }

    @Test
    public void findByNameFailure()
    {
        assertEquals("misskitty",
            userService.findByName("misskitty")
                .getUsername());
    }

    @Test
    public void save()
    {
        User newUser = new User("test one",
            "pass one",
            "myemail@email");

        User addUser = userService.save(newUser);
        assertNotNull(addUser);
        User foundUser = userService.findUserById(addUser.getUserid());
        assertEquals(addUser.getUsername(),
            foundUser.getUsername());
    }

    @Test
    public void update()
    {
        User newUser = new User("test two",
            "pass two",
            "myemail@email");

        User updateUser = userService.update(newUser,
            11);
        assertEquals("pass two",
            updateUser.getPassword());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateFailure()
    {
        User newUser = new User("test two",
            "pass two",
            "myemail@email");

        User updateUser = userService.update(newUser,
            777);
        assertEquals("pass two",
            updateUser.getPassword());
    }


    @Test (expected = EntityNotFoundException.class)
    public void deleteAll()
    {
    }
}