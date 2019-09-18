package dev.prince.rpgGameEngine.net.packets;

import org.lwjgl.Sys;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;


public class Packet00Login extends Packet {
	
	private String username;
	//private Handler handler;
	private int spawnX,spawnY,thisX,thisY;//spawnX and spawnY for player that enters game
	public Packet00Login(byte[]  data) {
		super(0);
		this.username = readData(data);
		
	}
	
	public Packet00Login(String  username) {
		super(0);
		this.username = username;
	}
	public Packet00Login(String  username,int spawnX,int spawnY,int thisX,int thisY) {
		super(0);
		this.username = username;
		this.spawnX=spawnX;
		this.spawnY=spawnY;
		this.thisX =thisX;
		this.thisY=thisY;
	}

	@Override
	public void sendToServer(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void sendToAllClients(GameServer server) {
		server.sendDataToAllClients(getTotalData());
	}
	
	public byte[] getTotalData(){
		return ("0"+" "+this.username+" "+spawnX+ " " + spawnY+" " + thisX+" "+thisY+" ").getBytes();
	}
	
	@Override
	public byte[] getData() {
		return ("0"+" "+this.username).getBytes();
	}
	
	public String getUsername(){
		return username;
	}

	public float getSpawnX() {
		return spawnX;
	}

	public float getSpawnY() {
		return spawnY;
	}

	public float getThisX() {
		return thisX;
	}

	public float getThisY() {
		return thisY;
	}
	
}
