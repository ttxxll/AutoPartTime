package auto.test;

import auto.AutoPartTimeMain;
import auto.config.SmsConfig;
import auto.service.SmsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author taoxinglong
 * @description TODO
 * @date 2024-08-30 12:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AutoPartTimeMain.class)
@Slf4j
public class SmsTest {

    @Autowired
    private SmsService smsService;

    @Autowired
    private SmsConfig smsConfig;

    @Test
    public void testSendSms() {
        smsService.send("18375324908", smsConfig.getTemplateId(),"000000");
    }
}
