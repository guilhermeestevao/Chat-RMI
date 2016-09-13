package server;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = 3L;
	private String name;
	private String content;
	
	public Message(String hame, String content) {
		this.name = hame;
		this.content = content;	
	}
	
	public String getName() {
		return name;
	}
	public void setName(String hame) {
		this.name = hame;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
	
	
}
