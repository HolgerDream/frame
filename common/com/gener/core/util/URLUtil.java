/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.URLUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：易昕炜</p>
 * <p>创建时间：2013-12-26下午8:04:46</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class URLUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(URLUtil.class);
    
    private static final String BUNDLE_NAME = "conf/url";
    
    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
    
    /**
     * 描述：取得url.properties中配置的URL
     * @param key
     * @return
     */
    public static String getUrlString(String key)
    {
        try
        {
            return RESOURCE_BUNDLE.getString(key);
        }
        catch (MissingResourceException e)
        {
            return '!' + key + '!';
        }
    }
}
