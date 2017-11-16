package fi.swd.projektityo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private long id;
    
    @Column(name = "title", nullable = false)
	private String title;
	
	public Game() {
		super();
	}
	
	public Game(String title) {
		super();
		this.title = title;
	}
	
	public Game(long id, String title) {
		super();
		this.id = id;
		this.title = title;
	}
		
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + "]";
	}
}
