package pt.eden.hbs.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Entity
public class Snapshot {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private LocalDateTime createDateTime;
    private Float accountBalance;
    private Float creditBalance;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
        return "Snapshot{" + "id=" + id + ", createDateTime=" + createDateTime + ", accountBalance=" + accountBalance
                + ", creditBalance=" + creditBalance + '}';
    }
}
