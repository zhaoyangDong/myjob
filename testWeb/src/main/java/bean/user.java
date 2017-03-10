package bean;

public class user {

	private String id;
	private String name;
	private String mima;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMima() {
		return mima;
	}
	public void setMima(String mima) {
		this.mima = mima;
	}
	
	@Override
	public String toString() {
		return "user [id=" + id + ", name=" + name + ", mima=" + mima + "]";
	}
	
}
