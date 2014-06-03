package controllers;

import java.io.*;

import views.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

import models.User;


public class Users {
	
	//用户信息链表
	private static List<User> userList = null;

	
	public static void main(String[] args) {
		//initUserList();
		//importFromExcel("./src/data/book1.xlsx", "123456789");
		//exportToExcel();
		//System.out.println(System.getProperty("file.separator"));
		String   s   =   "d:\\racke\\music";  
		//System.out.println(s.replaceAll("\\\\",   "/"));
		System.out.println(s.contains("\\"));
		
	}
	
	/*
	 * 初始化
	 * 程序窗口初始化时同时初始化用户链表
	 */
	public static void initUserList() {
		
		userList = new LinkedList<User>();
		
		/*
		 * 按行读取文件
		 */
		FileReader reader = null;
		try {
			reader = new FileReader("./src/data/User.txt");
		} catch (FileNotFoundException e) {
			//System.out.println("打开文件失败");
			new File("./src/data/User.txt");
			return;
		}
		  BufferedReader br = new BufferedReader(reader);
		  String userInfo = null;
		  try {
			while((userInfo = br.readLine()) != null) {
				
				//System.out.println(userInfo.equals(null));
				
				if(userInfo.contains("|")) {
					userList.add(new User(userInfo.split("\\|")));
					//用户各项信息以字符‘|’分割
				}
			  }
		} catch (IOException e) {
			System.out.println("读取失败");
		}
		  //System.out.println(userInfo + "1");
		  //System.out.println(userInfo.equals(""));
		 try {
			br.close();
			reader.close();
		} catch (IOException e) {
			System.out.println("关闭失败");
		}
	}
	
	/*
	 * 添加用户
	 * 参数：用户四个基本信息
	 */
	public static void addUser(String groupUuid, String userName, String nativePlace, 
			String major, String phoneNum, String email, String address) {
		User newUser = new User(groupUuid, userName, nativePlace, major, 
				phoneNum, email, address);
		
		userList.add(newUser);
	}
	
	/*
	 * 删除用户
	 * 接收用户uuid	
	 */
	public static boolean delUser(String uuid) {
		
		User user = findUserByUuid(uuid);
		
		if(user != null) {
			userList.remove(user);
			
			return true;
		}
		
		return false;
	}
	
	public static void delUserByGroupUuid(String groupUuid) {
		Iterator<User> it=Users.getUserList().iterator();
		ArrayList<User> tmpUserList=new ArrayList<User>();
		while(it.hasNext())
		{
			User user=it.next();
			if(user.getGroupUuid().equals(groupUuid)) {
				//Users.delUser(user.getUuid());
				//getUserList().remove(user);
				tmpUserList.add(user);
			}
		}
		userList.removeAll(tmpUserList);
	}
	
	/*
	 * 保存用户信息
	 * 接收用户uuid
	 */
	public static boolean saveUserInfo(String uuid, String userName, String nativePlace, 
			String major, String phoneNum, String email, String address) {
		User user = findUserByUuid(uuid);
		
		if(user != null) {
			user.setUserName(userName);
			user.setNativePlace(nativePlace);
			user.setMajor(major);
			user.setPhoneNum(phoneNum);
			user.setEmail(email);
			user.setAddress(address);
			
			return true;
		}
		
		return false;
	}
	
	/*
	 * 根据uuid查找用户
	 * 接收用户uuid
	 */
	public static User findUserByUuid(String uuid) {
		for(User user : userList) {
			if(user.getUuid().equalsIgnoreCase(uuid)) {
				return user;
			}
		}
		
		return null;
	}
	
	/*
	 * 获取用户链表
	 */
	public static List<User> getUserList() {
		
		return userList;
	}
	
	/*
	 * 更新用户链表内容到文件
	 */
	public static void updateToFile() {
		FileWriter writer = null;
		try {
			writer = new FileWriter("./src/data/User.txt");
		} catch (IOException e) {
			System.out.println("打开文件失败");
		} 
		  BufferedWriter bw = new BufferedWriter(writer);
		  String userInfo = null;
		  try {
			for(User user : userList) {
				userInfo = user.getUuid() + '|' + user.getGroupUuid() + '|'
						+ user.getUserName() + '|' + user.getNativePlace() + '|' 
						+ user.getMajor() + '|' + user.getPhoneNum() + '|'
						+ user.getEmail() + '|' + user.getAddress();
				
				bw.write(userInfo);
				bw.newLine();
			}
		} catch (IOException e) {
			System.out.println("写入失败");
		}
		 try {
			bw.close();
			writer.close();
		} catch (IOException e) {
			System.out.println("关闭失败");
		}
	}
	
