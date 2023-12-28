package hw2;

import api.PlayerPosition;
import api.BallType;
import static api.PlayerPosition.*;
import static api.BallType.*;

/**
 * Class that models the game of three-cushion billiards.
 * 
 * @author Owen Ternus
 */
public class ThreeCushion {

	/**
	 * keeps track of current inning
	 * @author Owen Ternus
	 */
	private int inningCount;
	
	/**
	 * keeps track of playerA score
	 * @author Owen Ternus
	 */
	private int playerAScore;
	
	/**
	 * keeps track of playerB score
	 * @author Owen Ternus
	 */
	private int playerBScore;
	
	/**
	 * keeps track of current player in certain inning
	 * @author Owen Ternus
	 */
	private PlayerPosition inningPlayer;
	
	/**
	 * keeps track of current players ball
	 * @author Owen Ternus
	 */
	private BallType inningPlayerBall;
	
	/**
	 * sets points to win game
	 * @author Owen Ternus
	 */
	private int pointsToWinGame;
	
	/**
	 * Sets whether winner of lag chooses to break or not
	 * @author Owen Ternus
	 */
	private boolean winnerChooses;
	
	/**
	 * keeps track of whether shot was started
	 * @author Owen Ternus
	 */
	private boolean isShotStarted;
	
	/**
	 * True/False for bank shot
	 * @author Owen Ternus
	 */
	private boolean bankShot;
	
	/**
	 * True/False for valid shot
	 * @author Owen Ternus
	 */
	private boolean goodShot;
	
	/**
	 * true/false for foul or not
	 * @author Owen Ternus
	 */
	private boolean isFoul;
	
	/**
	 * T/F of if ball is in motion
	 * @author Owen Ternus
	 */
	private boolean motionBall;
	
	/**
	 * T/F for break shot
	 * @author Owen Ternus
	 */
	private boolean isBreakShot;
	
	/**
	 * Keeps track of the number of shots taken
	 * @author Owen Ternus
	 */
	private int shotCount;
	
	/**
	 * keeps track of how many times red impacts balls/cushions
	 * @author Owen Ternus
	 */
	private int impactRed;
	
	/**
	 * keeps track of how many times other colored balls impact balls/cushions
	 * @author Owen Ternus
	 */
	private int impactOtherColors;
	
	/**
	 * keeps track of how many cushions have been hit
	 * @author Owen Ternus
	 */
	private int impactCushion;
	
	
	/**
	 * Constructor
	 * Creates a new game of three-cushion billiards with a given lag winner 
	 * + the predetermined number of points required to win the game. 
	 * Inning starts at 1
	 * @author Owen Ternus
	 * @param lagWinner
	 * @param pointsToWin
	 */
	public ThreeCushion(PlayerPosition lagWinner, int pointsToWin) {
		inningPlayer = lagWinner;
		pointsToWinGame = pointsToWin;
		inningCount = 1;
		playerAScore = 0;
		playerBScore = 0;
		impactRed = 0;
		impactOtherColors = 0;
		shotCount = 0;
		winnerChooses = false;
		isShotStarted = false;
		isBreakShot = true;
		bankShot = false;
		goodShot = false;
		isFoul = false;
		motionBall = false;
	}
	
	/**
	 * Sets whether the player that won the lag chooses to break (first shot) or chooses other player to break
	 * no effect if called more than once
	 * @author Owen Ternus
	 * @param selfBreak
	 * @param cueBall
	 */
	public void lagWinnerChooses(boolean selfBreak, BallType cueBall) {
		winnerChooses = true;
		if(selfBreak == false) {
			
			if(inningPlayer == PlayerPosition.PLAYER_A) {
				inningPlayer = PlayerPosition.PLAYER_B;
			}
			else {
				inningPlayer = PlayerPosition.PLAYER_A;
			}
			
			if(cueBall == BallType.WHITE) {
				inningPlayerBall = BallType.YELLOW;
			}
			else {
				inningPlayerBall = BallType.WHITE;
			}
			
			}
		else {
			inningPlayerBall = cueBall;
		}
		
	}
		
	
	
