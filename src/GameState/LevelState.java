package GameState;

import java.awt.Graphics2D;

import Entity.Player;

public abstract class LevelState extends GameState{

	public LevelState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public abstract void init();

	@Override
	public abstract void draw(Graphics2D g);


	public abstract void reset(Player player);

}
