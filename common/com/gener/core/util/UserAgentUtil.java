/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.ClientUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：易昕炜</p>
 * <p>创建时间：2014-1-17下午7:21:35</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.gener.core.web.entity.UserAgent;

/**
 * <p>描        述：描述：根据 user agent string 判断用户的平台、浏览器</p>
 * <p>创  建  人：易昕炜</p>
 * <p>创建时间：2014-1-17下午7:22:33</p>
 */
public class UserAgentUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(UserAgentUtil.class);

    /** 
     * 用途：根据客户端 User Agent Strings 判断其浏览器、操作平台 
     * if 判断的先后次序： 
     * 根据设备的用户使用量降序排列，这样对于大多数用户来说可以少判断几次即可拿到结果： 
     *  >>操作系统:Windows > 苹果 > 安卓 > Linux > ... 
     * @param userAgentStr 
     * @return 
     */  
    public static UserAgent getUserAgent(String userAgent) {  
        if (ObjectUtil.isEmpty(userAgent)) {  
            return null;  
        }   
          
        if (userAgent.contains("Windows")) 
        {//主流应用靠前  
            /** 
             * ****************** 
             * 台式机 Windows 系列 
             * ****************** 
             * Windows NT 6.2   -   Windows 8 
             * Windows NT 6.1   -   Windows 7 
             * Windows NT 6.0   -   Windows Vista 
             * Windows NT 5.2   -   Windows Server 2003; Windows XP x64 Edition 
             * Windows NT 5.1   -   Windows XP 
             * Windows NT 5.01  -   Windows 2000, Service Pack 1 (SP1) 
             * Windows NT 5.0   -   Windows 2000 
             * Windows NT 4.0   -   Microsoft Windows NT 4.0 
             * Windows 98; Win 9x 4.90  -   Windows Millennium Edition (Windows Me) 
             * Windows 98   -   Windows 98 
             * Windows 95   -   Windows 95 
             * Windows CE   -   Windows CE 
             * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx 
             */  
            if (userAgent.contains("Windows NT 6.2")) {//Windows 8  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "8" , null);//判断浏览器  
            }else if (userAgent.contains("Windows NT 6.1")) {//Windows 7  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "7" , null);  
            }else if (userAgent.contains("Windows NT 6.0")) {//Windows Vista  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "Vista" , null);  
            }else if (userAgent.contains("Windows NT 5.2")) {//Windows XP x64 Edition  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "XP" , "x64 Edition");  
            }else if (userAgent.contains("Windows NT 5.1")) {//Windows XP  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "XP" , null);  
            }else if (userAgent.contains("Windows NT 5.01")) {//Windows 2000, Service Pack 1 (SP1)  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "2000" , "SP1");  
            }else if (userAgent.contains("Windows NT 5.0")) {//Windows 2000  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "2000" , null);  
            }else if (userAgent.contains("Windows NT 4.0")) {//Microsoft Windows NT 4.0  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "NT 4.0" , null);  
            }else if (userAgent.contains("Windows 98; Win 9x 4.90")) {//Windows Millennium Edition (Windows Me)  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "ME" , null);  
            }else if (userAgent.contains("Windows 98")) {//Windows 98  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "98" , null);  
            }else if (userAgent.contains("Windows 95")) {//Windows 95  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "95" , null);  
            }else if (userAgent.contains("Windows CE")) {//Windows CE  
                return judgeBrowser(userAgent, PlatformTypeEnum.WINDOWS, "CE" , null);  
            }   
        }else if(userAgent.contains("Android")){
            /** 
             * ******** 
             * Android系列 
             * ******** 
             * Android 4.0.3 Mozilla/5.0 (Linux; U; Android 4.0.3; ko-kr; LG-L160L Build/IML74K) AppleWebkit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30
             * Android 2.3 Mozilla/5.0 (Linux; U; Android 2.3; en-us) AppleWebKit/999+ (KHTML, like Gecko) Safari/999.9
             * Android 2.3.3 Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; HTC_IncredibleS_S710e Build/GRJ90) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1
             * 判断依据:http://www.useragentstring.com/pages/Android%20Webkit%20Browser/
             */  
            if (userAgent.contains("Android 4.4")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "4.4");//判断浏览器  
            }else if (userAgent.contains("Android 4.3")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "4.3");
            }else if (userAgent.contains("Android 4.2")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "4.3");
            }else if (userAgent.contains("Android 4")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "4");
            }else if (userAgent.contains("Android 2.3")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "2.3");
            }else if (userAgent.contains("Android 2")) {  
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, "2");
            }else {
                return judgeBrowser(userAgent, PlatformTypeEnum.ANDROID, null, null);
            }
        }else if (userAgent.contains("Mac OS X")) {  
            /** 
             * ******** 
             * 苹果系列 
             * ******** 
             * iPod -          Mozilla/5.0 (iPod; U; CPU iPhone OS 4_3_1 like Mac OS X; zh-cn) AppleWebKit/533.17.9 (KHTML, like Gecko) Version/5.0.2 Mobile/8G4 Safari/6533.18.5 
             * iPad -          Mozilla/5.0 (iPad; U; CPU OS 3_2 like Mac OS X; en-us) AppleWebKit/531.21.10 (KHTML, like Gecko) Version/4.0.4 Mobile/7B334b Safari/531.21.10 
             * iPad2    -     Mozilla/5.0 (iPad; CPU OS 5_1 like Mac OS X; en-us) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9B176 Safari/7534.48.3 
             * iPhone 4 -   Mozilla/5.0 (iPhone; U; CPU iPhone OS 4_0 like Mac OS X; en-us) AppleWebKit/532.9 (KHTML, like Gecko) Version/4.0.5 Mobile/8A293 Safari/6531.22.7 
             * iPhone 5 -   Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3 
             * 判断依据:http://www.useragentstring.com/pages/Safari/ 
             * 参考:http://stackoverflow.com/questions/7825873/what-is-the-ios-5-0-user-agent-string 
             * 参考:http://stackoverflow.com/questions/3105555/what-is-the-iphone-4-user-agent 
             */  
            return judgeBrowser(userAgent, PlatformTypeEnum.MAC, null, null);//判断浏览器  
        }
        return null;  
    }  
      
    /** 
     * 用途：根据客户端 User Agent Strings 判断其浏览器 
     * if 判断的先后次序： 
     * 根据浏览器的用户使用量降序排列，这样对于大多数用户来说可以少判断几次即可拿到结果： 
     *  >>Browser:Chrome > FF > IE > ... 
     * @param userAgent:user agent 
     * @param platformType:平台 
     * @param platformSeries:系列 
     * @param platformVersion:版本 
     * @return 
     */  
    private static UserAgent judgeBrowser(String userAgent, PlatformTypeEnum platformType, String platformSeries, String platformVersion) {  
        if (userAgent.contains("MSIE")) {  
            /** 
             * ******* 
             * IE 系列 
             * ******* 
             * MSIE 10.0    -   Internet Explorer 10 
             * MSIE 9.0 -   Internet Explorer 9 
             * MSIE 8.0 -   Internet Explorer 8 or IE8 Compatibility View/Browser Mode 
             * MSIE 7.0 -   Windows Internet Explorer 7 or IE7 Compatibility View/Browser Mode 
             * MSIE 6.0 -   Microsoft Internet Explorer 6 
             * 判断依据:http://msdn.microsoft.com/en-us/library/ms537503(v=vs.85).aspx 
             */  
            if (userAgent.contains("MSIE 10.0")) {//Internet Explorer 10  
                return new UserAgent(BrowserTypeEnum.MSIE.toString(), "10", platformType.toString(), platformSeries, platformVersion);  
            }else if (userAgent.contains("MSIE 9.0")) {//Internet Explorer 9  
                return new UserAgent(BrowserTypeEnum.MSIE.toString(), "9", platformType.toString(), platformSeries, platformVersion);  
            }else if (userAgent.contains("MSIE 8.0")) {//Internet Explorer 8  
                return new UserAgent(BrowserTypeEnum.MSIE.toString(), "8", platformType.toString(), platformSeries, platformVersion);  
            }else if (userAgent.contains("MSIE 7.0")) {//Internet Explorer 7  
                return new UserAgent(BrowserTypeEnum.MSIE.toString(), "7", platformType.toString(), platformSeries, platformVersion);  
            }else if (userAgent.contains("MSIE 6.0")) {//Internet Explorer 6  
                return new UserAgent(BrowserTypeEnum.MSIE.toString(), "6", platformType.toString(), platformSeries, platformVersion);  
            }  
        }else if (userAgent.contains("Chrome")) {  
            /** 
             * *********** 
             * Chrome 系列 
             * *********** 
             * Chrome 24.0.1295.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.15 (KHTML, like Gecko) Chrome/24.0.1295.0 Safari/537.15 
             * Chrome 24.0.1292.0   -   Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.14 (KHTML, like Gecko) Chrome/24.0.1292.0 Safari/537.14 
             * Chrome 24.0.1290.1   -   Mozilla/5.0 (Macintosh; Intel Mac OS X 10_8_2) AppleWebKit/537.13 (KHTML, like Gecko) Chrome/24.0.1290.1 Safari/537.13 
             * 判断依据:http://www.useragentstring.com/pages/Chrome/ 
             */  
            String temp = userAgent.substring(userAgent.indexOf("Chrome/") + 7);//拿到User Agent String "Chrome/" 之后的字符串,结果形如"24.0.1295.0 Safari/537.15"或"24.0.1295.0"  
            String chromeVersion = null;  
            if (temp.indexOf(" ") < 0) {//temp形如"24.0.1295.0"  
                chromeVersion = temp;  
            }else {//temp形如"24.0.1295.0 Safari/537.15"  
                chromeVersion = temp.substring(0, temp.indexOf(" "));  
            }  
            return new UserAgent(BrowserTypeEnum.CHROME.toString(), 
                    chromeVersion, 
                    platformType.toString(), 
                    platformSeries, 
                    platformVersion);  
        }else if (userAgent.contains("Firefox")) {  
            /** 
             * ******* 
             * FF 系列 
             * ******* 
             * Firefox 16.0.1   -   Mozilla/5.0 (Windows NT 6.2; Win64; x64; rv:16.0.1) Gecko/20121011 Firefox/16.0.1 
             * Firefox 15.0a2   -   Mozilla/5.0 (Windows NT 6.1; rv:15.0) Gecko/20120716 Firefox/15.0a2 
             * Firefox 15.0.2   -   Mozilla/5.0 (Windows NT 6.2; WOW64; rv:15.0) Gecko/20120910144328 Firefox/15.0.2 
             * 判断依据:http://www.useragentstring.com/pages/Firefox/ 
             */  
            String temp = userAgent.substring(userAgent.indexOf("Firefox/") + 8);//拿到User Agent String "Firefox/" 之后的字符串,结果形如"16.0.1 Gecko/20121011"或"16.0.1"  
            String ffVersion = null;  
            if (temp.indexOf(" ") < 0) {//temp形如"16.0.1"  
                ffVersion = temp;  
            }else {//temp形如"16.0.1 Gecko/20121011"  
                ffVersion = temp.substring(0, temp.indexOf(" "));  
            }  
            return new UserAgent(BrowserTypeEnum.FIREFOX.toString(), 
                    ffVersion, 
                    platformType.toString(), 
                    platformSeries, 
                    platformVersion);  
        }else if (userAgent.contains("Safari")) {  
            /** 
             * ******* 
             * Safari 系列 
             * ******* 
             * Safari 6.0
             *   Mozilla/5.0 (iPad; CPU OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5355d Safari/8536.25
             * Safari 5.1.7
             *   Mozilla/5.0 (Macintosh; Intel Mac OS X 10_6_8) AppleWebKit/537.13+ (KHTML, like Gecko) Version/5.1.7 Safari/534.57.2
             * 判断依据:http://useragentstring.com/pages/Safari/
             */  
            String safariVersion = userAgent.substring(userAgent.indexOf("Version/"));
            
            if (safariVersion.indexOf(" ") > 0) {//temp形如"16.0.1"              
                safariVersion = safariVersion.substring(0, safariVersion.indexOf(" "));  
            }  
            return new UserAgent(BrowserTypeEnum.SAFARI.toString(), 
                    safariVersion, 
                    platformType.toString(), 
                    platformSeries, 
                    platformVersion);  
        }else {
            return new UserAgent(null, null, platformType.toString(), platformSeries, platformVersion);  
        }  
        return null;  
    }  
    
    public static UserAgent getUserAgent(javax.servlet.http.HttpServletRequest request) {  
        if(request !=null){
            String userAgent = request.getHeader("user-agent");
            return getUserAgent(userAgent);
        }
        return null;
    }
    
    /**
     * 浏览器版本枚举
     */
    public static enum BrowserTypeEnum
    {
        MSIE ( "MSIE"),        
        FIREFOX ("Firefox"),
        OPERA("Opera"),
        CHROME("Chrome"),
        SAFARI ("Safari"),
        MAXTHON("Maxthon"),
        QQ("QQBrowser"),
        GREEN("GreenBrowser"),
        SE360("360SE"),
        OTHER("OTHER");
        private String value;
        BrowserTypeEnum(String value){this.value=value;}
        public String toString() {return String.valueOf(this.value);}
    }
    
    /**
     * 操作平台枚举
     */
    public static enum PlatformTypeEnum
    {
        WINDOWS("Windows"),
        MAC("Mac OS X"),
        ANDROID ("Android"),
        OTHER("OTHER");
        private String value;
        PlatformTypeEnum(String value){this.value=value;}
        public String toString() {return String.valueOf(this.value);}
    }
}
