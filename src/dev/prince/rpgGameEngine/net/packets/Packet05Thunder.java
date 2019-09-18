package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet05Thunder extends Packet {
	
	private boolean startThunder; 
	
	public Packet05Thunder(boolean thunderProbability) {
		super(5);
		this.startThunder=thunderProbability;
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
		return ("5"+" "+startThunder+" ").getBytes();
	}

	public boolean isStartThunder() {
		return startThunder;
	}

	public void setStartThunder(boolean startThunder) {
		this.startThunder = startThunder;
	}

	
	
	
	
}
