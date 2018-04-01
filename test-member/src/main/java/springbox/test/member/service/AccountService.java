package springbox.test.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbox.test.sms.service.SmsService;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);

    @Autowired
    SmsService smsService;

    public boolean register(String phone, String smsCode) {
        if (smsService.check(phone, smsCode)) {
            logger.info("用户" + phone + "注册成功");
            return true;
        }
        return false;
    }
}