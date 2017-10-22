package entity;
import java.util.ArrayList;

public class Message {
	
	private String idMessage;
	private String text;
	private String type;
	
    public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	private String date;
	private ArrayList<User> users;
	 
	public String getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public ArrayList<User> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<User> users) {
		this.users = users;
	}
 
 
}