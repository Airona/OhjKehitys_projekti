package fi.swd.projektityo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image {
	@JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
	private long id;
	
	@Column(name = "gameId", nullable = false)
	private long gameId;
	
	@Column(name = "userId", nullable = false)
	private long userId;
	
	private String name, date;
	
	@Column(name = "url", nullable = false)
	private String url;
	
	public Image() {
		super();
	}

	public Image(long gameId, long userId, String name, String date, String url) {
		super();
		this.gameId = gameId;
		this.userId = userId;
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

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
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
		return "Image [id=" + id + ", gameId=" + gameId + ", userId=" + userId
				+ ", name=" + name + ", date=" + date + ", url=" + url + "]";
	}
}
