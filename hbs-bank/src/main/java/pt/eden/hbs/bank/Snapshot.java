package pt.eden.hbs.bank;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
public class Snapshot {

    private LocalDateTime createDateTime;

    private Float accountBalance;

    private Float creditBalance;

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

    public Float getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Float creditBalance) {
        this.creditBalance = creditBalance;
    }

    @Override
    public String toString() {
        return "Snapshot{" + "createDateTime=" + createDateTime + ", accountBalance=" + accountBalance
                + ", creditBalance=" + creditBalance + '}';
    }
}
