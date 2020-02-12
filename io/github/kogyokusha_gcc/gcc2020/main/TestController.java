package io.github.kogyokusha_gcc.gcc2020.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.logging.Level;

import io.github.kogyokusha_gcc.gcc2020.main.launch.Game;
import io.github.kogyokusha_gcc.gcc2020.main.launch.GameException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;

public class TestController implements EventHandler<KeyEvent> {
  public ImageView gameImage;

  public Label gameName;

  public Label gameDifficulty;

  public Label gamePopularity;

  public Label gameId;

  public static Label des;

  private static ImageView image;

  private static Label name;

  private static Label difficulty;

  private static Label popularity;

  private static Label id;

  private static int genre;

  private static int game;

  private static Game[][] games;

  public void handle(KeyEvent event) {
    TestMode.logger.finest("KeyPressed:" + event.getCode().getName());
    switch (event.getCode()) {
      case W:
        TestMode.logger.finest("genre++");
        if (genre < 4)
          genre++;
        game = 0;
        game = 0;
        break;
      case S:
        TestMode.logger.finest("genre--");
        if (genre > 0)
          genre--;
        game = 0;
        break;
      case D:
        TestMode.logger.finest("game++");
        if (game < (games[genre]).length - 1)
          game++;
        break;
      default:
        TestMode.logger.finest("game--");
        if (game > 0)
          game--;
        break;
    }
    refresh();
  }

  @FXML
  public void initialize() {
    image = this.gameImage;
    name = this.gameName;
    difficulty = this.gameDifficulty;
    popularity = this.gamePopularity;
    id = this.gameId;
    try {
      Game[] action = Game.Genre.ACTION.getListGames();
      Game[] command = Game.Genre.COMMAND.getListGames();
      Game[] shooting = Game.Genre.SHOOTING.getListGames();
      Game[] table = Game.Genre.TABLE.getListGames();
      Game[] others = Game.Genre.OTHERS.getListGames();
      games = new Game[5][];
      games[0] = action;
      games[1] = command;
      games[2] = shooting;
      games[3] = table;
      games[4] = others;
      TestMode.logger.fine("loaded games");
    } catch (GameException e) {
      TestMode.logger.log(Level.WARNING, e.toString());
      e.printStackTrace();
    }
    genre = 0;
    game = 0;
    refresh();
  }

  @FXML
  public void launchGame() {
    try {
      games[genre][game].launch();
    } catch (GameException e) {
      e.printStackTrace();
      TestMode.logger.warning("failed to launch " + games[genre][game]);
    }
  }

  private static void refresh() {
    Game nowGame = games[genre][game];
    name.setText(nowGame.getName());
    id.setText(nowGame.getGameID());
    difficulty.setText(String.valueOf(nowGame.getDifficulty()));
    popularity.setText(String.valueOf(nowGame.getPopularity()));
    des.setText(String.valueOf(nowGame.getDescription()) + "\n\n\n\n\n\n" + nowGame.getHowToPlay());
    try {
      image.setImage(new Image(new FileInputStream(new File(nowGame.getImagePath()))));
    } catch (FileNotFoundException e) {
      try {
        image.setImage(new Image(new FileInputStream(new File("image\\notfound.png"))));
      } catch (FileNotFoundException e1) {
        e1.printStackTrace();
      }
    }
  }
}
