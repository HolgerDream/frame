package com.gener.core.web.paging;

import java.util.ArrayList;
import java.util.List;

public class PadWebPage
{

	private int				currentPage	= 1;

	private int				pageSize	= 15;

	private int				totalSize	= 0;

	private int				totalPage	= 1;
	
	private List<Object>			list		= new ArrayList<Object>();

	public PadWebPage(int pageSize, int totalSize)
	{
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}

	public PadWebPage(int currentPage, int pageSize, int totalSize)
	{
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalSize = totalSize;
	}
	
	public static PadWebPage fromPageNavigator(PageNavigator webPage)
	{
		PadWebPage padPage = new PadWebPage(webPage.getCurrentPage(), webPage.getPageSize(), webPage.getTotalSize());
		padPage.setList((List<Object>)webPage.getList());
		return padPage;
	}

	public int getCurrentPage()
	{
		return currentPage;
	}

	public int getPageSize()
	{
		return pageSize;
	}

	public int getTotalPage()
	{
		return totalSize % pageSize == 0 ? totalSize / pageSize : totalSize / pageSize + 1;
	}

	public int getTotalSize()
	{
		return totalSize;
	}

	public void setCurrentPage(int currentPage)
	{
		this.currentPage = currentPage;
	}

	public List<Object> getList()
	{
		return list;
	}

	public void setList(List<Object> list)
	{
		this.list = list;
	}

	public void setTotalSize(int totalSize)
	{
		this.totalSize = totalSize;
	}

	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}


}
