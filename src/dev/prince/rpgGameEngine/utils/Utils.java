package dev.prince.rpgGameEngine.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Utils {
	
	private static Random random;
	
	public static void init(){
		random = new Random(System.nanoTime());
	}
	
	public static String loadFileAsString(String path){
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			
			String line="";
			
			while((line = br.readLine()) != null){
				stringBuilder.append(line+"\n");
			}
			br.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return stringBuilder.toString();
		
	}
	
	public static int parseInt(String i){
		try{
			return Integer.parseInt(i);
		}catch(NumberFormatException e){
			e.printStackTrace();return 0;
		}
	}
	
	public static int getRandomInt(int initialNum , int finalNum){
		return random.nextInt(finalNum+1)+initialNum;
	}
	
}
