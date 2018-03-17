package pt.eden.hbs.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping("/chart")
    String home() {
        return "{\n" + "  \"cols\": [\n"
                + "        {\"id\":\"\",\"label\":\"Date\",\"pattern\":\"\",\"type\":\"string\"},\n"
                + "        {\"id\":\"\",\"label\":\"Account Balance\",\"pattern\":\"\",\"type\":\"number\"},\n"
                + "        {\"id\":\"\",\"label\":\"Credit Balance\",\"pattern\":\"\",\"type\":\"number\"}\n"
                + "      ],\n" + "  \"rows\": [\n"
                + "        {\"c\":[{\"v\":\"2018-03-10\",\"f\":null},{\"v\":1,\"f\":null},{\"v\":5,\"f\":null}]},\n"
                + "        {\"c\":[{\"v\":\"2018-03-11\",\"f\":null},{\"v\":2,\"f\":null},{\"v\":4,\"f\":null}]},\n"
                + "        {\"c\":[{\"v\":\"2018-03-12\",\"f\":null},{\"v\":3,\"f\":null},{\"v\":3,\"f\":null}]},\n"
                + "        {\"c\":[{\"v\":\"2018-03-13\",\"f\":null},{\"v\":4,\"f\":null},{\"v\":2,\"f\":null}]},\n"
                + "        {\"c\":[{\"v\":\"2018-03-14\",\"f\":null},{\"v\":5,\"f\":null},{\"v\":1,\"f\":null}]}\n"
                + "      ]\n" + "}";
    }
}