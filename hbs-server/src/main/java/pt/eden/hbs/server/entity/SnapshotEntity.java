package pt.eden.hbs.server.entity;

import pt.eden.hbs.bank.Snapshot;

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

    @SuppressWarnings("unused")
    public Long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(Long id) {
        this.id = id;
    }

    @SuppressWarnings("unused")
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    @SuppressWarnings("all")
    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @SuppressWarnings("unused")
    public Float getAccountBalance() {
        return accountBalance;
    }

    @SuppressWarnings("all")
    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }

    @SuppressWarnings("unused")
    public Float getCreditBalance() {
        return creditBalance;
    }

    @SuppressWarnings("all")
    public void setCreditBalance(Float creditBalance) {
        this.creditBalance = creditBalance;
    }

    @Override
    public String toString() {
        return "Snapshot{" + "id=" + id + ", createDateTime=" + createDateTime + ", accountBalance=" + accountBalance
                + ", creditBalance=" + creditBalance + '}';
    }

    public static SnapshotEntity from(final Snapshot snapshot) {
        SnapshotEntity result = null;
        if (snapshot != null) {
            result = new SnapshotEntity();
            result.setCreateDateTime(snapshot.getCreateDateTime());
            result.setAccountBalance(snapshot.getAccountBalance());
            result.setCreditBalance(snapshot.getCreditBalance());
        }
        return result;
    }
}
