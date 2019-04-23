import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;

public class PasswordWindow{

	protected Shell shell;
	protected Table passwordTable;
	protected String passwordFileName;

	/**
	 * Launch the application.
	 * @param args
	 */
	
	/**
	 * Open the window.
	 * @wbp.parser.entryPoint
	 */
	public void open(String username) {
		Display display = Display.getDefault();
		passwordFileName = username + "Password.txt";
		System.out.println(passwordFileName);
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	private void populateTable() {
		File passwordFile = new File(passwordFileName);
		try {
			passwordFile.createNewFile();
			Scanner sc = new Scanner(passwordFile);
			while(sc.hasNextLine()) { //populate table with password file
				String[] userInfo = sc.nextLine().split(" ");
				TableItem tableItem = new TableItem(passwordTable, SWT.NONE);
				tableItem.setText(0, userInfo[0]); //application
				tableItem.setText(1, userInfo[1]); //username 
				tableItem.setText(2, userInfo[2]); //password
			}
			sc.close();
		}
		catch(FileNotFoundException ePasswordFile) {
			ePasswordFile.printStackTrace();
		}
		catch(IOException ePassworldFileIO) {
			ePassworldFileIO.printStackTrace();
		}
	}
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(528, 369);
		shell.setText("SWT Application");
		
		passwordTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		passwordTable.setBounds(10, 10, 416, 310);
		passwordTable.setHeaderVisible(true);
		passwordTable.setLinesVisible(true);
		
		TableColumn tblclmnApplication = new TableColumn(passwordTable, SWT.NONE);
		tblclmnApplication.setWidth(100);
		tblclmnApplication.setText("Application");
		
		TableColumn tblclmnUsername = new TableColumn(passwordTable, SWT.NONE);
		tblclmnUsername.setWidth(100);
		tblclmnUsername.setText("Username");
		
		TableColumn tblclmnPassword = new TableColumn(passwordTable, SWT.NONE);
		tblclmnPassword.setWidth(213);
		tblclmnPassword.setText("Password");
		
		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					AddPassword window = new AddPassword();
					window.open(passwordFileName);
				} catch (Exception eCreateRegisterWindow) {
					eCreateRegisterWindow.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(428, 10, 76, 24);
		btnAdd.setText("Add");
		
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.setBounds(428, 40, 76, 24);
		btnDelete.setText("Delete");
		
		Button btnCopy = new Button(shell, SWT.NONE);
		btnCopy.setBounds(432, 70, 76, 24);
		btnCopy.setText("Copy");
		
		populateTable();

	}
}
