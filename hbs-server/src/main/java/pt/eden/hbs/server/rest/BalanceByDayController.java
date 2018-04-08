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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@RestController
public class BalanceByDayController {

    @Autowired
    private DailySnapshotViewRepository dailySnapshotViewRepository;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd");

    @RequestMapping("/chart/last10days")
    String getLast10DaysChart() {
        return generateChart(this.dailySnapshotViewRepository.findTop10ByOrderByCreateDateTimeDesc());
    }

    @RequestMapping("/chart/alldays")
    String getAllDaysChart() {
        final Iterable<DailySnapshotViewEntity> collection = this.dailySnapshotViewRepository.findAll();
        Iterator<DailySnapshotViewEntity> iteratorToList = collection.iterator();
        List<DailySnapshotViewEntity> data = new ArrayList<>();
        while (iteratorToList.hasNext()) {
            data.add(iteratorToList.next());
        }
        return generateChart(data);
    }

    private String generateChart(final List<DailySnapshotViewEntity> list) {

        final PlotData overall = new PlotData("Overall", "rgb(51,102,204)");
        final PlotData account = new PlotData("Account", "rgb(255,153,0)");
        final PlotData credit = new PlotData("Credit", "rgb(220,57,18)");
        final PlotData euroticket = new PlotData("Euroticket", "rgb(16,150,24)");

        Collections.reverse(list);
        for (DailySnapshotViewEntity snapshot : list) {
            final String date = snapshot.getCreateDateTime().format(DATE_TIME_FORMATTER);
            account.addPoint(date, snapshot.getAccountBalance());
            credit.addPoint(date, snapshot.getCreditBalance());
            euroticket.addPoint(date, snapshot.getEuroticketBalance());
            overall.addPoint(date, snapshot.getOverallBalance());
        }

        final Chart chart = new Chart();
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