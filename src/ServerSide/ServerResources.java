package ServerSide;

import java.io.File;

public class ServerResources {

    public static final String directoryApp;
    public static final String databasePath;
    public static final String URL_SQLITE_DATABASE;
    public static final String MESSAGE_USER_SIGN_UP_FAILED;

    public static final String MESSAGE_USER_SIGN_UP_SUCCEEDED;


    static {
        directoryApp = System.getProperty("user.home") + File.separator + "Bitri";
        databasePath = directoryApp + File.separator + "database" + File.separator + "bitriDB.db";
        URL_SQLITE_DATABASE = "jdbc:sqlite:" + databasePath;

        MESSAGE_USER_SIGN_UP_SUCCEEDED = "The account creation operation was successful." + System.lineSeparator()
                + " You can log in to your account.";

        MESSAGE_USER_SIGN_UP_FAILED = "The account creation operation was unsuccessful." + System.lineSeparator()
                + " Such login already exists in sytem. Try another one.";

    }

}
