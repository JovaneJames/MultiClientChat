package process;

import gui.ClientGUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClientProcess extends ClientGUI implements ActionListener {
  private final InetAddress localhost;
  int port;
  private ObjectOutputStream outputStream;
  private Socket socket;

  ClientProcess(InetAddress localhost, int port) {
    this.localhost = localhost;
    this.port = port;
  }

  //creates a new socket object that takes in ip/port and connect to server
  void connectToServer() {
    try {
      socket = new Socket(localhost, port);
      displayServerFeed.append("connected to server\n");
      outputStream = new ObjectOutputStream(socket.getOutputStream());
      readMessagesFromServer();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //runs in separate executor thread - reads messages from the server
  private void readMessagesFromServer() {
    Executor executorService = Executors.newSingleThreadExecutor();
    executorService.execute(() -> {
      try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {
        while (true) {
          sendMessageToServer("alive");
          String line = inputStream.readUTF();
          System.out.println(line);
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @Override//Send messages from the text field to the server
  public synchronized void actionPerformed(ActionEvent e) {
    if (e.getSource() == submitButton) {
      if (!inputTextField.getText().isEmpty()) {
        try {
          sendMessageToServer(inputTextField.getText());
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      }
    }
  }

  private void sendMessageToServer(String message) throws IOException {
    outputStream.writeUTF(message);
    outputStream.flush();
  }
}
