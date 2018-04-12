package GameState;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Handlers.Keys;
import Main.GamePanel;

public class HostState extends GameState {
	
	private BufferedImage head;
	
	private int currentChoice = 0;
	private String[] options = {
		"Start Game",
		"Back"
	};
	
	private Color titleColor;
	private Font titleFont;
	
	private Font font;
	private Font font2;
	
	private ServerSocket serverSocket = null;
	private Socket socket = null;
	String localhost;
	private boolean connected = false;
	
	public HostState(GameStateManager gsm) {
		
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
			
			InetAddress inetAddress = InetAddress.getLocalHost();
			localhost = "Your Address:- " + inetAddress.getHostAddress();
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public void init() {
	}
	
	public void update() {
		
		// check keys
		handleInput();
		if(!connected) connect();
		
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
		g.drawString(localhost, 70, 125);
		g.drawString("Start Game", 145, 165);
		g.drawString("Back", 145, 185);
		
		// draw floating head
		if(currentChoice == 0) g.drawImage(head, 125, 154, null);
		else if(currentChoice == 1) g.drawImage(head, 125, 174, null);
		
		// other
		g.setFont(font2);
		g.drawString("2018 Kyle Whitlatch", 10, 232);
		
	}
	
	private void select() {
		if(currentChoice == 0) {
			GameStateManager.setTwoPlayer(true);
			GameStateManager.setNetworkConnection(true);
			GameStateManager.setHost(true);
			gsm.setLevelState(GameStateManager.LEVEL1ASTATE, null, null, socket);
		}
		else if(currentChoice == 1) {
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
	
	public void connect() {
		
		// Create Server socket
		try {
			serverSocket = new ServerSocket(12345);
			socket = serverSocket.accept();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if( socket != null ) connected = true;
	}
	
}










