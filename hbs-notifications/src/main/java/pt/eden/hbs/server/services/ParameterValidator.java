package pt.eden.hbs.server.services;

import pt.eden.hbs.common.entity.Snapshot;

import java.util.List;

public interface ParameterValidator {

    boolean validate(List<Snapshot> snapshots);
}
