package pt.eden.hbs.server.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pt.eden.hbs.common.entity.Snapshot;
import pt.eden.hbs.configuration.ApplicationConfigurations;
import pt.eden.hbs.server.services.AccountBalanceValidatorImpl;
import pt.eden.hbs.server.services.MaxCreditReachedValidatorImpl;
import pt.eden.hbs.server.services.ParameterValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
public class NotificationsController {

    private static final Logger log = LoggerFactory.getLogger(NotificationsController.class);

    private final ApplicationConfigurations configurations;

    private List<ParameterValidator> validators = new ArrayList<>();

    @Autowired
    public NotificationsController(ApplicationConfigurations configurations) {
        this.configurations = configurations;

        // register validators
        final Float maxCreditThreshold = Float.valueOf(this.configurations.get("credit.max.threshold"));
        this.validators.add(new MaxCreditReachedValidatorImpl(maxCreditThreshold));
        final Float accountDiffThreshold = Float.valueOf(this.configurations.get("account.difference.threshold"));
        this.validators.add(new AccountBalanceValidatorImpl(accountDiffThreshold));
    }
    /**
     * This method is used by the updater app
     */
    @RequestMapping(value = "/notifications/analyze", method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void analyze(@RequestParam final List<Snapshot> snapshots) {

        for (ParameterValidator validator : this.validators) {
            validator.validate(snapshots);
        }

    }
}