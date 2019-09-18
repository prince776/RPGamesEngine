package dev.prince.rpgGameEngine.sounds;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;



public class SoundEffects {
	
	public  ArrayList<Audio> walk;//grass,wood,sand,water
	public   ArrayList<String> names;
	public  Audio door,palletTown,thunder,rain;
	public  float pitch,gain,x,y,z;
	
	
	public  void init(){
		
		
		pitch=1f;
		gain=1f;
		
		walk = new ArrayList<Audio>();
		names = new ArrayList<String>();
		
		walk.add(loadAudio("WAV","res/sounds/grass.wav"));
		walk.add(loadAudio("WAV","res/sounds/wood.wav"));
		walk.add(loadAudio("WAV","res/sounds/sand.wav"));
		walk.add(loadAudio("WAV","res/sounds/water.wav"));
		walk.add(loadAudio("WAV","res/sounds/stone1.wav"));
		walk.add(loadAudio("WAV","res/sounds/stone1.wav"));
		walk.add(loadAudio("WAV","res/sounds/stone1.wav"));//floor
		walk.add(loadAudio("WAV","res/sounds/stone1.wav"));//path
		walk.add(loadAudio("OGG","res/sounds/stairs.ogg"));
		
		
		names.add("grass");
		names.add("wood");
		names.add("sand");
		names.add("water");
		names.add("stone");
		names.add("floor");
		names.add("path");
		names.add("stairs");

		door = loadAudio("WAV","res/sounds/door.wav");
		palletTown = loadAudio("WAV","res/sounds/PalletTown.wav");
		thunder = loadAudio("WAV","res/sounds/thunder.wav");
		rain = loadAudio("OGG","res/sounds/rain4.ogg");

	}
	
	public  void playSoundEffect( final Audio a, final float x, final float y, final float z){
		Thread t = new Thread(){
			public void run(){
				a.playAsSoundEffect(pitch, gain, false,x,y,z);
			}
		};
		t.start();try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public  void playSoundEffect( final Audio a, final boolean loop,final float x, final float y, final float z){
		Thread t = new Thread(){
			public void run(){
				a.playAsSoundEffect(pitch, gain, loop,x,y,z);
			}
		};
		t.start();try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public  Audio loadAudio(String format,String path){
        try {
			return AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return null;
	}
	
	public static void destroy(){
		AL.destroy();
	}
}
