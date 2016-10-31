/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2012-2014 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.6.0_21
 * <p>
 * 版本: 1.0版 文件名：com.gener.core.web.paging.QueryFilter.java
 * <p>
 * 作者: 孙立峰
 * <p>
 * 创建时间: 2013-4-11下午3:24:44
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：孙立峰
 * <p>
 * 修改时间：2013-4-11下午3:24:44
 * <p>
 */
package com.gener.core.web.paging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class QueryFilter
{
	public final static Log		logger		= LogFactory.getLog(QueryFilter.class);

	

	/**
	 * 查询条件的Map
	 */
	private Map<String, Object>	param		= new HashMap<String, Object>();

	private PagingBean			pagingBean	= null;
	private PageNavigator			webPage=null;
							// 排序方式asc/desc
	public QueryFilter(PageNavigator	webPage)
	{
		// 取得分页的信息
		Integer page = 1;
		Integer rows = PagingBean.DEFAULT_PAGE_SIZE;
		page = webPage.getCurrentPage();
		rows = webPage.getPageSize();
		this.pagingBean = new PagingBean(page, rows);
		this.webPage=webPage;
	}
	
	public void add(String name,Object value)
	{
		param.put(name, value);
	}
	public void clear()
	{
		param.clear();
	}
	public void setWebPageList(List<?>list)
	{
		this.getWebPage().setList(list);
		this.getWebPage().setTotalSize(this.getPagingBean().getTotalItems());
	}

	public Map<String, Object> getParam()
	{
		return param;
	}

	public void setParam(Map<String, Object> param)
	{
		this.param = param;
	}

	public PagingBean getPagingBean()
	{
		return pagingBean;
	}
	
	public void setPagingBean(PagingBean pagingBean)
	{
		this.pagingBean = pagingBean;
	}
	public PageNavigator getWebPage()
	{
		return webPage;
	}
	public void setWebPage(PageNavigator webPage)
	{
		this.webPage = webPage;
	}

}
