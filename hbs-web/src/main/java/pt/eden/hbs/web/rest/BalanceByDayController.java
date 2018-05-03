package pt.eden.hbs.web.rest;

import flotjf.Chart;
import flotjf.data.PlotData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pt.eden.hbs.common.entity.SnapshotExt;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BalanceByDayController {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd");

    private final ApplicationConfigurations configurations;

    private final RestTemplate restTemplate;

    @Autowired
    public BalanceByDayController(ApplicationConfigurations configurations, RestTemplate restTemplate) {
        this.configurations = configurations;
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/chart/last10days")
    String getLast10DaysChart() {
        final String url = buildURL("/snapshotview/search/findTop10ByOrderByCreateDateTimeDesc");
        PagedResources<SnapshotExt> pagedResources = this.restTemplate.getForObject(url, PagedResourceReturnType.class);
        return generateChart(pagedResources.getContent(), true);
    }

    @RequestMapping("/chart/alldays")
    String getAllDaysChart() {
        final String baseUrl = buildURL("/snapshotview/");

        Collection<SnapshotExt> list = new ArrayList<>();
        boolean doSearch = true;
        long page = 0;
        while (doSearch) {
            String url = baseUrl + "?page=" + (int) page;
            PagedResources<SnapshotExt> tmp = this.restTemplate.getForObject(url, PagedResourceReturnType.class);
            list.addAll(tmp.getContent());
            PagedResources.PageMetadata metadata = tmp.getMetadata();
            if (metadata.getTotalPages() <= page+1) {
                doSearch = false;
            } else {
                page++;
            }
        }

        return generateChart(list, false);
    }

    @RequestMapping("/latest")
    ResponseEntity<SnapshotExt> getLatestSnapshot() {
        final String url = buildURL("/snapshotview/search/findTop1ByOrderByCreateDateTimeDesc");
        return this.restTemplate.getForEntity(url, SnapshotExt.class);
    }

    private String generateChart(final Collection<SnapshotExt> list, final boolean reverse) {

        final PlotData overall = new PlotData("Overall", "rgb(51,102,204)");
        final PlotData account = new PlotData("Account", "rgb(255,153,0)");
        final PlotData credit = new PlotData("Credit", "rgb(220,57,18)");
        final PlotData euroticket = new PlotData("Euroticket", "rgb(16,150,24)");

        final LinkedList<SnapshotExt> linkedList = new LinkedList<>(list);
        Iterator<SnapshotExt> iterator = reverse ? linkedList.descendingIterator() : linkedList.iterator();
        while (iterator.hasNext()) {
            SnapshotExt snapshot = iterator.next();
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

    private String buildURL(final String relative) {
        return "http://" + this.configurations.get("hbs.server.host") + ":" + this.configurations.get("hbs.server.port")
                + relative;
    }
}