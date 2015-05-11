package com.glomozda.machinerepair.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.glomozda.machinerepair.controller.SessionScopeInfo;

@Service
public class SessionScopeInfoServiceImpl implements SessionScopeInfoService {
	
	@Autowired
	private SessionScopeInfo sessionScopeInfo;
	
	@Override
	public SessionScopeInfo getSessionScopeInfo() {
		return this.sessionScopeInfo;
	}
	
	public void setSessionScopeInfo(SessionScopeInfo sessionScopeInfo) {
		this.sessionScopeInfo = sessionScopeInfo;
	}
	
	@Override
	public void changeSessionScopeSelectedId(final Long selectedId) {
		this.sessionScopeInfo.setSelectedId(selectedId);
	}
	
	@Override
	public void changeSessionScopePagingInfo(Long pagingFirstIndex, Long pagingLastIndex,
			Long pageNumber) {
		this.sessionScopeInfo.setPagingFirstIndex(pagingFirstIndex);
    	this.sessionScopeInfo.setPagingLastIndex(pagingLastIndex);
    	this.sessionScopeInfo.setPageNumber(pageNumber);
	}
	
	@Override
	public void changeSessionScopePagingPlusInfo(Long pagingFirstIndexPlus,
			Long pagingLastIndexPlus,
			Long pageNumberPlus) {
		this.sessionScopeInfo.setPagingFirstIndexPlus(pagingFirstIndexPlus);
    	this.sessionScopeInfo.setPagingLastIndexPlus(pagingLastIndexPlus);
    	this.sessionScopeInfo.setPageNumberPlus(pageNumberPlus);
	}
	
	@Override
	public void changeSessionScopePagingPlusPlusInfo(Long pagingFirstIndexPlusPlus,
			Long pagingLastIndexPlusPlus,
			Long pageNumberPlusPlus) {
		this.sessionScopeInfo.setPagingFirstIndexPlusPlus(pagingFirstIndexPlusPlus);
    	this.sessionScopeInfo.setPagingLastIndexPlusPlus(pagingLastIndexPlusPlus);
    	this.sessionScopeInfo.setPageNumberPlusPlus(pageNumberPlusPlus);
	}

	@Override
	public void changeSessionScopeAddingInfo(String messageAdded,
			String messageNotAdded) {
		this.sessionScopeInfo.setMessageAdded(messageAdded);
		this.sessionScopeInfo.setMessageNotAdded(messageNotAdded);
	}

	@Override
	public void changeSessionScopeUpdateInfo(String messageUpdateFailed,
			String messageUpdateSucceeded, String messageNoChanges) {
		this.sessionScopeInfo.setMessageUpdateFailed(messageUpdateFailed);
		this.sessionScopeInfo.setMessageUpdateSucceeded(messageUpdateSucceeded);
		this.sessionScopeInfo.setMessageNoChanges(messageNoChanges);
	}
}
