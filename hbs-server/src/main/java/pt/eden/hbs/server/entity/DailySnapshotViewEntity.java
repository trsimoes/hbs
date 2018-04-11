package pt.eden.hbs.server.entity;

import jdk.nashorn.internal.ir.annotations.Immutable;
import org.springframework.data.rest.core.annotation.RestResource;
import pt.eden.hbs.common.entity.SnapshotExt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Entity
@Immutable
@Table(name = "daily_snapshot_view")
@RestResource
public class DailySnapshotViewEntity extends SnapshotExt {

    private Long id;

    @Id
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

    @Column(name = "overall_balance")
    public Float getOverallBalance() {
        return this.overallBalance;
    }

    public void setOverallBalance(Float overallBalance) {
        this.overallBalance = overallBalance;
    }

    @Override
    public String toString() {
        return "DailySnapshotViewEntity{" + "id=" + id + ", createDateTime=" + createDateTime + ", accountBalance="
                + accountBalance + ", creditBalance=" + creditBalance + ", euroticketBalance=" + euroticketBalance
                + ", overallBalance=" + overallBalance + '}';
    }

    public SnapshotExt convertToSnapshotExt() {
        SnapshotExt result = new SnapshotExt();
        result.setCreateDateTime(this.createDateTime);
        result.setAccountBalance(this.accountBalance);
        result.setCreditBalance(this.creditBalance);
        result.setEuroticketBalance(this.euroticketBalance);
        result.setOverallBalance(this.overallBalance);
        return result;
    }
}