	/**
	 * Indicates if cue has struck ball
	 * Shot not begun = indicate new shot
	 * If this method is called while a shot is still in progress (i.e., endShot() has not been called for the previous shot), 
	 * the player has committed a foul (see the method foul())
	 * this method means start of shot + inning
	 * No play can begin until the lag player has chosen who will break (see lagWinnderChooses).
	 * If this method is called before the break is chosen, it should do nothing.
	 * If this method is called after the game has ended, it should do nothing.
	 * @author Owen Ternus
	 * @param ball
	 */
	public void cueStickStrike(BallType ball) {
		if(shotCount == 0) {
			isBreakShot = true;
		}
		else {
			isBreakShot = false;
		}
		shotCount++;
		
		if(motionBall == true || ball != inningPlayerBall) {
			isShotStarted = false;
			goodShot = false;
			foul();
		}
		else {
			isShotStarted = true;
		}
		if(isGameOver()) {
			isShotStarted = false;
		}
		motionBall = true;
	}
	
	/**
	 * Indicates of cue has struck the ball
	 * A ball strike cannot happen before a stick strike.
	 * If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.
	 * If this method is called after the game has ended, it should do nothing.
	 * @author Owen Ternus
	 * @param ball
	 */
	public void cueBallStrike(BallType ball){
		if(ball == BallType.RED) {
			impactRed++;
		}
		else {
			impactOtherColors++;
		}
		if(isBreakShot == true) {
			if(impactRed > 0) {
				isBreakShot = false;
			}
			else if(impactCushion != 0 || impactOtherColors != 0) {
				isFoul = false;
				isBreakShot = false;
				endShot();
			}
		}
		motionBall = true;
		goodShot = true;
	}
	
	/**
	 * @author Owen Ternus
	 * Indicates the given ball has impacted the given cushion.
	 * Nothing if called before start of a shot.
	 * If this method is called after the game has ended, it should do nothing.
	 */
	public void cueBallImpactCushion() {
		impactCushion++;
		if(impactCushion >= 3) {
			if(impactOtherColors == 0 && impactRed == 0) {
				bankShot = true;
			}
		}
		if(isBreakShot == true) {
			if(impactRed > 0) {
				isBreakShot = false;
			}
			else if(impactCushion != 0 || impactOtherColors != 0) {
				isFoul = false;
				isBreakShot = false;
				endShot();
			}
		}
		goodShot = false;
		motionBall = true;
	}
	
	/**
	 * All balls have stopped motion
	 * valid + no foul = 1 point
	 * The shot cannot end before it has started with a call to cueStickStrike.
	 * If this method is called before cueStickStrike, it should be ignored.
	 * A shot cannot end before the start of a shot. 
	 * If this method is called before the start of a shot (i.e., cueStickStrike() is called), it should do nothing.
	 * @author Owen Ternus
	 */
	public void endShot() {
		if(impactOtherColors >= 1 && impactCushion >= 3 && goodShot == true && impactRed >= 1 && isFoul == false) {
			if(inningPlayer == PlayerPosition.PLAYER_A) {
				playerAScore++;
			}
			else {
				playerBScore++;
			}
		}
		else if (isFoul == false) {
			foul();
			bankShot = false;
		}
		motionBall = false;
		impactOtherColors = 0;
		impactRed = 0;
		impactCushion = 0;
		
	}
	
	/**
	 * foul ends inning
	 * foul means no points
	 * foul can happen before inning start
	 * In that case the player whose turn it was to shot has their inning forfeited and the inning count is increased by one.
	 * No foul before break
	 * If this method is called before the break is chosen, it should do nothing.
	 * If this method is called after the game has ended, it should do nothing.
	 * @author Owen Ternus
	 */
	public void foul() {
		isFoul = true;
		isBreakShot = false;
		
		if(inningPlayer == PlayerPosition.PLAYER_B) {
			inningPlayer = PlayerPosition.PLAYER_A;
		}
		else {
			inningPlayer = PlayerPosition.PLAYER_B;
		}
		
		if(inningPlayerBall == BallType.WHITE) {
			inningPlayerBall = BallType.YELLOW;
		}
		else {
			inningPlayerBall = BallType.WHITE;
		}
		
		if(winnerChooses == true && isGameOver() == false) {
			inningCount++;
		}
		isShotStarted = false;
	}
	
