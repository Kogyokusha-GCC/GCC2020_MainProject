package io.github.kogyokusha_gcc.gcc2020.main.launch;

public class Game{
	public enum Genre {
		ACTION(0),
		COMMAND(1),
		SHOOTING(2),
		TABLE(3),
		OTHERS(4);

		private int id;

		private Genre(int id) {
			this.id = id;
		}

		public int getId() {
			return this.id;
		}
	}
}