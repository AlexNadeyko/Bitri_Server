package ServerSide;

import java.io.File;

public class ServerResources {

    public static final String directoryApp;
    public static final String databasePath;
    public static final String URL_SQLITE_DATABASE;


    static {
        directoryApp = System.getProperty("user.home") + File.separator + "Bitri";
        databasePath = directoryApp + File.separator + "database" + File.separator + "bitriDB.db";
        URL_SQLITE_DATABASE = "jdbc:sqlite:" + databasePath;

    }

}
