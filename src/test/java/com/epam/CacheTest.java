package com.epam;

import com.epam.cfg.HibernateTestConfiguration;
import com.epam.dao.UserDao;
import com.epam.entity.Role;
import com.epam.entity.User;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HibernateTestConfiguration.class})

public class CacheTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserDao userDao;

    private User user;

    @Before
    public void setUp() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String name = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        user = new User();
        user.setName(name);
        user.setRole(Role.USER);
    }

    @Test
    public void testCacheL2() {
        EntityManager entityManager = sessionFactory.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();
        userDao.create(user);
        entityTransaction.commit();

        List<CacheManager> cacheManagers = CacheManager.ALL_CACHE_MANAGERS;
        if (!cacheManagers.isEmpty()) {
            CacheManager cacheManager = cacheManagers.get(0);
            Cache usersCache = cacheManager.getCache(User.class.getName());

            int expectedL2CacheSize = 1;
            int actualL2CacheSize = usersCache.getSize();

            assertEquals(expectedL2CacheSize, actualL2CacheSize);
        } else {
            fail();
        }
    }

    @After
    public void deleteCreatedUsers(){
        userDao.delete(user.getName());
    }

}
