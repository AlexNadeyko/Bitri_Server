package ServerSide;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class Server {

    //private Connection databaseConnection;

    public Server(){

    }

    public void initialize(){
        ///
        System.out.println("***Server/Server: initialization***");
        ///
        createDirectoryApp();
        createDatabase();
    }

    public void start(){

        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socketClient;

            while(true){
                socketClient = serverSocket.accept();
                new Thread(new ServerManager(socketClient)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void createDirectoryApp(){
        File directoryApp = new File(ServerResources.directoryApp);
        directoryApp.mkdir();
    }

    private void createDatabase(){

        String databasePath = ServerResources.directoryApp + File.separator + "database";
        File directoryDatabase = new File(databasePath);
        directoryDatabase.mkdir();


        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try(Connection databaseConnection = DriverManager.getConnection("jdbc:sqlite:" + databasePath + File.separator + "bitriDB.db")){
            if(databaseConnection != null){
                System.out.println("***Server/Server: database has been created.***");
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        initializeDatabase();
    }

    private void initializeDatabase() {

        String sqlCreateTableUsers = "CREATE TABLE IF NOT EXISTS users (\n"
                + " login text PRIMARY KEY, \n"
                + " password text NOT NULL, \n"
                + " name text NOT NULL, \n"
                + " surname text NOT NULL\n"
                + " );";

        try (Connection databaseConnection = DriverManager.getConnection(ServerResources.URL_SQLITE_DATABASE);
             Statement statement = databaseConnection.createStatement()){
            statement.execute(sqlCreateTableUsers);
            System.out.println("***Server/Server: table users was created in database***");
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

}
