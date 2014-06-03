package views;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class OkDialog {

	protected Shell shell;

	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			OkDialog window = new OkDialog();
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
	 * @wbp.parser.entryPoint
	 */
	protected void createContents() {
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		shell.setBounds((WIDTH-297)/2,(HEIGHT-300)/2,297, 161);
		//shell.setSize(297, 161);
		shell.setText("成功");
		shell.setSize(290, 125);
		Label messgLabel = new Label(shell, SWT.CENTER);
		messgLabel.setFont(SWTResourceManager.getFont("微软雅黑", 14, SWT.NORMAL));
		messgLabel.setBounds(10, 10, 244, 55);
		messgLabel.setText("    操作成功！");
		
		
		
		Button yesButton = new Button(shell, SWT.NONE);
		yesButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				shell.close();
			}
		});
		
		yesButton.setBounds(101, 48, 80, 27);
		yesButton.setText("确定");
		
	}

}
