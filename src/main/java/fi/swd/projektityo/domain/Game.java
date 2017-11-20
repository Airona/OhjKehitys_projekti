package fi.swd.projektityo.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private long id;
    
    @Column(name = "title", nullable = false)
	private String title;
    
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private Set<Image> images;
	
	public Game() {
		super();
	}

	public Game(long id, String title, Set<Image> images) {
		super();
		this.id = id;
		this.title = title;
		this.images = images;
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

	public Set<Image> getImages() {
		return images;
	}

	public void setImages(Set<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Game [id=" + id + ", title=" + title + ", images=" + images
				+ "]";
	}
}
