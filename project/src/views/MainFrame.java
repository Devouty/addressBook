package views;




import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import models.Group;
import models.User;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.custom.TreeEditor;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.*;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.events.*;

import controllers.Groups;
import controllers.Users;

import org.eclipse.wb.swt.SWTResourceManager;


public class MainFrame {
	
	protected Shell mainShell;
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Table table;
	final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
	final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
	static Group getMainTreeItem;
	static Tree tree;
	
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			MainFrame window = new MainFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Open the window.
	 */
	public void open() {
		Users.initUserList();
		
		Display display = Display.getDefault();
		createContents();
		/*
		 * 
		 */
		mainShell.open();
		mainShell.layout();
		while (!mainShell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		mainShell = new Shell(SWT.CLOSE | SWT.MIN);
		mainShell.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent arg0) {
				System.exit(0);
			}
		});
		final java.util.List<User> UserList=Users.getUserList();
		mainShell.setModified(true);
		mainShell.setMinimumSize(new Point(800, 600));
		mainShell.setBounds((WIDTH-800)/2,(HEIGHT-600)/2,450, 300);
		mainShell.setText("\u901A\u8BAF\u5F55");
		mainShell.setLayout(new FormLayout());
		/*
		 * menu
		 */
		Menu menu = new Menu(mainShell, SWT.BAR);
		
		mainShell.setMenuBar(menu);
		
		MenuItem fileMenu = new MenuItem(menu, SWT.CASCADE);
		fileMenu.setText("文件&F");
		
		final Menu fileSubMenu = new Menu(fileMenu);
		/*menu_1.addMenuListener(new MenuAdapter() {
			@Override
			public void menuHidden(MenuEvent e) {
				menu_1.dispose();
			}
		});
		添加快捷键时留下的不明物品
		*/
		
		fileMenu.setMenu(fileSubMenu);
		
		MenuItem newMenuItem = new MenuItem(fileSubMenu, SWT.CASCADE);
		newMenuItem.setText("新建&N");
		Menu newSubMenu = new Menu(newMenuItem);
		final MenuItem newUser = new MenuItem(newSubMenu, SWT.NONE);
		/*
		 * image
		 * 这里仅用于调试，不能发行，路径名需修改！！！
		 * 仅windows下拟用下句:
		 * Image image=new Image(Display.getDefault(), ".\\image\\java.ico");
		 */
		
		//Image image=new Image(Display.getDefault(), ".\\src\\views\\image\\java.ico");
		//mainShell.setImage(image);
		
		
		
		
		
		/*
		 * set tree and table
		 */
		tree = formToolkit.createTree(mainShell, SWT.NONE);
		//（Linux环境）Linux环境下没有右键打开树节点的功能，这里添加一个向左向右的快捷键
		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode==SWT.CR)
				{
					tree.getSelection()[0].setExpanded(true);
				}
			}
		});
		
		Menu treeMenu=new Menu(tree);
		tree.setMenu(treeMenu);
		
		
		
		final TreeEditor editor = new TreeEditor(tree);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
		editor.minimumWidth = 50;
		FormData fd_tree = new FormData();
		fd_tree.bottom = new FormAttachment(100, -21);
		fd_tree.left = new FormAttachment(0, 10);
		tree.setLayoutData(fd_tree);
		formToolkit.paintBordersFor(tree);
		/*
		 * table
		 */
		
		TableViewer tableViewer = new TableViewer(mainShell, SWT.BORDER | SWT.FULL_SELECTION|SWT.V_SCROLL|SWT.H_SCROLL);
		//这里的tableviewer已经有了按需求出现滚动条的能力
	
		table = tableViewer.getTable();
		table.setHeaderVisible(true);
		//table.setResizable(false);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) 
			{System.out.println(table.getSelection()[0].getText());
				new MessgFrame().open(table.getSelection()[0].getText(),true);
				
				table.removeAll();
				flush(tree);
			}
		});
		fd_tree.top = new FormAttachment(table, 0, SWT.TOP);
		fd_tree.right = new FormAttachment(table, -6);
		
		//更新程序上次关闭时创建的树
		
		final Group mainTreeItem = new Group(tree, SWT.NONE);
		getMainTreeItem=mainTreeItem;
		Groups.initgroupList();
		//mainTreeItem.setUuid("0");
		mainTreeItem.setText("通讯录列表");
		//System.out.println(mainTreeItem.getText());
		final java.util.LinkedList<Group> GroupList=(LinkedList<Group>) Groups.getGroupList();
		
		//tree里面的menu
		final MenuItem newGroupMenuItem = new MenuItem(treeMenu, SWT.NONE);
		
		newGroupMenuItem.setText("新建子分组");
		
		final MenuItem newUserTreeMenuItem = new MenuItem(treeMenu, SWT.NONE);
		newUserTreeMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Users.addUser(userName, phoneNum, email, address);
				//System.out.println((((GroupTreeItem)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(false);
				new MessgFrame().open((((Group)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(true);
				table.removeAll();
				flush(tree);
			}
		});
		newUserTreeMenuItem.setText("新建联系人");
		
		MenuItem delGroupMenuItem = new MenuItem(treeMenu, SWT.NONE);
		delGroupMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(((Group)tree.getSelection()[0]).getUuid().equals("0"))
				{
					new DelAllGroupDialog().open(mainTreeItem);
				}
				else
				{
					new DelDialog().open((Group)tree.getSelection()[0],tree);
					//tree.clearAll(true);
					//mainTreeItem.setText("通讯录列表");
					//Groups.initgroupList();
					//System.out.println(GroupList);
					
					Users.updateToFile();
				}
			}
		});
		delGroupMenuItem.setText("删除该分组");
		
		final MenuItem importFromExcelTreeMenu = new MenuItem(treeMenu, SWT.CASCADE);
		importFromExcelTreeMenu.setText("导入&I");
		
		Menu importFromExcelTreeSubMenu = new Menu(importFromExcelTreeMenu);
		importFromExcelTreeMenu.setMenu(importFromExcelTreeSubMenu);
		
		MenuItem importFromExcelTreeMenuItem = new MenuItem(importFromExcelTreeSubMenu, SWT.NONE);
		importFromExcelTreeMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mainShell.setVisible(false);
				
				JFileChooser fc=new JFileChooser(new File("./src/data"));
				int flag=fc.showOpenDialog(null);
				
				File selectedFile=null;
				selectedFile=fc.getSelectedFile();
				//System.out.println(selectedFile.getAbsolutePath());
				if(flag==JFileChooser.APPROVE_OPTION)
				{
					try{
						Users.importFromExcel(selectedFile.getAbsolutePath(),((Group)tree.getSelection()[0]).getUuid());
					}catch(Exception ioe){
						System.out.println("导入文件错误");
					}
				}
					table.removeAll();
					flush(tree);
					mainShell.setVisible(true);
					mainShell.setFocus();
			}
		});
		importFromExcelTreeMenuItem.setText("Excel文件&E");
		
		final MenuItem exportToExcelTreeMenu = new MenuItem(treeMenu, SWT.CASCADE);
		exportToExcelTreeMenu.setText("导出到&O");
		
		Menu exportToExcelTreeSubMenu = new Menu(exportToExcelTreeMenu);
		exportToExcelTreeMenu.setMenu(exportToExcelTreeSubMenu);
		
		MenuItem exportToExcelTreeMenuItem = new MenuItem(exportToExcelTreeSubMenu, SWT.NONE);
		exportToExcelTreeMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Users.exportToExcel(((Group)tree.getSelection()[0]).getUuid());
				new OkDialog().open();
			}
		});
		exportToExcelTreeMenuItem.setText("Excel文件&E");
		/*
		 * 对于到底用Group还是GroupTreeItem的保留代码
		GroupTreeItem mainTreeItem = new GroupTreeItem(tree, SWT.NONE);
		mainTreeItem.setText("通讯录列表");
		mainTreeItem.uuid="0";
		java.util.LinkedList<Group> GroupList=(LinkedList<Group>) Groups.getGroupList();
		GroupTreeItem[] tmpItem=new GroupTreeItem[GroupList.size()];
		int num=0;
		
		Iterator it=GroupList.iterator();
		while(it.hasNext())
		{
			Group tmp=(Group) it.next();
			String tmpUuid=tmp.getParentUuid();
			if(tmpUuid.equals("0"))
			{
				new GroupTreeItem(mainTreeItem,0);
			}
			else
			{
				for(int i=0;i<tmpItem.length;i++)
				{
					if(tmpUuid.equals(tmpItem[i].getUuid()))
					{
						new GroupTreeItem(tmpItem[i],0);
					}
				}
			}
		}*/
		/*
		 * for(Group g:groupList)
		 * {
		 * 		if(g.isTopGroup)
		 * 		{
		 * 			tree.add...
		 * 		}
		 * }
		 */
		
		
		/*
		 * 定义一个删除分组时的确认界面
		 
		final JDialog tmp=new JDialog();
		tmp.setTitle("警告");
		tmp.setBounds((WIDTH-400)/2, (HEIGHT-200)/2, 400, 200);
		tmp.getContentPane().setLayout(new FlowLayout());
		//tmp.setAlwaysOnTop(true);
		JLabel messg=new JLabel("您所选择的分组为通讯录列表，确认删除时清空所有分组！");
		messg.setFont(new Font("微软雅黑",Font.BOLD,20));
		JButton yes=new JButton("确认");
		JButton no=new JButton("取消");
		tmp.getContentPane().add(messg);
		tmp.getContentPane().add(yes);
		tmp.getContentPane().add(no);
		yes.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
				{
					Groups.getGroupList().clear();
					Users.getUserList().clear();
					Groups.updateToFile();
					Users.updateToFile();
					//final Group mainTreeItem = new Group(tree, SWT.NONE);
					//mainTreeItem.setText("通讯录列表");
					tmp.setVisible(false);
					//mainShell.setVisible(true);
					
					
				}
		});
		no.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae) 
				{
					tmp.setVisible(false);
					//mainShell.setVisible(true);
				}
		});
		*/
		
		
		table.setLayoutData(new FormData());
		FormData fd_table = new FormData();
		fd_table.right = new FormAttachment(100, -21);
		fd_table.left = new FormAttachment(0, 219);
		fd_table.top = new FormAttachment(0, 57);
		fd_table.bottom = new FormAttachment(100, -21);
		table.setLayoutData(fd_table);
		final TableColumn UUIDColumn = new TableColumn(table, SWT.NONE);
		UUIDColumn.setText("UUID");
		UUIDColumn.setResizable(false);
		
		TableColumn groupUuidColumn = new TableColumn(table, SWT.NONE);
		groupUuidColumn.setText("New Column");
		groupUuidColumn.setResizable(false);
		
		final TableColumn nameColumn = new TableColumn(table, SWT.NONE);
		nameColumn.setWidth(70);
		nameColumn.setText("姓名");
		
		TableColumn nativePlaceColumn = new TableColumn(table, SWT.NONE);
		nativePlaceColumn.setWidth(100);
		nativePlaceColumn.setText("籍贯");
		
		TableColumn majorColumn = new TableColumn(table, SWT.NONE);
		majorColumn.setWidth(100);
		majorColumn.setText("专业");
		
		final TableColumn phoneColumn = new TableColumn(table, SWT.NONE);
		phoneColumn.setWidth(100);
		phoneColumn.setText("手机号");
		
		final TableColumn emailColumn = new TableColumn(table, SWT.NONE);
		emailColumn.setWidth(200);
		emailColumn.setText("邮箱");
		
		final TableColumn addressColumn = new TableColumn(table, SWT.NONE);
		addressColumn.setWidth(175);
		addressColumn.setText("地址");
		
		
		
		/*
		 * tree
		 */
		
		

		
		/*
		Tree tree = new Tree(mainShell, SWT.BORDER);
		FormData fd_tree = new FormData();
		fd_tree.bottom = new FormAttachment(toolBar, 307, SWT.BOTTOM);
		fd_tree.top = new FormAttachment(toolBar, 17);
		fd_tree.right = new FormAttachment(0, 240);
		fd_tree.left = new FormAttachment(0, 10);
		tree.setLayoutData(fd_tree);
		formToolkit.adapt(tree);
		formToolkit.paintBordersFor(tree);
		
		TreeColumn trclmnColumn = new TreeColumn(tree, SWT.NONE);
		trclmnColumn.setWidth(100);
		trclmnColumn.setText("Column");
		
		TreeColumn trclmnNewColumn = new TreeColumn(tree, SWT.NONE);
		trclmnNewColumn.setWidth(100);
		trclmnNewColumn.setText("New Column");
		
		ListViewer listViewer = new ListViewer(mainShell, SWT.BORDER | SWT.V_SCROLL);
		List list = listViewer.getList();
		FormData fd_list = new FormData();
		fd_list.bottom = new FormAttachment(0, 354);
		fd_list.right = new FormAttachment(tree, 230, SWT.RIGHT);
		fd_list.top = new FormAttachment(0, 50);
		fd_list.left = new FormAttachment(tree, 47);
		list.setLayoutData(fd_list);
		*/
		
		
		
		
		newMenuItem.setMenu(newSubMenu);
		
		final MenuItem newGroup = new MenuItem(newSubMenu, SWT.NONE);
		newGroup.setAccelerator(SWT.ALT + 'G');
		
		
		newGroup.setText("分组&G");
		
		newUser.setAccelerator(SWT.ALT+'U');
		newUser.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Users.addUser(userName, phoneNum, email, address);
				//System.out.println((((GroupTreeItem)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(false);
				new MessgFrame().open((((Group)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(true);
				table.removeAll();
				flush(tree);
			}
			
		});
		
		newUser.setText("联系人&U");
		
		MenuItem openMenu = new MenuItem(fileSubMenu, SWT.CASCADE);
		
		openMenu.setText("打开&O");
		openMenu.setAccelerator(SWT.ALT + 'O');
		
		Menu openSubmenu = new Menu(openMenu);
		openMenu.setMenu(openSubmenu);
		
		MenuItem analyMenuItem = new MenuItem(openSubmenu, SWT.NONE);
		analyMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AnalyFrame().open();
			}
		});
		analyMenuItem.setText("用户信息统计界面");
		
		MenuItem searchingMenuItem = new MenuItem(openSubmenu, SWT.NONE);
		searchingMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new SearchingFrame().open();
			}
		});
		searchingMenuItem.setText("查找界面");
		
		MenuItem saveMenuItem = new MenuItem(fileSubMenu, SWT.NONE);
		saveMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Groups.updateToFile();
				Users.updateToFile();
				new OkDialog().open();
			}
		});
		saveMenuItem.setText("保存&S");
		saveMenuItem.setAccelerator(SWT.CTRL + 'S');
		
		final MenuItem importMenuItem = new MenuItem(fileSubMenu, SWT.CASCADE);
		importMenuItem.setText("导入&I");
		
		Menu importSubMenu = new Menu(importMenuItem);
		importMenuItem.setMenu(importSubMenu);
		
		MenuItem importFromExcel = new MenuItem(importSubMenu, SWT.NONE);
		importFromExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				mainShell.setVisible(false);
				
				JFileChooser fc=new JFileChooser(new File("./src/data"));
				int flag=fc.showOpenDialog(null);
				
				File selectedFile=null;
				selectedFile=fc.getSelectedFile();
				//System.out.println(selectedFile.getAbsolutePath());
				if(flag==JFileChooser.APPROVE_OPTION)
				{
					try{
						Users.importFromExcel(selectedFile.getAbsolutePath(),((Group)tree.getSelection()[0]).getUuid());
					}catch(Exception ioe){
						System.out.println("导入文件错误");
					}
				}
					table.removeAll();
					flush(tree);
					mainShell.setVisible(true);
					mainShell.setFocus();
			}
		});
		importFromExcel.setText("Excel文件&E");
		
		final MenuItem exportMenuItem = new MenuItem(fileSubMenu, SWT.CASCADE);
		exportMenuItem.setText("导出到&O");
		
		Menu exportSubMenu = new Menu(exportMenuItem);
		exportMenuItem.setMenu(exportSubMenu);
		
		MenuItem exportToExcel = new MenuItem(exportSubMenu, SWT.NONE);
		exportToExcel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				 Users.exportToExcel(((Group)tree.getSelection()[0]).getUuid());
				 new OkDialog().open();
			}
		});
		exportToExcel.setText("Excel文件&E");
		
		MenuItem exitMenuItem = new MenuItem(fileSubMenu, SWT.NONE);
		exitMenuItem.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				System.exit(0);
			}
		});
		exitMenuItem.setText("退出&Q");
		exitMenuItem.setAccelerator(SWT.ALT + 'Q');
		
		MenuItem helpMenu = new MenuItem(menu, SWT.CASCADE);
		helpMenu.setText("帮助&H");
		
		Menu helpSubmenu = new Menu(helpMenu);
		helpMenu.setMenu(helpSubmenu);
		
		MenuItem bookMenuItem = new MenuItem(helpSubmenu, SWT.NONE);
		bookMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new HelpFrame().open();
			}
		});
		bookMenuItem.setAccelerator(SWT.ALT+'B');
		bookMenuItem.setText("操作手册&B");
		
		MenuItem aboutMenuItem = new MenuItem(helpSubmenu, SWT.NONE);
		aboutMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AboutFrame().open();
			}
		});
		aboutMenuItem.setText("关于&A");
		aboutMenuItem.setAccelerator(SWT.ALT+'A');
		Button messgButton = new Button(mainShell, SWT.NONE);
		messgButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*
				 * 当table选中项不为空时，弹出选中项对应的MessgFrame，
				 * 为空时，无回应
				 * （未实现）
				 */
				if(table.getSelection().length!=0)
				{
					new MessgFrame().open(table.getSelection()[0].getText(0),true);
					//System.out.println(table.getSelection()[0].getText(0));
					table.removeAll();
					flush(tree);
				}
			}
		});
		FormData fd_messgButton = new FormData();
		fd_messgButton.bottom = new FormAttachment(table, -6);
		fd_messgButton.right = new FormAttachment(table, 67);
		fd_messgButton.left = new FormAttachment(table, 0, SWT.LEFT);
		fd_messgButton.top = new FormAttachment(0, 10);
		messgButton.setLayoutData(fd_messgButton);
		formToolkit.adapt(messgButton, true, true);
		messgButton.setText("个人信息&M");
		
		Button delButton = new Button(mainShell, SWT.NONE);
		delButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(table.getSelection().length!=0)
				new DelDialog().open(table.getSelection()[0],tree);
			}
		});
		delButton.setText("删除用户&D");
		
		FormData fd_delButton = new FormData();
		fd_delButton.left = new FormAttachment(messgButton, 6);
		fd_delButton.bottom = new FormAttachment(table, -6);
		fd_delButton.top = new FormAttachment(messgButton, 0, SWT.TOP);
		delButton.setLayoutData(fd_delButton);
		formToolkit.adapt(delButton, true, true);
		
		Button AnalyButton = new Button(mainShell, SWT.NONE);
		AnalyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new AnalyFrame().open();
			}
		});
		AnalyButton.setText("用户信息统计界面&S");
		FormData fd_AnalyButton = new FormData();
		fd_AnalyButton.top = new FormAttachment(messgButton, 0, SWT.TOP);
		
		fd_AnalyButton.bottom = new FormAttachment(table, -6);
		
		AnalyButton.setLayoutData(fd_AnalyButton);
		formToolkit.adapt(AnalyButton, true, true);
		
		Button searchButton = new Button(mainShell, SWT.NONE);
		fd_AnalyButton.left = new FormAttachment(searchButton, -121, SWT.LEFT);
		fd_AnalyButton.right = new FormAttachment(searchButton, -6);
		searchButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new SearchingFrame().open();
			}
		});
		
		fd_delButton.right = new FormAttachment(100, -425);
		searchButton.setText("打开查找用户界面&L");
		
		FormData fd_searchButton = new FormData();
		

		fd_searchButton.left = new FormAttachment(delButton, 300);
		fd_searchButton.top = new FormAttachment(messgButton, 0, SWT.TOP);
		fd_searchButton.bottom = new FormAttachment(table, -6);
		fd_searchButton.right = new FormAttachment(100, -10);
		searchButton.setLayoutData(fd_searchButton);
		formToolkit.adapt(searchButton, true, true);
		
		final Button newGroupButton = new Button(mainShell, SWT.NONE);
		newGroupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] ti=tree.getSelection(); 
				boolean flag=(ti.length!=0);
				//直接判断ti.equals(null)是没用的
				/*
				 * 这里改用TreeItem子类GroupTreeItem完成任务，为的是实现
				 * 每个节点都有UUID
				 */
				if(!flag)
					new NewTreeItemFrame().open(tree);
				else
					new NewTreeItemFrame().open(ti,mainTreeItem);
				if(tree.getSelection()[0].getItems().length!=0)
				{
					newUser.setEnabled(false);
					newUserTreeMenuItem.setEnabled(false);
					importMenuItem.setEnabled(false);
					exportMenuItem.setEnabled(false);
				}
				else{
					newUser.setEnabled(true);
					newUserTreeMenuItem.setEnabled(true);
					importMenuItem.setEnabled(true);
					exportMenuItem.setEnabled(true);
				}
				if(table.getItems().length!=0)
				{
					newGroupButton.setEnabled(false);
					newGroup.setEnabled(false);
				}
				else
				{
					newGroupButton.setEnabled(true);
					newGroup.setEnabled(true);
				}
			}
		});
		newGroupMenuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem[] ti=tree.getSelection(); 
				boolean flag=(ti.length!=0);
				//直接判断ti.equals(null)是没用的
				/*
				 * 这里改用TreeItem子类GroupTreeItem完成任务，为的是实现
				 * 每个节点都有UUID
				 */
				if(!flag)
					new NewTreeItemFrame().open(tree);
				else
					new NewTreeItemFrame().open(ti,mainTreeItem);
				if(tree.getSelection()[0].getItems().length!=0)
				{
					newUser.setEnabled(false);
					newUserTreeMenuItem.setEnabled(false);
					importMenuItem.setEnabled(false);
					exportMenuItem.setEnabled(false);
				}
				else{
					newUser.setEnabled(true);
					newUserTreeMenuItem.setEnabled(true);
					importMenuItem.setEnabled(true);
					exportMenuItem.setEnabled(true);
				}
				if(table.getItems().length!=0)
				{
					newGroupButton.setEnabled(false);
					newGroup.setEnabled(false);
				}
				else
				{
					newGroupButton.setEnabled(true);
					newGroup.setEnabled(true);
				}
			}
		});
		newGroup.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				//mainShell.setVisible(false);
				
				TreeItem[] ti=tree.getSelection(); 
				boolean flag=(ti.length!=0);
				//直接判断ti.equals(null)是没用的
				/*
				 * 这里改用TreeItem子类GroupTreeItem完成任务，为的是实现
				 * 每个节点都有UUID
				 */
				if(!flag)
					new NewTreeItemFrame().open(tree);
				else
					new NewTreeItemFrame().open(ti,mainTreeItem);
				Groups.updateToFile();
				//mainShell.setVisible(true);
				if(tree.getSelection()[0].getItems().length!=0)
				{
					newUser.setEnabled(false);
					newUserTreeMenuItem.setEnabled(false);
					importMenuItem.setEnabled(false);
					exportMenuItem.setEnabled(false);
				}
				else{
					newUser.setEnabled(true);
					newUserTreeMenuItem.setEnabled(true);
					importMenuItem.setEnabled(true);
					exportMenuItem.setEnabled(true);
				}
				if(table.getItems().length!=0)
				{
					newGroupButton.setEnabled(false);
					newGroup.setEnabled(false);
				}
				else
				{
					newGroupButton.setEnabled(true);
					newGroup.setEnabled(true);
				}
			}
		});
		newGroupButton.setText("新建分组&N");
		FormData fd_newGroupButton = new FormData();
		fd_newGroupButton.bottom = new FormAttachment(tree, -6);
		fd_newGroupButton.left = new FormAttachment(0, 10);
		fd_newGroupButton.top = new FormAttachment(0, 10);
		fd_newGroupButton.right = new FormAttachment(0, 77);
		newGroupButton.setLayoutData(fd_newGroupButton);
		formToolkit.adapt(newGroupButton, true, true);
		
		Button delGroupButton = new Button(mainShell, SWT.NONE);
		delGroupButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(((Group)tree.getSelection()[0]).getUuid().equals("0"))
				{
					new DelAllGroupDialog().open(mainTreeItem);
					
					
					
				}
				else
				{
					new DelDialog().open((Group)tree.getSelection()[0],tree);
					//tree.clearAll(true);
					//mainTreeItem.setText("通讯录列表");
					//Groups.initgroupList();
					//System.out.println(GroupList);
					
				}
			}
		});
		delGroupButton.setText("删除分组&D");
		FormData fd_delGroupButton = new FormData();
		fd_delGroupButton.bottom = new FormAttachment(tree, -6);
		fd_delGroupButton.right = new FormAttachment(newGroupButton, 73, SWT.RIGHT);
		fd_delGroupButton.top = new FormAttachment(messgButton, 0, SWT.TOP);
		fd_delGroupButton.left = new FormAttachment(newGroupButton, 6);
		delGroupButton.setLayoutData(fd_delGroupButton);
		formToolkit.adapt(delGroupButton, true, true);
		
		Menu menu_1 = new Menu(table);
		table.setMenu(menu_1);
		
		MenuItem menuItem_2 = new MenuItem(menu_1, SWT.NONE);
		menuItem_2.addSelectionListener(new SelectionAdapter() {
			
			@Override
			public void widgetSelected(SelectionEvent e) {
				//Users.addUser(userName, phoneNum, email, address);
				//System.out.println((((GroupTreeItem)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(false);
				new MessgFrame().open((((Group)tree.getSelection()[0]).getUuid()));
				//mainShell.setVisible(true);
				table.removeAll();
				flush(tree);
			}
			
		});
		menuItem_2.setText("新建联系人");
		
		MenuItem menuItem = new MenuItem(menu_1, SWT.NONE);
		menuItem.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				/*
				 * 当table选中项不为空时，弹出选中项对应的MessgFrame，
				 * 为空时，无回应
				 * （未实现）
				 */
				if(table.getSelection().length!=0)
				{
					new MessgFrame().open(table.getSelection()[0].getText(0),true);
					//System.out.println(table.getSelection()[0].getText(0));
					table.removeAll();
					flush(tree);
				}
			}
		});
		menuItem.setText("显示或修改详细信息");
		
		MenuItem menuItem_1 = new MenuItem(menu_1, SWT.NONE);
		menuItem_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				new DelDialog().open(table.getSelection()[0],tree);
			}
		});
		menuItem_1.setText("删除该联系人");
		
		//tree添加listener，判断是否应该显示相关操作
		tree.addSelectionListener(new SelectionAdapter() 
		{
			public void widgetSelected(SelectionEvent e) 
			{
				//flush右边的list
				//System.out.println(tree.getDisplay());
				//System.out.println(tree.getData());
				//System.out.println(tree.getToolTipText());
				table.removeAll();
				flush(tree);
				if(tree.getSelection()[0].getItems().length!=0)
				{
					newUser.setEnabled(false);
					newUserTreeMenuItem.setEnabled(false);
					importMenuItem.setEnabled(false);
					importFromExcelTreeMenu.setEnabled(false);
					exportToExcelTreeMenu.setEnabled(false);
					exportMenuItem.setEnabled(false);
				}
				else{
					newUser.setEnabled(true);
					newUserTreeMenuItem.setEnabled(true);
					importMenuItem.setEnabled(true);
					exportMenuItem.setEnabled(true);
					importFromExcelTreeMenu.setEnabled(true);
					exportToExcelTreeMenu.setEnabled(true);
				}
				if(table.getItems().length!=0)
				{
					newGroupButton.setEnabled(false);
					newGroup.setEnabled(false);
					newGroupMenuItem.setEnabled(false);
				}
				else
				{
					newGroupButton.setEnabled(true);
					newGroup.setEnabled(true);
					newGroupMenuItem.setEnabled(true);
				}
				
			}
		});
	}
	
	void addTableMessg(Group parent)
	{
		//Users.getUserList();
		java.util.List<User> userList =Users.getUserList();
		Iterator<User> it=userList.iterator();
		while(it.hasNext())
		{
			User tmp=it.next();
			if(tmp.getGroupUuid().equals(parent.getUuid()))
			{
			new TableItem(table,SWT.None).setText(new String[]{
		 	
					tmp.getUuid(),
					tmp.getGroupUuid(),
					tmp.getUserName(),
					tmp.getNativePlace(),
					tmp.getMajor(),
					tmp.getPhoneNum(),
					tmp.getEmail(),
					tmp.getAddress()}); 
			}
		}
		
	}
	void flush(Tree tree)
	{
		TreeItem[] ti=tree.getSelection();
		TreeItem[] ti2=ti[0].getItems();
		addTableMessg((Group)ti[0]);
		
		
		/*
		 * 将table分色，提高用户体验
		 */
		for(int i=0;i<table.getItemCount();i++)
		{
			if(0==i%2)
				table.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
			else
				table.getItem(i).setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		}
		/*if(ti2.equals(null))
		{
			System.out.println("no");
		}
		else
		{
			
			for(int j=0;j<ti2.length;j++)
				System.out.println(ti2[j]);
			
		}*/
		
			/*
			 * 当选中节点的为树叶时，刷新右边的table
			 */
			/*
			 * 这段用于调试  
			 * for(int i=0;i<10;i++)
			{
				TableItem tmpItem=new TableItem(table, SWT.NONE);
				tmpItem.setText(new String[]
						{"tmpUser.getUuid()",
						 "tmpUser.getGroupUuid()",
						 "tmpUser.getUserName()",
						" tmpUser.getNativePlace()",
						 "tmpUser.getMajor()",
						" tmpUser.getPhoneNum()",
						" tmpUser.getEmail()",
						" tmpUser.getAddress()"}
				);*/
				
			
			
			/*
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
		
		
	}
	
	public static Group getMainTreeItem()
	{
		return getMainTreeItem;
	}
	public static Tree getTree()
	{
		return tree;
	}
}
