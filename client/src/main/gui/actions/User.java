package main.gui.actions;

import main.net.Client;
import main.net.Message;
import main.gui.DialogWindow;
import main.gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class User {
    private final MainFrame mainFrame;

    private final Client client;

    private final Action newUserAction;

    private final Action loginAction;



    public User(MainFrame mainFrame, Client client) {
        this.mainFrame = mainFrame;
        this.client = client;

        newUserAction = new NewUserAction();
        loginAction = new LoginAction();
    }

    public Action getNewUserAction() {
        return newUserAction;
    }

    public Action getLoginAction() {
        return loginAction;
    }


    private class NewUserAction extends AbstractAction {
        NewUserAction() {
            super("Registration");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogWindow authDialog = new DialogWindow(mainFrame);
            client.sendToServer(new Message(6,authDialog.getUserName() + ";" + authDialog.getPassword()));
            Message answer = client.getMessage();
            if (answer.getMessage().equals("OK")) {
                mainFrame.setIsConnected(true);
                JOptionPane.showMessageDialog(mainFrame, "Registration OK");
            } else if(answer.getMessage().equals("Exists")) {
                JOptionPane.showMessageDialog(mainFrame, "A User with that name already exists");
            }
        }
    }

    private class LoginAction extends AbstractAction {
        LoginAction() {
            super("Login");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            DialogWindow authDialog = new DialogWindow(mainFrame);
            client.sendToServer(new Message(5,authDialog.getUserName() + ";" + authDialog.getPassword()));
            Message answer = client.getMessage();
            if (answer.getMessage().equals("OK")) {
                mainFrame.setIsConnected(true);
                JOptionPane.showMessageDialog(mainFrame, "LOGIN OK");
            } else if(answer.getMessage().equals("Exists")) {
                JOptionPane.showMessageDialog(mainFrame, "User's name/password invalid");
            }
        }
    }
}



