/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.Tools.java</p>
 * <p>部        门：产品研发部
 * <p>版        本： 1.0</p>
 * <p>创  建  者：Xhuanlee</p>
 * <p>创建时间：2016年9月14日上午9:47:58</p>
 * <p>修  改  者：Xhuanlee</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.util.UUID;

public class Tools {
    
    public static final String DBVersion = "ORACLE";
    //public static final String DBVersion = "MYSQL";
    
    public static String getTimeByDB(){
	if(DBVersion.equals("ORACLE")){
		return "sysdate";
	}else if(DBVersion.equals("SQLSERVER")){
		return "GETDATE()";
	}else if(DBVersion.equals("MYSQL")){
		return "SYSDATE()";
	}else{
	    return "sysdate";
	}
    }
    
    public static String getUUID() {
	return UUID.randomUUID().toString().replace("-", "");
    }
    
}
