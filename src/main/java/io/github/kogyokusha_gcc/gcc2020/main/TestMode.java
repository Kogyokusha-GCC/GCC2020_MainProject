package io.github.kogyokusha_gcc.gcc2020.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TestMode extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
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

	public static void main(String[] args) {
		System.out.println("Version:" + TestMode.class.getPackage().getImplementationVersion());
		launch(args);
	}

}
