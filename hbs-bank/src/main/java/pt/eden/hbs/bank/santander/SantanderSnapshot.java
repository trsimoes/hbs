package pt.eden.hbs.bank.santander;

import pt.eden.hbs.bank.AbstractSnapshot;

/**
 * @author : trsimoes
 */
public class SantanderSnapshot extends AbstractSnapshot {

    private Float creditBalance;

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
