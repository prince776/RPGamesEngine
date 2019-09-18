package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public class Packet03Weather extends Packet{
	
	public byte startHour , startMinute;
	public int rainStart , rainDuration;
	
	public Packet03Weather(int sH,int sM,int rS,int rD) {
		super(3);
		this.startHour = (byte) sH;
		this.startMinute = (byte)sM;
		this.rainStart = rS;
		this.rainDuration =rD;
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
		return ("3"+ " " +startHour+" " + startMinute+" " + rainStart+" " + rainDuration+" ").getBytes();
	}

	public int getStartHour() {
		return startHour;
	}

	public void setStartHour(int startHour) {
		this.startHour = (byte) startHour;
	}

	public int getStartMinute() {
		return startMinute;
	}

	public void setStartMinute(int startMinute) {
		this.startMinute = (byte) startMinute;
	}

	public int getRainStart() {
		return rainStart;
	}

	public void setRainStart(int rainStart) {
		this.rainStart =  rainStart;
	}

	public int getRainDuration() {
		return rainDuration;
	}

	public void setRainDuration(int rainDuration) {
		this.rainDuration =  rainDuration;
	}
	
}
