package pt.eden.hbs.server.services;

import pt.eden.hbs.common.entity.Snapshot;

import java.util.List;

public class AccountBalanceValidatorImpl implements ParameterValidator {

    private final Float accountDiffThreshold;

    public AccountBalanceValidatorImpl(final Float accountDiffThreshold) {
        this.accountDiffThreshold = accountDiffThreshold;
    }

    @Override
    public boolean validate(final List<Snapshot> snapshots) {
        return false;
    }
}
