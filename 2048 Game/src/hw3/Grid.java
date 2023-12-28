package hw3;

import api.Tile;

/**
 * Represents the game's grid.
 */
public class Grid {
	
	/**
	 * Instance variable that keeps track of the grid width
	 * @author Owen Ternus
	 */
	private int gridWidth;
	/**
	 * Instance variable that keeps track of the grid height
	 * @author Owen Ternus
	 */
	private int gridHeight;
	/**
	 * Creates a new tile instance
	 * @author Owen Ternus
	 */
	Tile gridTile[][];
	
	/**
	 * Creates a new grid.
	 * @author Owen Ternus
	 * @param width  number of columns
	 * @param height number of rows
	 */
	public Grid(int width, int height) {
		gridWidth = width;
		gridHeight = height;
		gridTile = new Tile[width][height];
		for(int i = 0; i < width; ++i) {
			for(int j = 0; j < height; ++j) {
				gridTile[i][j] = new Tile(1);
			}
		}
	}

	/**
	 * Get the grid's width.
	 * @author Owen Ternus
	 * @return width
	 */
	public int getWidth() {
		return gridWidth;
	}

	/**
	 * Get the grid's height.
	 * @author Owen Ternus
	 * @return height
	 */
	public int getHeight() {
		return gridHeight;
	}

	/**
	 * Gets the tile for the given column and row.
	 * @author Owen Ternus
	 * @param x the column
	 * @param y the row
	 * @return
	 */
	public Tile getTile(int x, int y) {
		return gridTile[x][y];
	}

	/**
	 * Sets the tile for the given column and row. Calls tile.setLocation().
	 * @author Owen Ternus
	 * @param tile the tile to set
	 * @param x    the column
	 * @param y    the row
	 */
	public void setTile(Tile tile, int x, int y) {
		gridTile[x][y] = tile;
		tile.setLocation(x, y);
//		gridTile[x][y] = tile;
	}
	
	@Override
	public String toString() {
		String str = "";
		for (int y=0; y<getHeight(); y++) {
			if (y > 0) {
				str += "\n";
			}
			str += "[";
			for (int x=0; x<getWidth(); x++) {
				if (x > 0) {
					str += ",";
				}
				str += getTile(x, y);
			}
			str += "]";
		}
		return str;
	}
}
