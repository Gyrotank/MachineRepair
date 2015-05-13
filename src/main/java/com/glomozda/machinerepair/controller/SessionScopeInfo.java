package com.glomozda.machinerepair.controller;

import java.io.Serializable;

public class SessionScopeInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9079771536236334622L;
	
	private static final Long DEFAULT_PAGE_SIZE = 10L;
	
	private Long selectedId;
	
	private Long pagingFirstIndex;
	private Long pagingLastIndex;
	private Long pageNumber;
	
	private Long pagingFirstIndexPlus;
	private Long pagingLastIndexPlus;
	private Long pageNumberPlus;
	
	private Long pagingFirstIndexPlusPlus;
	private Long pagingLastIndexPlusPlus;
	private Long pageNumberPlusPlus;
	
	private String messageAdded;
	private String messageNotAdded;
	
	private String messageUpdateFailed;
	private String messageUpdateSucceeded;
	private String messageNoChanges;
	
	private SearchQuery searchQuery;
	
	public SessionScopeInfo() {
		this.selectedId = 0L;
		
		this.pagingFirstIndex = 0L;
		this.pagingLastIndex = DEFAULT_PAGE_SIZE - 1;
		this.pageNumber = 0L;
		
		this.pagingFirstIndexPlus = 0L;
		this.pagingLastIndexPlus = DEFAULT_PAGE_SIZE - 1;
		this.pageNumberPlus = 0L;
		
		this.pagingFirstIndexPlusPlus = 0L;
		this.pagingLastIndexPlusPlus = DEFAULT_PAGE_SIZE - 1;
		this.pageNumberPlusPlus = 0L;
		
		this.messageAdded = "";
		this.messageNotAdded = "";
		
		this.messageUpdateFailed = "";
		this.messageUpdateSucceeded = "";
		this.messageNoChanges = "";
		
		this.searchQuery = new SearchQuery();
	}
	
	public SessionScopeInfo(final Long selectedId,
			final Long pagingFirstIndex,
			final Long pagingLastIndex,
			final Long pageNumber,
			final Long pagingFirstIndexPlus,
			final Long pagingLastIndexPlus,
			final Long pageNumberPlus,
			final Long pagingFirstIndexPlusPlus,
			final Long pagingLastIndexPlusPlus,
			final Long pageNumberPlusPlus,
			final String messageAdded,
			final String messageNotAdded,
			final String messageUpdateFailed,
			final String messageUpdateSucceeded,
			final String messageNoChanges,
			final SearchQuery searchQuery) {
		
		this.selectedId = selectedId;
		
		this.pagingFirstIndex = pagingFirstIndex;
		this.pagingLastIndex = pagingLastIndex;
		this.pageNumber = pageNumber;
		
		this.pagingFirstIndexPlus = pagingFirstIndexPlus;
		this.pagingLastIndexPlus = pagingLastIndexPlus;
		this.pageNumberPlus = pageNumberPlus;
		
		this.pagingFirstIndexPlusPlus = pagingFirstIndexPlusPlus;
		this.pagingLastIndexPlusPlus = pagingLastIndexPlusPlus;
		this.pageNumberPlusPlus = pageNumberPlusPlus;
		
		this.messageAdded = messageAdded;
		this.messageNotAdded = messageNotAdded;
		
		this.messageUpdateFailed = "";
		this.messageUpdateSucceeded = "";
		this.messageNoChanges = "";
		
		this.searchQuery = searchQuery;
	}

	public Long getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(Long selectedId) {
		this.selectedId = selectedId;
	}

	public Long getPagingFirstIndex() {
		return pagingFirstIndex;
	}

	public void setPagingFirstIndex(Long pagingFirstIndex) {
		this.pagingFirstIndex = pagingFirstIndex;
	}

	public Long getPagingLastIndex() {
		return pagingLastIndex;
	}

	public void setPagingLastIndex(Long pagingLastIndex) {
		this.pagingLastIndex = pagingLastIndex;
	}

	public Long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Long getPagingFirstIndexPlus() {
		return pagingFirstIndexPlus;
	}

	public void setPagingFirstIndexPlus(Long pagingFirstIndexPlus) {
		this.pagingFirstIndexPlus = pagingFirstIndexPlus;
	}

	public Long getPagingLastIndexPlus() {
		return pagingLastIndexPlus;
	}

	public void setPagingLastIndexPlus(Long pagingLastIndexPlus) {
		this.pagingLastIndexPlus = pagingLastIndexPlus;
	}

	public Long getPageNumberPlus() {
		return pageNumberPlus;
	}

	public void setPageNumberPlus(Long pageNumberPlus) {
		this.pageNumberPlus = pageNumberPlus;
	}

	public Long getPagingFirstIndexPlusPlus() {
		return pagingFirstIndexPlusPlus;
	}

	public void setPagingFirstIndexPlusPlus(Long pagingFirstIndexPlusPlus) {
		this.pagingFirstIndexPlusPlus = pagingFirstIndexPlusPlus;
	}

	public Long getPagingLastIndexPlusPlus() {
		return pagingLastIndexPlusPlus;
	}

	public void setPagingLastIndexPlusPlus(Long pagingLastIndexPlusPlus) {
		this.pagingLastIndexPlusPlus = pagingLastIndexPlusPlus;
	}

	public Long getPageNumberPlusPlus() {
		return pageNumberPlusPlus;
	}

	public void setPageNumberPlusPlus(Long pageNumberPlusPlus) {
		this.pageNumberPlusPlus = pageNumberPlusPlus;
	}

	public String getMessageAdded() {
		return messageAdded;
	}

	public void setMessageAdded(String messageAdded) {
		this.messageAdded = messageAdded;
	}

	public String getMessageNotAdded() {
		return messageNotAdded;
	}

	public void setMessageNotAdded(String messageNotAdded) {
		this.messageNotAdded = messageNotAdded;
	}

	public String getMessageUpdateFailed() {
		return messageUpdateFailed;
	}

	public void setMessageUpdateFailed(String messageUpdateFailed) {
		this.messageUpdateFailed = messageUpdateFailed;
	}

	public String getMessageUpdateSucceeded() {
		return messageUpdateSucceeded;
	}

	public void setMessageUpdateSucceeded(String messageUpdateSucceeded) {
		this.messageUpdateSucceeded = messageUpdateSucceeded;
	}

	public String getMessageNoChanges() {
		return messageNoChanges;
	}

	public void setMessageNoChanges(String messageNoChanges) {
		this.messageNoChanges = messageNoChanges;
	}

	public SearchQuery getSearchQuery() {
		return searchQuery;
	}

	public void setSearchQuery(SearchQuery searchQuery) {
		this.searchQuery = searchQuery;
	}	
}
