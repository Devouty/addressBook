package views;

import org.eclipse.swt.widgets.Display;

import models.Group;

import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.swt.SWT;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;

import controllers.Groups;

public class NewTreeItemFrame {
	Tree tree;
	Group treeItem;
	Group mainTreeItem;
	Group trtmNewTreeitem;
	protected Shell shell;
	private Text groupNameText;
	private String name;
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			NewTreeItemFrame window = new NewTreeItemFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

	/**
	 * Open the window.
	 */
	public void open(Tree t) {
		
		tree=t;
		open();
		
	}
	public void open(TreeItem[] t,Group mainTreeItem) {
		this.mainTreeItem=mainTreeItem;
		treeItem=(Group) t[0];
		
		open();
	}
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
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		shell.setBounds((WIDTH-428)/2,(HEIGHT-400)/2,428, 180);
		shell.setText("新建分组");
		/*
		 * image
		 * 这里仅用于调试，不能发行，路径名需修改！！！
		 * 仅windows下拟用下句:
		 * Image image=new Image(Display.getDefault(), ".\\image\\java.ico");
		 */
		
		//Image image=new Image(Display.getDefault(), "./src/views/image/java.ico");
		//shell.setImage(image);
		
		Label messgLabel = new Label(shell, SWT.NONE);
		messgLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		messgLabel.setBounds(23, 29, 138, 31);
		messgLabel.setText("请输入分组名：");
		
		groupNameText = new Text(shell, SWT.BORDER);
		//text.setText(null);
		groupNameText.setBounds(51, 66, 205, 23);
		
		Button button = new Button(shell, SWT.NONE);
		button.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				name=groupNameText.getText();
				System.out.print(name);
				if(!name.equals(""))
				{
					/*if(!flag)
					{
						trtmNewTreeitem = new Group(tree, SWT.NONE);
						trtmNewTreeitem.setText(name);
						shell.close();
					}
					else
					{*/
						if(treeItem.getUuid().equals("0")){
						/*trtmNewTreeitem = new Group(treeItem.getUuid(), 
								name,mainTreeItem);*/
							Groups.addGroup("0", name,mainTreeItem);
						}
						else
						{
							Groups.addGroup(treeItem.getUuid(), name,mainTreeItem);
							/*trtmNewTreeitem = new Group(treeItem.getUuid(), 
									name,
									Groups.findGroupByUuid(treeItem.getUuid()));*/
						}
						
						//trtmNewTreeitem.setText(name);
						Groups.updateToFile();
						shell.close();
						
					//}
				}
			}
		});
		button.setBounds(275, 66, 80, 27);
		button.setText("确定&S");
		
	}
	
}
