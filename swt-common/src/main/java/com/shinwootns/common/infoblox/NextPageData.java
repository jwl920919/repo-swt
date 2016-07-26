package com.shinwootns.common.infoblox;

import com.google.gson.JsonArray;

public class NextPageData {
	
	public JsonArray jArrayData = null;
	public String nextPageID = null;
	
	public NextPageData(JsonArray jArrayData, String nextPageID) {
		if (jArrayData != null) {
			this.jArrayData.addAll( jArrayData );
			this.nextPageID = nextPageID;
		}
	}
	
	public boolean IsExistNextPage() {
		if (this.nextPageID == null || this.nextPageID.isEmpty())
			return false;
		else
			return true;
	}
}
