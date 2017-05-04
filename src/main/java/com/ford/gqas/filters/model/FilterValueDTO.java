package com.ford.gqas.filters.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"groupId","groupOwner","groupName","fdesc","valCol1","valCol2","valCol3","valCol4","valCol5"})
public class FilterValueDTO implements Serializable{

	private long groupId;
	private String groupOwner;
	private String groupName;
	private String fdesc;
	private String valCol1;
	private String valCol2;
	private String valCol3;
	private String valCol4;
	private String valCol5;
	
	public FilterValueDTO(){
		
	}

	public long getGroupId() {
		return groupId;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	public String getGroupOwner() {
		return groupOwner;
	}

	public void setGroupOwner(String groupOwner) {
		this.groupOwner = groupOwner;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFdesc() {
		return fdesc;
	}

	public void setFdesc(String fdesc) {
		this.fdesc = fdesc;
	}

	public String getValCol1() {
		return valCol1;
	}

	public void setValCol1(String valCol1) {
		this.valCol1 = valCol1;
	}

	public String getValCol2() {
		return valCol2;
	}

	public void setValCol2(String valCol2) {
		this.valCol2 = valCol2;
	}

	public String getValCol3() {
		return valCol3;
	}

	public void setValCol3(String valCol3) {
		this.valCol3 = valCol3;
	}

	public String getValCol4() {
		return valCol4;
	}

	public void setValCol4(String valCol4) {
		this.valCol4 = valCol4;
	}

	public String getValCol5() {
		return valCol5;
	}

	public void setValCol5(String valCol5) {
		this.valCol5 = valCol5;
	}
}
