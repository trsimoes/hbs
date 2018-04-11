package pt.eden.hbs.web.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import flotjf.Chart;
import flotjf.data.PlotData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.*;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pt.eden.hbs.common.entity.SnapshotExt;

import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.springframework.hateoas.MediaTypes.HAL_JSON;

@RestController
public class BalanceByDayController {

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd");

    private final static String BASE_URL = "http://localhost:8080";

    private RestTemplate restTemplate() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);

        return new RestTemplate(Collections.singletonList(converter));
    }

    @RequestMapping("/chart/last10days")
    String getLast10DaysChart() {
        final String url = BASE_URL + "/snapshotview/search/findTop10ByOrderByCreateDateTimeDesc";
        PagedResources<SnapshotExt> pagedResources = restTemplate().getForObject(url, PagedResourceReturnType.class);
        return generateChart(pagedResources.getContent());
    }

    @RequestMapping("/chart/alldays")
    String getAllDaysChart() {
        final String url = BASE_URL + "/snapshotview";
        PagedResources<SnapshotExt> pagedResources = restTemplate().getForObject(url, PagedResourceReturnType.class);
        return generateChart(pagedResources.getContent());
    }

    @RequestMapping("/latest")
    ResponseEntity<SnapshotExt> getLatestSnapshot() {
        final String url = BASE_URL + "/snapshotview/search/findTop1ByOrderByCreateDateTimeDesc";
        return restTemplate().getForEntity(url, SnapshotExt.class);
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
}