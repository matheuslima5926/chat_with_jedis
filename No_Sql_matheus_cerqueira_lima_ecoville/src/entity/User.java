package entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User {
	
	private String nickname;
	private String name;
	private String registerDate;
	private ArrayList<String> messagesRecived;
	private ArrayList<String> messagesSended;
	
	public ArrayList<String> getMessagesSended() {
		return messagesSended;
	}
	public void setMessagesSended(ArrayList<String> messagesSended) {
		this.messagesSended = messagesSended;
	}
	public String getNickname() {
		return nickname;
	}
	public String getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(String localDate) {
		this.registerDate = localDate;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<String> getMessagesRecived() {
		return messagesRecived;
	}
	public void setMessagesRecived(ArrayList<String> messagesRecived) {
		this.messagesRecived = messagesRecived;
	} 
 
}
