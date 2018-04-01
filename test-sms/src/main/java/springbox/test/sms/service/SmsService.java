package springbox.test.sms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    public boolean send(String phone, String content) {
        logger.info("发送短信:" + phone + "[" + content + "]");
        return true;
    }

    public boolean check(String phone, String smsCode) {
        logger.info("校验短信验证码:" + phone + "[" + smsCode + "]");
        return true;
    }
}