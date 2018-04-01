package springbox.test.account.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springbox.test.account.service.BalanceService;
import springbox.test.account.dto.UseBalanceDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/account/balance")
public class BalanceController {

    @Autowired
    BalanceService balanceService;

    @RequestMapping(method = RequestMethod.GET)
    public Object balance(Long memberId) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "true");
        result.put("memberId", memberId);
        result.put("balance", balanceService.getBalanceByMemberId(memberId));
        return result;
    }

    @RequestMapping(value = "/use", method = RequestMethod.POST)
    public Object use(@RequestBody UseBalanceDTO useBalanceDTO) {
        BigDecimal amount = balanceService.useBalanceByMemberId(useBalanceDTO.getMemberId(), useBalanceDTO.getAmount());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("result", "true");
        result.put("memberId", useBalanceDTO.getMemberId());
        result.put("amount", useBalanceDTO.getAmount());
        result.put("balance", amount);
        return result;
    }
}
