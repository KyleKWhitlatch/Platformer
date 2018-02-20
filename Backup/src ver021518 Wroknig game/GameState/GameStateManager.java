package GameState;

import Audio.JukeBox;
import Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int MENUSTATE = 0;
	public static final int OPTIONSTATE = 1;
	public static final int HOSTSTATE = 2;
	public static final int CONNECTSTATE = 3;
	
	public static final int LEVEL1ASTATE = 11;
	public static final int LEVEL1BSTATE = 12;
	public static final int LEVEL1CSTATE = 13;
	public static final int ACIDSTATE = 14;

	public static boolean twoPlayer = false;
	
	
	public GameStateManager() {
		
		JukeBox.init();
		
		gameStates = new GameState[NUMGAMESTATES];
		
		pauseState = new PauseState(this);
		paused = false;
		
		currentState = MENUSTATE;
		loadState(currentState);
		
	}
	
	private void loadState(int state) {
		if(state == MENUSTATE)
			gameStates[state] = new MenuState(this);
		else if(state == OPTIONSTATE)
			gameStates[state] = new OptionState(this);
		else if(state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this);
		else if(state == LEVEL1BSTATE)
			gameStates[state] = new Level1BState(this);
		else if(state == LEVEL1CSTATE)
			gameStates[state] = new Level1CState(this);
		else if(state == ACIDSTATE)
			gameStates[state] = new AcidState(this);
		else if(state == HOSTSTATE)
			gameStates[state] = new HostState(this);
		else if(state == CONNECTSTATE)
			gameStates[state] = new ConnectState(this);
	}
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	
	public void setPaused(boolean b) { paused = b; }
	
	public void update() {
		if(paused) {
			pauseState.update();
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].update();
	}
	
	public void draw(java.awt.Graphics2D g) {
		if(paused) {
			pauseState.draw(g);
			return;
		}
		if(gameStates[currentState] != null) gameStates[currentState].draw(g);
		else {
			g.setColor(java.awt.Color.BLACK);
			g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		}
	}
	
	public static boolean getTwoPlayer() {return twoPlayer;}
	public static void setTwoPlayer(boolean b) {twoPlayer = b;}
	
}