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
import java.util.Collections;
import java.util.List;

@RestController
public class BalanceByDayController {

    @Autowired
    private DailySnapshotViewRepository dailySnapshotViewRepository;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd");

    @RequestMapping("/balancebyday")
    String getBalanceByDay() {

        PlotData overall = new PlotData("Overall", "rgb(51,102,204)");
        PlotData account = new PlotData("Account", "rgb(220,57,18)");
        PlotData credit = new PlotData("Credit", "rgb(255,153,0)");
        PlotData euroticket = new PlotData("Euroticket", "rgb(16,150,24)");

        List<DailySnapshotViewEntity> top10 =
                this.dailySnapshotViewRepository.findTop10ByOrderByCreateDateTimeDesc();
        Collections.reverse(top10);
        for (DailySnapshotViewEntity snapshot : top10) {
            final String date = snapshot.getCreateDateTime().format(DATE_TIME_FORMATTER);
            account.addPoint(date, snapshot.getAccountBalance());
            credit.addPoint(date, snapshot.getCreditBalance());
            euroticket.addPoint(date, snapshot.getEuroticketBalance());
            overall.addPoint(date, snapshot.getOverallBalance());
        }

        Chart chart = new Chart();
        chart.addElements(overall);
        chart.addElements(account);
        chart.addElements(credit);
        chart.addElements(euroticket);

        return chart.printChart();
    }

    @RequestMapping("/latest")
    ResponseEntity<DailySnapshotViewEntity> getLatestSnapshot() {
        final DailySnapshotViewEntity snapshot = this.dailySnapshotViewRepository.findTop1ByOrderByCreateDateTimeDesc();
        return new ResponseEntity<>(snapshot, HttpStatus.OK);
    }
}