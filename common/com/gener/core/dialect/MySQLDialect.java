/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br>
 * 版权所有(C)2012-2014 上海杰之能信息科技有限公司<br>
 * 公司名称：上海杰之能信息科技有限公司<br>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br>
 * 网址:http://www.gener-tech.com
 * <p>
 * Compiler: JDK1.6.0_21
 * <p>
 * 版本: 1.0版 文件名：com.gener.core.dialect.MySQLDialect.java
 * <p>
 * 作者: 孙立峰
 * <p>
 * 创建时间: 2013-4-11下午5:57:17
 * <p>
 * 负责人: 孙立峰
 * <p>
 * 部门: 研发部
 * <p>
 * <p>
 * 修改者：孙立峰
 * <p>
 * 修改时间：2013-4-11下午5:57:17
 * <p>
 */
package com.gener.core.dialect;

import ibator.dialect.Dialect;

public class MySQLDialect extends Dialect
{

	public String getLimitString(String sql, int offset, int limit)
	{
		return getLimitString(sql, offset, String.valueOf(offset), limit, String.valueOf(limit));
	}

	@Override
	public String getLimitString(String sql, int offset, String offsetPlaceholder, int limit, String limitPlaceholder)
	{
		if (offset > 0)
		{
			return sql + " limit " + offsetPlaceholder + "," + limitPlaceholder;
		}
		else
		{
			return sql + " limit " + limitPlaceholder;
		}
	}

	@Override
	public boolean supportsLimit()
	{
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supportsLimitOffset()
	{
		// TODO Auto-generated method stub
		return true;
	}

}
