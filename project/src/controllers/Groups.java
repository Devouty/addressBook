package controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.TreeItem;

import views.MainFrame;
import models.*;

public class Groups {
		//分组信息链表
		private static List<Group> groupList = null;
		
		/*
		 * 初始化
		 * 程序窗口初始化时同时初始化分组链表
		 */
		public static void initgroupList() {
			
			groupList = new LinkedList<Group>();
			
			/*
			 * 按行读取文件
			 */
			FileReader reader = null;
			try {
				reader = new FileReader("./src/data/Group.txt");
			} catch (FileNotFoundException e) {
				//System.out.println("打开文件失败");
				new File("./src/data/Group.txt");
				return;
			}
			  BufferedReader br = new BufferedReader(reader);
			  String info = null;
			  try {
				while((info = br.readLine()) != null) {
					if(!info.contains("|")) {
						continue;
					}
					  //System.out.println(s1);
					String[] groupInfo = info.split("\\|");
					Group tmpGroup=findGroupByUuid(groupInfo[0]);
					
						//groupList.add(new Group(groupInfo, findGroupByUuid(groupInfo[0]));
					try{
						groupList.add(new Group(groupInfo, findGroupByUuid(groupInfo[0])));//分组信息以字符‘|’分割
					}
					catch(Exception ex)
					{
						groupList.add(new Group(groupInfo, MainFrame.getMainTreeItem()));
					}
				}
			} catch (IOException e) {
				System.out.println("读取失败");
			}
			 try {
				br.close();
				reader.close();
			} catch (IOException e) {
				System.out.println("关闭失败");
			}
		}
		
		/*
		 * 添加分组
		 * 参数：父分组uuid，分组名, 
		 */
		public static void addGroup(String parentUuid, String groupName,Group mainTreeItem) {
			Group newGroup;
			if(parentUuid.equals("0"))
			{
				newGroup = new Group(parentUuid, groupName, mainTreeItem);
			}
			else
			{
				newGroup = new Group(parentUuid, groupName, findGroupByUuid(parentUuid));
			}
			groupList.add(newGroup);
		}
		
		public static Group findGroupByParentUuid(String parentuuid) {
			for(Group group : getGroupList()) {
				if(group.getUuid().equals(parentuuid)) {
					return group;
				}
			}
			
			return null;
		}
		
		/*
		 * 删除分组
		 * 接收分组uuid	
		 */
		public static void delGroup(String uuid) {
			//List<String> userUuidList = new ArrayList<String>();
			//List<String> groupUuidList = new ArrayList<String>();
			//先把该组下的用户信息删除
			//Users.delUserByGroupUuid(uuid);
			
			//接着删除该组下的子分组
			//用递归
			/*for(Group group : Groups.getGroupList()) {
				String childGroup = findGroupByParentUuid(uuid).getUuid();
				groupUuidList.add();
				
				
			}*/
			
			TreeItem[] treeItems=Groups.findGroupByUuid(uuid).getItems();
			for(TreeItem item : treeItems) {
				Group group = (Group)item;
				if(Groups.findGroupByUuid(group.getUuid()).getItems().length == 0) {
					Users.delUserByGroupUuid(group.getUuid());
				}
				else {
					delGroup(group.getUuid());
				}
				groupList.remove(group);
			}
			
			
			
			
			
			//最后删除该组
			
			Group group = findGroupByUuid(uuid);
			
			if(group != null) {
				groupList.remove(group);
			}
			
			//return false;
		}
		
		/*
		 * 保存分组信息
		 * 接收分组uuid
		 * (暂不支持父分组)
		 */
		public static boolean saveGroupInfo(String uuid, String groupName) {
			Group group = findGroupByUuid(uuid);
			
			if(group != null) {
				group.setName(groupName);
				
				return true;
			}
			
			return false;
		}
		
		
		/*
		 * 根据uuid查找分组
		 * 接收分组uuid
		 */
		public static Group findGroupByUuid(String uuid) {
			for(Group group : groupList) {
				if(group.getUuid().equalsIgnoreCase(uuid)) {
					return group;
				}
			}
			
			return null;
		}
		
		/*
		 * 获取分组链表
		 */
		public static List<Group> getGroupList() {
			
			return groupList;
		}
		
		/*
		 * 更新分组链表内容到文件
		 */
		public static void updateToFile() {
			FileWriter writer = null;
			try {
				writer = new FileWriter("./src/data/Group.txt");
			} catch (IOException e) {
				System.out.println("打开文件失败");
			} 
			  BufferedWriter bw = new BufferedWriter(writer);
			  String groupInfo = null;
			  try {
				for(Group group : groupList) {
					groupInfo = group.getParentUuid() + '|' + group.getUuid()
							+ '|' + group.getName() + '|' 
							+ String.valueOf(group.isLast());
					
					bw.write(groupInfo);
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
}
