package views;

import models.Group;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import controllers.Groups;
import controllers.Users;

public class DelDialog {

	protected Shell shell;
	Group delGroup;
	TableItem delTableItem;
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			DelDialog window = new DelDialog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open(Group group,Tree tree) {
		this.delGroup=group;
		Display display = Display.getDefault();
		createContents(group,tree);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	public void open(TableItem tableItem,Tree tree) {
		this.delTableItem=tableItem;
		Display display = Display.getDefault();
		createContents(delTableItem,tree);
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents(Group group,Tree tree) {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		shell.setBounds((WIDTH-297)/2,(HEIGHT-300)/2,297, 161);
		//shell.setSize(297, 161);
		shell.setText("删除");
		
		Label messgLabel = new Label(shell, SWT.CENTER);
		messgLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		messgLabel.setBounds(10, 10, 244, 55);
		messgLabel.setText("    确认删除？");
		
		final String uuid=delGroup.getUuid();
		
		Button yesButton = new Button(shell, SWT.NONE);
		yesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delGroup.dispose();
				
				
				shell.close();
				Groups.delGroup(uuid);
				Groups.updateToFile();
				Users.updateToFile();

			}
		});
		yesButton.setBounds(20, 71, 80, 27);
		yesButton.setText("确定");
		
		Button noButton = new Button(shell, SWT.NONE);
		noButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		noButton.setText("取消");
		noButton.setBounds(174, 71, 80, 27);

	}
	protected void createContents(TableItem tableItem,Tree tree) {
		shell = new Shell();
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		shell.setBounds((WIDTH-297)/2,(HEIGHT-300)/2,297, 161);
		//shell.setSize(297, 161);
		shell.setText("删除");
		
		Label messgLabel = new Label(shell, SWT.CENTER);
		messgLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		messgLabel.setBounds(10, 10, 244, 55);
		messgLabel.setText("    确认删除？");
		
		final String uuid=tableItem.getText();
		
		Button yesButton = new Button(shell, SWT.NONE);
		yesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				delTableItem.dispose();
				Users.delUser(uuid);
				Users.updateToFile();
				
				shell.close();
			}
		});
		yesButton.setBounds(20, 71, 80, 27);
		yesButton.setText("确定");
		
		Button noButton = new Button(shell, SWT.NONE);
		noButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		noButton.setText("取消");
		noButton.setBounds(174, 71, 80, 27);

	}
}
