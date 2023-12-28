package hw3;

import java.util.ArrayList;
import java.util.Random;

import api.ScoreUpdateListener;
import api.ShowDialogListener;
import api.Tile;

/**
 * Class that models a game.
 */
public class ConnectGame {
	private ShowDialogListener dialogListener;
	private ScoreUpdateListener scoreListener;
	/**
	 * Sets min tile level
	 */
	private int minTileLevel;
	/**
	 * Sets max tile level
	 */
	private int maxTileLevel;
	/*
	 * Sets max width of the board
	 */
	private int maxWidth = 5;
	/**
	 * sets max height of the board
	 */
	private int maxHeight = 8;
	/**
	 * keeps track of the game score
	 */
	private long score = 0;
	/**
	 * creates a random number for the game
	 */
	private Random randomNumber;
	/**
	 * new array list of tiles
	 */
	private ArrayList<Tile> selectedTiles = new ArrayList<Tile>();
	/**
	 * new grid instance
	 */
	Grid grid;
	
	
	/**
	 * Constructs a new ConnectGame object with given grid dimensions and minimum
	 * and maximum tile levels.
	 * @author Owen Ternus
	 * @param width  grid width
	 * @param height grid height
	 * @param min    minimum tile level
	 * @param max    maximum tile level
	 * @param rand   random number generator
	 */
	public ConnectGame(int width, int height, int min, int max, Random rand) {
		grid = new Grid(width, height);
		minTileLevel = min;
		maxTileLevel = max;
		randomNumber = rand;
	}

	/**
	 * Gets a random tile with level between minimum tile level inclusive and
	 * maximum tile level exclusive. For example, if minimum is 1 and maximum is 4,
	 * the random tile can be either 1, 2, or 3.
	 * <p>
	 * DO NOT RETURN TILES WITH MAXIMUM LEVEL
	 * @author Owen Ternus
	 * @return a tile with random level between minimum inclusive and maximum
	 *         exclusive
	 */
	public Tile getRandomTile() {
		int level = randomNumber.nextInt(3) + 1;
		Tile tile = new Tile(level);
		return tile;
	}

	/**
	 * @author Owen Ternus
	 * Regenerates the grid with all random tiles produced by getRandomTile().
	 */
	public void radomizeTiles() {
	while((grid.getWidth() < maxWidth) && (grid.getHeight() < maxHeight)) {
		for(int i = 0; i < grid.getWidth(); i++) {
			for(int j = 0; j < grid.getHeight(); j++) {
				grid.setTile(getRandomTile(), i, j);
			}
		}
	}
	}

	/**
	 * Determines if two tiles are adjacent to each other. The may be next to each
	 * other horizontally, vertically, or diagonally.
	 * 
	 * @param t1 one of the two tiles
	 * @param t2 one of the two tiles
	 * @author Owen Ternus
	 * @return true if they are next to each other horizontally, vertically, or
	 *         diagonally on the grid, false otherwise
	 */
	public boolean isAdjacent(Tile t1, Tile t2) {
	    boolean a = false;
		for(int i = -1; i < 2; i++) {
	    	for (int j = -1; j < 2; j++) {
	    		int x = t1.getX() + i;
	    		int y = t1.getY() + j;
	    		if(x == t2.getX() && y == t2.getY()) {
	    			a = true;
	    		}
	    	}
	    }
	    return a;
	}


	/**
	 * Indicates the user is trying to select (clicked on) a tile to start a new
	 * selection of tiles.
	 * <p>
	 * If a selection of tiles is already in progress, the method should do nothing
	 * and return false.
	 * <p>
	 * If a selection is not already in progress (this is the first tile selected),
	 * then start a new selection of tiles and return true.
	 * @author Owen Ternus
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return true if this is the first tile selected, otherwise false
	 */
	public boolean tryFirstSelect(int x, int y) {
		boolean a = false;
		if(selectedTiles.isEmpty() == false) {
			return a;
		}else {
			selectedTiles.add(grid.gridTile[x][y]);
			grid.gridTile[x][y].setSelect(true);
			a = true;
			return a;
		}
	}

