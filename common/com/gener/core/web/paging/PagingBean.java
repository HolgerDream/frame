/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2012-2014 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.6.0_21
 * <p>
 * 版本: 1.0版 文件名：com.gener.core.web.paging.PagingBean.java
 * <p>
 * 作者: 孙立峰
 * <p>
 * 创建时间: 2013-4-11下午3:21:53
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：孙立峰
 * <p>
 * 修改时间：2013-4-11下午3:21:53
 * <p>
 */
package com.gener.core.web.paging;

public class PagingBean
{


	public static Integer		DEFAULT_PAGE_SIZE	= 25;

	/**
	 * 最多显示页码数
	 */
	public static final int		SHOW_PAGES			= 6;

	/**
	 * 每页开始的索引号
	 */
	public Integer				start;
	
	/**
	 * 第n页
	 */
	public Integer             page;
	
	/**
	 * 页码大小
	 */
	private Integer				pageSize;

	/**
	 * 总记录数
	 */
	private Integer				totalItems=0;

	public PagingBean()
	{
	}

	public PagingBean(int page, int pageSize)
	{
		this.page = page;
		this.pageSize = pageSize;
		this.start=pageSize*(page-1);
		
	}

	public Integer getStart()
	{
		return start;
	}

	public void setStart(Integer start)
	{
		this.start = start;
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public Integer getPageSize()
	{
		return pageSize;
	}

	public void setPageSize(Integer pageSize)
	{
		this.pageSize = pageSize;
	}

	public Integer getTotalItems()
	{
		return totalItems;
	}

	public void setTotalItems(Integer totalItems)
	{
		this.totalItems = totalItems;
	}

	
}
