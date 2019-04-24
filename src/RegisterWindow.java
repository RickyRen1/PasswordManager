import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RegisterWindow {
	private static final String USER_FILE_NAME = "userFile.txt";
	private static final String DELINEATOR = " ";

	protected Shell shell;
	private Text usernameInput;
	private Text passwordInput;
	private Text confirmPasswordInput;

	/**
	 * Launch the application.
	 * @param args
	 */

	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(275, 300);
		shell.setText("SWT Application");
	
		CLabel lblUsername = new CLabel(shell, SWT.NONE);
		lblUsername.setBounds(10, 10, 69, 25);
		lblUsername.setText("Username");
		
		usernameInput = new Text(shell, SWT.BORDER);
		usernameInput.setBounds(10, 41, 234, 22);
		
		CLabel lblPassword = new CLabel(shell, SWT.NONE);
		lblPassword.setBounds(10, 69, 69, 25);
		lblPassword.setText("Password");
		
		passwordInput = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		passwordInput.setBounds(10, 100, 234, 22);
		
		CLabel lblConfirmPassword = new CLabel(shell, SWT.NONE);
		lblConfirmPassword.setBounds(10, 128, 113, 25);
		lblConfirmPassword.setText("Confirm Password");
		
		confirmPasswordInput = new Text(shell, SWT.PASSWORD | SWT.BORDER);
		confirmPasswordInput.setBounds(10, 159, 234, 22);
		
		
		Button btnRegister = new Button(shell, SWT.NONE);
		btnRegister.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				String username = usernameInput.getText();
				String password = passwordInput.getText();
				String confirmPassword = confirmPasswordInput.getText();
				File userFile = new File(USER_FILE_NAME);
				try {
					userFile.createNewFile();
					Scanner sc = new Scanner(userFile);
					Boolean duplicateUsername = false;
					if(!(username.isEmpty()) && !(password.isEmpty())) {
						while(sc.hasNextLine()) { //make sure username doesn't already exist
							String[] userPass = sc.nextLine().split(DELINEATOR);
							if((userPass[0].equals(usernameInput.getText()))) {
								duplicateUsername = true;
							}
						}
						if(!duplicateUsername && 
								!username.contains(DELINEATOR) && //user can't have space
								!password.contains(DELINEATOR) && //password can't have space
								confirmPassword.equals(password)) { //password and confirm password are the same
							FileWriter fw = new FileWriter(USER_FILE_NAME, true);
							fw.write("\n");
							fw.write(username);
							fw.write(DELINEATOR);
							fw.write(password);
							fw.close();
							System.out.println("Register Successful");
							shell.close();
						}
						else {
							System.out.println("username already exists or user/pass contains invalid characters");
						}
					}
					else {
						System.out.println("Username and password fields cannot be blank");
					}
					sc.close();
				}
				catch(FileNotFoundException eRegister) {
					eRegister.printStackTrace();
				}
				catch(IOException eRegisterWrite) {
					eRegisterWrite.printStackTrace();
				};
			}
		});
		btnRegister.setBounds(168, 187, 76, 24);
		btnRegister.setText("Register");
		

	}

}
