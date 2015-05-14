package com.glomozda.machinerepair.controller;

import java.io.Serializable;

public class SearchQuery implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2765402440209161913L;
	
	private String searchQueryArgument;
	
	public SearchQuery() {
		this.searchQueryArgument = "";
	}
	
	public SearchQuery(String searchQueryArgument) {
		this.searchQueryArgument = searchQueryArgument;
	}

	public String getSearchQueryArgument() {
		return searchQueryArgument;
	}

	public void setSearchQueryArgument(String searchQueryArgument) {
		this.searchQueryArgument = searchQueryArgument;
	}	
}
