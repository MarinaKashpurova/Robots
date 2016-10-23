package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.*;

import javax.swing.*;

import log.Logger;

public class MainApplicationFrame extends JFrame
{
    private final JDesktopPane desktopPane = new JDesktopPane();
    
    public MainApplicationFrame() {

        int inset = 50;        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
            screenSize.width  - inset*2,
            screenSize.height - inset*2);

        setContentPane(desktopPane);

        LogWindow logWindow = createLogWindow();
        addWindow(logWindow);

        GameWindow gameWindow = new GameWindow(10,10);
        gameWindow.setSize(400,  400);
        addWindow(gameWindow);

        CoordsWindow coordsWindow = new CoordsWindow(gameWindow.get_visualazer().get_gameModel());
        coordsWindow.setLocation(200, 200);
        coordsWindow.setSize(200, 200);
        addWindow(coordsWindow);

        setJMenuBar(generateMenuBar());
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                super.windowClosing(windowEvent);
                String ObjButtons[] = {"Да", "Нет"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Вы уверены?",
                        null,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        ObjButtons,
                        ObjButtons[1]);
                if(PromptResult==JOptionPane.YES_OPTION)
                    closeAndSave();
            }

            @Override
            public void windowOpened(WindowEvent windowEvent) {
                super.windowOpened(windowEvent);
                try {
                    openAndReestablish();
                }
                catch (PropertyVetoException e)
                {
                    System.out.println("Error with privileges");
                }
            }
        });
    }

    private void closeAndSave(){
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream("winlocation.txt"));
        }
        catch (IOException ex) {
            File stFile = new File(System.getProperty("user.home"), "winlocation.txt");
            try {
                stFile.createNewFile();
                oos = new ObjectOutputStream(new FileOutputStream("winlocation.txt"));
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        if (oos != null) {
            try {
                WindowLocation state = new WindowLocation(this);
                oos.writeObject(state);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            try {
                oos.flush();
                oos.close();
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        dispose();
    }

    private void openAndReestablish() throws PropertyVetoException
    {
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream("winlocation.txt"));
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        WindowLocation state = null;
        if (ois != null)
            try {
                state = (WindowLocation) ois.readObject();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            } catch (ClassNotFoundException e) {
                System.out.println(e.getMessage());
            }
        if (state != null)
            loadState(state);
    }

    public void loadState(WindowLocation state) throws PropertyVetoException
    {
        String description = "public class gui.";
        for (WindowInfo istate: state.getAllStates()) {
            if (istate.getTag().equals(description + "GameWindow")) {
                GameWindow game = new GameWindow(0, 0);
                addWindow(game);
                game.setState((WindowInfo<GameWindow>)istate);
            }
            else if (istate.getTag().equals(description + "LogWindow")) {
                LogWindow log = createLogWindow();
                addWindow(log);
                log.setState((WindowInfo<LogWindow>)istate);
            }
        }

    }

    public JInternalFrame[] getAllFrames() {
        return desktopPane.getAllFrames();
    }


    protected LogWindow createLogWindow()
    {
        LogWindow logWindow = new LogWindow(Logger.getDefaultLogSource());
        logWindow.setLocation(10,10);
        logWindow.setSize(300, 800);
        setMinimumSize(logWindow.getSize());
        logWindow.pack();
        Logger.debug("Протокол работает");
        return logWindow;
    }

    protected void addWindow(JInternalFrame frame)
    {
        desktopPane.add(frame);
        frame.setVisible(true);
    }


    protected JMenuBar generateMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(documentMenu());
        menuBar.add(lookAndFeelMenu());
        menuBar.add(testMenu());
        return menuBar;
    }

    protected JMenu documentMenu(){

        JMenu menu = new JMenu("Документ");
        menu.setMnemonic(KeyEvent.VK_D);
        menu.add(newGame());
        menu.add(quit());
        return menu;
    }


    private JMenuItem newGame() {
        JMenuItem menuItem = new JMenuItem("Новая игра");
        menuItem.setMnemonic(KeyEvent.VK_G);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, ActionEvent.ALT_MASK));
        menuItem.addActionListener(actionEvent -> addWindow(new GameWindow(400, 400)));
        return menuItem;
    }

    private JMenuItem quit() {
        JMenuItem menuItem = new JMenuItem("Выход");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(actionEvent ->
                Toolkit .getDefaultToolkit()
                        .getSystemEventQueue()
                        .postEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)));
        return menuItem;
    }

    private JMenu lookAndFeelMenu() {

        JMenu lookAndFeelMenu = new JMenu("Режим отображения");
        String prev = "Изменить режим отображения";
        lookAndFeelMenu.setMnemonic(KeyEvent.VK_V);
        lookAndFeelMenu.getAccessibleContext().setAccessibleDescription(prev);
        lookAndFeelMenu.add(systemLook());
        lookAndFeelMenu.add(universalLook());
        lookAndFeelMenu.add(nimbusLook());
        lookAndFeelMenu.add(metalLook());
        return lookAndFeelMenu;
    }

    private JMenuItem systemLook(){
        JMenuItem systemLook = new JMenuItem("Системная схема", KeyEvent.VK_S);
        systemLook.addActionListener((event) -> {
            setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            this.invalidate(); });
        return systemLook;
    }

    private JMenuItem universalLook() {
        JMenuItem crossplatformLook = new JMenuItem("Универсальная схема", KeyEvent.VK_U);
        crossplatformLook.addActionListener((event) -> {
            setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            this.invalidate(); });
        return crossplatformLook;
    }

    private JMenuItem nimbusLook() {
        JMenuItem crossplatformLook = new JMenuItem("Нимбус", KeyEvent.VK_N);
        crossplatformLook.addActionListener((event) -> {
            setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            this.invalidate(); });
        return crossplatformLook;
    }

    private JMenuItem metalLook() {
        JMenuItem crossplatformLook = new JMenuItem("Металическая схема", KeyEvent.VK_M);
        crossplatformLook.addActionListener((event) -> {
            setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            this.invalidate(); });
        return crossplatformLook;
    }


    private JMenu testMenu() {
        JMenu testMenu = new JMenu("Тесты");
        testMenu.setMnemonic(KeyEvent.VK_T);
        testMenu.getAccessibleContext().setAccessibleDescription("Тестовые комманды");
        JMenuItem addLogMessageItem = new JMenuItem("Сообщение в лог", KeyEvent.VK_S);
        addLogMessageItem.addActionListener((event) -> {
            Logger.debug("Новая строка");
        });
        testMenu.add(addLogMessageItem);
        return testMenu;
    }


    private void setLookAndFeel(String className)
    {
        try
        {
            UIManager.setLookAndFeel(className);
            SwingUtilities.updateComponentTreeUI(this);
        }
        catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            // just ignore
        }
    }
}
