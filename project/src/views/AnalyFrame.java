package views;

import java.util.LinkedList;
import java.util.List;

import models.User;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.experimental.chart.swt.ChartComposite;

import controllers.Users;

public class AnalyFrame {

	protected Shell shell;
	
	
	/**
	 * Launch the application.
	 * @param args
	 */
	/*public static void main(String[] args) {
		try {
			AnalyFrame window = new AnalyFrame();
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
		final int WIDTH=java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
		final int HEIGHT=java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
		
		shell = new Shell(SWT.CLOSE | SWT.MIN);
		//shell.setSize(800, 493);
		shell.setBounds(0,(HEIGHT)/8,WIDTH,HEIGHT/2+90);
		shell.setText("搜索");
		/*
		 * image
		 * 这里仅用于调试，不能发行，路径名需修改！！！
		 * 仅windows下拟用下句:
		 * Image image=new Image(Display.getDefault(), ".\\image\\java.ico");
		 */
		
		//Image image=new Image(Display.getDefault(), "./src/views/image/java.ico");
		//shell.setImage(image);
		/*
		 * 对专业的分析————饼状图
		 */
		DefaultPieDataset majorData = this.getMajorData();
		DefaultPieDataset nativePlaceData = this.getNativePlaceData();

		
		JFreeChart majorChart = ChartFactory.createPieChart3D( "",majorData,false,false,false);
		final org.jfree.experimental.chart.swt.ChartComposite majorFrame = new ChartComposite(shell, SWT.NONE, majorChart,
				                true);
		majorFrame.setLocation((WIDTH-50)/2, 10);
		majorFrame.pack();
		majorFrame.setSize((WIDTH-50)/16*9,HEIGHT/2);
		
		JFreeChart nativePlaceChart = ChartFactory.createPieChart3D( "",nativePlaceData,false,false,false);
		final org.jfree.experimental.chart.swt.ChartComposite nativePlaceFrame = new ChartComposite(shell, SWT.NONE, nativePlaceChart,
								true);
		nativePlaceFrame.setLocation(-10, 10);
		nativePlaceFrame.pack();
		nativePlaceFrame.setSize((WIDTH-50)/16*9,HEIGHT/2);
								      
	}
	LinkedList<User> userList=(LinkedList<User>)Users.getUserList();
	
	DefaultPieDataset getMajorData() 
	{
		//Users.initUserList();
		DefaultPieDataset d=new DefaultPieDataset();
		StringBuffer sb=new StringBuffer();
		
		for(int i=0;i<userList.size();i++)
		{
			
			String major=userList.get(i).getMajor();
			if(sb.indexOf(major)==-1)
			{
				sb.append(major);
				sb.append(" ");
			}
			
		}
		String[] majorName=sb.toString().trim().split(" ");
		int tmpNum=0;
		int[] majorDetailNum=new int[majorName.length];
		for(int i=0;i<majorName.length;i++)
		{
			
			for(User user:userList)
			{
				if(majorName[i].equals(user.getMajor().trim()))
					{majorDetailNum[i]++;tmpNum++;}
			}
			//System.out.println(majorDetailNum[i]);
		}
		//System.out.println(sb);
		//System.out.println(sb.length());
		//System.out.println(majorNum);
		for(int i=0;i<majorName.length;i++)
		{
			if(majorName[i].length()!=0)
				d.setValue(majorName[i]+(majorDetailNum[i])+"人",majorDetailNum[i]);
				
		}
		d.setValue("未知专业"+(userList.size()-tmpNum)+"人",userList.size()-tmpNum);
		return d;
	}
	DefaultPieDataset getNativePlaceData() 
	{
		DefaultPieDataset d=new DefaultPieDataset();
		StringBuffer sb=new StringBuffer();
		
		for(int i=0;i<userList.size();i++)
		{
			
			String nativePlace=userList.get(i).getNativePlace();
			if(sb.indexOf(nativePlace)==-1)
			{
				sb.append(nativePlace);
				sb.append(" ");
			}
			
		}
		String[] nativePlaceName=sb.toString().trim().split(" ");
		int tmpNum=0;
		int[] nativePlaceDetailNum=new int[nativePlaceName.length];
		for(int i=0;i<nativePlaceName.length;i++)
		{
			
			for(User user:userList)
			{
				if(nativePlaceName[i].equals(user.getNativePlace().trim()))
					{nativePlaceDetailNum[i]++;tmpNum++;}
			}
			//System.out.println(majorDetailNum[i]);
		}
		//System.out.println(sb);
		//System.out.println(sb.length());
		//System.out.println(majorNum);
		for(int i=0;i<nativePlaceName.length;i++)
		{
			if(nativePlaceName[i].length()!=0)
			d.setValue(nativePlaceName[i]+(nativePlaceDetailNum[i])+"人",nativePlaceDetailNum[i]);
				//System.out.println(nativePlaceDetailNum[i]);
		}
		d.setValue("未知籍贯"+(userList.size()-tmpNum)+"人",userList.size()-tmpNum);
		return d;
	}
}
