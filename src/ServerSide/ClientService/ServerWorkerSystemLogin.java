package ServerSide.ClientService;

import MessagesClientServer.*;
import ServerSide.ServerResources;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.*;

public class ServerWorkerSystemLogin implements Runnable {

    private Socket socketClient;
    private InnerMessageSystemExistUserLogin innerMessage;

    private String[] userData;
    private String messageToClient;

    public ServerWorkerSystemLogin(Socket socketClient, InnerMessageSystemExistUserLogin innerMessage){
        this.socketClient = socketClient;
        this.innerMessage = innerMessage;
    }

    @Override
    public void run() {
        boolean correctData = false;
        userData = getUserData();
        correctData = validateUserDataDatabase();

        if (correctData){
            sendClientMessageOperationSucceeded();
        }else {
            sendClientMessageOperationFailed();
        }

    }

    private String[] getUserData() {
        String[] userData = new String[2];
        userData[0] = innerMessage.getLogin();
        userData[1] = innerMessage.getPassword();
        return userData;
    }

    private boolean validateUserDataDatabase() {
        String sqlSelectUserDataFromTableUsers = "SELECT * FROM users WHERE login LIKE ?";

        try(Connection databaseConnection = DriverManager.getConnection(ServerResources.URL_SQLITE_DATABASE);
            PreparedStatement preparedStatement = databaseConnection.prepareStatement(sqlSelectUserDataFromTableUsers)){

            preparedStatement.setString(1, userData[0]);
            ResultSet resultQuery = preparedStatement.executeQuery();

            ////
            System.out.println("***Server/ServerWorkerSystemLogin: select user from table users with defined login***");
            ///

            if (resultQuery.next()){
                String password = resultQuery.getString("password");
                if (password.equals(userData[1])){
                    messageToClient = ServerResources.MESSAGE_USER_DATA_CORRECT;
                    return true;
                }else {
                    messageToClient = ServerResources.MEESAGE_USER_PASSWORD_INCORRECT;
                    return false;
                }
            }
            else {
                messageToClient = ServerResources.MESSAGE_USER_LOGIN_NOT_EXIST;
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void sendClientMessageOperationSucceeded() {
        try(ObjectOutputStream outputStreamClient = new ObjectOutputStream(socketClient.getOutputStream())){
            InnerMessage innerMessage = new InnerMessageSystemOperationResult(true, messageToClient);
            BasicMessage basicMessage = new BasicMessage(TypeOfInnerMessage.OPERATION_RESULT, innerMessage);
            outputStreamClient.writeObject(basicMessage);
            outputStreamClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendClientMessageOperationFailed() {
        try(ObjectOutputStream outputStreamClient = new ObjectOutputStream(socketClient.getOutputStream())){
            InnerMessage innerMessage = new InnerMessageSystemOperationResult(false, messageToClient);
            BasicMessage basicMessage = new BasicMessage(TypeOfInnerMessage.OPERATION_RESULT, innerMessage);
            outputStreamClient.writeObject(basicMessage);
            outputStreamClient.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
