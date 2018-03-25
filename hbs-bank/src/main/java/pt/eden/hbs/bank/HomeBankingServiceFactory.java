package pt.eden.hbs.bank;

/**
 * @author : trsimoes
 */
public class HomeBankingServiceFactory {

    private HomeBankingService homeBankingService;

    private static HomeBankingServiceFactory INSTANCE = new HomeBankingServiceFactory();

    public static HomeBankingServiceFactory getInstance() {
        return INSTANCE;
    }

    private HomeBankingServiceFactory() {
    }

    public HomeBankingService get(final Context context) {
        if (this.homeBankingService == null)
            this.homeBankingService = new HomeBankingServiceImpl(context);
        return this.homeBankingService;
    }
}
