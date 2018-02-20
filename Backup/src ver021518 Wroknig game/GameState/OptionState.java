package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entity.PlayerSave;
import Handlers.Keys;
import Main.GamePanel;

public class OptionState extends GameState {
	
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Local",
		"Host",
		"Connect",
		"Back"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font font2;
	
	public OptionState(GameStateManager gsm) {
		
		super(gsm);
		
		try {
			
			// load floating head
			head = ImageIO.read(
				getClass().getResourceAsStream("/HUD/Hud.gif")
			).getSubimage(0, 12, 12, 11);
			
			// titles and fonts
			titleColor = Color.WHITE;
			titleFont = new Font("Times New Roman", Font.PLAIN, 28);
			font = new Font("Arial", Font.PLAIN, 14);
			font2 = new Font("Arial", Font.PLAIN, 10);
			
			// load sound fx
			JukeBox.load("/SFX/menuoption.mp3", "menuoption");
			JukeBox.load("/SFX/menuselect.mp3", "menuselect");
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void init() {}
	
	public void update() {
		
		// check keys
		handleInput();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw bg
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
		
		// draw title
		g.setColor(titleColor);
		g.setFont(titleFont);
		g.drawString("A R T I F A C T", 70, 90);
		
		// draw menu options
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Local", 145, 165);
		g.drawString("Host", 145, 185);
		g.drawString("Connect", 145, 205);
		g.drawString("Back", 145, 225);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 125, 154, null);
		else if(currentChoice == 1) g.drawImage(head, 125, 174, null);
		else if(currentChoice == 2) g.drawImage(head, 125, 194, null);
		else if(currentChoice == 3) g.drawImage(head, 125, 214, null);
		
		// other
		g.setFont(font2);
		g.drawString("2013 Mike S.", 10, 232);
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			gsm.setState(GameStateManager.HOSTSTATE);
		}
		if(currentChoice == 1) {
			gsm.setState(GameStateManager.HOSTSTATE);
		}
		else if(currentChoice == 2) {
			gsm.setState(GameStateManager.CONNECTSTATE);
		}
		else if(currentChoice == 3) {
			gsm.setState(GameStateManager.MENUSTATE);
		}
	}
	
	public void handleInput() {
		if(Keys.isPressed(Keys.ENTER)) select();
		if(Keys.isPressed(Keys.UP)) {
			if(currentChoice > 0) {
				JukeBox.play("menuoption", 0);
				currentChoice--;
			}
		}
		if(Keys.isPressed(Keys.DOWN)) {
			if(currentChoice < options.length - 1) {
				JukeBox.play("menuoption", 0);
				currentChoice++;
			}
		}
	}
	
}










