package models;

import java.util.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class Group extends TreeItem
{
	
	/*
	 * Group.txt中信息排列顺序：
	 * 		parentUuid|uuid|name|last
	 */
	
	private String parentUuid;
	private String uuid;
	private String groupName;
	private boolean last;//标志是否是最后一级目录
	/*
	public Group(String parentUuid, String groupName) {
		this.parentUuid = parentUuid;
		this.groupName = groupName;
		this.uuid = UUID.randomUUID().toString();
		this.last = false;
	}
	
	public Group(String[] groupInfo) {
		this.parentUuid = groupInfo[0];
		this.uuid = groupInfo[1];
		this.groupName = groupInfo[2];
		this.last = Boolean.valueOf(groupInfo[3]);
	}*/
	
	public Group(String parentUuid, String groupName, TreeItem parent) {
		super(parent,0);
		this.parentUuid = parentUuid;
		this.groupName = groupName;
		this.uuid = UUID.randomUUID().toString();
		this.last = false;
		this.setText(groupName);
	}
	public Group(Group treeItem, int style) {
		super(treeItem, style);
		//this.uuid = "0";
		
	}
	public Group(Tree tree, int style) {
		super(tree, style);
		/*
		 * 下面这句要加，否则通讯录列表这个Group没有uuid
		 */
		this.uuid = "0";
	}
	public Group(String[] groupInfo,Group parent) {
		super(parent,0);
		this.parentUuid = groupInfo[0];
		this.uuid = groupInfo[1];
		this.groupName = groupInfo[2];
		this.last = Boolean.valueOf(groupInfo[3]);
		this.setText(groupName);
	}
	/*
	 * setter
	 */
	public void setName(String groupName) {
		this.groupName = groupName;
	}
	
	public void setLast(boolean last) {
		this.last = last;
	}
	
	/*
	 * getter
	 */
	public String getParentUuid() {
		return this.parentUuid;
	}
	
	public String getUuid() {
		return this.uuid;
	}
	
	public String getName() {
		return this.groupName;
	}
	
	public boolean isLast() {
		return this.last;
	}
	protected void checkSubclass() {}
	/*
	public List<String> getChildUuidList() {
		return this.childUuidList;
	}*/
}
