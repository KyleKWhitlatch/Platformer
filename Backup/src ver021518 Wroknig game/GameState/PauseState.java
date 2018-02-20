package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.Keys;
import Main.GamePanel;

public class PauseState extends GameState {
	
	private Font bigFont;
	private Font font;
	
	public PauseState(GameStateManager gsm) {
		
		super(gsm);
		
		// fonts
		font = new Font("Century Gothic", Font.PLAIN, 14);
		
	}
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(GamePanel.WIDTH / 2 - 60, GamePanel.HEIGHT / 2 - 8, 104, 15);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Paused", GamePanel.WIDTH / 2 - 58, GamePanel.HEIGHT / 2 + 4);
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ESCAPE)) gsm.setPaused(false);
		if(Keys.isPressed(Keys.JUMP)) {
			gsm.setPaused(false);
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
