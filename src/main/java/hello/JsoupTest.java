//package hello;
//
//import org.jsoup.Connection;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.Scanner;
//
///**
// * http://joelmin.blogspot.pt/2016/04/how-to-login-to-website-using-jsoup-java_4.html
// *
// * @author : trsimoes
// */
//public class JsoupTest {
//
//    public static void main(String[] args) throws IOException {
//        JsoupTest app = new JsoupTest();
//        //app.testGitHub();
//        app.testBank();
//    }
//
//    private void testBank() throws IOException {
//        final String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n"
//                + "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";
//        String loginFormUrl = "https://www.particulares.santandertotta.pt/pagina/indice/0,,276_1_2,00.html"
//                + "0.shtml?usr=Nome+de+Utilizador";
//        String loginActionUrl = "https://www.particulares.santandertotta.pt/bepp/sanpt/usuarios/login/?";
//
//        HashMap<String, String> cookies = new HashMap<>();
//        HashMap<String, String> formData = new HashMap<>();
//
//        Connection.Response loginForm = Jsoup.connect(loginFormUrl)
//                .method(Connection.Method.GET)
//                .userAgent(USER_AGENT)
//                .ignoreContentType(true)
//                .followRedirects(true)
//                .execute();
//        Document loginDoc = loginForm.parse(); // this is the document that contains response html
//
//        cookies.putAll(loginForm.cookies()); // save the cookies, this will be passed on to next request
//        /**
//         * Get the value of authenticity_token with the CSS selector we saved before
//         **/
//
//    }
//
//    private void testGitHub() throws IOException {
//        final String USER_AGENT = "\"Mozilla/5.0 (Windows NT\" +\n"
//                + "          \" 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2\"";
//        String loginFormUrl = "https://github.com/login";
//        String loginActionUrl = "https://github.com/session";
//
//        String username = null;
//        String password = null;
//        try (Scanner scanner = new Scanner( System.in )) {
//            System.out.print("Username: ");
//            username = scanner.next();
//            System.out.print("Password: ");
//            password = scanner.next();
//        }
//
//        HashMap<String, String> cookies = new HashMap<>();
//        HashMap<String, String> formData = new HashMap<>();
//
//        Connection.Response loginForm = Jsoup.connect(loginFormUrl).method(Connection.Method.GET).userAgent(USER_AGENT)
//                .execute();
//        Document loginDoc = loginForm.parse(); // this is the document that contains response html
//
//        cookies.putAll(loginForm.cookies()); // save the cookies, this will be passed on to next request
//        /**
//         * Get the value of authenticity_token with the CSS selector we saved before
//         **/
//        final Elements select = loginDoc.select("form[action=/session] [name=authenticity_token]");
//        final Element first = select.first();
//        String authToken = first.attr("value");
//
//        formData.put("commit", "Sign in");
//        formData.put("utf8", "e2 9c 93");
//        formData.put("login", username);
//        formData.put("password", password);
//        formData.put("authenticity_token", authToken);
//
//        Connection.Response homePage = Jsoup.connect(loginActionUrl).cookies(cookies).data(formData).method(
//                Connection.Method.POST).userAgent(USER_AGENT).execute();
//
//        System.out.println(homePage.parse().html());
//    }
//}
