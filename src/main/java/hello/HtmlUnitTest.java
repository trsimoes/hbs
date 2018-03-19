//package hello;
//
//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
//import com.gargoylesoftware.htmlunit.html.HtmlForm;
//import com.gargoylesoftware.htmlunit.html.HtmlPage;
//import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
//import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
//
//import java.io.IOException;
//import java.util.List;
//
///**
// * @author : trsimoes
// */
//public class HtmlUnitTest {
//    public static void main(String[] args) throws IOException {
//        HtmlUnitTest app = new HtmlUnitTest();
//        //app.demo();
//        app.bank();
//    }
//
//    private void bank() throws IOException {
//        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
//            //webClient.getOptions().setThrowExceptionOnScriptError(false);
//
//            final HtmlPage page = webClient.getPage(
//                    "https://www.particulares.santandertotta.pt/bepp/sanpt/usuarios/login/?");
//
//            System.out.println("----------------------------------------------------------------------------");
//            System.out.println(page.asXml());
//
//            final HtmlForm form = page.getFormByName("formulario");
//
//            final HtmlTextInput usernameField = form.getInputByName("identificacionUsuario");
//            usernameField.setValueAttribute("xxx");
//            final HtmlPasswordInput passwordField = form.getInputByName("claveConsultiva");
//            passwordField.setValueAttribute("xxx");
//
//            List<HtmlAnchor> anchorList = page.getByXPath("//a[@id='login_button']");
//            HtmlAnchor anchor = anchorList.get(0);
//            anchor.click();
//
//            final HtmlPage page2 = webClient.getPage("https://www.particulares.santandertotta.pt/pagina/indice/0,,"
//                    + "282_1_2_1,00.html?nuevo=si&bf=2");
//
//            System.out.println("----------------------------------------------------------------------------");
//            System.out.println(page2.asXml());
//
////            System.out.println("----------------------------------------------------------------------------");
////            System.out.println(page2.asXml());
//        }
//    }
//
//    private void demo() throws IOException {
//        try (final WebClient webClient = new WebClient()) {
//            final HtmlPage page = webClient.getPage("http://htmlunit.sourceforge.net");
//            //Assert.assertEquals("HtmlUnit - Welcome to HtmlUnit", page.getTitleText());
//            System.out.println(page.getTitleText());
//
//            final String pageAsXml = page.asXml();
//            //Assert.assertTrue(pageAsXml.contains("<body class=\"composite\">"));
//            System.out.println(page.asXml());
//
//            final String pageAsText = page.asText();
//            //Assert.assertTrue(pageAsText.contains("Support for the HTTP and HTTPS protocols"));
//            System.out.println(pageAsText);
//        }
//    }
//}
