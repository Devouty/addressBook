package views;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;

public class HelpFrame {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			HelpFrame window = new HelpFrame();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
*/
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
		
		shell.setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_BACKGROUND));
		
		shell.setText("帮助页面");
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		shell.setBounds((WIDTH)/4*3-30, 0, WIDTH/4+30, HEIGHT-60);
		
		Label messgLabel = new Label(shell, SWT.NONE);
		messgLabel.setBounds(10, 10, WIDTH/4, HEIGHT-60-20);
		messgLabel.setText("导入导出Excel文件\n\n导入文件，选择一个分组，在菜单栏选择\n"
						+ "\n导入-导入Excel文件，选择符合要求的文件，即可完成导入。\n"
						+ "\n导出文件，选中一个分组，在菜单栏选择导出-导出Excel文件，\n"
						+ "程序生成一个  (分组名).xlx 文件。位置为./src/。导入文\n"
						+ "件的格式要求：每一类信息须遵守本程序规定的名称。如“姓名”\n"
						+ "不能改为“名字”。Excel文件应该有六列第一行应该有六个信息\n"
						+ "，包括姓名，手机号，籍贯，专业，地址，邮箱。\n"
						+ "\n\n分组\n\n"
						+ "分组没有界限，但规定只有树叶可以记录联系人信息，\n"
						+ "即：同一个分组内，分组和联系人无法共存推荐分组形\n"
						+ "式：通讯录列表->学校->学院->专业->联系人。\n\n"
						+ "\n快捷键：\n\n"
						+ "新建分组 ALT+G\n"
						+ "新建联系人 ALT+U\n"
						+ "删除分组 ALT+D\n"
						+ "删除联系人 ALT+D\n"
						+ "打开查找界面 ALT+L\n"
						+ "打开分析界面 ALT+S\n"
						+ "打开帮助页面 ALT+B\n"
						+ "打开关于 ALT+A\n"
						+ "信息界面的保存按钮 ALT+S\n"
						+ "查找界面的查找按钮 ALT+C\n"
						+ "\n\n分析页面\n\n"
						+ "分析页面根据所有联系人的专业和籍贯进行分析，\n"
						+ "生成两个饼状图，专业或籍贯未填写的联系人归为\n"
						+ "“未知专业”和“未知籍贯”。");
		
		
		
	}

}
