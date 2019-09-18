package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet07NpcMove extends Packet{
	
	byte xMove,yMove;
	
	public Packet07NpcMove(byte xMove,byte yMove) {
		super(7);
		this.xMove=xMove;
		this.yMove=yMove;
		
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
		return ("7"+ " " + xMove+" "+yMove+" ").getBytes();
	}

}
