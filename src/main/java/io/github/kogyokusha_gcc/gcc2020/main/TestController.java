package io.github.kogyokusha_gcc.gcc2020.main;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyEvent;

public class TestController implements EventHandler<KeyEvent>{
	@Override
	public void handle(KeyEvent event) {//十字キー
		switch(event.getCode()) {
		case UP:
			//ジャンル増加
			break;
		case DOWN:
			//ジャンル減少
			break;
		case RIGHT:
			//ゲーム増加
			break;
		case LEFT:
			//ゲーム減少
			break;
		default:
			break;
		}

	}
	@FXML
	public void initialize() {

	}
	@FXML
	public void launchGame() {

	}

}
