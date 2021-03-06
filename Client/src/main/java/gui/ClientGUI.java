package gui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;

public abstract class ClientGUI {
  protected final JButton submitButton = new JButton();
  protected final JTextField inputTextField = new JTextField();
  private final JFrame frame = new JFrame();
  private final JPanel panel = new JPanel();
  private final JScrollPane scrollPane = new JScrollPane();
  private final GridBagConstraints gbc = new GridBagConstraints();
  protected JTextArea displayServerFeed = new JTextArea();

  public synchronized void createWindowFrame() {
    frame.setTitle("Client");
    frame.setSize(350, 350);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    frame.setResizable(false);
    attachComponentsToFrame();
    frame.pack();
    frame.setVisible(true);
  }

  protected void attachComponentsToFrame() {
    panel.setLayout(new GridBagLayout());
    //display label

    //textarea to displayed server feed messages
    displayServerFeed.setRows(20);
    displayServerFeed.setColumns(30);
    scrollPane.setViewportView(displayServerFeed);
    scrollPane.setWheelScrollingEnabled(true);
    displayServerFeed.setEditable(false);
    displayServerFeed.setText(null);
    gbc.gridwidth = 3;
    gbc.gridx = 0;
    gbc.gridy = 1;
    gbc.insets = new Insets(10, 10, 10, 10);
    gbc.fill = GridBagConstraints.BOTH;
    panel.add(displayServerFeed, gbc);
    //text field to input IP the server should try to connect to
    inputTextField.setColumns(30);
    gbc.gridwidth = 2;
    gbc.gridx = 0;
    gbc.gridy = 2;
    gbc.insets = new Insets(0, 10, 0, 0);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(inputTextField, gbc);
    //submit button for text field data
    submitButton.setText("Send");
    gbc.gridwidth = 1;
    gbc.gridx = 2;
    gbc.gridy = 2;
    gbc.insets = new Insets(5, 0, 10, 10);
    gbc.fill = GridBagConstraints.HORIZONTAL;
    panel.add(submitButton, gbc);
    //super.submitButton.addActionListener((ActionListener) this);
    submitButton.addActionListener((ActionListener) this);
    frame.add(panel, null);
  }
}