	/**
	 * Indicates the user is trying to select (mouse over) a tile to add to the
	 * selected sequence of tiles. The rules of a sequence of tiles are:
	 * 
	 * <pre>
	 * 1. The first two tiles must have the same level.
	 * 2. After the first two, each tile must have the same level or one greater than the level of the previous tile.
	 * </pre>
	 * 
	 * For example, given the sequence: 1, 1, 2, 2, 2, 3. The next selected tile
	 * could be a 3 or a 4. If the use tries to select an invalid tile, the method
	 * should do nothing. If the user selects a valid tile, the tile should be added
	 * to the list of selected tiles.
	 * @author Owen Ternus
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 */
	public void tryContinueSelect(int x, int y) {
		if(isAdjacent(selectedTiles.get(selectedTiles.size() - 1), grid.gridTile[x][y]) && ((selectedTiles.size() >=2 && selectedTiles.get(selectedTiles.size() - 1).level == grid.gridTile[x][y].level - 1) || selectedTiles.get(selectedTiles.size() - 1).level == grid.gridTile[x][y].level)) {
			grid.gridTile[x][y].setSelect(true);
			selectedTiles.add(grid.gridTile[x][y]);
		}
	}

	/**
	 * Indicates the user is trying to finish selecting (click on) a sequence of
	 * tiles. If the method is not called for the last selected tile, it should do
	 * nothing and return false. Otherwise it should do the following:
	 * 
	 * <pre>
	 * 1. When the selection contains only 1 tile reset the selection and make sure all tiles selected is set to false.
	 * 2. When the selection contains more than one block:
	 *     a. Upgrade the last selected tiles with upgradeLastSelectedTile().
	 *     b. Drop all other selected tiles with dropSelected().
	 *     c. Reset the selection and make sure all tiles selected is set to false.
	 * </pre>
	 * @author Owen Ternus
	 * @param x the column of the tile selected
	 * @param y the row of the tile selected
	 * @return return false if the tile was not selected, otherwise return true
	 */
	public boolean tryFinishSelection(int x, int y) {
	    boolean a = false;
	    if (selectedTiles.size() == 1 || selectedTiles.size() == 0) {
	        if (selectedTiles.size() == 1 && selectedTiles.get(0) != null) {
	            grid.gridTile[selectedTiles.get(0).getX()][selectedTiles.get(0).getY()].setSelect(false);
	        }
	        selectedTiles.clear();
	    } else {
	        upgradeLastSelectedTile();
	        dropSelected();
	        for (int i = 0; i < selectedTiles.size(); ++i) {
	            if (selectedTiles.get(i) != null) {
	                grid.gridTile[selectedTiles.get(i).getX()][selectedTiles.get(i).getY()].setSelect(false);
	            }
	        }
	        selectedTiles.clear();
	        a = true;
	    }
	    return a;
	}


	/**
	 * Increases the level of the last selected tile by 1 and removes that tile from
	 * the list of selected tiles. The tile itself should be set to unselected.
	 * <p>
	 * @author Owen Ternus
	 * If the upgrade results in a tile that is greater than the current maximum
	 * tile level, both the minimum and maximum tile level are increased by 1. A
	 * message dialog should also be displayed with the message "New block 32,
	 * removing blocks 2". Not that the message shows tile values and not levels.
	 * Display a message is performed with dialogListener.showDialog("Hello,
	 * World!");
	 */
	public void upgradeLastSelectedTile() {
		Tile cur = selectedTiles.get(selectedTiles.size() - 1);
		cur.setLevel(cur.getLevel());
		grid.gridTile[cur.getX()][cur.getY()].level += 1;
		if(cur.getLevel() > maxTileLevel) {
			setMaxTileLevel(getMaxTileLevel() + 1);
			setMinTileLevel(getMinTileLevel() + 1);
			dialogListener.showDialog("New block " + Math.pow(2, maxTileLevel) + ", removing blocks " + Math.pow(2, minTileLevel));
			for(int i = 0; i < grid.getWidth(); ++i) {
				for(int j = 0; j < grid.getHeight(); ++j) {
					if(grid.gridTile[i][j].getLevel() == minTileLevel - 1) {
						grid.gridTile[i][j].setSelect(true);
					}
				}
		}
			dropLevel(minTileLevel - 1);
		}
		cur.setSelect(false);
		grid.gridTile[selectedTiles.get(selectedTiles.size() - 1).getX()][selectedTiles.get(selectedTiles.size() - 1).getY()] = cur;
		selectedTiles.remove(selectedTiles.size() - 1);
	}

	/**
	 * Gets the selected tiles in the form of an array. This does not mean selected
	 * tiles must be stored in this class as a array.
	 * @author Owen Ternus
	 * @return the selected tiles in the form of an array
	 */
	public Tile[] getSelectedAsArray() {
		Tile[] tiles = new Tile[selectedTiles.size()];
		for(int i = 0; i < tiles.length - 1; ++i) {
			tiles[i] = selectedTiles.get(i); 
		}
		return tiles;
	}