	/*
	 * 从excel文件导入
	 * 参数：excel文件名,分组uuid
	 */
	public static void importFromExcel(String fileName, String groupUuid) {

		//if(fileName.contains("\\"))
			//fileName.replaceAll("\\\\",   "/");
		
		try {
			String readType = fileName.substring(fileName.lastIndexOf("."));
			
			File file = new File(fileName);
			
			InputStream inp = null;
			Workbook workbook;
			Sheet sheet;
			
			if (file.exists()) {
			    inp = new FileInputStream(file);
			   } 
			else {
				System.out.println("文件不存在！");
			   }
			
			//根据不同文件类型创建不同的对象
			if (readType.equals(".xls")) {
				workbook = new HSSFWorkbook(inp);
			   } 
			else  {
				workbook = new XSSFWorkbook(inp);
			} 
			sheet = workbook.getSheetAt(0);

        	Row row;//定义行对象
        	row = sheet.getRow(0);//获取第一行
        	
        	//临时数组，保存excel文件中第一行信息的下标，
        	//接下来读取用户信息时可以根据下标对号入座
        	
        	int[] tmpArr = new int[]{-1, -1, -1, -1, -1, -1};
        	int i = 0;
        	//userName|nativePlace|major|phoneNum|email|address
        	for(Cell cell : row) {
        		switch(cell.toString())
        		{
        		case "姓名":tmpArr[0] = i;break;
        		case "籍贯":tmpArr[1] = i;break;
        		case "年级专业":tmpArr[2] = i;break;
        		case "联系电话":tmpArr[3] = i;break;
        		case "邮箱":tmpArr[4] = i;break;
        		case "地址":tmpArr[5] = i;break;
        		default: break;
        		}
        		
        		i++;
        	}
        
        	List<String> tempList = new ArrayList<String>();
        	String[] infoArray;
        	int rowNum = sheet.getLastRowNum();//行数
	        for (i = 1; i <= rowNum; i++) { 
	        	row = sheet.getRow(i);
	        	tempList.clear();
	            //按照tmpArr数组的下标信息读取相应方格
	            for (int j = 0; j < 6; j++) { 
	            	if(tmpArr[j] == -1) {
	            		tempList.add(" ");
	            		continue;
	            	}
	            	
	            	//Cell cell = row.getCell(tmpArr[j]);
	            	Cell cell = row.getCell(tmpArr[j], Row.RETURN_BLANK_AS_NULL);
	            	if(cell == null) {
	            		System.out.println("fail");
	            		//cell.setCellValue(" ");
	            		tempList.add(" ");
	            	}
	            	else
	            		tempList.add(cell.toString());
	            } 
	            infoArray = tempList.toArray(new String[tempList.size()]);
	            
	            User user = new User(groupUuid, infoArray[0], infoArray[1], infoArray[2], 
	            		infoArray[3],infoArray[4],infoArray[5]);
	            
	            userList.add(user);
	            System.out.println(user.getUuid() + "  " + user.getUserName());
	        } 
        
        //关闭输入流 
        inp.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		updateToFile();
	}
	
	/*
	 * 导出到excel文件
	 * 参数：分组uuid
	 */
	public static void exportToExcel(String groupUuid) {

		try {
            HSSFWorkbook workbook= new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet("UserData");
            HSSFRow row = sheet.createRow(0);
            //HSSFCell cell = row.createCell(0);
            //cell.setCellValue("test");
            //userName|nativePlace|major|phoneNum|email|address
            row.createCell(0).setCellValue("姓名");
            row.createCell(1).setCellValue("籍贯");
            row.createCell(2).setCellValue("年级专业");
            row.createCell(3).setCellValue("联系电话");
            row.createCell(4).setCellValue("邮箱");
            row.createCell(5).setCellValue("地址");
            
            int rowNum = 1;//行数
            for(User user : userList) {
            	row = sheet.createRow(rowNum);
            	
            	row.createCell(0).setCellValue(user.getUserName());
                row.createCell(1).setCellValue(user.getNativePlace());
                row.createCell(2).setCellValue(user.getMajor());
                row.createCell(3).setCellValue(user.getPhoneNum());
                row.createCell(4).setCellValue(user.getEmail());
                row.createCell(5).setCellValue(user.getAddress());
                
            	rowNum++;
            }
            
            String outputFileName = null;
            
            if(System.getProperty("file.separator").equals("\\")) {//瘟到死
            	outputFileName = "." + "\\" + "src" + "\\" + "data" + "\\"
            			+ Groups.findGroupByUuid(groupUuid).getName() + ".xls";
            }
            else {//linux
            	outputFileName = "." + "/" + "src" + "/" + "data" + "/"
            			+ Groups.findGroupByUuid(groupUuid).getName() + ".xls";
            }
            
            FileOutputStream os = null;
            //os = new FileOutputStream("./src/data/用户信息.xls");
            os = new FileOutputStream(outputFileName);
            workbook.write(os);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("ok");
	
	}
}
