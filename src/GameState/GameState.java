package GameState;

import java.awt.Graphics2D;

import Entity.Player;
import Handlers.Keys;

public abstract class GameState {
	
	protected GameStateManager gsm;
	private Player player;
	private Player player2;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}
	
	public abstract void init();
	public void update(){
		Keys.update();
	}
	public abstract void draw(Graphics2D g);
	
}
