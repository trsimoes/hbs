package pt.eden.hbs.bank.edenred;

import pt.eden.hbs.bank.AbstractSnapshot;

/**
 * @author : trsimoes
 */
public class EdenredSnapshot extends AbstractSnapshot {

    @Override
    public String toString() {
        return "EdenredSnapshot{" + "createDateTime=" + createDateTime + ", accountBalance=" + accountBalance + '}';
    }
}
