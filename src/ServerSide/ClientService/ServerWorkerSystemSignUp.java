package ServerSide.ClientService;

import MessagesClientServer.InnerMessageSystemAddUserSignUp;
import ServerSide.ServerResources;

import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ServerWorkerSystemSignUp implements Runnable{

    private Socket socketClient;
    private InnerMessageSystemAddUserSignUp innerMessage;


    public ServerWorkerSystemSignUp(Socket socketClient, InnerMessageSystemAddUserSignUp innerMessage){
        this.socketClient = socketClient;
        this.innerMessage = innerMessage;
    }

    @Override
    public void run() {
        String[] userData = getUserData();
        insertDataToTableUsers(userData);
    }


    private String[] getUserData(){
        String[] userData = new String[4];
        userData[0] = innerMessage.getLogin();
        userData[1] = innerMessage.getPassword();
        userData[2] = innerMessage.getName();
        userData[3] = innerMessage.getSurname();

        return userData;
    }


    private void insertDataToTableUsers(String[] userData){

        String sqlInsertDataToTableUsers = "INSERT INTO users(login, password, name, surname) VALUES (?, ?, ?, ?)";

        try (Connection databaseConnection = DriverManager.getConnection(ServerResources.URL_SQLITE_DATABASE);
             PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlInsertDataToTableUsers)){

            preparedStatement.setString(1, userData[0]);
            preparedStatement.setString(2, userData[1]);
            preparedStatement.setString(3, userData[2]);
            preparedStatement.setString(4, userData[3]);

            preparedStatement.executeUpdate();

            ////
            System.out.println("***insert data to table users***");
            ///

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
