package pt.eden.hbs.common.entity;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
public class Snapshot {

    protected LocalDateTime createDateTime;

    protected Float accountBalance;

    protected Float creditBalance;

    protected Float euroticketBalance;

    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    public Float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Float getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Float creditBalance) {
        this.creditBalance = creditBalance;
    }

    public Float getEuroticketBalance() {
        return euroticketBalance;
    }

    public void setEuroticketBalance(Float euroticketBalance) {
        this.euroticketBalance = euroticketBalance;
    }
}
