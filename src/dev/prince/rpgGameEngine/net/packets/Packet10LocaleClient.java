package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet10LocaleClient extends Packet {
	
	private String location,level,locS,lvlS;
	
	public Packet10LocaleClient(String location,String level,String locS,String lvlS) {
		super(10);
		this.location=location;
		this.level=level;
		this.locS=locS;
		this.lvlS=lvlS;
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
		return ("10"+ " " + location+ " "+level+ " " +locS+ " "+lvlS+ " ").getBytes();
	}

}
