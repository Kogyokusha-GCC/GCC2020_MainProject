package io.github.kogyokusha_gcc.gcc2020.main.launch;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import io.github.kogyokusha_gcc.gcc2020.main.TestMode;

public class Game{
	private Genre genre;
	private int game;
	private String name;public String getName() {return name;}
	private String exe;public String getExePath() {return exe;}
	private String image;public String getImagePath() {return image;}
	private int difficulty;public int getDifficulty() {return difficulty;}
	private int popularity;public int getPopularity() {return popularity;}
	private String howtoplay;public String getHowToPlay() {return howtoplay;}
	private String description;public String getDescription() {return description;}
	private Properties properties;
	private static Process process;

	public enum Genre {
		ACTION(0,new File("games/action/")),
		COMMAND(1,new File("games/command/")),
		SHOOTING(2,new File("games/shooting/")),
		TABLE(3,new File("games/table/")),
		OTHERS(4,new File("games/others/"));

		private int id;
		private File file;

		private Genre(int id,File file) {
			this.id = id;
			this.file = file;
		}
		private int getId() {
			return this.id;
		}
		public long getMaxGame() {
			return file.list().length;
		}
		private File getFile() {
			return file;
		}
		private File[] getListFiles() {
			return file.listFiles();
		}
		public Game[] getListGames() throws GameException {
			Game[] games = new Game[(int)file.length()];
			for(int i = 0;i<getMaxGame();i ++) {
				games[i] = new Game(this,i);
			}
			return games;
		}
	}
	public Game(Genre ge,int ga) throws GameException {
		genre = ge;

		GameException e = new GameException(1);GameException e2 = new GameException(2);
		if(ga < 0) {
			throw e;
		} else if(ga >= ge.getMaxGame()) {
			throw e;
		} else {
			game = ga;
		}

		try {
			properties.load(new FileInputStream(ge.getListFiles()[ga].getPath() + "game.cfg"));TestMode.logger.finest("loaded properties of " + getGameID() + " to memory");//プロパティファイルをメモリにロード
		} catch (IOException e1) {
			throw e2;
		}
		if(properties.isEmpty()) {
			throw e2;
		}
		name = properties.getProperty("name");TestMode.logger.finest("loaded name of" + getGameID());
		exe = properties.getProperty("exe");TestMode.logger.finest("loaded exefilepath of" + getGameID());
		image = properties.getProperty("img");TestMode.logger.finest("loaded name of" + getGameID());
		difficulty = Integer.parseInt(properties.getProperty("dif"));TestMode.logger.finest("loaded difficulty of" + getGameID());
		popularity =Integer.parseInt(properties.getProperty("pop"));TestMode.logger.finest("loaded popularity of" + getGameID());
		description = properties.getProperty("des");TestMode.logger.finest("loaded description of" + getGameID());
		howtoplay = properties.getProperty("htp");TestMode.logger.finest("loaded how_to_play of" + getGameID());

		TestMode.logger.finer(getGameID() + "succcessfully loaded.");
	}

	public void launch() throws GameException {
		ProcessBuilder launch = new ProcessBuilder(genre.getFile().getPath());
		try {
			process = launch.start();
			TestMode.logger.config("now starting GameID{" + String.valueOf(genre.getId()) + "," +  String.valueOf(game) + "}");
			process.waitFor();
			TestMode.logger.config("process finished.");
		} catch (IOException | InterruptedException e) {
			throw new GameException(3);
		}
	}
	public void killProcess() {
		process.destroy();
		TestMode.logger.config("process successfully killed.");
	}

	public String getGameID() {
		return "GameID{" + String.valueOf(genre.getId()) + "," +  String.valueOf(game) + "}";
	}
	public String toString() {
		return getGameID();
	}


}



