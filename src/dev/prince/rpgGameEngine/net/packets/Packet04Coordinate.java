package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet04Coordinate extends Packet{
	
	int x,y;
	
	public Packet04Coordinate(int x, int y) {
		super(4);
		this.x=x;
		this.y=y;
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
		return ("4"+" "+x+" "+y+" ").getBytes();
	}

}
