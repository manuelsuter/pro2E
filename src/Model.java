import java.util.Observable;

public class Model extends Observable {
	private String data = "ggg";

	public void setData(String data) {
		this.data = data.toUpperCase();
		notifyObservers(); 
	}

	public String getData() {
		return data;
	}

	public void notifyObservers() {
		setChanged();
		super.notifyObservers();
	}
}