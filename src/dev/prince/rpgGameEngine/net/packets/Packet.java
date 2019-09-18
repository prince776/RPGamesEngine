package dev.prince.rpgGameEngine.net.packets;

import dev.prince.rpgGameEngine.net.GameClient;
import dev.prince.rpgGameEngine.net.GameServer;

public abstract class Packet {
	
	public static enum PacketTypes{
		INVALID(-1) , LOGIN(0) , DISCONNECT(1),MOVE(2) ,WEATHER(3) ,COORDINATE(4),THUNDER(5),BASICLOGIN(6),NPCMOVE(7),
		WORLDDATA(8),LOCALE(9),LOCALECLIENT(10);
		private int packetID;
		
		private PacketTypes(int packetID){
			this.packetID = packetID;
		}
		public int getID(){
			return packetID;
		}
		
	}
	
	public byte packetID;
	
	public Packet(int packetID){
		this.packetID=(byte)packetID;
	}
	
	public abstract void sendToServer(GameClient client);//SEND DATA TO SPECEFIC CLIENT
	 
	public abstract void sendToAllClients(GameServer server);//DATA WILL BE SENT TO ALL CLIENTS JOINED WITH SERVER
	
	public abstract byte[] getData(); 
	
	public String readData(byte[] data){//DATA OF PACKETS EXCEPT PACKET ID
		 String message = new String(data).trim();
		 return message.substring(2);//SINCE STARTING TWO TERMS WILL BE FOR ID NEXT WILL BE DATA
	}
	
	public static PacketTypes lookUpPackets(String packetID){
		try{
			return lookUpPackets(Integer.parseInt(packetID));
		}catch(NumberFormatException e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static PacketTypes lookUpPackets(int id){
		for(PacketTypes p:PacketTypes.values()){
			if(p.getID() == id){
				return p;
			}
		}
		return PacketTypes.INVALID;
	}
	
}
