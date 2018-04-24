package GameState;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Audio.JukeBox;
import Entity.Enemy;
import Entity.EnemyProjectile;
import Entity.EnergyParticle;
import Entity.Explosion;
import Entity.HUD;
import Entity.Player;
import Entity.Teleport;
import Entity.Title;
import Entity.Enemies.Gazer;
import Entity.Enemies.GelPop;
import Handlers.Keys;
import Main.GamePanel;
import TileMap.Background;
import TileMap.TileMap;

public class Level1AState extends LevelState {
	
	private Background sky;
	private Background clouds;
	private Background mountains;
	
	private Player player;
	private Player player2;
	public Keys keys;

	private TileMap tileMap;
	private ArrayList<Enemy> enemies;
	private ArrayList<EnemyProjectile> eprojectiles;
	private ArrayList<EnergyParticle> energyParticles;
	private ArrayList<Explosion> explosions;
	
	private HUD hud;
	private BufferedImage hageonText;
	private Title title;
	private Title subtitle;
	private Teleport teleport;
	
	// events
	private boolean blockInput = false;
	private int eventCount = 0;
	private boolean eventStart;
	private ArrayList<Rectangle> tb;
	private boolean eventFinish;
	private boolean eventDead;
	
	// Network
	private Socket socket;
	private DataInputStream dIn = null;
	private DataOutputStream dOut = null;
	private boolean host;
	
	public Level1AState(GameStateManager gsm, Player p, Player p2, Socket s, boolean h) {
		super(gsm);
		player = p;
		player2 = p2;
		socket = s;
		host = h;
		init();
	}
	
