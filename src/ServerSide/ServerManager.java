package ServerSide;

import MessagesClientServer.BasicMessage;
import MessagesClientServer.InnerMessageSystemAddUserSignUp;
import MessagesClientServer.InnerMessageSystemExistUserLogin;
import ServerSide.ClientService.ServerWorkerSystemLogin;
import ServerSide.ClientService.ServerWorkerSystemSignUp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ServerManager implements Runnable {

    private Socket socketClient;

    public ServerManager(Socket socketClient){

        this.socketClient = socketClient;
    }


    @Override
    public void run() {

        BasicMessage message = getClientMessage();
        manageClientInnerMessage(message);

    }

    private BasicMessage getClientMessage() {

        BasicMessage message = null;

        try{
            ObjectInputStream ois = new ObjectInputStream(socketClient.getInputStream());
            message = (BasicMessage) ois.readObject();
            ///
            System.out.println("Server/ServerManager: get message with type = " + message.getTypeOfInnerMessage());
            ///
        }catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        return message;
    }

    private void manageClientInnerMessage(BasicMessage message) {
        switch (message.getTypeOfInnerMessage()){
            case ADD_USER_SIGN_UP:
                new Thread(new ServerWorkerSystemSignUp(socketClient,
                        (InnerMessageSystemAddUserSignUp) message.getInnerMessage())).start();

                break;
            case EXIST_USER_LOGIN:
                new Thread(new ServerWorkerSystemLogin(socketClient,
                        (InnerMessageSystemExistUserLogin) message.getInnerMessage())).start();
                break;
        }
    }
}
