package io.github.kogyokusha_gcc.gcc2020.main;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import io.github.kogyokusha_gcc.gcc2020.main.launch.Game;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestMode extends Application {
  public static FileHandler logFileHandler;

  public static ConsoleHandler logConsoleHandler;

  public static Logger logger;

  public void start(Stage primaryStage) throws IOException {
    ScrollPane pane2 = new ScrollPane();
    Label description = new Label("ゲーム説明");
    TestController.des = description;
    pane2.setContent(description);
    Scene scene2 = new Scene(pane2, 500.0D, 500.0D);
    Stage stage2 = new Stage();
    stage2.setScene(scene2);
    stage2.setResizable(false);
    stage2.sizeToScene();
    stage2.setTitle("テストモードウィンドウ2");
    stage2.show();
    scene2.setOnKeyPressed(new TestController());
    AnchorPane root = FXMLLoader.<AnchorPane>load(getClass().getResource("Test.fxml"));
    Scene scene = new Scene(root);
    primaryStage.setResizable(false);
    primaryStage.sizeToScene();
    primaryStage.setTitle("テストモードウィンドウ1");
    primaryStage.setScene(scene);
    primaryStage.show();
    scene.setOnKeyPressed(new TestController());
  }

  public static void main(String[] args) throws SecurityException, IOException {
    logger = Logger.getLogger("testmode");
    Date date = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
    logFileHandler = new FileHandler("log/" + sdf.format(date) + ".log");
    logFileHandler.setFormatter(new SimpleFormatter());
    logFileHandler.setEncoding("UTF8");
    logger.addHandler(logFileHandler);
    logConsoleHandler = new ConsoleHandler();
    logConsoleHandler.setLevel(Level.FINEST);
    logger.addHandler(logConsoleHandler);
    logger.setLevel(Level.FINEST);
    logger.info("now starting");
    logger.finest("Version:" + TestMode.class.getPackage().getImplementationVersion());
    logger.finest("CHDIR=" + System.getProperty("user.dir"));
    logger.finest("ACTION:" + Game.Genre.ACTION.getMaxGame());
    logger.finest("COMMAND:" + Game.Genre.COMMAND.getMaxGame());
    logger.finest("SHOOTING:" + Game.Genre.SHOOTING.getMaxGame());
    logger.finest("TABLE:" + Game.Genre.TABLE.getMaxGame());
    logger.finest("OTHERS:" + Game.Genre.OTHERS.getMaxGame());
    launch(args);
  }

  public static void download(URL url) throws IOException {
    String path = url.getPath();
    String name = path.substring(path.lastIndexOf("/") + 1);
    int size = 0;
    Exception exception1 = null, exception2 = null;
    try {
      DataInputStream in = new DataInputStream(url.openStream());
      try {

      } finally {
        exception2 = null;
        if (exception1 == null) {
          exception1 = exception2;
        } else if (exception1 != exception2) {
          exception1.addSuppressed(exception2);
        }
        if (in != null)
          in.close();
      }
    } finally {
      exception2 = null;
      if (exception1 == null) {
        exception1 = exception2;
      } else if (exception1 != exception2) {
        exception1.addSuppressed(exception2);
      }
    }
  }
}
