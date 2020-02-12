package io.github.kogyokusha_gcc.gcc2020.main.launch;

public class GameException extends Exception {
  private int type;

  public GameException(int t) {
    this.type = t;
  }

  public void setType(int t) {
    this.type = t;
  }

  public int getType() {
    return this.type;
  }

  public String getTypeInString() {
    switch (this.type) {
      case 1:
        return "GameException(1):Game ID out of bounds.";
      case 2:
        return "GameException(2):Config file cant load.";
      case 3:
        return "GameException(3):failed to launch game.";
    }
    return "GameException(0):Unknown Exception occured during game setup.";
  }

  public String toString() {
    return getTypeInString();
  }
}
