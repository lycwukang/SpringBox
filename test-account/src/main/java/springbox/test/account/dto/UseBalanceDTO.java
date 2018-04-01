package springbox.test.account.dto;

import java.math.BigDecimal;

public class UseBalanceDTO {

    private Long memberId;
    private BigDecimal amount;

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}