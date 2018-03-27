package pt.eden.hbs.server.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pt.eden.hbs.server.entity.DailySnapshotViewEntity;
import pt.eden.hbs.server.persistence.DailySnapshotViewRepository;

import java.time.format.DateTimeFormatter;
import java.util.Iterator;

/**

 {
 "cols": [
 {"id":"","label":"Date","pattern":"","type":"string"},
 {"id":"","label":"Account Balance","pattern":"","type":"number"},
 {"id":"","label":"Credit Balance","pattern":"","type":"number"}
 ],
 "rows": [
 {"c":[{"v":"2018-03-10","f":null},{"v":1,"f":null},{"v":5,"f":null}]},
 {"c":[{"v":"2018-03-11","f":null},{"v":2,"f":null},{"v":4,"f":null}]},
 {"c":[{"v":"2018-03-12","f":null},{"v":3,"f":null},{"v":3,"f":null}]},
 {"c":[{"v":"2018-03-13","f":null},{"v":4,"f":null},{"v":2,"f":null}]},
 {"c":[{"v":"2018-03-14","f":null},{"v":5,"f":null},{"v":1,"f":null}]}
 ]
 }

 */
@RestController
public class ChartController {

    @Autowired
    private DailySnapshotViewRepository dailySnapshotViewRepository;

    private final static DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @RequestMapping("/chart")
    String execute() {

        StringBuilder builder = new StringBuilder();

        builder.append("{");
        builder.append("\"cols\": [");
        builder.append("{\"id\":\"\",\"label\":\"Date\",\"pattern\":\"\",\"type\":\"string\"},");
        builder.append("{\"id\":\"\",\"label\":\"Overall Balance\",\"pattern\":\"\",\"type\":\"number\"},");
        builder.append("{\"id\":\"\",\"label\":\"Account Balance\",\"pattern\":\"\",\"type\":\"number\"},");
        builder.append("{\"id\":\"\",\"label\":\"Credit Balance\",\"pattern\":\"\",\"type\":\"number\"},");
        builder.append("{\"id\":\"\",\"label\":\"Euroticket\",\"pattern\":\"\",\"type\":\"number\"}");
        builder.append("],");
        builder.append("\"rows\": [");

        final Iterable<DailySnapshotViewEntity> all = this.dailySnapshotViewRepository.findAll();
        for (Iterator<DailySnapshotViewEntity> iterator = all.iterator(); iterator.hasNext(); ) {
            DailySnapshotViewEntity snapshot = iterator.next();
            builder.append(toJson(snapshot));
            if (iterator.hasNext()) {
                builder.append(",");
            }
        }

        builder.append("]");
        builder.append("}");

        return builder.toString();
    }

    private String toJson(final DailySnapshotViewEntity snapshot) {
        return
                // create date time
                "{\"c\":[{\"v\":\"" +
                snapshot.getCreateDateTime().format(DATE_TIME_FORMATTER) +
                "\",\"f\":null}," +
                // overall balance
                "{\"v\":" +
                snapshot.getOverallBalance() +
                ",\"f\":null}," +
                // account balance
                "{\"v\":" +
                snapshot.getAccountBalance() +
                ",\"f\":null}," +
                "{\"v\":" +
                snapshot.getCreditBalance() +
                ",\"f\":null}," +
                // credit balance
                "{\"v\":" +
                snapshot.getEuroticketBalance() +
                ",\"f\":null}]}";
    }
}