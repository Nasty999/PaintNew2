package main.gui;

import main.net.Client;
import main.net.Message;
import main.gui.actions.Board;
import main.gui.actions.Edit;
import main.gui.actions.User;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final Client client;
    private boolean isConnected = false;

    private final User userActionFactory;
    private final Board boardActionFactory;
    private final Edit editActionFactory;

    private BoardPanel board;

    public static void main(String[] args) {
        new MainFrame();
    }

    public MainFrame() {
        super("Paint");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        client = new Client("localhost", 10001);

        userActionFactory = new User(this, client);
        boardActionFactory = new Board(this,client);
        editActionFactory = new Edit(this);

        setJMenuBar(createMainMenu());

        getContentPane().add(creatToolBar(), BorderLayout.NORTH);

        setSize(840, 600);
        setResizable(false);
        setVisible(true);
    }

    private JMenuBar createMainMenu() {
        JMenuBar mainMenu = new JMenuBar();
        mainMenu.add(createUserMenu());
        mainMenu.add(createBoardMenu());
        return mainMenu;
    }

    private JMenu createUserMenu() {
        JMenu userMenu = new JMenu("Client");
        userMenu.add(userActionFactory.getNewUserAction());
        userMenu.add(userActionFactory.getLoginAction());
        return userMenu;
    }

    private JToolBar creatToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.add(editActionFactory.getChangeBrushSize());
        toolBar.add(editActionFactory.getChangeBrushColor());
        toolBar.add(editActionFactory.getEraser());
        toolBar.setVisible(true);
        return toolBar;
    }

    private JMenu createBoardMenu() {
        JMenu boardMenu = new JMenu("Board");
        boardMenu.add(boardActionFactory.getNewBoardAction());
        boardMenu.add(boardActionFactory.getJoinBoardAction());
        return boardMenu;
    }


    public void createBoard(String mode, String name) {
        this.board = new BoardPanel(this, client, mode, name, Color.black, 10);
        getContentPane().add(board, BorderLayout.CENTER);
        repaint();
        revalidate();
    }

    public void repaintBoard() {
        removeBoard();
        String nameBoard = board.getName();
        Color brushColor = board.getBrushColor();
        int brushSize = board.getBrushSize();
        client.sendToServer(new Message(1, board.getName()));
        Message answer = client.getMessage();
        if (answer.getMessage().equals("OK")) {
            this.board = new BoardPanel(this, client, "CONNECT", nameBoard, brushColor, brushSize);
            getContentPane().add(board, BorderLayout.CENTER);
            repaint();
            revalidate();
        }
    }


    public void removeBoard() {
        if(board != null) {
            remove(board);
            repaint();
            revalidate();
        }
    }

    public BoardPanel getBoard() {
        return this.board;
    }

    public boolean getIsConnected() {
        return isConnected;
    }

    public void setIsConnected(boolean is) {
        this.isConnected = is;
    }
}

