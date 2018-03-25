package pt.eden.hbs.server.entity;

import jdk.nashorn.internal.ir.annotations.Immutable;

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
public class DailySnapshotView {

    @Id
    private Long id;

    @Column(name = "create_date_time")
    private LocalDateTime createDateTime;

    @Column(name = "account_balance")
    private Float accountBalance;

    @Column(name = "credit_balance")
    private Float creditBalance;

    @Column(name = "overall_balance")
    private Float overallBalance;

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

    public Float getOverallBalance() {
        return overallBalance;
    }

    public void setOverallBalance(Float overallBalance) {
        this.overallBalance = overallBalance;
    }

    @Override
    public String toString() {
        return "DailySnapshotView{" + "id=" + id + ", createDateTime=" + createDateTime + ", accountBalance="
                + accountBalance + ", creditBalance=" + creditBalance + ", overallBalance=" + overallBalance + '}';
    }
}
