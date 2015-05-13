package com.glomozda.machinerepair.controller;

public class SearchQuery {
	
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
