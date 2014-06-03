package views;

import models.User;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import controllers.*;
public class MessgFrame {

	protected Shell shell;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Text nameText;
	private Text groupUuidText;
	private Text uuidText;
	private Text phoneText;
	private Text emailText;
	private Text addressText;
	private Text nativePlace;
	private Text majorText;
	private String parentUuid=null;
	final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			MessgFrame window = new MessgFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open(String parentUuid)
	{
		this.parentUuid=parentUuid;
		open();
	}
	public void open(String uuid,boolean b)
	{
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		final User tmp=Users.findUserByUuid(uuid);
		this.parentUuid=tmp.getGroupUuid();
		//groupUuidText.setText(tmp.getGroupUuid());
		//uuidText.setText(tmp.getUuid());
		//System.out.println(tmp.getNativePlace());
		
		nameText = formToolkit.createText(shell, " ", SWT.NONE);
		//nameText.setText("");
		nameText.setBounds(103, 48, 67, 23);
		
		phoneText = formToolkit.createText(shell, " ", SWT.NONE);
		//phoneText.setText("");
		phoneText.setBounds(103, 120, 97, 23);
		
		emailText = formToolkit.createText(shell, " ", SWT.NONE);
		//email.setText("");
		emailText.setBounds(103, 182, 209, 23);
		
		addressText = formToolkit.createText(shell, " ", SWT.NONE);
		//addressText.setText("");
		addressText.setBounds(103, 252, 364, 94);
		
		nativePlace = formToolkit.createText(shell, " ", SWT.NONE);
		nativePlace.setBounds(271, 48, 55, 23);
		formToolkit.adapt(nativePlace, true, true);
		
		majorText = formToolkit.createText(shell, " ", SWT.NONE);
		majorText.setBounds(271, 120, 122, 23);
		formToolkit.adapt(majorText, true, true);
		
		nameText.setText(tmp.getUserName());
		nativePlace.setText(tmp.getNativePlace());
		majorText.setText(tmp.getMajor());
		phoneText.setText(tmp.getPhoneNum());
		emailText.setText(tmp.getEmail());
		addressText.setText(tmp.getAddress());
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent ex) {
				//uuid|groupUuid|userName|nativePlace|major|phoneNum|email|address
				String[] tmpStr=new String[]{
						nameText.getText().trim(),
						nativePlace.getText().trim(),
						majorText.getText().trim(),
						phoneText.getText().trim(),
						emailText.getText().trim(),
						addressText.getText().trim()};
				for(int i=0;i<tmpStr.length;i++)
				{
					if(tmpStr[i].equals(""))
					{
						tmpStr[i]=" ";
					}
				}
				tmp.setUserName(tmpStr[0]);
				tmp.setNativePlace(tmpStr[1]);
				tmp.setMajor(tmpStr[2]);
				tmp.setPhoneNum(tmpStr[3]);
				tmp.setEmail(tmpStr[4]);
				tmp.setAddress(tmpStr[5]);
				Users.updateToFile();
				
				shell.close();
			}
		});
		button.setBounds(428, 352, 80, 27);
		formToolkit.adapt(button, true, true);
		button.setText("保存&S");
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public void open() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		
		
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		nameText = formToolkit.createText(shell, "", SWT.NONE);
		nameText.setText("");
		nameText.setFocus();
		nameText.setCursor(null);
		
		nameText.setBounds(103, 48, 67, 23);
		
		nativePlace = formToolkit.createText(shell, "", SWT.NONE);
		nativePlace.setText("");
		nativePlace.setBounds(271, 48, 55, 23);
		formToolkit.adapt(nativePlace, true, true);
		
		phoneText = formToolkit.createText(shell, "", SWT.NONE);
		phoneText.setText("");
		phoneText.setBounds(103, 120, 97, 23);
		
		majorText = formToolkit.createText(shell, "", SWT.NONE);
		majorText.setText("");
		majorText.setBounds(271, 120, 122, 23);
		
		formToolkit.adapt(majorText, true, true);
		
		emailText = formToolkit.createText(shell, "", SWT.NONE);
		emailText.setText("");
		emailText.setBounds(103, 182, 209, 23);
		
		addressText = formToolkit.createText(shell, "", SWT.NONE);
		addressText.setText("");
		addressText.setBounds(103, 252, 364, 94);
		
		Button saveButton = new Button(shell, SWT.NONE);
		saveButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent ex) {
				//System.out.println(nameText.getText().trim());
				//uuid|groupUuid|userName|nativePlace|major|phoneNum|email|address
				String[] tmp=new String[]{
						nameText.getText().trim(),
						nativePlace.getText().trim(),
						majorText.getText().trim(),
						phoneText.getText().trim(),
						emailText.getText().trim(),
						addressText.getText().trim()};
				for(int i=0;i<tmp.length;i++)
				{
					if(tmp[i].equals(""))
					{
						tmp[i]=" ";
					}
				}
				System.out.println(nameText.getText().trim());
				Users.addUser(parentUuid,tmp[0],tmp[1],tmp[2],tmp[3],tmp[4],tmp[5]);
				Users.updateToFile();
				
				shell.close();
			}
		});
		saveButton.setBounds(428, 352, 80, 27);
		formToolkit.adapt(saveButton, true, true);
		saveButton.setText("保存&S");
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
		
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setBounds((WIDTH-566)/2,(HEIGHT-427)/2,566, 427);
		
		shell.setText("\u4E2A\u4EBA\u4FE1\u606F");
		/*
		 * image
		 * 这里仅用于调试，不能发行，路径名需修改！！！
		 * 仅windows下拟用下句:
		 * Image image=new Image(Display.getDefault(), ".\\image\\java.ico");
		 */
		
		//Image image=new Image(Display.getDefault(), "./src/views/image/java.ico");
		//shell.setImage(image);
		
		
		
		
		Label messgLabel = new Label(shell, SWT.NONE);
		messgLabel.setBounds(36, 51, 61, 224);
		formToolkit.adapt(messgLabel, true, true);
		messgLabel.setText("姓名\n\n\n\n手机号\n\n\n\n邮箱\n\n\n\n地址");
		
		Label msgLabel = new Label(shell, SWT.NONE);
		msgLabel.setBounds(230, 51, 35, 95);
		formToolkit.adapt(msgLabel, true, true);
		msgLabel.setText("籍贯\n\n\n\n专业");
		
		
	}

	
}
