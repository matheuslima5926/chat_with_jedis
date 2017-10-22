package DAO;

import java.awt.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.IIOException;

import entity.User;
import redis.clients.jedis.Jedis;

public class Run {

	public static void main(String[] args) {
		String select = "";
	do{
		System.out.println("---------GENERIC CHAT SYSTEM v0.1.0 -----------");
			System.out.println("selecione a opera��o");
			System.out.println("1- cadastrar");
			System.out.println("2- mandar mensagem");
			System.out.println("3- ver mensagens enviadas");
			System.out.println("0 - sair");
		System.out.println("---------all rights reserved to matheuslima5926 -----------");
			Scanner scanner = new Scanner(System.in);
			select = scanner.nextLine().trim();
			switch(select){
			case "1":
				
				User user = new User();
				System.out.println("Digite o seu nome:");
				user.setName(scanner.nextLine());
				
				if(!user.getName().isEmpty()){
					
					System.out.println("Digite o seu apelido:");
					user.setNickname(scanner.nextLine());
					
					if(!user.getNickname().isEmpty()){
						
						System.out.println("cadastro realizado com sucesso!");
						
					}else{
						
						System.out.println("apelido j� existente ou inv�lido!");
						break;
					}
				}else{
					
					System.out.println("nome inv�lido!");
					break; 
				}
				break;
			case "2":
				break;
			case "3":
				break;
			case "4":
				    Jedis jedis = new Jedis("localhost");
				    System.out.println(jedis.get("user"));
					//System.out.println(UserDAO.getAllUsers());
					scanner.next();
				
				break;
			case "0":
				System.out.println("Good bye. See you soon!");
				break;
			default:
				System.out.println("op��o inv�lida!");
				break;
			}
		}while(!select.equals("0"));
		
	}

}
