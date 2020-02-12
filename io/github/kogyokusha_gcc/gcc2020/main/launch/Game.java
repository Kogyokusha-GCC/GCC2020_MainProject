package io.github.kogyokusha_gcc.gcc2020.main.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import io.github.kogyokusha_gcc.gcc2020.main.TestMode;

public class Game {
  private Genre genre;

  private int game;

  private String name;

  private String exe;

  private String image;

  private int difficulty;

  private int popularity;

  private String howtoplay;

  private String description;

  private Properties properties;

  private static Process process;

  public String getName() {
    return this.name;
  }

  public String getExePath() {
    return String.valueOf(System.getProperty("user.dir")) + "\\" + getGamePath() + "\\" + this.exe;
  }

  public String getImagePath() {
    return String.valueOf(System.getProperty("user.dir")) + "\\" + getGamePath() + "\\" + this.image;
  }

  public int getDifficulty() {
    return this.difficulty;
  }

  public int getPopularity() {
    return this.popularity;
  }

  public String getHowToPlay() {
    if (!this.howtoplay.contains("{"))
      return this.howtoplay;
    String res = "";
    res = this.howtoplay.replace("}", "");
    res = res.replace("{", "");
    Path file = Paths.get(String.valueOf(getGamePath()) + "\\" + res, new String[0]);
    try {
      List<String> text = Files.readAllLines(file);
      StringBuilder fin = new StringBuilder(text.get(0));
      for (int i = 1; i < text.size(); i++)
        fin.append("\n" + (String)text.get(i));
      return fin.toString();
    } catch (Exception e) {
      return "error:" + e.toString();
    }
  }

  public String getDescription() {
    if (!this.description.contains("{"))
      return this.description;
    String res = "";
    res = this.description.replace("}", "");
    res = res.replace("{", "");
    Path file = Paths.get(String.valueOf(getGamePath()) + "\\" + res, new String[0]);
    try {
      List<String> text = Files.readAllLines(file);
      StringBuilder fin = new StringBuilder(text.get(0));
      for (int i = 1; i < text.size(); i++)
        fin.append("\n" + (String)text.get(i));
      return fin.toString();
    } catch (Exception e) {
      return "error:" + e.toString();
    }
  }

  public enum Genre {
    ACTION(0, new File("games/action/")),
    COMMAND(1, new File("games/command/")),
    SHOOTING(2, new File("games/shooting/")),
    TABLE(3, new File("games/table/")),
    OTHERS(4, new File("games/other/"));

    private int id;

    private File file;

    Genre(int id, File file) {
      this.id = id;
      this.file = file;
    }

    private int getId() {
      return this.id;
    }

    public long getMaxGame() {
      return (this.file.list()).length;
    }

    public File getFile() {
      return this.file;
    }

    private File[] getListFiles() {
      return this.file.listFiles();
    }

    public Game[] getListGames() throws GameException {
      Game[] games = new Game[(int)getMaxGame()];
      for (int i = 0; i < getMaxGame(); i++) {
        TestMode.logger.finest(this.file + String.valueOf(i));
        games[i] = new Game(this, i);
        TestMode.logger.finest("success(Game)");
      }
      return games;
    }
  }

  public Game(Genre ge, int ga) throws GameException {
    this.genre = ge;
    GameException e = new GameException(1), e2 = new GameException(2);
    if (ga < 0)
      throw e;
    if (ga >= ge.getMaxGame())
      throw e;
    this.game = ga;
    try {
      TestMode.logger.finest("loading properties of " + getGameID() + " to memory");
      TestMode.logger.finest(String.valueOf(ge.getListFiles()[ga].getPath()) + "\\game.cfg");
      this.properties = new Properties();
      InputStream istream = new FileInputStream(String.valueOf(ge.getListFiles()[ga].getPath()) + "\\game.cfg");
      InputStreamReader isr = new InputStreamReader(istream, "UTF-8");
      this.properties.load(isr);
      TestMode.logger.finest("success(properties)");
    } catch (IOException e1) {
      throw e2;
    }
    if (this.properties.isEmpty())
      throw e2;
    this.name = this.properties.getProperty("name");
    TestMode.logger.finest("loaded name of" + getGameID());
    this.exe = this.properties.getProperty("exe");
    TestMode.logger.finest("loaded exefilepath of" + getGameID());
    this.image = this.properties.getProperty("img");
    TestMode.logger.finest("loaded name of" + getGameID());
    this.difficulty = Integer.parseInt(this.properties.getProperty("dif"));
    TestMode.logger.finest("loaded difficulty of" + getGameID());
    this.popularity = Integer.parseInt(this.properties.getProperty("pop"));
    TestMode.logger.finest("loaded popularity of" + getGameID());
    this.description = this.properties.getProperty("des");
    TestMode.logger.finest("loaded description of" + getGameID());
    this.howtoplay = this.properties.getProperty("htp");
    TestMode.logger.finest("loaded how_to_play of" + getGameID());
    TestMode.logger.finer(String.valueOf(getGameID()) + "succcessfully loaded.");
  }

  public void launch() throws GameException {
		if(process == null || !process.isAlive()) {
			ProcessBuilder launch = new ProcessBuilder(new String[] { getExePath() });
			TestMode.logger.finest(getExePath());
			try {
				process = launch.start();
				TestMode.logger.config("now starting GameID{" + String.valueOf(this.genre.getId()) + "," + String.valueOf(this.game) + "}");
				TestMode.logger.config("process finished.");
			} catch (IOException e) {
			    throw new GameException(3);
			}
		}
  }

  public void killProcess() {
    process.destroy();
    TestMode.logger.config("process successfully killed.");
  }

  public String getGameID() {
    return "GameID{" + String.valueOf(this.genre.getId()) + "," + String.valueOf(this.game) + "}";
  }

  public String toString() {
    return getGameID();
  }

  public String getGamePath() {
    return this.genre.getListFiles()[this.game].getPath();
  }
}
