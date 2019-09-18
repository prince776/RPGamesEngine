package dev.prince.rpgGameEngine.states;

import org.lwjgl.input.Keyboard;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;

public class CreditState extends State{
	
	private float y=handler.getHeight();
	private boolean  restart=false;
	public CreditState(Handler handler) {
		super(handler);
	}

	@Override
	public void tick() {
		if(restart){
			y=handler.getHeight();
			restart = false;
		}
		y--;
		if(y<=-handler.getHeight())
			y=handler.getHeight();
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
			restart = true;
			State.setState(handler.getGame().getMenuState());
		}
	}

	@Override
	public void render() {
		Renderer.renderImage(Assets.logo, 0, 0, handler.getWidth(),handler.getHeight(), (byte) 1);
		Renderer.renderImage(Assets.credit, 0, y, handler.getWidth(), handler.getHeight(), (byte) 1);
	}

}
