import com.example.springboot.redisdemo.RedisApp;
import com.example.springboot.redisdemo.config.RedisConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liuguo on 2017/8/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public class SpringRedisTest {
    @Autowired
    private StringRedisTemplate template;

    @Autowired
    private RedisConfig redisConfig;

    @Before
    public void testBefore(){
        template.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.flushAll();
                return null;
            }
        });
    }
    @Test
    public void testRedisConfig(){
        System.out.println(redisConfig.toString());
    }

    @Test
    public void testRedis(){
        long o = template.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection redisConnection) throws DataAccessException {
                 ((StringRedisConnection)redisConnection).set("test","哈哈");
                Long size = redisConnection.dbSize();
                return size;
            }
        });

        Assert.assertEquals(1l,o);

        ValueOperations<String,String> valueOperations = template.opsForValue();
        Assert.assertEquals(valueOperations.get("test"),"哈哈");
    }

    @After
    public void showKeys(){
        template.keys("*").forEach(s -> {
            System.out.println(s);
        });
    }
}
