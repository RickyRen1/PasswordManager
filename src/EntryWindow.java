import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import java.io.*;
import java.util.Scanner;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Button;

public class EntryWindow {

	private static final String USER_FILE_NAME = "userFile.txt";
	
	protected Shell shell;
	private Text usernameInput;
	private Text passwordInput;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			EntryWindow window = new EntryWindow();
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
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	public Text getUsername() {
		return usernameInput;
	}
	
	//Helper function for login text and login button listeners
	private void logInLogic() { 
		String username = usernameInput.getText();
		String password = passwordInput.getText();
		File userFile = new File(USER_FILE_NAME);
		try {
//			System.out.println("LogIn Clicked Once");
			userFile.createNewFile();
			Scanner sc = new Scanner(userFile);
			Boolean loginSuccessFlag = false;
			while(sc.hasNextLine()) { //authenticate login information
				String[] userPass = sc.nextLine().split(" ");
				if(userPass[0].equals(username) && userPass[1].equals(password)) {
					loginSuccessFlag = true;
					break;
				} 
				//System.out.println(sc.nextLine());
			}
			sc.close();
			if(loginSuccessFlag) {
				System.out.println("Login Successful");
				try {
					shell.close();
					PasswordWindow window = new PasswordWindow();
					window.open(username);
				} catch (Exception eCreatePasswordWindow) {
					eCreatePasswordWindow.printStackTrace();
				}
			}
			else {
				System.out.println("Login Fail");
			}
		}
		catch(FileNotFoundException eLogin) {
			eLogin.printStackTrace();
		}
		catch(IOException eLoginWrite) {
			eLoginWrite.printStackTrace();
		};
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(280, 300);
		shell.setText("SWT Application");
		
		usernameInput = new Text(shell, SWT.BORDER);
		usernameInput.setBounds(10, 41, 234, 22);
		
		passwordInput = new Text(shell, SWT.BORDER);
		passwordInput.addListener(SWT.Traverse, new Listener() {
			public void handleEvent(Event e) {
				if(e.detail == SWT.TRAVERSE_RETURN) {
					logInLogic();
				}
			}
		});
		passwordInput.setBounds(10, 100, 234, 22);
		
		CLabel lblUsername = new CLabel(shell, SWT.NONE);
		lblUsername.setBounds(10, 10, 69, 25);
		lblUsername.setText("Username");
		
		CLabel lblPassword = new CLabel(shell, SWT.NONE);
		lblPassword.setBounds(10, 69, 69, 25);
		lblPassword.setText("Password");
		
		Button btnLogIn = new Button(shell, SWT.NONE);
		btnLogIn.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				logInLogic();
			}
			
		});
		
		btnLogIn.setBounds(168, 128, 76, 24);
		btnLogIn.setText("Log In");
		
		Button btnRegister = new Button(shell, SWT.NONE);
		btnRegister.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					RegisterWindow window = new RegisterWindow();
					window.open();
				} catch (Exception eCreateRegisterWindow) {
					eCreateRegisterWindow.printStackTrace();
				}
			}
			
		});
		btnRegister.setBounds(10, 128, 76, 24);
		btnRegister.setText("Register");

	}
}