	/**
	 * Removes all tiles of a particular level from the grid. When a tile is
	 * removed, the tiles above it drop down one spot and a new random tile is
	 * placed at the top of the grid.
	 * @author Owen Ternus
	 * @param level the level of tile to remove
	 */
	public void dropLevel(int level) {
	    // Iterate through all rows and columns of the grid
	    for (int i = 0; i < grid.getHeight(); i++) {
	        for (int j = 0; j < grid.getWidth(); j++) {
	            // Check if the current tile is on the given level
	        	if (grid.gridTile[j][i] != null && grid.gridTile[j][i].getLevel() == level) {
					// Remove the tile from the grid
	                grid.gridTile[j][i] = null;
	                // Shift the tiles above it down by one
	                for (int i1 = i; i1 >= 0; i1--) {
	                    grid.gridTile[j][i1 + 1] = grid.gridTile[j][i1];
	                }
	                // Add a new random tile at the top of the grid
	                grid.gridTile[j][0] = getRandomTile();
	            }
	        }
	    }
	}


	/**
	 * Removes all selected tiles from the grid. When a tile is removed, the tiles
	 * above it drop down one spot and a new random tile is placed at the top of the
	 * grid.
	 * @author Owen Ternus
	 */
	public void dropSelected() {
	    for (int i = 0; i < grid.getWidth(); i++) {
	        for (int j = 0; j < grid.getHeight(); j++) {
	            Tile tile = grid.gridTile[i][j];
	            if (tile.isSelected()) {
	                for (int k = j; k < grid.getHeight(); k++) {
	                    Tile tileAbove = grid.gridTile[i][k];
	                    grid.gridTile[i][k] = tileAbove; // Move the tile down
	                }
	                score += tile.getValue();
	                grid.gridTile[i][grid.getHeight() - 1] = getRandomTile(); // Place a new random tile at the top
	            }
	        }
	    }
	}


	/**
	 * Remove the tile from the selected tiles.
	 * @author Owen Ternus
	 * @param x column of the tile
	 * @param y row of the tile
	 */
	public void unselect(int x, int y) {
	    Tile tile = grid.gridTile[x][y];
	    if (selectedTiles.contains(tile)) {
	        selectedTiles.remove(tile); // Remove the tile from the selected tiles list
	    }
	}


	/**
	 * Gets the player's score.
	 * @author Owen Ternus
	 * @return the score
	 */
	public long getScore() {
		return score;
	}

	/**
	 * Gets the game grid.
	 * @author Owen Ternus
	 * @return the grid
	 */
	public Grid getGrid() {
		return grid;
	}

	/**
	 * Gets the minimum tile level.
	 * @author Owen Ternus
	 * @return the minimum tile level
	 */
	public int getMinTileLevel() {
		return minTileLevel;
	}

	/**
	 * Gets the maximum tile level.
	 * @author Owen Ternus
	 * @return the maximum tile level
	 */
	public int getMaxTileLevel() {
		return maxTileLevel;
	}

	/**
	 * Sets the player's score.
	 * @author Owen Ternus
	 * @param score number of points
	 */
	public void setScore(long score) {
		this.score = score;
	}

	/**
	 * Sets the game's grid.
	 * @author Owen Ternus
	 * @param grid game's grid
	 */
	public void setGrid(Grid grid) {
		this.grid = grid;
	}

	/**
	 * Sets the minimum tile level.
	 * @author Owen Ternus
	 * @param minTileLevel the lowest level tile
	 */
	public void setMinTileLevel(int minTileLevel) {
		this.minTileLevel = minTileLevel;
	}

	/**
	 * Sets the maximum tile level.
	 * @author Owen Ternus
	 * @param maxTileLevel the highest level tile
	 */
	public void setMaxTileLevel(int maxTileLevel) {
		this.maxTileLevel = maxTileLevel;
	}

	/**
	 * Sets callback listeners for game events.
	 * @author Owen Ternus
	 * @param dialogListener listener for creating a user dialog
	 * @param scoreListener  listener for updating the player's score
	 */
	public void setListeners(ShowDialogListener dialogListener, ScoreUpdateListener scoreListener) {
		this.dialogListener = dialogListener;
		this.scoreListener = scoreListener;
	}

	/**
	 * Save the game to the given file path.
	 * @author Owen Ternus
	 * @param filePath location of file to save
	 */
	public void save(String filePath) {
		GameFileUtil.save(filePath, this);
	}

	/**
	 * Load the game from the given file path
	 * @author Owen Ternus
	 * @param filePath location of file to load
	 */
	public void load(String filePath) {
		GameFileUtil.load(filePath, this);
	}
}
