import com.example.springboot.redisdemo.RedisApp;
import com.example.springboot.redisdemo.dao.RedisRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by liuguo on 2017/8/2.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisApp.class)
public class TestRedisRipository {
    @Autowired
    private RedisRepository redisRepository;

    @Test
    public void test(){
        redisRepository.addUser("lg","123456");

        String uid = redisRepository.findUid("lg");

        System.out.println(uid);

    }

}
