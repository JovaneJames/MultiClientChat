package com.server.process;

import com.server.User;
import com.server.gui.ServerGUI;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerProcess extends ServerGUI {

  private static int counter = 1;
  private static final Vector<ClientHandler> clientHandlerVector = new Vector<>();
  private ServerSocket serverSocket;

  ServerProcess(int port) {
    try {
      serverSocket = new ServerSocket(port);
      if (serverSocket.isClosed())
        throw new RuntimeException("cannot connect to port: " + port);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  static int getCounter() {
    return counter++;
  }

  @Override
  protected void serverSocketHandler() {
    System.out.println("starting....");
    displayServerFeed.append("starting...\n");
    new Thread(() -> incomingConnectionsHandler(serverSocket)).start();
  }

  private void incomingConnectionsHandler(ServerSocket serverSocket) {
    while (true) {
      System.out.println("waiting...");
      displayServerFeed.append("waiting...\n");
      try {
        Socket socket = serverSocket.accept();
        System.out.println("connection accepted...");
        displayServerFeed.append("connection accepted...\n");
        multiClientHandler(socket);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  private void multiClientHandler(Socket socket) {
    ExecutorService executorService = Executors.newSingleThreadExecutor();
    ClientHandler clientHandler = new ClientHandler(socket);
    System.out.println("here method");
    executorService.execute(clientHandler::execute);
    clientHandlerVector.addElement(clientHandler);
    System.out.println("created method");
  }

  protected static Vector<User> getListOfUsers() {
    Vector<User> listOfUsers = new Vector<>();
    for (ClientHandler clientHandler : clientHandlerVector)
      if (clientHandler.user != null)
        listOfUsers.addElement(clientHandler.user);
    return listOfUsers;
  }
}
