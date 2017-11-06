package entity;
import java.util.ArrayList;

public class Message {
	
	private String idMessage;
	private String sender;
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	private String text;
	private String date;
	private ArrayList<String> sendTo;
	private ArrayList<String> reply;
	private String parentMessage;
	
	//methods
	public ArrayList<String> getSendTo() {
		return sendTo;
	}
	public void setSendTo(ArrayList<String> sendTo) {
		this.sendTo = sendTo;
	}
	public String getParentMessage() {
		return parentMessage;
	}
	public void setParentMessage(String parentMessage) {
		this.parentMessage = parentMessage;
	}
	
	public ArrayList<String> getReply() {
		return reply;
	}
	public void setReply(ArrayList<String> reply) {
		this.reply = reply;
	}
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
	public ArrayList<String> getUsers() {
		return sendTo;
	}
	public void setUsers(ArrayList<String> sendTo) {
		this.sendTo = sendTo;
	}
 
 
}