	/**
	 * Gets the number of points scored by Player A.
	 * @author Owen Ternus
	 * @return number of points
	 */
	public int getPlayerAScore() {
		return playerAScore;
	}
	
	/**
	 * Gets the number of points scored by Player B.
	 * @author Owen Ternus
	 * @return number of points
	 */
	public int getPlayerBScore() {
		return playerBScore;
	}
	
	/**
	 * Gets the inning number. 
	 * The inning count starts at 1.
	 * @author Owen Ternus
	 * @return inning number
	 */
	public int getInning() {
		return inningCount;
	}
	
	/**
	 * gets cue ball for current player
	 * If this method is called in between innings, the cue ball should be the for the player of the upcoming inning.
	 * called before lag winner has chosen cue ball = cue ball undefined
	 * @author Owen Ternus
	 * @return players cue ball
	 */
	public BallType getCueBall() {
		return inningPlayerBall;
	}
	
	/**
	 * called between inning = cue ball for player in upcoming inning
	 * if called before lag winner has chosen to break = cue ball undefined
	 * @author Owen Ternus
	 * @return current player
	 */
	public PlayerPosition getInningPlayer() {
		return inningPlayer;
	}
	
	/**
	 * @author Owen Ternus
	 * @return true if AND ONLY IF this is break shot
	 */
	public boolean isBreakShot() {
		return isBreakShot;
	}
	
	/**
	 * Returns true if and only if the most recently completed shot was a bank shot.
	 * A bank shot is when the cue ball impacts the cushions at least 3 times and then strikes both object balls.
	 * @author Owen Ternus
	 * @return true if shot was a bank shot, false otherwise
	 */
	public boolean isBankShot() {
		return bankShot;
	}

	/**
	 * Returns true if a shot has been taken (see cueStickStrike()), but not ended (see endShot()).
	 * @author Owen Ternus
	 * @return true if the shot has been started, false otherwise
	 */
	public boolean isShotStarted() {
		return isShotStarted;
	}
	
	/**
	 * Returns true if the shooting player has taken their first shot of the inning.
	 * The inning starts at the beginning of the shot (i.e., the shot may not have ended yet).
	 * @author Owen Ternus
	 * @return true if the inning has started, false otherwise
	 */
	public boolean isInningStarted() {
		return isShotStarted;
	}
	
	/**
	 * Returns true if the game is over (i.e., one of the players has reached the designated number of points to win).
	 * @author Owen Ternus
	 * @return true if the game is over, false otherwise
	 */
	public boolean isGameOver() {
		if(playerAScore == pointsToWinGame || playerBScore == pointsToWinGame) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns a one-line string representation of the current game state. The
	 * format is:
	 * <p>
	 * <tt>Player A*: X Player B: Y, Inning: Z</tt>
	 * <p>
	 * The asterisks next to the player's name indicates which player is at the
	 * table this inning. The number after the player's name is their score. Z is
	 * the inning number. Other messages will appear at the end of the string.
	 * 
	 * @return one-line string representation of the game state
	 */
	public String toString() {
		String fmt = "Player A%s: %d, Player B%s: %d, Inning: %d %s%s";
		String playerATurn = "";
		String playerBTurn = "";
		String inningStatus = "";
		String gameStatus = "";
		if (getInningPlayer() == PLAYER_A) {
			playerATurn = "*";
		} else if (getInningPlayer() == PLAYER_B) {
			playerBTurn = "*";
		}
		if (isInningStarted()) {
			inningStatus = "started";
		} else {
			inningStatus = "not started";
		}
		if (isGameOver()) {
			gameStatus = ", game result final";
		}
		return String.format(fmt, playerATurn, getPlayerAScore(), playerBTurn, getPlayerBScore(), getInning(),
				inningStatus, gameStatus);
	}


}
