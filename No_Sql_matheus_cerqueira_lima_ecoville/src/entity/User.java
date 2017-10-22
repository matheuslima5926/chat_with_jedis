package entity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;

public class User {
	
	private String nickname;
	private String name;
	private LocalDate registerDate;
	private ArrayList<Message> messages;
	
	public String getNickname() {
		return nickname;
	}
	public LocalDate getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(LocalDate localDate) {
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
	
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	} 
 
}
