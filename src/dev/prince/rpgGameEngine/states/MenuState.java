package dev.prince.rpgGameEngine.states;

import org.newdawn.slick.Color;

import dev.prince.rpgGameEngine.Game;
import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.gfx.Assets;
import dev.prince.rpgGameEngine.gfx.Renderer;
import dev.prince.rpgGameEngine.ui.ClickListener;
import dev.prince.rpgGameEngine.ui.UIButton;
import dev.prince.rpgGameEngine.ui.UIManager;

public class MenuState extends State{
	
	private UIManager uiManager;
	
	private UIButton startButton ,saveButton, helpButton , creditButton , exitButton;	
	private boolean save=false;
	private Color c=Color.darkGray.darker();
	private float fadeValue=1.0f,fadeMagnitude=0f;
	
	public MenuState(final Handler handler) {
		super(handler);
		
		uiManager = new UIManager(handler);
		
		//START
		startButton = new UIButton(handler,handler.getWidth()/2-9*8-16/2,80f,9*16,42,"START",Color.gray,1f,1f,1f,true,9,new ClickListener(){
			public void onClick() {
				State.setState(handler.getGame().getGameState());
			}
		});
		
		//SAVE
		saveButton = new UIButton(handler,handler.getWidth()/2-9*8,160f,8*16,42,"SAVE",Color.gray,1f,1f,1f,true,8,new ClickListener(){
			public void onClick() {
				handler.getGame().getGameState().getGameCreationSate().saveGame();
				save=true;
			}
		});
		
		//HELP
		helpButton = new UIButton(handler,handler.getWidth()/2-9*8,240f,8*16,42,"HELP",Color.gray,1f,1f,1f,true,8,new ClickListener(){
			public void onClick() {
			}
		});
		
		//CREDIT
		creditButton = new UIButton(handler,handler.getWidth()/2-9*8-16/2,320f,9*16,42,"CREDIT",Color.gray,1f,1f,1f,true,9,new ClickListener(){
			public void onClick() {
				State.setState(handler.getGame().getCreditState());
			}
		});
		//EXIT
		exitButton = new UIButton(handler,handler.getWidth()/2-9*8,400f,8*16,42,"EXIT",Color.gray,1f,1f,1f,true,8,new ClickListener(){
			public void onClick() {
				if(Game.joinServer){
					handler.getGame().disconnectMP();
				}
				Game.closeProgram();
			}
		});
			
		//ADD TO UIManager List
		uiManager.addUIObject(startButton);
		uiManager.addUIObject(saveButton);
		uiManager.addUIObject(helpButton);
		uiManager.addUIObject(creditButton);
		uiManager.addUIObject(exitButton);
	}

	@Override
	public void tick() {
		uiManager.tick();
	}

	@Override
	public void render() {
		Renderer.renderImage(Assets.logo, 0, 0, handler.getWidth(),handler.getHeight(),  1f);
		uiManager.render();
		
		//SAVING
		if(save){
			handler.getGame().getGameState().getGameCreationSate().giveMessage(c,"PROGRESS SAVED");
			c=c.brighter(1f-fadeValue);
			fadeValue-=0.001f;
			fadeMagnitude+=(1f-fadeValue);		}
		
		if(fadeMagnitude>=1){
			fadeMagnitude=0f;
			fadeValue=1f;
			save=false;
			c=Color.darkGray.darker();
		}
	}

}
