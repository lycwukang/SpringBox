package springbox.test.member.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbox.test.member.dto.RegisterDTO;
import springbox.test.member.service.AccountService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/member/account")
public class AccountController {

    @Autowired
    AccountService accountService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(@RequestBody RegisterDTO registerDTO) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "true");
        result.put("phone", registerDTO.getPhone());
        result.put("smsCode", registerDTO.getSmsCode());
        result.put("register", accountService.register(registerDTO.getPhone(), registerDTO.getSmsCode()));
        return result;
    }
}