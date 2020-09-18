package ServerSide.ClientService;

import MessagesClientServer.*;
import ServerSide.ServerResources;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class ServerWorkerSystemSignUp implements Runnable{

    private Socket socketClient;
    private InnerMessageSystemAddUserSignUp innerMessage;

    private String[] userData;


    public ServerWorkerSystemSignUp(Socket socketClient, InnerMessageSystemAddUserSignUp innerMessage){
        this.socketClient = socketClient;
        this.innerMessage = innerMessage;
    }

    @Override
    public void run() {
        boolean userExist = false;
        userData = getUserData();
        userExist = checkIfUserExistInTableUsers();
        if (userExist){
            sendClientMessageOperationFailed();

        }else{
            insertDataToTableUsers();
            sendClientMessageOperationSucceeded();
        }

    }

    private void sendClientMessageOperationSucceeded() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketClient.getOutputStream())){

            InnerMessage innerMessage = new InnerMessageSystemOperationResult(true,
                    ServerResources.MESSAGE_USER_SIGN_UP_SUCCEEDED);
            BasicMessage basicMessage = new BasicMessage(TypeOfInnerMessage.OPERATION_RESULT, innerMessage);
            objectOutputStream.writeObject(basicMessage);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendClientMessageOperationFailed() {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketClient.getOutputStream())){

            InnerMessage innerMessage = new InnerMessageSystemOperationResult(false,
                    ServerResources.MESSAGE_USER_SIGN_UP_FAILED);
            BasicMessage basicMessage = new BasicMessage(TypeOfInnerMessage.OPERATION_RESULT, innerMessage);
            objectOutputStream.writeObject(basicMessage);
            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfUserExistInTableUsers() {

        String sqlSelectLoginFromTableUsers = "SELECT * FROM users WHERE login like ?";

        try (Connection databaseConnection = DriverManager.getConnection(ServerResources.URL_SQLITE_DATABASE);
             PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlSelectLoginFromTableUsers)){

            preparedStatement.setString(1, userData[0]);
            ResultSet resultQuery = preparedStatement.executeQuery();

            ////
            System.out.println("***select users's login from table users***");
            ///

            if(resultQuery.next()) {
                ////
                System.out.println("***database: such login exists***");
                ////
                return true;
            }else {
                ////
                System.out.println("***database: such login does not exist***");
                ////
            }


        }catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    private String[] getUserData(){
        String[] userData = new String[4];
        userData[0] = innerMessage.getLogin();
        userData[1] = innerMessage.getPassword();
        userData[2] = innerMessage.getName();
        userData[3] = innerMessage.getSurname();

        return userData;
    }


    private void insertDataToTableUsers(){

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
