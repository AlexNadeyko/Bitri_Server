package ServerSide;

import java.io.File;

public class ServerResources {

    public static final String directoryApp;
    public static final String databasePath;
    public static final String URL_SQLITE_DATABASE;
    public static final String MESSAGE_USER_SIGN_UP_FAILED;
    public static final String MESSAGE_USER_LOGIN_NOT_EXIST;
    public static final String MEESAGE_USER_PASSWORD_INCORRECT;
    public static final String MESSAGE_USER_DATA_CORRECT;


    public static final String MESSAGE_USER_SIGN_UP_SUCCEEDED;


    static {
        directoryApp = System.getProperty("user.home") + File.separator + "Bitri";
        databasePath = directoryApp + File.separator + "database" + File.separator + "bitriDB.db";
        URL_SQLITE_DATABASE = "jdbc:sqlite:" + databasePath;

        MESSAGE_USER_SIGN_UP_SUCCEEDED = "The account creation operation was successful." + System.lineSeparator()
                + " You can log in to your account.";

        MESSAGE_USER_SIGN_UP_FAILED = "The account creation operation was unsuccessful." + System.lineSeparator()
                + " Such login already exists in sytem. Try another one.";

        MESSAGE_USER_LOGIN_NOT_EXIST = "Such login does not exist. Try another one.";

        MEESAGE_USER_PASSWORD_INCORRECT = "Such password is incorrect. Try again. ";

        MESSAGE_USER_DATA_CORRECT = "User's data are correct.";
    }

}
