/**
 * Copyright(C) 2015-2020 Shang hai haocang-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2015-2020 <br/>
 * 公司名称：上海昊沧系统控制技术有限责任公司<br/>
 * 公司地址：中国上海市徐汇区云锦路500号绿地汇中心A座20楼2001<br/>
 * 网址：http://www.haocang.com/<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.TransactionUtil.java</p>
 * <p>部        门：产品研发部
 * <p>版        本： 1.0</p>
 * <p>创  建  者：Administrator</p>
 * <p>创建时间：2016年10月9日上午10:48:01</p>
 * <p>修  改  者：Administrator</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class TransactionUtil extends AbstractUtil {
    /**
     * @描述：事务回滚
     */
    public static void rollback() {
	TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }

}
