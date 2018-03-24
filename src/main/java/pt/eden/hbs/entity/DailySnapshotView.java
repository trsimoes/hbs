package pt.eden.hbs.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * @author : trsimoes
 */
@Entity
@Table(name = "daily_snapshot_view")
public class DailySnapshotView {

    @Id
    private Long id;

    private LocalDateTime createDateTime;

    private Float accountBalance;

    private Float creditBalance;

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
