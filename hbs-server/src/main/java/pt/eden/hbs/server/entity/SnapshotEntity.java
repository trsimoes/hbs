package pt.eden.hbs.server.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Entity
@SequenceGenerator(name = "idgen", sequenceName = "s_snapshot")
@Table(name = "snapshot")
public class SnapshotEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "idgen")
    private Long id;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "account_balance")
    private Float accountBalance;

    @Column(name = "credit_balance")
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
