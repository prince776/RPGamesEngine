package dev.prince.rpgGameEngine.net.packets;

import java.net.InetAddress;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet06BasicLogin extends Packet{
	private String username;
	private int x,y,port;
	private InetAddress ipAddress;
	public Packet06BasicLogin(String  username,int x,int y,InetAddress ipAddress,int port) {
		super(6);
		this.username = username;
		this.x=x;
		this.y=y;
		this.ipAddress=ipAddress;
		this.port=port;
	}
	@Override
	public void sendToServer(GameClient client) {
		client.sendData(getData());
	}

	@Override
	public void sendToAllClients(GameServer server) {
		server.sendDataToAllClients(getData());
	}

	@Override
	public byte[] getData() {
		return ("6"+" "+username+" "+x+" "+y+" "+port+" "+ipAddress.getHostName()+" ").getBytes();
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
}
