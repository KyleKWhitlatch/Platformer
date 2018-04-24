package Handlers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import Entity.Enemy;
import Entity.Explosion;
import GameState.GameStateManager;

public class enemySync extends Thread implements Runnable{

	private ServerSocket enemyServerSocket;
	private Socket enemySocket;
	private String address;
	private DataInputStream eIn;
	private DataOutputStream eOut;
	private boolean isHost;
	private ArrayList<Enemy> enemies;

	public enemySync(String inAddress, boolean h, ArrayList<Enemy> e) {
		address = inAddress;
		isHost = h;
		enemies = e;
	}

	@Override
	public void run() {
		createSocket();
		while(GameStateManager.networkConnection)
		{
			sync();
		}
	}

	public void sync() {
		
		System.out.print("Syncing");
		
		if(isHost)
		{
			for(int i = 0; i < enemies.size(); i++) {
				Enemy e = enemies.get(i);
				e.update();
				try {
						eOut.writeInt(i); //1
						eOut.writeDouble(e.getx()); //2
						eOut.writeDouble(e.gety()); //3
						if(e.isDead()) eOut.writeBoolean(true); //4
						else eOut.writeBoolean(false); //4 alt
				} catch (Exception exception) {exception.printStackTrace();}
	
				if(e.isDead()) {
					enemies.remove(i);
					i--;
					//explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
				}
			}
		}
		else
		{
			try{
				int n = eIn.readInt();
				Enemy e = enemies.get(n); //1
				e.setPosition(eIn.readDouble(),eIn.readDouble()); //2, 3
				if(eIn.readBoolean())
				{
					enemies.remove(n);
					//explosions.add(new Explosion(tileMap, e.getx(), e.gety()));
				}
			} catch (Exception exception) {exception.printStackTrace();}
		}
		
	}

	private void createSocket() {
		
		if(isHost) {
			try{
				
				System.out.println("MAKING SYNC SERVER" + isHost);
				enemyServerSocket = new ServerSocket(22222);
				enemySocket = enemyServerSocket.accept();
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		} else
		{
			try {
				enemySocket = new Socket("localhost", 22222);
				System.out.println("sync connected");
			} catch (Exception e) {e.printStackTrace();} 
		}
	}

}
