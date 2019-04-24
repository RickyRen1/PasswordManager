import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Button;

public class PasswordWindow{
	private static final int APPLICATION_INDEX = 0;
	private static final int USERNAME_INDEX = 1;
	private static final int PASSWORD_INDEX = 2;
	private static final int STAR_INDEX = 3;
	private static final String DELINEATOR = " ";
	private static final String HIDDEN_TEXT = "******";

	private TableItem selectedPasswordInTable = null;
	protected Shell shell;
	protected Display display;
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
		display = Display.getDefault();
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
				String line = null;
				if(!(line = sc.nextLine()).isEmpty()) {
					String[] userInfo = line.split(" ");
					TableItem tableItem = new TableItem(passwordTable, SWT.NONE);
					tableItem.setText(APPLICATION_INDEX, userInfo[APPLICATION_INDEX]);
					tableItem.setText(USERNAME_INDEX, userInfo[USERNAME_INDEX]);
					tableItem.setText(PASSWORD_INDEX, userInfo[PASSWORD_INDEX]);
					tableItem.setText(STAR_INDEX, HIDDEN_TEXT);
				}
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
		shell.setSize(528, 400);
		shell.setText("SWT Application");
		
		passwordTable = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION);
		passwordTable.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				selectedPasswordInTable = passwordTable.getItem(passwordTable.getSelectionIndex());
//				TableItem[] selection = passwordTable.getSelection();
//				for(int i=0; i<selection.length; i++) {
//					selectedPassword += selection[i] + " ";
//				}
//				System.out.println(selectedPassword);
				
			}
		});
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
		tblclmnPassword.setWidth(0);
		tblclmnPassword.setText("Password");
		
		TableColumn tblclmnPasswordStars = new TableColumn(passwordTable, SWT.NONE);
		tblclmnPasswordStars.setWidth(213);
		tblclmnPasswordStars.setText("Password");
		
		Button btnAdd = new Button(shell, SWT.NONE);
		btnAdd.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				try {
					AddPassword window = new AddPassword(passwordFileName, passwordTable);
					window.open(passwordFileName);
				} catch (Exception eCreateRegisterWindow) {
					eCreateRegisterWindow.printStackTrace();
				}
			}
		});
		btnAdd.setBounds(428, 10, 76, 24);
		btnAdd.setText("Add");
		
		Button btnDelete = new Button(shell, SWT.NONE);
		btnDelete.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				File passwordFile = new File(passwordFileName);
				File tempFile = new File("tempFile.txt");
				if(selectedPasswordInTable != null) {
					try {
						BufferedReader reader = new BufferedReader(new FileReader(passwordFile));
						BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
						String application = selectedPasswordInTable.getText(APPLICATION_INDEX);
						String username = selectedPasswordInTable.getText(USERNAME_INDEX);
						String password = selectedPasswordInTable.getText(PASSWORD_INDEX);
						String dataToRemove = application + DELINEATOR + username + DELINEATOR + password;
						//https://stackoverflow.com/questions/1377279/find-a-line-in-a-file-and-remove-it
						String currentLine;
						while((currentLine = reader.readLine()) != null) {
							String trimmedLine = currentLine.trim();
							if(trimmedLine.equals(dataToRemove)) {
								continue;
							}
							writer.write("\n");
							writer.write(currentLine);
						}
						writer.close();
						reader.close();
						passwordFile.delete();
						System.out.println(tempFile.renameTo(passwordFile));
						passwordTable.remove(passwordTable.getSelectionIndex());
						
					} catch (FileNotFoundException eDeletePassword) {
						eDeletePassword.printStackTrace();
						
					} catch (IOException eWriteTempFile) {
						// TODO Auto-generated catch block
						eWriteTempFile.printStackTrace();
					}
				} else {
					SelectionError window = new SelectionError();
					window.open();
				}
			}
		});
		btnDelete.setBounds(428, 40, 76, 24);
		btnDelete.setText("Delete");
		
		Button btnCopy = new Button(shell, SWT.NONE);
		//http://www.java2s.com/Code/Java/SWT-JFace-Eclipse/CopyandPaste.htm
		btnCopy.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if(selectedPasswordInTable != null) {
					Clipboard clipboard = new Clipboard(display);
					String password = selectedPasswordInTable.getText(PASSWORD_INDEX);
					TextTransfer textTransfer = TextTransfer.getInstance();
					clipboard.setContents(new Object[] {password}, new Transfer[] {textTransfer});
				} else {
					SelectionError window = new SelectionError();
					window.open();
				}
			}
		});
		btnCopy.setBounds(428, 70, 76, 24);
		btnCopy.setText("Copy");
		
		Button btnReveal = new Button(shell, SWT.NONE);
		btnReveal.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if(selectedPasswordInTable != null) {
					String password = selectedPasswordInTable.getText(PASSWORD_INDEX);
					selectedPasswordInTable.setText(STAR_INDEX, password);
				} else {
					SelectionError window = new SelectionError();
					window.open();
				}
			}
		});
		btnReveal.setBounds(428, 99, 75, 25);
		btnReveal.setText("Reveal");
		
		Button btnHide = new Button(shell, SWT.NONE);
		btnHide.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				if(selectedPasswordInTable != null) {
					selectedPasswordInTable.setText(STAR_INDEX, HIDDEN_TEXT);
				} else {
					SelectionError window = new SelectionError();
					window.open();
				}
			}
		});
		btnHide.setText("Hide");
		btnHide.setBounds(428, 130, 75, 25);
		
		Button btnLogOut = new Button(shell, SWT.NONE);
		btnLogOut.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				EntryWindow window = new EntryWindow();
				shell.close();
				window.open();
			}
		});
		btnLogOut.setBounds(428, 161, 75, 25);
		btnLogOut.setText("Log Out");
		
		populateTable();
	}
}
