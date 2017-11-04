package fi.swd.projektityo.domain;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image {
	private @JsonIgnore @Id @GeneratedValue(strategy = GenerationType.AUTO) long id;
	private String game, name, date, url, tags;
	
	public Image() {
		super();
	}

	public Image(String game, String name, String date, String url,
			String tags) {
		super();
		this.game = game;
		this.name = name;
		this.date = date;
		this.url = url;
		this.tags = tags;		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Image [id=" + id + ", game=" + game + ", name=" + name
				+ ", date=" + date + ", url=" + url + ", tags=" + tags + "]";
	}
	
}
