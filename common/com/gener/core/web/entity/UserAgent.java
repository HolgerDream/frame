/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.web.entity.UserAgent.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：易昕炜</p>
 * <p>创建时间：2014-1-17下午8:28:12</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.web.entity;

/**
 * <p>描        述： 根据 user agent string 来判断出客户端的浏览器以及平台等信息  </p>
 * <p>创  建  人：易昕炜</p>
 * <p>创建时间：2014-1-17下午8:28:26</p>
 */
public class UserAgent
{
    
    
    private String browserType;//浏览器类型  
    private String browserVersion;//浏览器版本  
    private String platformType;//平台类型  
    private String platformSeries;//平台系列  
    private String platformVersion;//平台版本
    
    public UserAgent(){}  
    
    public UserAgent(String browserType, String browserVersion,  
            String platformType, String platformSeries, String platformVersion){  
        this.browserType = browserType;  
        this.browserVersion = browserVersion;  
        this.platformType = platformType;  
        this.platformSeries = platformSeries;  
        this.platformVersion = platformVersion;  
    }  
    
    /**
     * 获取 浏览器类型
     */
    public String getBrowserType()
    {
        return browserType;
    }
    /**
     * 设置 浏览器类型
     */
    public void setBrowserType(String browserType)
    {
        this.browserType = browserType;
    }
    /**
     * 获取 浏览器版本  
     */
    public String getBrowserVersion()
    {
        return browserVersion;
    }
    /**
     * 设置 浏览器版本  
     */
    public void setBrowserVersion(String browserVersion)
    {
        this.browserVersion = browserVersion;
    }
    /**
     * 获取 平台类型    
     */
    public String getPlatformType()
    {
        return platformType;
    }
    /**
     * 设置 平台类型    
     */
    public void setPlatformType(String platformType)
    {
        this.platformType = platformType;
    }
    /**
     * 获取 平台系列    
     */
    public String getPlatformSeries()
    {
        return platformSeries;
    }
    /**
     * 设置 平台系列    
     */
    public void setPlatformSeries(String platformSeries)
    {
        this.platformSeries = platformSeries;
    }
    /**
     * 获取 平台版本    
     */
    public String getPlatformVersion()
    {
        return platformVersion;
    }
    /**
     * 设置 平台版本    
     */
    public void setPlatformVersion(String platformVersion)
    {
        this.platformVersion = platformVersion;
    }
}