	public void init() {
		
		// backgrounds
		sky = new Background("/Backgrounds/sky.gif", 0);
		clouds = new Background("/Backgrounds/clouds.gif", 0.1);
		mountains = new Background("/Backgrounds/mountains.gif", 0.2);
		
		// tilemap
		tileMap = new TileMap(30);
		tileMap.loadTiles("/Tilesets/ruinstileset.gif");
		tileMap.loadMap("/Maps/level1a.map");
		tileMap.setPosition(140, 0);
		tileMap.setBounds(
			tileMap.getWidth() - 1 * tileMap.getTileSize(),
			tileMap.getHeight() - 2 * tileMap.getTileSize(),
			0, 0
		);
		tileMap.setTween(0.2);
		
		// player
		player = new Player(tileMap, gsm, this);
		player.setPosition(260, 161);

		
		// Player two
		if (GameStateManager.getTwoPlayer()) {
			System.out.println("True");
			if(GameStateManager.getNetworkConnection()){
				try {
					dOut = new DataOutputStream(socket.getOutputStream());
					dIn = new DataInputStream(socket.getInputStream());
				} catch (Exception e) {e.printStackTrace();}
			}
			player2 = new Player(tileMap, gsm, this);
			player2.setPosition(320, 161);
		} else {System.out.println("False");}
		
		// enemies
		enemies = new ArrayList<Enemy>();
		eprojectiles = new ArrayList<EnemyProjectile>();
		populateEnemies();
		
		// energy particle
		energyParticles = new ArrayList<EnergyParticle>();
		
		// init player
		player.init(enemies, energyParticles);
		if (GameStateManager.getTwoPlayer()) player2.init(enemies, energyParticles);
		
		// explosions
		explosions = new ArrayList<Explosion>();
		
		// hud
		hud = new HUD(player);
		
		// title and subtitle
		try {
			hageonText = ImageIO.read(
				getClass().getResourceAsStream("/HUD/HageonTemple.gif")
			);
			title = new Title(hageonText.getSubimage(0, 0, 178, 20));
			title.sety(60);
			subtitle = new Title(hageonText.getSubimage(0, 20, 82, 13));
			subtitle.sety(85);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		// teleport
		teleport = new Teleport(tileMap);
		teleport.setPosition(3700, 131);
		
		// start event
		eventStart = true;
		tb = new ArrayList<Rectangle>();
		eventStart();
		
		// sfx
		JukeBox.load("/SFX/teleport.mp3", "teleport");
		JukeBox.load("/SFX/explode.mp3", "explode");
		JukeBox.load("/SFX/enemyhit.mp3", "enemyhit");
		
		// music
//TODO	JukeBox.load("/Music/level1.mp3", "level1");   Music Disabled
//		JukeBox.loop("level1", 600, JukeBox.getFrames("level1") - 2200);
		
		keys = new Keys(gsm, player, player2);
		
	}
	
	private void populateEnemies() {
		enemies.clear();
		/*Tengu t = new Tengu(tileMap, player, enemies);
		t.setPosition(1300, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1330, 100);
		enemies.add(t);
		t = new Tengu(tileMap, player, enemies);
		t.setPosition(1360, 100);
		enemies.add(t);*/
		GelPop gp;
		Gazer g;
		
		gp = new GelPop(tileMap, player);
		gp.setPosition(1300, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1320, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1340, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1660, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1680, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(1700, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2177, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2960, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(2980, 100);
		enemies.add(gp);
		gp = new GelPop(tileMap, player);
		gp.setPosition(3000, 100);
		enemies.add(gp);
		
		g = new Gazer(tileMap);
		g.setPosition(2600, 100);
		enemies.add(g);
		g = new Gazer(tileMap);
		g.setPosition(3500, 100);
		enemies.add(g);
	}


	//public void 
	public void update(){
		
		// check keys
		Keys.update();

		// update enemies
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			e.update();
		}

		if(GameStateManager.getNetworkConnection()){
			try {
				dOut.writeDouble(player.getx()); //1
				dOut.writeDouble(player.gety()); //2
				dOut.writeDouble(player.getdx()); //3
				dOut.writeDouble(player.getdy()); //4
				dOut.writeBoolean(player.getLeft()); //5
				dOut.writeBoolean(player.getRight()); //6
				dOut.writeBoolean(player.getCharging()); //7 
				dOut.writeBoolean(player.getDashing()); //8 
				dOut.writeBoolean(player.getAttacking()); //9
				dOut.writeBoolean(player.getKnockback()); //10
				if(host)
				{
					dOut.writeInt(enemies.size()); //11
					for(int i = 0; i < enemies.size(); i++) {
						Enemy e = enemies.get(i);
						dOut.writeDouble(e.getx()); 
						dOut.writeDouble(e.gety());
					}
				}
				dOut.flush();
			} catch (IOException e) {e.printStackTrace();}
			try {
				player2.setPosition(dIn.readDouble(),dIn.readDouble()); //1, 2
				player2.setVector(dIn.readDouble(), dIn.readDouble()); //3, 4
				player2.setLeft(dIn.readBoolean()); //5
				player2.setRight(dIn.readBoolean()); //6
				player2.setCharging(dIn.readBoolean()); //7
				player2.setDashing(dIn.readBoolean()); //8
				if(dIn.readBoolean()){player2.setAttacking();}; //9
				player2.setKnockback(dIn.readBoolean()); //10
				if(!host)
				{
					int hostSize = dIn.readInt();
					for( int i = 0; i < hostSize; i++) //11
					{
						Enemy e = enemies.get(i);
						e.setPosition(dIn.readDouble(),dIn.readDouble());
					}	
				}
			} catch (IOException e) {e.printStackTrace();}
		}
		for(int i = 0; i < enemies.size(); i++) {
			Enemy e = enemies.get(i);
			if(e.isDead()) {
				enemies.remove(i);
				i--;
				explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
			}
		}

		
		// check if end of level event should start
		if(teleport.contains(player)) {
			eventFinish = true;
			player.setBlockInput(true);
		}
		if (GameStateManager.getTwoPlayer()) {
			if(teleport.contains(player2)) {
				eventFinish = true;
				player2.setBlockInput(true);
			}
		}
		
	
//		if (GameStateManager.getTwoPlayer()){
//			if(player2.getHealth() == 0 || player2.gety() > tileMap.getHeight()) {
//				eventP2Dead = blockInput = true;
//			}
//		}
		
		// play events
		if(eventStart) eventStart();
		if(eventDead) eventDead();
		if(eventFinish) eventFinish();
		
		// move title and subtitle
		if(title != null) {
			title.update();
			if(title.shouldRemove()) title = null;
		}
		if(subtitle != null) {
			subtitle.update();
			if(subtitle.shouldRemove()) subtitle = null;
		}
		
		// move backgrounds
		clouds.setPosition(tileMap.getx(), tileMap.gety());
		mountains.setPosition(tileMap.getx(), tileMap.gety());
		
		// update player
		player.update();
		if (GameStateManager.getTwoPlayer()) player2.update();
		
		// update tilemap
		if (GameStateManager.getTwoPlayer() 
				&& ( java.lang.Math.abs( player.getx() - player2.getx() ) < GamePanel.WIDTH * 0.75 ) ){
			tileMap.setPosition(
				GamePanel.WIDTH / 2 - (player.getx() + player2.getx()) / 2,
				GamePanel.HEIGHT / 2 - (player.gety() + player2.gety()) / 2
			);
		} else {
			tileMap.setPosition(
					GamePanel.WIDTH / 2 - player.getx(),
					GamePanel.HEIGHT / 2 - player.gety()
				);
		}
		tileMap.update();
		tileMap.fixBounds();
		

		
		// update enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			EnemyProjectile ep = eprojectiles.get(i);
			ep.update();
			if(ep.shouldRemove()) {
				eprojectiles.remove(i);
				i--;
			}
		}
		
		// update explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).update();
			if(explosions.get(i).shouldRemove()) {
				explosions.remove(i);
				i--;
			}
		}
		
		// update teleport
		teleport.update();
		
	}
	
