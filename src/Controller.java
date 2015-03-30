public class Controller {
	private Model model;
	private View view;
	private Application frame;

	public Controller(Model model) {
		this.model = model;
	}
	
	
	public void setVisibility(Boolean flag, int width, int height){
		view.setVisibility(flag);
		frame.setWindowSize(width, height);
		
	}
	
	public void setView(View view) {
		this.view = view;
	}
	
	public void setFrame(Application frame){
		this.frame = frame;
	}

	//public void btAction(String stInfo) {
	//	model.setData(stInfo);
		// Klasse.Methode(Übergabewert)

	//}
}
