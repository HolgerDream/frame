/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.FileUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：YIXW</p>
 * <p>创建时间：2013-12-13上午11:41:17</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>描        述：文件工具类</p>
 * <p>创建时间：2013-12-13下午12:22:22</p>
 */
public class FileUtil extends AbstractUtil
{
    private static final Log logger = LogFactory.getLog(FileUtil.class);
    
	 /****
	  *  
	  * 描述：TODO文件下载
	  * @param filepath 文件在服务器的绝对路径
	  * @param request 
	  * @param response
	  */
    public static void download(String filepath,HttpServletRequest request,HttpServletResponse response){  
        File file=new File(filepath);  
        if(file.exists()&&!"".equals(filepath)){  
            try {
                DataOutputStream dos=new DataOutputStream(response.getOutputStream());  
                DataInputStream dis=new DataInputStream(new FileInputStream(file.getAbsolutePath()));  
                byte[] data=new byte[2048];  
                while((dis.read(data))!=-1){  
                    dos.write(data);  
                    dos.flush();  
                }  
                dis.close();  
                dos.close();  
            } catch (IOException e) {  
                logger.error(StringUtil.getTrace(e));  
            }  
        }  
    }  
    
    /**
     * 文档下载
     * @param  相应流对象
     * @param  文件路径
     * @param  文件名称
     * */
    public static void download(String filePath,String newFileName,HttpServletResponse response){
		response.setContentType("application/x-msdownload; charset=UTF-8");
		try{
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(newFileName, "UTF-8"));
			OutputStream os = response.getOutputStream();
			BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[4096];
			int i = -1;
			while ((i = reader.read(buffer)) != -1) {
				os.write(buffer, 0, i);
			}
			os.flush();
			os.close();
			reader.close();
		}catch(Exception e){
			logger.error(StringUtil.getTrace(e));
		}
    }
    
    /**
     * 文档下载
     * @param  相应流对象
     * @param  文件路径
     * @param  文件名称
     * */
    public static void download(HttpServletResponse response,String filePath,String fileName){
		response.setContentType("application/x-msdownload; charset=UTF-8");
		try{
			response.setHeader("Content-Disposition", "attachment; filename=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
			OutputStream os = response.getOutputStream();
			BufferedInputStream reader = null;
			File file = new File(filePath);
			if(file.isDirectory()){
			    filePath = filePath + "/" + fileName;
			}
			reader = new BufferedInputStream(new FileInputStream(filePath));
			byte[] buffer = new byte[4096];
			int i = -1;
			while ((i = reader.read(buffer)) != -1) {
				os.write(buffer, 0, i);
			}
			os.flush();
			os.close();
			reader.close();
		}catch(Exception e){
			logger.error(StringUtil.getTrace(e));
		}
    }
    
    /**
     * 描述：创建文件夹，若有该文件夹则返回File，若没有则先创建再返回
     * @param path 文件夹绝对路径
     * @return File
     */
    public static File createDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        } 
        return dir;
    }
    
    /**
     * 描述：检查文件是否存在
     * @param path 文件绝对路径
     * @return boolean
     */
    public static boolean isFileExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        } 
        return true;
    }
    
    /**
     * 描述：目录或文件重命名
     * @param path 目录或文件的路径        
     * @param oldname 原来的目录名或文件名        
     * @param newname 新的目录名或文件名      
     */
    public static void renameFile(String path, String oldname, String newname) {
        // 新的文件名或目录名不同时,才有必要进行重命名
        if (!oldname.equals(newname)) {
            File oldfile = new File(path + oldname);
            File newfile = new File(path + newname);
            if (!oldfile.exists()) {
                // 目录补好,文件假数据数据库删除
                createDir(path);
            } else {
                // 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
                if (newfile.exists())
                    logger.error(newname + "已经存在！");
                else {
                    oldfile.renameTo(newfile);
                }
            }
        }
    }
    
    /**
     * 描述：通过文件路径删除文件
     * @param path 文件路径
     * @return
     */
    public static void deleteFile(String path) {
        try {
            //如果存在并且是文件，那么就删除它 否则输出文件不存在， 返回false
            File file = new File(path);
            if (file.exists() && file.isFile()) {
                file.delete();
            } else {
                logger.error("你要删除的文件不存在！");
            }
        } catch (Exception e) {
            logger.error(StringUtil.getTrace(e));
        }
    }
}
