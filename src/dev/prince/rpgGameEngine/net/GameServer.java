package dev.prince.rpgGameEngine.net;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Iterator;
import java.util.List;

import dev.prince.rpgGameEngine.Handler;
import dev.prince.rpgGameEngine.entities.Entity;
import dev.prince.rpgGameEngine.entities.creatures.PlayerMP;
import dev.prince.rpgGameEngine.features.Clock;
import dev.prince.rpgGameEngine.net.packets.Packet;
import dev.prince.rpgGameEngine.net.packets.Packet.PacketTypes;
import dev.prince.rpgGameEngine.net.packets.Packet00Login;
import dev.prince.rpgGameEngine.net.packets.Packet03Weather;
import dev.prince.rpgGameEngine.net.packets.Packet06BasicLogin;
import dev.prince.rpgGameEngine.net.packets.Packet10LocaleClient;
import dev.prince.rpgGameEngine.states.GameState;
import dev.prince.rpgGameEngine.utils.Utils;

public class GameServer extends Thread{
	
	private DatagramSocket socket;
	private Handler handler;
	public static short port =1931;
	public Packet06BasicLogin basic ;
	public List<PlayerMP> connectedPlayers = new ArrayList<PlayerMP>();
	public Iterator<PlayerMP> iterator;
	public static String serverName,passCode;
	
	public GameServer(Handler handler){
		this.handler=handler;
		try {
			this.socket = new DatagramSocket(port);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}
	
	public void run(){
		System.out.println("SERVER INITIATED");
		while(true){
			byte[] data = new byte[256];
			DatagramPacket packet = new DatagramPacket(data,data.length);//Constructs a DatagramPacket for receiving packets of length data.length.
			try {
				socket.receive(packet); 
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			parsePacket(packet.getData(),packet.getAddress(),packet.getPort());
			
		
		}
	}
	
	private void parsePacket(byte[] data, InetAddress address, int port) {
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
			case LOGIN:
				String location=GameState.currentLocation,level=GameState.currentLevel;
				String[] pData = message.split("\\s+");
				packet = new Packet00Login(pData[1]);
				pData=null;
				System.out.println(address.getHostName() + " has joined the game of Port :" +port);
				
				
				String fileName = GameServer.serverName+address.getHostAddress()+((Packet00Login)packet).getUsername()+".multiplayer";
				
				int x=(int)handler.getPlayerX()+100,y=(int) handler.getPlayerY();
				
				
				if(new File("res/multiplayerData/"+fileName).exists()){
					String[] playerData = Utils.loadFileAsString("res/multiplayerData/"+fileName).split("\\s+");
					x=Utils.parseInt(playerData[1]);
					y=Utils.parseInt(playerData[2]);
					location = playerData[3];
					level=playerData[4];
				}
				
				//CONNECTION ESTABLISHMENT BETWEEN SERVER AND CURRENT CLIENT
				PlayerMP playerMP = new PlayerMP(handler,x,y,((Packet00Login)packet).getUsername(),address,port,location,level);
				
				handler.getWorld().getEntityManager().addEntity(playerMP);
				
				
				
				
				//ASK EXISTING CLIENTS TO ADD CURRENT CLIENT
				basic = new Packet06BasicLogin(((Packet00Login)packet).getUsername(),x,y,address,port);
				basic.sendToAllClients(this);
				
				//ASK CURRENT CLIENT TO ADD EXISTING CLIENTS
				iterator=connectedPlayers.iterator();
				while(iterator.hasNext()){
					PlayerMP p= iterator.next();
					this.sendData(("06"+" "+p.getUsername()+" " + p.getX()+" "+p.getY()+" "+p.port+" "+p.ipAddress.getHostName()+" ").getBytes(), address, port);
				}
				//NOW ADD TO LIST OF CONNECTED PLAYERS
				connectedPlayers.add(playerMP);  
				
				Packet00Login login;
				login = new  Packet00Login(handler.getPlayer().getUsername(),(int)handler.getPlayer().getX(),(int)handler.getPlayer().getY(),x,y);//(int)playerMP.getX(),(int)playerMP.getY());
				this.sendData(login.getTotalData(),address, port);
				
				Packet10LocaleClient local = new Packet10LocaleClient(location,level,GameState.currentLocation,GameState.currentLevel);
				System.out.println("FILES: " + GameState.currentLocation + "  "+GameState.currentLevel);
				this.sendData(local.getData(), address, port);
				local=null;
				
				//SEND DATA ABOUT WEATHER
				Packet03Weather packetW =new Packet03Weather(Clock.hrs,Clock.minutes,handler.getClock().getWeatherSystem().rainVars[0],handler.getClock().getWeatherSystem().rainVars[1]);
				packetW.sendToAllClients(this);
				packetW=null;
				break;
			case MOVE:
				
				String[] packetData = message.split("\\s+");
				byte x1 = (byte) Utils.parseInt(packetData[1]);
				byte y1 = (byte) Utils.parseInt(packetData[2]);
				for(PlayerMP p :connectedPlayers){
					if(p.ipAddress.equals(address) && p.port == port){
						p.setxMove(x1);
						p.setyMove(y1);
					}
				}	
				
				break;
			case COORDINATE:
				
				String[] packetData1 = message.split("\\s+");
				int x2 = (int) Utils.parseInt(packetData1[1]);
				int y2 = (int) Utils.parseInt(packetData1[2]);
				for(PlayerMP p :connectedPlayers){
					if(p.ipAddress.equals(address) && p.port == port){
						p.setX(x2);
						p.setY(y2);
					}
				}
				break;
			
			case DISCONNECT:
				System.out.println("EXECUTING DISCONNECTION");
				String[] disconnectData=message.split("\\s+");
				int xD = Utils.parseInt(disconnectData[1]);
				int yD = Utils.parseInt(disconnectData[2]); 
				String Username = disconnectData[3];
				
				PlayerMP target=null;
				
				for(PlayerMP p:connectedPlayers){
					if(p.ipAddress.equals(address) && p.port==port){
						target = p;
					}
				}
				
				handler.getWorld().getEntityManager().removeEntity(target);
				Formatter f=null;
				
				try {
					f= new Formatter("res/multiplayerData/"+GameServer.serverName+address.getHostAddress()+Username+".multiplayer");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				f.format(Username +" ");
				f.format(xD +" ");
				f.format(yD +" ");
				for(PlayerMP p:connectedPlayers){
					if(p.ipAddress.equals(address) && p.port==port){
						f.format(p.getLocation()+ " ");
						f.format( p.getLevel()+ " ");
						System.out.println("LOCALE DATA: " +p.getLocation() + " " + p.getLevel());
						break;
					}
				}
				f.close();
				connectedPlayers.remove(target);

				break;

		}
		packet=null;

		
	}

	public void addConnection(PlayerMP player, Packet00Login packet) {
		 
		
	}

	public void sendData(byte[] data,InetAddress ipAddress,int port){
		DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sendDataToAllClients(byte[] data) {
		iterator=connectedPlayers.iterator();
		while(iterator.hasNext()){
			PlayerMP p = iterator.next();
			sendData(data,p.ipAddress,p.port);
		}
	}
		public List<PlayerMP> getConnectedPlayers  (){
			return connectedPlayers;
		}
		
	
	
}
