package fi.swd.projektityo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image {
	@JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@ManyToOne
	@Column(name = "gameId", nullable = false)
	private Game game;
	
	@ManyToOne
	@Column(name = "userId", nullable = false)
	private User user;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "date", nullable = false)
	private String date;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	public Image() {
		super();
	}

	public Image(long id, Game game, User user, String name, String date,
			String url) {
		super();
		this.id = id;
		this.game = game;
		this.user = user;
		this.name = name;
		this.date = date;
		this.url = url;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", game=" + game + ", user=" + user
				+ ", name=" + name + ", date=" + date + ", url=" + url + "]";
	}
}
