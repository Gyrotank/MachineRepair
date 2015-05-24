package com.glomozda.machinerepair.controller;

import java.io.Serializable;

public class SessionScopeInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 9079771536236334622L;
	
	private static final long DEFAULT_PAGE_SIZE = 10L;
	
	private long selectedId;
	
	private long pagingFirstIndex;
	private long pagingLastIndex;
	private long pageNumber;
	
	private long pagingFirstIndexPlus;
	private long pagingLastIndexPlus;
	private long pageNumberPlus;
	
	private long pagingFirstIndexPlusPlus;
	private long pagingLastIndexPlusPlus;
	private long pageNumberPlusPlus;
	
	private String messageAdded;
	private String messageNotAdded;
	
	private String messageEnableDisableFailed;
	private String messageEnableDisableSucceeded;
	
	private String messageUpdateFailed;
	private String messageUpdateSucceeded;
	private String messageNoChanges;	
	
	private SearchQuery searchQuery;
	
	public SessionScopeInfo() {
		this.selectedId = 0;
		
		this.pagingFirstIndex = 0;
		this.pagingLastIndex = DEFAULT_PAGE_SIZE - 1;
		this.pageNumber = 0;
		
		this.pagingFirstIndexPlus = 0;
		this.pagingLastIndexPlus = DEFAULT_PAGE_SIZE - 1;
		this.pageNumberPlus = 0;
		
		this.pagingFirstIndexPlusPlus = 0;
		this.pagingLastIndexPlusPlus = DEFAULT_PAGE_SIZE - 1;
		this.pageNumberPlusPlus = 0;
		
		this.messageAdded = "";
		this.messageNotAdded = "";
		
		this.messageEnableDisableFailed = "";
		this.messageEnableDisableSucceeded = "";
		
		this.messageUpdateFailed = "";
		this.messageUpdateSucceeded = "";
		this.messageNoChanges = "";
		
		this.searchQuery = new SearchQuery();
	}
	
	public SessionScopeInfo(final long selectedId,
			final long pagingFirstIndex,
			final long pagingLastIndex,
			final long pageNumber,
			final long pagingFirstIndexPlus,
			final long pagingLastIndexPlus,
			final long pageNumberPlus,
			final long pagingFirstIndexPlusPlus,
			final long pagingLastIndexPlusPlus,
			final long pageNumberPlusPlus,
			final String messageAdded,
			final String messageNotAdded,
			final String messageEnableDisableFailed,
			final String messageEnableDisableSucceeded,
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
		
		this.messageEnableDisableFailed = messageEnableDisableFailed;
		this.messageEnableDisableSucceeded = messageEnableDisableSucceeded;
		
		this.messageUpdateFailed = messageUpdateFailed;
		this.messageUpdateSucceeded = messageUpdateSucceeded;
		this.messageNoChanges = messageNoChanges;
		
		this.searchQuery = searchQuery;
	}

	public Long getSelectedId() {
		return selectedId;
	}

	public void setSelectedId(long selectedId) {
		this.selectedId = selectedId;
	}

	public long getPagingFirstIndex() {
		return pagingFirstIndex;
	}

	public void setPagingFirstIndex(long pagingFirstIndex) {
		this.pagingFirstIndex = pagingFirstIndex;
	}

	public long getPagingLastIndex() {
		return pagingLastIndex;
	}

	public void setPagingLastIndex(long pagingLastIndex) {
		this.pagingLastIndex = pagingLastIndex;
	}

	public long getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(long pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getPagingFirstIndexPlus() {
		return pagingFirstIndexPlus;
	}

	public void setPagingFirstIndexPlus(long pagingFirstIndexPlus) {
		this.pagingFirstIndexPlus = pagingFirstIndexPlus;
	}

	public long getPagingLastIndexPlus() {
		return pagingLastIndexPlus;
	}

	public void setPagingLastIndexPlus(long pagingLastIndexPlus) {
		this.pagingLastIndexPlus = pagingLastIndexPlus;
	}

	public long getPageNumberPlus() {
		return pageNumberPlus;
	}

	public void setPageNumberPlus(long pageNumberPlus) {
		this.pageNumberPlus = pageNumberPlus;
	}

	public long getPagingFirstIndexPlusPlus() {
		return pagingFirstIndexPlusPlus;
	}

	public void setPagingFirstIndexPlusPlus(long pagingFirstIndexPlusPlus) {
		this.pagingFirstIndexPlusPlus = pagingFirstIndexPlusPlus;
	}

	public long getPagingLastIndexPlusPlus() {
		return pagingLastIndexPlusPlus;
	}

	public void setPagingLastIndexPlusPlus(long pagingLastIndexPlusPlus) {
		this.pagingLastIndexPlusPlus = pagingLastIndexPlusPlus;
	}

	public long getPageNumberPlusPlus() {
		return pageNumberPlusPlus;
	}

	public void setPageNumberPlusPlus(long pageNumberPlusPlus) {
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

	public String getMessageEnableDisableFailed() {
		return messageEnableDisableFailed;
	}

	public void setMessageEnableDisableFailed(String messageEnableDisableFailed) {
		this.messageEnableDisableFailed = messageEnableDisableFailed;
	}

	public String getMessageEnableDisableSucceeded() {
		return messageEnableDisableSucceeded;
	}

	public void setMessageEnableDisableSucceeded(
			String messageEnableDisableSucceeded) {
		this.messageEnableDisableSucceeded = messageEnableDisableSucceeded;
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
