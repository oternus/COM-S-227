package hw3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import api.Tile;

/**
 * Utility class with static methods for saving and loading game files.
 */
public class GameFileUtil {
	/**
	 * Saves the current game state to a file at the given file path.
	 * <p>
	 * The format of the file is one line of game data followed by multiple lines of
	 * game grid. The first line contains the: width, height, minimum tile level,
	 * maximum tile level, and score. The grid is represented by tile levels. The
	 * conversion to tile values is 2^level, for example, 1 is 2, 2 is 4, 3 is 8, 4
	 * is 16, etc. The following is an example:
	 * @author Owen Ternus
	 * <pre>
	 * 5 8 1 4 100
	 * 1 1 2 3 1
	 * 2 3 3 1 3
	 * 3 3 1 2 2
	 * 3 1 1 3 1
	 * 2 1 3 1 2
	 * 2 1 1 3 1
	 * 4 1 3 1 1
	 * 1 3 3 3 3
	 * </pre>
	 * 
	 * @param filePath the path of the file to save
	 * @param game     the game to save
	 */
	public static void save(String filePath, ConnectGame game) {
	    try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
	        writer.write(game.getGrid().getWidth() + " " + game.getGrid().getHeight() + " " 
	        + game.getMinTileLevel() + "" + game.getMaxTileLevel() + " " + game.getScore() + "\n");

	        // Write game grid to following lines
	        for(int height = 0; height < game.getGrid().getHeight(); height++) {
	        	for(int width = 0; width < game.getGrid().getWidth(); width++) {
	        		if(height == game.getGrid().getHeight() - 1 && width == game.getGrid().getWidth() - 1) {
	        			writer.write(game.getGrid().getTile(width, height).getLevel() + " ");
	        		}else if(width == game.getGrid().getWidth() - 1){
	        			writer.write(game.getGrid().getTile(width, height).getLevel() + "\n");
	        		}else {
	        			writer.write(game.getGrid().getTile(width, height).getLevel() + " ");
	        		}
	        	}
	        }

	        writer.close();
	    } catch (IOException e) {
	        throw new RuntimeException("Error", e);
	    }
	}

	
	/**
	 * Loads the file at the given file path into the given game object. When the
	 * method returns the game object has been modified to represent the loaded
	 * game.
	 * <p>
	 * See the save() method for the specification of the file format.
	 * @author Owen Ternus
	 * @param filePath the path of the file to load
	 * @param game     the game to modify
	 */
	public static void load(String filePath, ConnectGame game) {
	    try {
	        Scanner scnr = new Scanner(new File(filePath));
	        int width = scnr.nextInt();
	        int height = scnr.nextInt();
	        game.setMinTileLevel(scnr.nextInt());
	        game.setMaxTileLevel(scnr.nextInt());
	        game.setScore(scnr.nextLong());
	        game.setGrid(new Grid(width, height));
	        
	        for(int h = 0; h < height; h++) {
	        	for(int w = 0; w < width; w++) {
	        		game.getGrid().setTile(new Tile(scnr.nextInt()), w, h);
	        	}
	        }
	        scnr.close();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	}

