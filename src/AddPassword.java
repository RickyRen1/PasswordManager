import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.custom.CLabel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;

public class AddPassword {
	private static final int APPLICATION_INDEX = 0;
	private static final int USERNAME_INDEX = 1;
	private static final int PASSWORD_INDEX = 2;
	private static final String DELINEATOR = " ";

	protected Object result;
	protected Shell shell;
	
	private String passwordFileName;
	private Table passwordTable;
	
	private Text applicationInput;
	private Text usernameInput;
	private Text passwordInput;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */

	AddPassword(String passwordFileName, Table passwordTable) {
		this.passwordFileName = passwordFileName;
		this.passwordTable = passwordTable;
	}
	/**
	 * Open the dialog.
	 * @return the result
	 * @wbp.parser.entryPoint
	 */
	public Object open(String passwordFileName) {
		createContents();
		shell.open();
		shell.layout();
		Display display = Display.getDefault();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}

	/**
	 * Create contents of the dialog.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(200, 285);
		shell.setText("Add Password");
		
		CLabel lblApplication = new CLabel(shell, SWT.NONE);
		lblApplication.setBounds(10, 10, 80, 25);
		lblApplication.setText("Application");
		
		applicationInput = new Text(shell, SWT.BORDER);
		applicationInput.setBounds(10, 41, 168, 22);
		
		CLabel lblUsername = new CLabel(shell, SWT.NONE);
		lblUsername.setBounds(10, 69, 69, 25);
		lblUsername.setText("Username");
		
		usernameInput = new Text(shell, SWT.BORDER);
		usernameInput.setBounds(10, 100, 168, 22);
		
		CLabel lblPassword = new CLabel(shell, SWT.NONE);
		lblPassword.setBounds(10, 128, 69, 25);
		lblPassword.setText("Password");
		
		passwordInput = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		passwordInput.setBounds(10, 159, 168, 22);
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				System.out.println(passwordFileName);
				File passwordFile = new File(passwordFileName);
				String application = applicationInput.getText();
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				try {
					passwordFile.createNewFile();
					if(!(application.isEmpty()) && !(username.isEmpty()) && !(password.isEmpty())) {
						if(!(application.contains(" ")) && !(username.contains(" ")) && !(password.contains(" "))) {
							FileWriter fw = new FileWriter(passwordFileName, true);
							fw.write("\n");
							fw.write(application);
							fw.write(DELINEATOR);
							fw.write(username);
							fw.write(DELINEATOR);
							fw.write(password);
							fw.close();
							TableItem tableItem = new TableItem(passwordTable, SWT.NONE);
							tableItem.setText(APPLICATION_INDEX, application);
							tableItem.setText(USERNAME_INDEX, username);
							tableItem.setText(PASSWORD_INDEX, password);
							System.out.println("Information Saved");
						}
					}
				}
				catch(FileNotFoundException ePasswordFile) {
					ePasswordFile.printStackTrace();
				}
				catch(IOException ePassworldFileIO) {
					ePassworldFileIO.printStackTrace();
				}
				shell.close();
			}
		});
		btnSave.setBounds(102, 187, 76, 24);
		btnSave.setText("Save");
		
		Button btnCancel = new Button(shell, SWT.NONE);
		btnCancel.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				shell.close();
			}
		});
		btnCancel.setBounds(102, 217, 76, 24);
		btnCancel.setText("Cancel");

	}
}
