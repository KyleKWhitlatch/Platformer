package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import Handlers.Keys;
import Main.GamePanel;

public class GameOverState extends GameState {
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
		font = new Font("Century Gothic", Font.PLAIN, 14);
	}

	private Font font;
	
	public void init() {}
	
	public void update() {
		handleInput();
	}
	
	public void draw(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(GamePanel.WIDTH / 2 - 60, GamePanel.HEIGHT / 2 - 8, 104, 15);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString("Game Over", GamePanel.WIDTH / 2 - 58, GamePanel.HEIGHT / 2 + 4);
	}
	
	public void handleInput() {
		try {
			wait(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(Keys.anyKeyPress()) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}

}
