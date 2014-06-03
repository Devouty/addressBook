package views;

import models.Group;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

import controllers.Groups;
import controllers.Users;

public class DelAllGroupDialog {

	protected Shell DelAllShell;
	Group mainTreeItem;
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try 
		{
			DelAllGroupDialog window = new DelAllGroupDialog();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open(Group mainTreeItem) {
		this.mainTreeItem=mainTreeItem;
		Display display = Display.getDefault();
		createContents();
		DelAllShell.open();
		DelAllShell.layout();
		while (!DelAllShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		DelAllShell = new Shell(SWT.CLOSE | SWT.MIN);
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		DelAllShell.setBounds((WIDTH-441)/2,(HEIGHT-300)/2,441, 177);
		DelAllShell.setText("清空");
		
		Label messgLabel_1 = new Label(DelAllShell, SWT.CENTER);
		messgLabel_1.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		messgLabel_1.setBounds(10, 10, 405, 23);
		messgLabel_1.setText("您所选择的分组为通讯录列表，确认删除时");
		
		Label messgLabel_2 = new Label(DelAllShell, SWT.CENTER);
		messgLabel_2.setFont(SWTResourceManager.getFont("微软雅黑", 13, SWT.NORMAL));
		messgLabel_2.setBounds(62, 39, 272, 23);
		messgLabel_2.setText("清空所有内容！");
		
		Button yesButton = new Button(DelAllShell, SWT.NONE);
		yesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try{
					TreeItem[] treeItem=mainTreeItem.getItems();
					for(int i=0;i<treeItem.length;i++)
					{
						treeItem[i].dispose();
					}
				Groups.getGroupList().clear();
				Users.getUserList().clear();
				Groups.updateToFile();
				Users.updateToFile();
				}catch(Exception ex){
					System.out.println("清空文件失败");
					}
				
				DelAllShell.close();
				//final Group mainTreeItem = new Group(tree, SWT.NONE);
				//mainTreeItem.setText("通讯录列表");
				//mainShell.setVisible(true);
			}
		});
		yesButton.setBounds(86, 102, 80, 27);
		yesButton.setText("确定");
		
		Button noButton = new Button(DelAllShell, SWT.NONE);
		noButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e)
			{
				DelAllShell.close();
			}
		});
		noButton.setText("取消");
		noButton.setBounds(254, 102, 80, 27);

	}
}
