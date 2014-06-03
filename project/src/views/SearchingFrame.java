package views;

import models.User;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Table;
import org.eclipse.jface.viewers.TableViewer;
import controllers.Users;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class SearchingFrame {

	protected Shell shell;
	private Text searchingText;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	int selectNum=0;
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			SearchingFrame window = new SearchingFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/

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

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		shell.setSize(523, 466);
		shell.setMaximized(false);
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		shell.setBounds((WIDTH-430)/2,(HEIGHT-420)/2,430, 374);
		shell.setText("查找&S");
		
		/*
		 * image
		 * 这里仅用于调试，不能发行，路径名需修改！！！
		 * 仅windows下拟用下句:
		 * Image image=new Image(Display.getDefault(), ".\\image\\java.ico");
		 */
		
		//Image image=new Image(Display.getDefault(), ".\\src\\views\\image\\java.ico");
		//shell.setImage(image);
		
		final Group group=new Group(shell,  SWT.NO_TRIM);
		group.setText("偏好");
		group.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		group.setBounds(21, 100, 340, 17);
		//System.out.println(group.getChildren().toString());
		TableViewer tableViewer = new TableViewer(shell, SWT.BORDER | SWT.FULL_SELECTION);
		final Table searchingTable;
		
		searchingTable = tableViewer.getTable();
		searchingTable.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				new MessgFrame().open(searchingTable.getSelection()[0].getText(),true);
				searchingTable.removeAll();
				String key=searchingText.getText().trim();
				//searchingTable.removeAll();
				switch (selectNum)
				{
				case 0:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getUserName().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 1:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getNativePlace().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 2:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getMajor().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 3:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getPhoneNum().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 4:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getEmail().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 5:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getAddress().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				
				}
				
				/*
				 * 将table分色，提高用户体验
				 */
				for(int i=0;i<searchingTable.getItemCount();i++)
				{
					if(0==i%2)
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					else
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
				//flush(text);
			}
		});
		searchingTable.setHeaderVisible(true);
		searchingTable.setBounds(10, 133, 394, 193);
		formToolkit.paintBordersFor(searchingTable);
		
		TableColumn uuidColumn = new TableColumn(searchingTable, SWT.NONE);
		uuidColumn.setText("UUID");
		uuidColumn.setResizable(false);
		
		TableColumn groupUuidColumn = new TableColumn(searchingTable, SWT.NONE);
		groupUuidColumn.setText("groupUuid");
		groupUuidColumn.setResizable(false);
		
		TableColumn nameColumn = new TableColumn(searchingTable, SWT.NONE);
		nameColumn.setWidth(70);
		nameColumn.setText("姓名");
		
		TableColumn nativePlaceColumn = new TableColumn(searchingTable, SWT.NONE);
		nativePlaceColumn.setWidth(100);
		nativePlaceColumn.setText("籍贯");
		
		TableColumn majorColumn = new TableColumn(searchingTable, SWT.NONE);
		majorColumn.setWidth(100);
		majorColumn.setText("专业");
		
		TableColumn phoneColumn = new TableColumn(searchingTable, SWT.NONE);
		phoneColumn.setWidth(100);
		phoneColumn.setText("手机号");
		
		TableColumn emailColumn = new TableColumn(searchingTable, SWT.NONE);
		emailColumn.setWidth(200);
		emailColumn.setText("邮箱");
		
		TableColumn addressColumn = new TableColumn(searchingTable, SWT.NONE);
		addressColumn.setWidth(175);
		addressColumn.setText("地址");
		
		Menu menu = new Menu(searchingTable);
		searchingTable.setMenu(menu);
		
		MenuItem showMessgItem = new MenuItem(menu, SWT.NONE);
		showMessgItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(searchingTable.getSelection().length!=0)
				new DelDialog().open(searchingTable.getSelection()[0],MainFrame.getTree());
			}
		});
		showMessgItem.setText("显示详细信息");
		
		MenuItem delItem = new MenuItem(menu, SWT.NONE);
		delItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(searchingTable.getSelection().length!=0)
					new DelDialog().open(searchingTable.getSelection()[0],MainFrame.getTree());
			}
		});
		delItem.setText("删除该联系人");

		
		
		searchingText = new Text(shell, SWT.BORDER);
		searchingText.setFocus();
		/* 原意是用于回车，但未完善
		 * searchingText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				String key=searchingText.getText().trim();
				searchingTable.removeAll();
				switch (selectNum)
				{
				case 0:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getUserName().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 1:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getNativePlace().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 2:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getMajor().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 3:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getPhoneNum().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 4:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getEmail().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 5:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getAddress().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				
				}
				
				
				//将table分色，提高用户体验
				
				for(int i=0;i<searchingTable.getItemCount();i++)
				{
					if(0==i%2)
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					else
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
				//flush(text);
			}
		});*/
		searchingText.setBounds(44, 71, 221, 23);
		
		Label messgLabel = new Label(shell, SWT.NONE);
		messgLabel.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		messgLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		messgLabel.setBounds(21, 29, 138, 36);
		messgLabel.setText("\u8BF7\u8F93\u5165\u5173\u952E\u5B57\uFF1A");
		
		final Button nameButton = new Button(group, SWT.RADIO);
		nameButton.setSelection(true);
		
		searchingText.setFocus();
		nameButton.setCursor(null);
		
		nameButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(group.getChildren());
				//selectNum=0;
				setSelectNum(0);
			}
		});
		nameButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		nameButton.setBounds(38, 0, 45, 17);
		nameButton.setText("\u59D3\u540D");
		
		final Button nativePlaceButton = new Button(group,SWT.RADIO);
		nativePlaceButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(group.getChildren());
				setSelectNum(1);
			}
		});
		nativePlaceButton.setText("籍贯");
		nativePlaceButton.setBounds(89, 0, 45, 17);
		formToolkit.adapt(nativePlaceButton, true, true);
		
		
		final Button majorButton = new Button(group, SWT.RADIO);
		majorButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(majorButton.getText());
				setSelectNum(2);
			}
		});
		majorButton.setText("专业");
		majorButton.setBounds(139, 0, 45, 17);
		formToolkit.adapt(majorButton, true, true);
		
		final Button phoneButton = new Button(group, SWT.RADIO);
		phoneButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSelectNum(3);
			}
		});
		phoneButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		phoneButton.setBounds(190, 0, 45, 17);
		phoneButton.setText("\u624B\u673A");
		
		final Button emailButton = new Button(group, SWT.RADIO);
		emailButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		emailButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setSelectNum(4);
			}
		});
		emailButton.setBounds(241, 0, 45, 17);
		emailButton.setText("邮箱");
		
		
		final Button addressButton = new Button(group,SWT.RADIO);
		addressButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//System.out.println(group.getChildren());
				setSelectNum(5);
			}
		});
		addressButton.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		addressButton.setBounds(292, 0, 45, 17);
		addressButton.setText("地址");
		
		final Button searchingButton = new Button(shell,SWT.NONE);
		searchingButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String key=searchingText.getText().trim();
				searchingTable.removeAll();
				switch (selectNum)
				{
				case 0:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getUserName().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 1:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getNativePlace().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 2:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getMajor().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 3:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getPhoneNum().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 4:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getEmail().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				case 5:
					for(User tmp:Users.getUserList())
					{
						if(tmp.getAddress().contains(key))
						{
							new TableItem(searchingTable,0).setText(
									new String[]{
										tmp.getUuid(),
										tmp.getGroupUuid(),
										tmp.getUserName(),
										tmp.getNativePlace(),
										tmp.getMajor(),
										tmp.getPhoneNum(),
										tmp.getEmail(),
										tmp.getAddress()
									}
									);
							
						}
					}
					break;
				
				}
				
				/*
				 * 将table分色，提高用户体验
				 */
				for(int i=0;i<searchingTable.getItemCount();i++)
				{
					if(0==i%2)
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
					else
						searchingTable.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
				}
				//flush(text);
			}
		});
		searchingButton.setBounds(271, 69, 80, 27);
		searchingButton.setText("查找&C");
		
		
	}
	/*原计划中Group应该可以识别选中项而准备的刷新函数，已废弃
	 *
	private void flush(Text text)
	{
		//if(text.getText().length()!=0)
		//长度不等于0，说明有内容，则调用后台的User的链表，搜索并显示结果
		
		java.util.List<Group> groupList=Groups.getGroupList();
		Iterator it=groupList.iterator();
		while(it.hasNext())
		{
			//TableItem tableItem = new TableItem(table, SWT.NONE);
			//tableItem.setText("New TableItem");
			TableItem tmpItem=new TableItem(table, SWT.NONE);
			User tmpUser=(User)it.next();
			tmpItem.setText(new String[]
					{tmpUser.getUuid(),
					 tmpUser.getGroupUuid(),
					 tmpUser.getUserName(),
					 tmpUser.getNativePlace(),
					 tmpUser.getMajor(),
					 tmpUser.getPhoneNum(),
					 tmpUser.getEmail(),
					 tmpUser.getAddress()});
			
		}
		
	}*/
	
	
	public void setSelectNum(int num)//将单选项的信息反馈到这里
	{
		selectNum=num;
	}
}
