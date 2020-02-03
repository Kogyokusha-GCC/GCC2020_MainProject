package io.github.kogyokusha_gcc.gcc2020.main;

import java.util.logging.Level;

import io.github.kogyokusha_gcc.gcc2020.main.launch.Game;
import io.github.kogyokusha_gcc.gcc2020.main.launch.Game.Genre;
import io.github.kogyokusha_gcc.gcc2020.main.launch.GameException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class TestController implements EventHandler<KeyEvent>{
	public ImageView gameImage;
	public Label gameName,gameDifficulty,gameNum;
	public static Label gameDes;


	private static int genre;
	private static int game;

	private static Game[][] games;
	@Override
	public void handle(KeyEvent event) {//十字キー
		switch(event.getCode()) {
		case UP:
			//ジャンル増加
			if(genre <= 4) {genre++;}
			break;
		case DOWN:
			//ジャンル減少
			if(genre >= 0) {genre--;}
			genre--;
			break;
		case RIGHT:
			//ゲーム増加
			if(game < games[genre].length) {game++;}
			break;
		case LEFT:
			if(game <= 0) {game--;}
			break;
		default:
			break;
		}
		this.refresh();

	}

	@FXML
	public void initialize() {
		try {
			Game[] action = Genre.ACTION.getListGames();
			Game[] command = Genre.COMMAND.getListGames();
			Game[] shooting = Genre.SHOOTING.getListGames();
			Game[] table = Genre.TABLE.getListGames();
			Game[] others = Genre.OTHERS.getListGames();
			games = new Game[5][];
			games[0] = action;
			games[1] = command;
			games[2] = shooting;
			games[3] = table;
			games[4] = others;
			TestMode.logger.fine("loaded games");
		} catch (GameException e) {
			TestMode.logger.log(Level.WARNING,e.toString());
		}
		genre = 0;game=0;
		this.refresh();
	}
	@FXML
	public void launchGame() {
		try {
		games[genre][game-1].launch();
		} catch(GameException e) {
			e.printStackTrace();
			TestMode.logger.warning("failed to launch " + games[genre][game]);
		}
	}

	private void refresh() {
		System.out.println("a");
		System.out.println(games.length);
		Game nowGame = games[genre][game];
		gameImage.setImage(new Image(nowGame.getImagePath()));
	}

}
