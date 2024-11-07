package run;

import dao.ClientHander;
import view.ConnectServer;
import view.HomeView;
import view.InfoPlayerView;
import view.JoinRoomView;
import view.LoginView;
import view.MessageView;
import view.RegisterView;
import view.RoomGameView;

public class ClientRun {
    public enum SceneName {
        CONNECTSERVER,
        LOGIN,
        REGISTER,
        HOMEVIEW,
        INFOPLAYER,
        MESSAGEVIEW,
        JOINROOMVIEW,
        ROOMGAMEVIEW
    }

    // scenes
    public static ConnectServer connectServer;
    public static LoginView loginView;
    public static RegisterView registerView;
    public static HomeView homeView;
    public static InfoPlayerView infoPlayerView;
    public static MessageView messageView;
    public static JoinRoomView joinRoomView;
    public static RoomGameView roomGameView;

    // controller 
    public static ClientHander clientHander;

    public ClientRun() {
        clientHander = new ClientHander();
        initScene();
        openScene(SceneName.CONNECTSERVER);
    }
    // Test
    public void initScene() {
        connectServer = new ConnectServer();
        loginView = new LoginView();
        registerView = new RegisterView();
        homeView = new HomeView();
        infoPlayerView = new InfoPlayerView();
        messageView = new MessageView();
        joinRoomView = new JoinRoomView();
        roomGameView = new RoomGameView();
    }

    public static void openScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    connectServer = new ConnectServer();
                    connectServer.setVisible(true);
                    break;
                case LOGIN:
                    loginView = new LoginView();
                    loginView.setVisible(true);
                    break;
                case REGISTER:
                    registerView = new RegisterView();
                    registerView.setVisible(true);
                    break;
                case HOMEVIEW:
                    homeView = new HomeView();
                    homeView.setVisible(true);
                    break;
                case INFOPLAYER:
                    infoPlayerView = new InfoPlayerView();
                    infoPlayerView.setVisible(true);
                    break;
                case MESSAGEVIEW:
                    messageView = new MessageView();
                    messageView.setVisible(true);
                    break;
                case JOINROOMVIEW:
                    joinRoomView = new JoinRoomView();
                    joinRoomView.setVisible(true);
                    break;
                case ROOMGAMEVIEW:
                    roomGameView = new RoomGameView();
                    roomGameView.setVisible(true);
                default:
                    break;
            }
        }
    }

    public static void closeScene(SceneName sceneName) {
        if (null != sceneName) {
            switch (sceneName) {
                case CONNECTSERVER:
                    connectServer.dispose();
                    break;
                case LOGIN:
                    loginView.dispose();
                    break;
                case REGISTER:
                    registerView.dispose();
                    break;
                case HOMEVIEW:
                    homeView.dispose();
                    break;
                case INFOPLAYER:
                    infoPlayerView.dispose();
                    break;
                case MESSAGEVIEW:
                    messageView.dispose();
                    break;
                case JOINROOMVIEW:
                    joinRoomView.dispose();
                    break;
                case ROOMGAMEVIEW:
                    roomGameView.dispose();
                default:
                    break;
            }
        }
    }

    public static void closeAllScene() {
        connectServer.dispose();
        loginView.dispose();
        registerView.dispose();
        homeView.dispose();
        infoPlayerView.dispose();
        messageView.dispose();
        joinRoomView.dispose();
        roomGameView.dispose();
    }

    public static void main(String[] args) {
        new ClientRun();
    }
}
