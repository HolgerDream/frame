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
 * 文 件 名：com.gener.core.servlet.WebServiceServlet.java
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
 * 创建时间：2014-5-14上午9:09:30
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
package com.gener.core.servlet;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.generalModules.service.GeneralWebservice;

/**
 * <p>
 * 描 述：TODO
 * </p>
 * <p>
 * 创 建 人：Jimmy
 * </p>
 * <p>
 * 创建时间：2014-5-14上午9:09:30
 * </p>
 */

public class WebServiceServlet extends HttpServlet
{

    /**
     * serialVersionUID
     */
    private static final long   serialVersionUID = 1L;
    private static final String BEAN_NAME        = "webService";
    
    
    private GeneralWebservice webService;

    /**
     * The doGet method of the servlet. <br />
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // sth to do
    }

    /**
     * The doPost method of the servlet. <br />
     * 
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // sth to do
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        InputStream inputStream = request.getInputStream();
        ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
        int i = 0;
        byte[] b = new byte[1024];
        while ((i = inputStream.read(b)) != -1)
        {
            byteOutput.write(b, 0, i);
        }
        String requestBody = new String(byteOutput.toByteArray(), "utf-8");
        String responseBody = webService.accessPost(requestBody);
        response.getWriter().print(responseBody);
        response.getWriter().close();
    }

    /**
     * Initialization of the servlet. <br />
     * 
     * @throws ServletException
     *             if an error occurs
     */
    public void init(ServletConfig servletConfig) throws ServletException
    {
        ServletContext servletContext = servletConfig.getServletContext();
        WebApplicationContext webApplicationContext = WebApplicationContextUtils
                .getWebApplicationContext(servletContext);
        this.setWebService((GeneralWebservice) webApplicationContext.getBean(BEAN_NAME));
    }

    public void destroy()
    {
        super.destroy();
    }

    /**
     * 获取webService
     */
    public GeneralWebservice getWebService()
    {
        return webService;
    }

    /**
     * 设置webService
     */
    public void setWebService(GeneralWebservice webService)
    {
        this.webService = webService;
    }

}
