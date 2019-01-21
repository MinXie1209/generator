package top.myjnxj.generator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import top.myjnxj.generator.service.MailService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testSimpleMail() throws Exception {
        mailService.sendMail("15817061252@163.com","GENERATOR-REGISTER","[GENERATOR]欢迎使用GENERATOR服务，您的注册验证码是1586，请不要把验证码泄漏给其他人，如非本人请勿操作。");
    }
}