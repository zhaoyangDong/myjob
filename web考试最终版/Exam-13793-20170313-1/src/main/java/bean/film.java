package bean;

public class film {
	private String film_id;
	private String tile;
	private String description;
	private String language;
	public String getFilm_id() {
		return film_id;
	}
	public void setFilm_id(String film_id) {
		this.film_id = film_id;
	}
	public String getTile() {
		return tile;
	}
	public void setTile(String tile) {
		this.tile = tile;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	@Override
	public String toString() {
		return "film [film_id=" + film_id + ", tile=" + tile + ", description=" + description + ", language=" + language
				+ "]";
	}
	
	

}
