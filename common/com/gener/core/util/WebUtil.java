/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>
 * 标 题：
 * </p>
 * <p>
 * 文 件 名：com.gener.core.util.WebUtil.java
 * </p>
 * <p>
 * 部 门：研发一部
 * <p>
 * 版 本： 1.0
 * </p>
 * <p>
 * Compiler: JDK1.6.0_21
 * </p>
 * <p>
 * 创 建 者：Jimmy
 * </p>
 * <p>
 * 创建时间：2014-1-9下午2:21:59
 * </p>
 * <p>
 * 修 改 者：
 * </p>
 * <p>
 * 修改时间：
 * </p>
 */
/**
 * 
 */
package com.gener.core.util;

/**
 * <p>
 * 描 述：TODO
 * </p>
 * <p>
 * 创 建 人：Jimmy
 * </p>
 * <p>
 * 创建时间：2014-1-9下午2:21:59
 * </p>
 */

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;

/**
 * 网页常用工具类
 * 
 * @author Sunnykid
 * 
 *         Create Date:2012-5-22
 */
public class WebUtil
{
    private static final Log logger = LogFactory.getLog(WebUtil.class);
    /**
     * 输出页面内容
     * 
     * @param object
     *            需要输出的对象
     */
    public static void servletOutPrint(Object object)
    {
        HttpServletResponse response = ServletActionContext.getResponse();
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out = null;
        try
        {
            out = response.getWriter();
        }
        catch (IOException e)
        {
            logger.error(StringUtil.getTrace(e));
        }
        out.print(new Gson().toJson(object));
        out.flush();
        out.close();
    }
}
