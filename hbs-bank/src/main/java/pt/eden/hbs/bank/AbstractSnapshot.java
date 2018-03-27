package pt.eden.hbs.bank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
public abstract class AbstractSnapshot {

    protected LocalDateTime createDateTime;

    protected Float accountBalance;

    @JsonDeserialize(using= LocalDateTimeDeserializer.class)
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

    @Override
    public String toString() {
        return "AbstractSnapshot{" + "createDateTime=" + createDateTime + ", accountBalance=" + accountBalance + '}';
    }
}
