package com.glomozda.machinerepair.service;

import com.glomozda.machinerepair.controller.SearchQuery;
import com.glomozda.machinerepair.controller.SessionScopeInfo;

public interface SessionScopeInfoService {
	
	public SessionScopeInfo getSessionScopeInfo();
	
	public void changeSessionScopeSelectedId(final Long selectedId);
    
    public void changeSessionScopePagingInfo(final Long pagingFirstIndex,
			final Long pagingLastIndex,
			final Long pageNumber);
    
    public void changeSessionScopePagingPlusInfo(final Long pagingFirstIndexPlus,
			final Long pagingLastIndexPlus,
			final Long pageNumberPlus);
    
    public void changeSessionScopePagingPlusPlusInfo(final Long pagingFirstIndexPlusPlus,
			final Long pagingLastIndexPlusPlus,
			final Long pageNumberPlusPlus);
    
    public void changeSessionScopeAddingInfo(final String messageAdded,
			final String messageNotAdded);
    
    public void changeSessionScopeUpdateInfo(final String messageUpdateFailed,
			final String messageUpdateSucceeded,
			final String messageNoChanges);
    
    public void changeSessionScopeSearchQuery(final SearchQuery searchQuery);
}
