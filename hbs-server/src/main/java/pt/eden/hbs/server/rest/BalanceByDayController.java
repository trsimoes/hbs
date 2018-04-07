package pt.eden.hbs.server.rest;

import flotjf.Chart;
import flotjf.data.PlotData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;
import pt.eden.hbs.server.persistence.DailySnapshotViewRepository;

import java.time.format.DateTimeFormatter;

@RestController
public class BalanceByDayController {

    @Autowired
    private DailySnapshotViewRepository dailySnapshotViewRepository;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MMM/dd");

    @RequestMapping("/balancebyday")
    String getBalanceByDay() {

        PlotData account = new PlotData("Account", "rgb(244,67,54)");
        PlotData credit = new PlotData("Credit", "rgb(0,188,212)");
        PlotData euroticket = new PlotData("Euroticket", "rgb(139,195,74)");
        PlotData overall = new PlotData("Overall", "rgb(33,150,243)");

        final Iterable<DailySnapshotViewEntity> all = this.dailySnapshotViewRepository.findAll();
        for (DailySnapshotViewEntity snapshot : all) {
            final String date = snapshot.getCreateDateTime().format(DATE_TIME_FORMATTER);
            account.addPoint(date, snapshot.getAccountBalance());
            credit.addPoint(date, snapshot.getCreditBalance());
            euroticket.addPoint(date, snapshot.getEuroticketBalance());
            overall.addPoint(date, snapshot.getOverallBalance());
        }

        Chart chart = new Chart();
        chart.addElements(account);
        chart.addElements(credit);
        chart.addElements(euroticket);
        chart.addElements(overall);

        return chart.printChart();
    }

    @RequestMapping("/latest")
    ResponseEntity<DailySnapshotViewEntity> getLatestSnapshot() {
        final DailySnapshotViewEntity snapshot = this.dailySnapshotViewRepository.findTop1ByOrderByCreateDateTimeDesc();
        return new ResponseEntity<>(snapshot, HttpStatus.OK);
    }
}