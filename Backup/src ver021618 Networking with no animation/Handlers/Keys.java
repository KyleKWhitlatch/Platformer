package Handlers;

import java.awt.event.KeyEvent;

import Entity.Player;
import GameState.GameStateManager;

// this class contains a boolean array of current and previous key states
// for the 10 keys that are used for this game.
// a key k is down when keyState[k] is true.

public class Keys {
	
	private static Player player;
	private static Player player2;
	private static GameStateManager gsm;
	
	public static final int NUM_KEYS = 18;
	
	public static boolean keyState[] = new boolean[NUM_KEYS];
	public static boolean prevKeyState[] = new boolean[NUM_KEYS];
	
	// Player 1
	public static int UP = 0;
	public static int LEFT = 1;
	public static int DOWN = 2;
	public static int RIGHT = 3;
	public static int JUMP = 4; 
	public static int SPRINT = 5; 
	public static int ATTACK = 6; 
	public static int DASH = 7; 
	//Menu
	public static int ENTER = 8;
	public static int ESCAPE = 9;
	// Player 2
	public static int UP2 = 10;
	public static int LEFT2 = 11;
	public static int DOWN2 = 12;
	public static int RIGHT2 = 13;
	public static int JUMP2 = 14; 
	public static int SPRINT2 = 15; 
	public static int ATTACK2 = 16; 
	public static int DASH2 = 17;
	
	public Keys(GameStateManager g, Player p, Player p2) {
		gsm = g;
		player = p;
		player2 = p2;
	}
	
	
	public static void keySet(int i, boolean b) {
		if(i == KeyEvent.VK_UP) keyState[UP] = b;
		else if(i == KeyEvent.VK_LEFT) keyState[LEFT] = b;
		else if(i == KeyEvent.VK_DOWN) keyState[DOWN] = b;
		else if(i == KeyEvent.VK_RIGHT) keyState[RIGHT] = b;
		else if(i == KeyEvent.VK_SPACE) keyState[JUMP] = b;
		else if(i == KeyEvent.VK_Z) keyState[SPRINT] = b;
		else if(i == KeyEvent.VK_X) keyState[ATTACK] = b;
		else if(i == KeyEvent.VK_C) keyState[DASH] = b;
		else if(i == KeyEvent.VK_ENTER) keyState[ENTER] = b;
		else if(i == KeyEvent.VK_ESCAPE) keyState[ESCAPE] = b;
		else if(i == KeyEvent.VK_W) keyState[UP2] = b;
		else if(i == KeyEvent.VK_A) keyState[LEFT2] = b;
		else if(i == KeyEvent.VK_S) keyState[DOWN2] = b;
		else if(i == KeyEvent.VK_D) keyState[RIGHT2] = b;
		else if(i == KeyEvent.VK_Q) keyState[JUMP2] = b;
		else if(i == KeyEvent.VK_E) keyState[SPRINT2] = b;
		else if(i == KeyEvent.VK_R) keyState[ATTACK2] = b;
		else if(i == KeyEvent.VK_F) keyState[DASH2] = b;
	}
	
	public static void update() {
		
		if(player != null){
			if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(true);
			if(player.blockInput() || player.getHealth() == 0) return;
			player.setUp(Keys.keyState[Keys.UP]);
			player.setLeft(Keys.keyState[Keys.LEFT]);
			player.setDown(Keys.keyState[Keys.DOWN]);
			player.setRight(Keys.keyState[Keys.RIGHT]);
			player.setJumping(Keys.keyState[Keys.JUMP]);
			player.setDashing(Keys.keyState[Keys.SPRINT]);
			if(Keys.isPressed(Keys.ATTACK)) player.setAttacking();
			if(Keys.isPressed(Keys.DASH)) player.setCharging();
		}
		
		if(player2 != null && !GameStateManager.getNetworkConnection()) {
			if(player.blockInput() || player2.getHealth() == 0) return;
			player2.setUp(Keys.keyState[Keys.UP2]);
			player2.setLeft(Keys.keyState[Keys.LEFT2]);
			player2.setDown(Keys.keyState[Keys.DOWN2]);
			player2.setRight(Keys.keyState[Keys.RIGHT2]);
			player2.setJumping(Keys.keyState[Keys.JUMP2]);
			player2.setDashing(Keys.keyState[Keys.SPRINT2]);
			if(isPressed(Keys.ATTACK2)) player2.setAttacking();
			if(isPressed(Keys.DASH2)) player2.setCharging();
		}
		
		for(int i = 0; i < NUM_KEYS; i++) {
			prevKeyState[i] = keyState[i];
		}
	}
	
	public static boolean isPressed(int i) {
		return keyState[i] && !prevKeyState[i];
	}
	
	public static boolean anyKeyPress() {
		for(int i = 0; i < NUM_KEYS; i++) {
			if(keyState[i]) return true;
		} 
		return false;
	}
	
}
