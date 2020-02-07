package io.github.kogyokusha_gcc.gcc2020.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import io.github.kogyokusha_gcc.gcc2020.main.launch.Game.Genre;
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

	@Override
	public void start(Stage primaryStage) throws IOException{
		AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Test.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setResizable(false);
		primaryStage.sizeToScene();
		primaryStage.setTitle("テストモードウィンドウ1");
		//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		scene.setOnKeyPressed(new TestController());

		ScrollPane pane2 = new ScrollPane();
		Label description = new Label("ゲーム説明");
		TestController.gameDes = description;
		pane2.setContent(description);
		Scene scene2 = new Scene(pane2,500,500);
		Stage stage2 = new Stage();
		stage2.setScene(scene2);
		stage2.setResizable(false);
		stage2.sizeToScene();
		stage2.setTitle("テストモードウィンドウ2");
		stage2.show();
		scene2.setOnKeyPressed(new TestController());


	}

	public static void main(String[] args) throws SecurityException, IOException {
		/*//ログの設定(startup)//*/
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
		/*//*/
		logger.info("now starting");
		logger.finest("Version:" + TestMode.class.getPackage().getImplementationVersion());
		logger.finest("CHDIR=" + System.getProperty("user.dir"));
		logger.finest("ACTION:" + Genre.ACTION.getMaxGame());
		logger.finest("COMMAND:" + Genre.COMMAND.getMaxGame());
		logger.finest("SHOOTING:" + Genre.SHOOTING.getMaxGame());
		logger.finest("TABLE:" + Genre.TABLE.getMaxGame());
		logger.finest("OTHERS:" + Genre.OTHERS.getMaxGame());

		launch(args);
	}

	public static void download(URL url) throws IOException {

	    String path = url.getPath();
	    String name = path.substring(path.lastIndexOf("/") + 1);
	    int size = 0;

	    try (DataInputStream in = new DataInputStream(url.openStream());
	         DataOutputStream out = new DataOutputStream(new FileOutputStream(name))) {

	        byte[] buf = new byte[8192];
	        int len = 0;

	        //入力ストリームからバイト配列に読み込む。ストリームが終端に達した場合は -1 が返る
	        while ((len = in.read(buf)) != -1) {
	            //バイト配列を出力ストリームに書き込む
	            out.write(buf, 0, len);
	            size += len;
	        }

	        //バッファリングされていたすべての出力バイトを強制的に書き込む
	        out.flush();
	    }

	    System.out.println(name + " - " + size + " bytes");
	}

}
