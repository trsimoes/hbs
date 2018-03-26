package pt.eden.hbs.server.entity;

import pt.eden.hbs.bank.HomeBankingSnapshot;

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

    @Column(name = "euroticket_balance")
    private Float euroticketBalance;

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

    public static SnapshotEntity from(final HomeBankingSnapshot homeBankingSnapshot) {
        SnapshotEntity result = null;
        if (homeBankingSnapshot != null) {
            result = new SnapshotEntity();
            result.setCreateDateTime(homeBankingSnapshot.getCreateDateTime());
            result.setAccountBalance(homeBankingSnapshot.getAccountBalance());
            result.setCreditBalance(homeBankingSnapshot.getCreditBalance());
        }
        return result;
    }
}
