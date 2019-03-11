package Base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)//开启工厂
@ContextConfiguration({"classpath:/spring/spring-dao.xml","classpath:/spring/spring-service.xml"})
public class BaseTest {
}