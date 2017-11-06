package DAO;

import java.security.Timestamp;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

import com.google.gson.Gson;

import entity.Message;
import entity.User;
import redis.clients.jedis.Jedis;

public class Run {

	public static void main(String[] args) {
		String select = null;
		User user = new User();
		User sendTo = new User();
		Message message = new Message();
		Gson json = new Gson();
	do{
		Jedis jedis = new Jedis("localhost");
		System.out.println("---------GENERIC CHAT SYSTEM v0.1.0 -----------");
			System.out.println("selecione a opera��o");
			System.out.println("1- cadastrar");
			System.out.println("2- mandar mensagem");
			System.out.println("3- ver mensagens recebidas");
			System.out.println("4- ver mensagens enviadas");
			System.out.println("5- responder mensagem");
			System.out.println("0 - sair");
		System.out.println("---------all rights reserved to matheuslima5926 -----------");
		
			Scanner scanner = new Scanner(System.in);
			
			select = scanner.nextLine().trim();
			
			switch(select){
			
			/////////////////////////////////////////////////////////////////////////////////////////
			
			case "1":
				
				user = new User();
			
				
				System.out.println("Digite o seu nome:");
				user.setName(scanner.nextLine());
				
				if (!user.getName().isEmpty()){
					
					System.out.println("Digite o seu apelido:");
					user.setNickname(scanner.nextLine());
					
					if (!user.getNickname().trim().isEmpty() || user.getNickname() == null || !user.getName().trim().isEmpty() || user.getName() == null ){
						
						if (UserDAO.findUserByNick(user) == null){

							UserDAO.saveUser(user);
							System.out.println("cadastro realizado com sucesso!");
							System.out.println("------- digite 0 para sair -------");
							scanner.next();
						
						}else{
							System.out.println("apelido j� existente ou � inv�lido!");
							System.out.println("------- digite 0 para sair -------");
							scanner.next();
							break;
						}
					}
						
				}else{
					
					System.out.println("nome inv�lido!");
					System.out.println("------- digite 0 para sair -------");
					scanner.next();
					break;
				} 
				break;
				
			/////////////////////////////////////////////////////////////////////////////////////////
				
			case "2":
				message = new Message();
				String selfSend = null;
				ArrayList<String> auxSendTo = new ArrayList<>();
				ArrayList<User> usersSendTo = new ArrayList<>();
				
				System.out.println("digite o seu apelido");
				User userSender = new User();
				     sendTo = new User();
				userSender.setNickname(scanner.nextLine().trim());
				
				if (userSender.getNickname().trim() != null && jedis.get("user:"+userSender.getNickname()).trim() != null){
					String option = null;
					userSender = json.fromJson(jedis.get("user:"+userSender.getNickname()), User.class);
					
					do{
						System.out.println("digite o apelido do destinat�rio: ");
						sendTo.setNickname(scanner.nextLine().trim());
						if (UserDAO.findUserByNick(sendTo) != null){
							auxSendTo.add(sendTo.getNickname());
							 if (sendTo.getNickname().trim().equals(userSender.getNickname())){
								 selfSend = userSender.getNickname();
							 };
							
						}else{
							System.out.println("destinat�rio inv�lido!");
						}
						System.out.println("deseja adicionar mais destinat�rios? s/n");
						option = scanner.nextLine().trim();
					}while (!option.equals("n".trim().toLowerCase()));
					
							json = new Gson();
							User userSendToAux = new User();
							
							for (String usr : auxSendTo){
								
								userSendToAux = json.fromJson(jedis.get("user:"+usr), User.class);
								usersSendTo.add(userSendToAux);
							}
							
							
						if (UserDAO.findUserByNick(userSender) != null){
							 System.out.println("digite a mensagem:");
							 
							 message.setText(scanner.nextLine());
							 if (message.getText().isEmpty()){
								 System.out.println("a mensagem n�o pode ser vazia!");
								 break;
								
							 }else{
								     ArrayList<String> recivedMessagesFromUserSender = new ArrayList<>();
								     if (userSender.getMessagesRecived() != null){
								    	 recivedMessagesFromUserSender = userSender.getMessagesRecived();
								     }
								                                        
								     ArrayList<String> sendedMessagesFromUserSender = new ArrayList<>();
								     if (userSender.getMessagesSended()!= null){
								    	 sendedMessagesFromUserSender = userSender.getMessagesSended();
								     }
								     
								     ArrayList<String> recivedMessagesFromUserSendTo = new ArrayList<>();
								     for (User usrSendTo : usersSendTo){
								    	 	if (usrSendTo.getMessagesRecived() != null){
								    	 		recivedMessagesFromUserSendTo = usrSendTo.getMessagesRecived();
								    	 	}
								     }
								     
								     ArrayList<String> sendedMessagesFromUserSendTo = new ArrayList<>();
								     for (User usrSendTo : usersSendTo){
								    	 	if (usrSendTo.getMessagesSended() != null){
								    	 		sendedMessagesFromUserSendTo = usrSendTo.getMessagesSended();
								    	 	}
								     }
								     
									 String idMsg = String.valueOf(System.currentTimeMillis()); //valor do Id da mensagem
									 System.out.println("id da mensagem: "+idMsg.toString());
									 String parserString = jedis.get("user:"+userSender.getNickname()); //vari�vel que transforma o json em usu�rio
									 userSender = json.fromJson(parserString, User.class);
									 System.out.println("userSender: " + userSender.getNickname());
									 
								     message.setIdMessage(idMsg);
									 message.setSender(userSender.getNickname());
									 message.setSendTo(auxSendTo);
									 message.setDate(String.valueOf(LocalDate.now()));
									 
									 System.out.println("message Id: "+message.getIdMessage());
									 
									 sendedMessagesFromUserSender.add(message.getIdMessage());
									 recivedMessagesFromUserSendTo.add(message.getIdMessage());
								 
  	  										userSender.setMessagesSended(sendedMessagesFromUserSender);
  	 									    userSender.setMessagesRecived(recivedMessagesFromUserSender);
  	  									 
									 
									 userSender.setMessagesSended(sendedMessagesFromUserSender);
									 userSender.setMessagesRecived(recivedMessagesFromUserSender);
									 
									 for (User userSendTo : usersSendTo){
										 userSendTo.setMessagesRecived(recivedMessagesFromUserSendTo);
										 jedis.set("user:"+userSendTo.getNickname(), json.toJson(userSendTo));
									 }
									 
  									 jedis.set("message:"+message.getIdMessage(), json.toJson(message));
  									 jedis.set("user:"+userSender.getNickname(), json.toJson(userSender));
									 
  									for (User usrSendTo : usersSendTo){
  										jedis.set("user:"+usrSendTo.getNickname(), json.toJson(usrSendTo));
  									}
  									
									 System.out.println("mensagem: " + jedis.get("message:"+message.getIdMessage()));
									 System.out.println("userSender: " + jedis.get("user:"+userSender.getNickname()));

									 scanner.next();
							 }
							 
						}else{
							System.out.println("usuario n�o cadastrado");
						}
					}
					else{
						System.out.println("apelido invalido!");
					}	
				
				break;
				
				/////////////////////////////////////////////////////////////////////////////////////////
				
			case "3":
				user = new User();
				message = new Message();
				ArrayList<Message> messagesRecived = new ArrayList<>();
				System.out.println("digite seu apelido");
				scanner = new Scanner(System.in);
				user.setNickname(scanner.nextLine().trim());
				String result = jedis.get("user:"+user.getNickname());
				if (result != null){
					user = json.fromJson(result, User.class);
					if (user.getMessagesRecived() != null){
						for (String msg : user.getMessagesRecived()){
							message = json.fromJson(jedis.get("message:"+msg), Message.class);
							messagesRecived.add(message);
						}
					}else{
						System.out.println("voc� n�o recebeu nenhuma mensagem at� agora");
						break;
					}
					
					message = null;
					if (user.getMessagesRecived().size() > 0){
						System.out.println("------- MENSAGENS RECEBIDAS: -------");
						for (Message msg : messagesRecived){
							
						    System.out.println("--------------------- \n");
							System.out.println("id: " + msg.getIdMessage());
							System.out.println("data: " + msg.getDate());
							System.out.println("autor: "+ msg.getSender());
							System.out.println("mensagem: " + msg.getText());
							String parent = msg.getParentMessage();
							if (parent != null){
								System.out.println("***** resposta da mensagem: " + parent + " *****");
							}
						}
							System.out.println("--------------------- \n");
						
				      }
					
						
					scanner.next();
					}else{
					System.out.println("usu�rio n�o cadastrado!");
				}
				
				break;
				/////////////////////////////////////////////////////////////////////////////////////////
			case "4":
				user = new User();
				message = new Message();
				ArrayList<Message> messagesSended = new ArrayList<>();
				ArrayList<Message> resp = new ArrayList<>();
				System.out.println("digite seu apelido");
				scanner = new Scanner(System.in);
				user.setNickname(scanner.nextLine().trim());
				if (jedis.get("user:"+user.getNickname())== null){
					System.out.println("usu�rio n�o cadastrado");
					break;
				}
				if (user.getNickname() != null || jedis.get("user:"+user.getNickname()) != null){
					
					user = json.fromJson(jedis.get("user:"+user.getNickname()), User.class);
					if (user.getMessagesSended() != null){
						for (String msg : user.getMessagesSended()){
							message = json.fromJson(jedis.get("message:"+msg), Message.class);
							messagesSended.add(message);
						}
					}else{
						System.out.println("voc� n�o enviou nenhuma mensagem.");
						break;
					}
					
					message = null;
					if (user.getMessagesSended().size() > 0){
						System.out.println("------- MENSAGENS ENVIADAS: -------");
						ArrayList<String> replyes = new ArrayList<>();
						
						for (Message msg : messagesSended){
						    System.out.println("--------------------- \n");
							System.out.println("id: " + msg.getIdMessage());
							System.out.println("data: " + msg.getDate());
							System.out.println("autor: "+ msg.getSender());
							System.out.println("mensagem: " + msg.getText());
							 System.out.println("--------------------- \n");
							if (msg.getReply() != null){
								for (String rep : msg.getReply()){
									replyes.add(rep);
								}
							}
							if(replyes != null){
								System.out.println("RESPOSTAS ENVIADAS A VOC�: ");
								for (String sms : replyes){
									  System.out.println(jedis.get("message:"+sms));
									  message = json.fromJson(jedis.get("message:"+sms), Message.class);
									  System.out.println("mensagem original: "+message.getParentMessage());
									  System.out.println("autor:"+ message.getSender());
									  System.out.println("mensagem: " + message.getText());
									  System.out.println("data de envio: "+ message.getDate());
									  
									System.out.println("-------------------------------\n");
								}
							}
							
							System.out.println("--------------------- \n");
						}
						
					}
					
					scanner.next();
					}else{
						break;
				}
				break;
			case "5":
				Message reply = new Message();
				user = new User();
				message = new Message();
				messagesRecived = new ArrayList<>();
				ArrayList<String> messagesSendedFromUser = new ArrayList<>();
				System.out.println("digite seu apelido: ");
				user.setNickname(scanner.nextLine().trim());
				if(jedis.get("user:"+user.getNickname())!= null){
					user = json.fromJson(jedis.get("user:"+user.getNickname()), User.class);
					if (user.getMessagesRecived() == null){
						System.out.println("voc� n�o possui nenhuma mensagem");
						break;
					}
					messagesSendedFromUser = user.getMessagesSended();
					if (user.getMessagesRecived().size() > 0){
						reply.setIdMessage(String.valueOf(System.currentTimeMillis()));
						for (String msg : user.getMessagesRecived()){
							message = json.fromJson(jedis.get("message:"+msg), Message.class);
							messagesRecived.add(message);
						}
						message = null;
						System.out.println("------- MENSAGENS RECEBIDAS: -------");
						for (Message msg : messagesRecived){
							
						    System.out.println("--------------------- \n");
							System.out.println("id: " + msg.getIdMessage());
							System.out.println("data: " + msg.getDate());
							System.out.println("autor: "+ msg.getSender());
							System.out.println("mensagem: " + msg.getText());
							System.out.println("--------------------- \n");
						}
						System.out.println("digite o id da mensagem que deseja responder: ");
						String msgId = scanner.nextLine().trim();
						System.out.println(msgId);
						
						if (jedis.get("message:"+msgId)!= null){
							 message = json.fromJson(jedis.get("message:"+msgId), Message.class);
							 sendTo = new User();
							 sendTo = json.fromJson(jedis.get("user:"+message.getSender()), User.class);
							 ArrayList<String> userSendToMessagesRecived = new ArrayList<>();
							 if (sendTo.getMessagesRecived() != null){
								 userSendToMessagesRecived = sendTo.getMessagesRecived();
							 }
							 System.out.println("userSendTO:"+ sendTo.getNickname());
							 ArrayList<String> repl = new ArrayList<>();
							 ArrayList<String> send = new ArrayList<>();
							 
							 send.add(message.getSender());
							 if (message.getReply() != null){
								 repl = message.getReply();
							 }
							 System.out.println("digite o texto da mensagem:");
							 reply.setText(scanner.nextLine());
							 if (!reply.getText().isEmpty()){
								 reply.setIdMessage(String.valueOf(System.currentTimeMillis()));
								 
								 userSendToMessagesRecived.add(reply.getIdMessage());
								 if(userSendToMessagesRecived != null){
									 sendTo.setMessagesRecived(userSendToMessagesRecived);
								 }
								 repl.add(reply.getIdMessage());
								 message.setReply(repl);
								 reply.setSender(user.getNickname());
								 reply.setParentMessage(message.getIdMessage());
								 reply.setSendTo(send);
								 reply.setDate(String.valueOf(LocalDate.now()));
								 jedis.set("message:"+reply.getIdMessage(), json.toJson(reply));
								 jedis.set("message:"+message.getIdMessage(), json.toJson(message));
								 jedis.set("user:"+user.getNickname(), json.toJson(user));
								 jedis.set("user:"+sendTo.getNickname(), json.toJson(sendTo));
								 for (String s : message.getReply()){
									 System.out.println("respostas: " + s);
								 }
								
								 System.out.println("msg enviada com sucess!");
								 System.out.println(jedis.get("message:"+message.getIdMessage()));
							 }else{
								 System.out.println("mensagem n�o pode ser vazia");
							 }
							 
						}
						scanner.next();
					}
					
					
				}else{
					System.out.println("usu�rio n�o localizado!");
					scanner.next();
				}
				break;
			case "0":
				System.out.println("Good bye. See you soon!");
				
				break;
			default:
				System.out.println("op��o inv�lida!");
				System.out.println("------- digite 0 para sair -------");
				scanner.next();
				
				break;
			}
			jedis.close();
		}while(!select.equals("0"));


		
	}

}
