package hello;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.net.MalformedURLException;

/**
 * @author : trsimoes
 */
public class HtmlUnitTest {
    public static void main(String[] args) throws IOException {
        HtmlUnitTest app = new HtmlUnitTest();
        //app.demo();
        app.bank();
    }

    private void bank() {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("https://www.particulares.santandertotta.pt/pagina/indice/0,,276_1_2,00.html");
            //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
            System.out.println(page.getTitleText());

            final String pageAsXml = page.asXml();
            //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
            System.out.println(page.asXml());

            final String pageAsText = page.asText();
            //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
            System.out.println(pageAsText);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void demo() throws IOException {
        try (final WebClient webClient = new WebClient()) {
            final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
            //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
            System.out.println(page.getTitleText());

            final String pageAsXml = page.asXml();
            //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
            System.out.println(page.asXml());

            final String pageAsText = page.asText();
            //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
            System.out.println(pageAsText);
        }
    }
}
