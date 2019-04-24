import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;

public class SelectionError {

	protected Shell shlError;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SelectionError window = new SelectionError();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shlError.open();
		shlError.layout();
		while (!shlError.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shlError = new Shell();
		shlError.setSize(185, 109);
		shlError.setText("Error");
		
		Label lblErrorItemMust = new Label(shlError, SWT.NONE);
		lblErrorItemMust.setBounds(10, 10, 159, 15);
		lblErrorItemMust.setText("Error: Item must be selected.");
		
		Button btnOk = new Button(shlError, SWT.NONE);
		btnOk.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				shlError.close();
			}
		});
		btnOk.setBounds(46, 31, 75, 25);
		btnOk.setText("OK");

	}
}
