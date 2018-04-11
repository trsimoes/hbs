package pt.eden.hbs.web.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import flotjf.Chart;
import flotjf.data.PlotData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.*;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pt.eden.hbs.common.entity.SnapshotExt;
import pt.eden.hbs.configuration.ApplicationConfigurations;

import java.time.format.DateTimeFormatter;
import java.util.*;

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
        return generateChart(pagedResources.getContent());
    }

    @RequestMapping("/chart/alldays")
    String getAllDaysChart() {
        final String url = buildURL("/snapshotview");
        PagedResources<SnapshotExt> pagedResources = this.restTemplate.getForObject(url, PagedResourceReturnType.class);
        return generateChart(pagedResources.getContent());
    }

    @RequestMapping("/latest")
    ResponseEntity<SnapshotExt> getLatestSnapshot() {
        final String url = buildURL("/snapshotview/search/findTop1ByOrderByCreateDateTimeDesc");
        return this.restTemplate.getForEntity(url, SnapshotExt.class);
    }

    private String generateChart(final Collection<SnapshotExt> list) {

        final PlotData overall = new PlotData("Overall", "rgb(51,102,204)");
        final PlotData account = new PlotData("Account", "rgb(255,153,0)");
        final PlotData credit = new PlotData("Credit", "rgb(220,57,18)");
        final PlotData euroticket = new PlotData("Euroticket", "rgb(16,150,24)");

//        Collections.reverse(list);
        for (SnapshotExt snapshot : list) {
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