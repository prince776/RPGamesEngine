package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet01Disconnect extends Packet {
	
	private int x,y;
	private String username;
	
	public Packet01Disconnect( float x, float y,String username) {
		super(1);
		this.x=(int)x;
		this.y=(int)y;
		this.username=username;
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
		return ("1"+" "+x+" "+y+" "+username+" ").getBytes();
	}

}
