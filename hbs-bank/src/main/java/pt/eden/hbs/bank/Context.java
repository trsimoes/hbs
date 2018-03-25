package pt.eden.hbs.bank;

/**
 * @author : trsimoes
 */
public class Context {

    protected static final String BASE_URL = "https://www.particulares.santandertotta.pt/";

    private String username;
    private String password;

    public Context() {
    }

    public Context(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Credentials{" + "username='" + username + '\'' + ", password='" + password + '\'' + '}';
    }
}