	public void draw(Graphics2D g) {
		
		// draw background
		sky.draw(g);
		clouds.draw(g);
		mountains.draw(g);
		
		// draw tilemap
		tileMap.draw(g);
		
		// draw enemies
		for(int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw(g);
		}
		
		// draw enemy projectiles
		for(int i = 0; i < eprojectiles.size(); i++) {
			eprojectiles.get(i).draw(g);
		}
		
		// draw explosions
		for(int i = 0; i < explosions.size(); i++) {
			explosions.get(i).draw(g);
		}
		
		// draw player
		player.draw(g);
		if (GameStateManager.getTwoPlayer()) player2.draw(g);
		
		// draw teleport
		teleport.draw(g);
		
		// draw hud
		hud.draw(g);
		
		// draw title
		if(title != null) title.draw(g);
		if(subtitle != null) subtitle.draw(g);
		
		// draw transition boxes
		g.setColor(java.awt.Color.BLACK);
		for(int i = 0; i < tb.size(); i++) {
			g.fill(tb.get(i));
		}
		
	}
	


///////////////////////////////////////////////////////
//////////////////// EVENTS
///////////////////////////////////////////////////////
	
	// reset level
	public void reset(Player p) {
		p.reset();
		p.setPosition(300, 161);
		System.out.println("Level reset!");
		//populateEnemies();
		p.setBlockInput(true);
		eventCount = 0;
		tileMap.setShaking(false, 0);
		eventStart = true;
		eventStart();
		title = new Title(hageonText.getSubimage(0, 0, 178, 20));
		title.sety(60);
		subtitle = new Title(hageonText.getSubimage(0, 33, 91, 13));
		subtitle.sety(85);
		p.setBlockInput(false);
	}
	
	// level started
	private void eventStart() {
		eventCount++;
		if(eventCount == 1) {
			tb.clear();
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(0, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
			tb.add(new Rectangle(0, GamePanel.HEIGHT / 2, GamePanel.WIDTH, GamePanel.HEIGHT / 2));
			tb.add(new Rectangle(GamePanel.WIDTH / 2, 0, GamePanel.WIDTH / 2, GamePanel.HEIGHT));
		}
		if(eventCount > 1 && eventCount < 60) {
			tb.get(0).height -= 4;
			tb.get(1).width -= 6;
			tb.get(2).y += 4;
			tb.get(3).x += 6;
		}
		if(eventCount == 30) title.begin();
		if(eventCount == 60) {
			eventStart = false;
			player.setBlockInput(false);
			eventCount = 0;
			subtitle.begin();
			tb.clear();
		}
	}
	
	// player has died
	private void eventDead() {//TODO
		eventCount++;
		if(eventCount == 1) {
			player.setDead();
			player.stop();
		}
		if(eventCount == 60) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 60) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
		}
		if(eventCount >= 120) {
			if(player.getLives() == 0) {
				//gsm.setState(GameStateManager.GAMEOVERSTATE);
			}
			else {
				eventDead = false;
				player.setBlockInput(false);
				eventCount = 0;
				player.loseLife();
				reset(player);
			}
		}
	}
	
	// finished level
	private void eventFinish() {
		eventCount++;
		if(eventCount == 1) {
			JukeBox.play("teleport");
			player.setTeleporting(true);
			player.stop();
		}
		else if(eventCount == 120) {
			tb.clear();
			tb.add(new Rectangle(
				GamePanel.WIDTH / 2, GamePanel.HEIGHT / 2, 0, 0));
		}
		else if(eventCount > 120) {
			tb.get(0).x -= 6;
			tb.get(0).y -= 4;
			tb.get(0).width += 12;
			tb.get(0).height += 8;
			JukeBox.stop("teleport");
		}
		if(eventCount == 180) {
			player.setTeleporting(false);
			player.setBlockInput(false);
			//gsm.setLevelState(GameStateManager.LEVEL1BSTATE, player, player2);
		}
		
	}


}