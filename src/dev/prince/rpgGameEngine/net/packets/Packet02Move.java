package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet02Move extends Packet{
	
	private byte x, y;
	
	public Packet02Move(byte x,byte y){
		super(2);
		this.x = x;
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
		return ("2" +" "+ x+" " + y+" ").getBytes();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(byte x) {
		this.x =x;
	}

	public void setY(byte y) {
		this.y = y;
	}
	
}
