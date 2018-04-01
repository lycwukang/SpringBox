package springbox.test.sms.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbox.test.sms.dto.SmsSendDTO;
import springbox.test.sms.service.SmsService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sms/send")
public class SmsSendController {

    @Autowired
    SmsService smsSendService;

    @RequestMapping(method = RequestMethod.POST)
    public Object smsSendDTO(@RequestBody SmsSendDTO smsSendDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "true");
        result.put("phone", smsSendDTO.getPhone());
        result.put("content", smsSendDTO.getContent());
        result.put("sendResult", smsSendService.send(smsSendDTO.getPhone(), smsSendDTO.getContent()));
        return result;
    }
}