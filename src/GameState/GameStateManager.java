package GameState;

import java.net.Socket;

import Audio.JukeBox;
import Entity.Player;
import Handlers.Keys;
import Main.GamePanel;

public class GameStateManager {
	
	private GameState[] gameStates;
	private int currentState;
	public Keys keys;
	
	private PauseState pauseState;
	private boolean paused;
	
	public static final int NUMGAMESTATES = 16;
	public static final int GAMEOVERSTATE = 0;
	public static final int MENUSTATE = 1;
	public static final int OPTIONSTATE = 2;
	public static final int HOSTSTATE = 3;
	public static final int CONNECTSTATE = 4;
	
	public static final int LEVEL1ASTATE = 11;
	public static final int LEVEL1BSTATE = 12;
	public static final int LEVEL1CSTATE = 13;
	public static final int ACIDSTATE = 14;
	
	public static boolean twoPlayer = false;
	public static boolean networkConnection = false;
	private static boolean host = false;
	private Player player;
	private Player player2;
	private Socket socket;
	
	
	public GameStateManager() {
		
		keys = new Keys(this, player, player2);
		
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
		else if(state == ACIDSTATE)
			gameStates[state] = new AcidState(this);
		else if(state == HOSTSTATE)
			gameStates[state] = new HostState(this);
		else if(state == CONNECTSTATE)
			gameStates[state] = new ConnectState(this);
		else if(state == GAMEOVERSTATE)
			gameStates[state] = new ConnectState(this);
		if(state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this, player, player2, socket, host);
	}
	
	private void loadLevelState(int state, Player p1, Player p2, Socket socket, boolean b) {
		if(state == LEVEL1ASTATE)
			gameStates[state] = new Level1AState(this, p1, p2, socket, b);
		else if(state == LEVEL1BSTATE)
			gameStates[state] = new Level1BState(this, p1, p2);
		else if(state == LEVEL1CSTATE)
			gameStates[state] = new Level1CState(this, p1, p2);
	}
	
	
	private void unloadState(int state) {
		gameStates[state] = null;
	}
	
	public void setState(int state) {
		unloadState(currentState);
		currentState = state;
		loadState(currentState);
	}
	public void setLevelState(int level, Player p1, Player p2, Socket socket, boolean b) {
		unloadState(currentState);
		currentState = level;
		loadLevelState(currentState, p1, p2, socket, b);
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
	public static boolean getNetworkConnection() {return networkConnection;}
	public static void setNetworkConnection(boolean b) { networkConnection = b;}
	public static boolean getHost() {return host;}
	public static void setHost(boolean b) {host = b;}
	
}