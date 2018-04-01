package springbox.test.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;

@Service
public class BalanceService {

    private static final Logger logger = LoggerFactory.getLogger(BalanceService.class);

    private Random random = new Random();

    public BigDecimal getBalanceByMemberId(Long memberId) {
        BigDecimal amount = BigDecimal.ONE.multiply(new BigDecimal(random.nextInt(10000) + ""));
        logger.info("获取用户" + memberId + "的账户余额:" + amount);
        return amount;
    }

    public BigDecimal useBalanceByMemberId(Long memberId, BigDecimal amount) {
        BigDecimal balanceAmount = BigDecimal.ONE.multiply(new BigDecimal(random.nextInt(10000) + ""));
        logger.info("使用" + memberId + "的账户余额:" + amount + ", 剩余账户余额:" + balanceAmount);
        return balanceAmount;
    }
}