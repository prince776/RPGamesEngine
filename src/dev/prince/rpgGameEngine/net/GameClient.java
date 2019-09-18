package dev.prince.rpgGameEngine.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.entities.creatures.PlayerMP;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.features.Weather;
import dev.prince.rpgGameEngine.net.packets.Packet;
import dev.prince.rpgGameEngine.net.packets.Packet.PacketTypes;
import dev.prince.rpgGameEngine.net.packets.Packet00Login;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;

public class GameClient extends Thread{
	
	public static InetAddress ipAddress;
	private DatagramSocket socket;
	private Handler handler;
	private PlayerMP playerMP;
	private ArrayList<PlayerMP> connectedPlayers;
	public Iterator<PlayerMP> iterator;

	public GameClient(Handler handler,String ipAddress){
		this.handler=handler;
		try {
			this.socket = new DatagramSocket();
			this.ipAddress = InetAddress.getByName(ipAddress);
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}
		connectedPlayers = new ArrayList<PlayerMP>();
	}
	
	public void run(){
		System.out.println("YOU ARE CLIENT");
		while(true){
			byte[] data = new byte[256];
			DatagramPacket packet = new DatagramPacket(data,data.length);//Constructs a DatagramPacket for receiving packets of length data.length.
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}

			try {
				parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}

		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) throws UnknownHostException {
		String message = new String(data).trim();
		String id = message.split("\\s+")[0];
		PacketTypes type = Packet.lookUpPackets(id);
		Packet packet = null;
		
		switch(type){
			default:
			case INVALID:
				break;
			case LOCALE:
				String[] dataL = message.split("\\s+");
				for(PlayerMP p :connectedPlayers){
					if(p.ipAddress.equals(address) && p.port==port){
						p.setLocation(dataL[1]);
						p.setLevel(dataL[2]);
					}
				}
				break;
			case LOCALECLIENT:
				String locale[] = message.split("\\s+");
				GameState.currentLocation=locale[1];
				GameState.currentLevel = locale[2];
				System.out.println("LOCALE MANIPULATIONS INIT");

				for(PlayerMP p :connectedPlayers){
					System.out.println("LOCALE MANIPULATIONS");

					if(p.ipAddress.equals(address) && p.port==port){
						p.setLocation(locale[3]);
						p.setLevel(locale[4]);
					}
				}
				//handler.getWorld().setWorldPath(GameState.root+GameState.currentLocation+"/"+GameState.currentLevel);
				handler.getWorld().loadWorld(GameState.root+GameState.currentLocation+"/"+GameState.currentLevel);
				
				break;
			case BASICLOGIN:
				String[] basicLogin = message.split("\\s+");
				int x = Utils.parseInt(basicLogin[2]);
				int y = Utils.parseInt(basicLogin[3]);
				int portMP = Utils.parseInt(basicLogin[4]);
				String name = basicLogin[1];
				iterator= connectedPlayers.iterator();
				while(iterator.hasNext()){
					PlayerMP p = iterator.next();
					if(!p.ipAddress.equals(basicLogin[5]) || p.port != portMP){
						playerMP=new PlayerMP(handler,x,y,name,InetAddress.getByName(basicLogin[5]),portMP);
						connectedPlayers.add(playerMP);
						handler.getWorld().getEntityManager().addEntity(playerMP);
					}
				}
				
				break;
			case LOGIN:
				String[] packetData = message.split("\\s+");
				packet = new Packet00Login(packetData[1],Utils.parseInt(packetData[2]),Utils.parseInt(packetData[3]),Utils.parseInt(packetData[4]),Utils.parseInt(packetData[5]));	
				playerMP = new PlayerMP(handler,((Packet00Login)packet).getSpawnX(),((Packet00Login)packet).getSpawnY(),((Packet00Login)packet).getUsername(),address,port);
				connectedPlayers.add(playerMP);
				handler.getPlayer().setX(((Packet00Login)packet).getThisX());
				handler.getPlayer().setY(((Packet00Login)packet).getThisY());
				handler.getGameCamera().centerOnEntity(handler.getPlayer());
				handler.getWorld().getEntityManager().addEntity(playerMP);
				break;
				
			case MOVE:
				String[] packetData1 = message.split("\\s+");
				byte x1 = (byte) Utils.parseInt(packetData1[1]);
				byte y1 = (byte) Utils.parseInt(packetData1[2]);
				for(PlayerMP p :connectedPlayers){
					if(p.ipAddress.equals(address) && p.port == port){
						p.setxMove(x1);
						p.setyMove(y1);
						break;
					}
				}	
				break;
				
			case COORDINATE:
				String[] packetData11 = message.split("\\s+");
				int x2 = (int) Utils.parseInt(packetData11[1]);
				int y2 = (int) Utils.parseInt(packetData11[2]);
				for(PlayerMP p :connectedPlayers){
					if(p.ipAddress.equals(address) && p.port == port){
						p.setX(x2);
						p.setY(y2);
						break;
					}
				}
				break;
				
			case WEATHER:
				String[] packetdata = message.split("\\s+");
				Weather.packet.setRainStart(Utils.parseInt(packetdata[3]));
				Weather.packet.setRainDuration(Utils.parseInt(packetdata[4]));
				Clock.hrs=Utils.parseInt(packetdata[1]);
				Clock.minutes=Utils.parseInt(packetdata[2]);
				break;
			case THUNDER:
				String[] thunderData = message.split("\\s+");
				handler.getGameState().getGameCreationSate().getEffects().setClientThunder(((thunderData[1].equalsIgnoreCase("true"))?true:false));
				break;
			case DISCONNECT:
				break;
		}
		
	}

	public void sendData(byte[] data){//SEND ONLY TO SERVER
		DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,GameServer.port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void sendData(byte[] data,InetAddress ipAddress,int port){
		DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public ArrayList<PlayerMP> getConnectedPlayers() {
		return connectedPlayers;
	}
	
}
