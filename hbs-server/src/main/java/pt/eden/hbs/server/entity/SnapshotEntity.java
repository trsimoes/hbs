package pt.eden.hbs.server.entity;

import org.springframework.data.rest.core.annotation.RestResource;
import pt.eden.hbs.common.entity.Snapshot;

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
@RestResource
public class SnapshotEntity extends Snapshot {

    private Long id;

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE, generator = "idgen")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "create_date_time")
    public LocalDateTime getCreateDateTime() {
        return createDateTime;
    }

    public void setCreateDateTime(LocalDateTime createDateTime) {
        this.createDateTime = createDateTime;
    }

    @Column(name = "account_balance")
    public Float getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Float accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Column(name = "credit_balance")
    public Float getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(Float creditBalance) {
        this.creditBalance = creditBalance;
    }

    @Column(name = "euroticket_balance")
    public Float getEuroticketBalance() {
        return euroticketBalance;
    }

    public void setEuroticketBalance(Float euroticketBalance) {
        this.euroticketBalance = euroticketBalance;
    }

    @Override
    public String toString() {
        return "SnapshotEntity{" + "id=" + id + ", createDateTime=" + createDateTime + ", accountBalance="
                + accountBalance + ", creditBalance=" + creditBalance + ", euroticketBalance=" + euroticketBalance
                + '}';
    }

    public static SnapshotEntity from(final Snapshot snapshot) {
        SnapshotEntity result = null;
        if (snapshot != null) {
            result = new SnapshotEntity();
            result.setCreateDateTime(snapshot.getCreateDateTime());
            result.setAccountBalance(snapshot.getAccountBalance());
            result.setCreditBalance(snapshot.getCreditBalance());
            result.setEuroticketBalance(snapshot.getEuroticketBalance());
        }
        return result;
    }
}
