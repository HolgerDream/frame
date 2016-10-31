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
 * 文 件 名：com.gener.core.util.ConvertUtil.java
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
 * 创建时间：2014-11-3下午2:14:54
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * <p>
 * 描 述：TODO
 * </p>
 * <p>
 * 创 建 人：Jimmy
 * </p>
 * <p>
 * 创建时间：2014-11-3下午2:14:54
 * </p>
 */

public class FileConvertUtil
{

    private static final Logger      logger      = Logger.getLogger(FileConvertUtil.class);
    private static final Set<String> OFFICE_TYPE = new HashSet<String>();
    private static final String      VISIO_TYPE  = "vsd";
    private static String            webPath     = "";
    private static String            webFileName = "";

    static
    {
        OFFICE_TYPE.add("doc");
        OFFICE_TYPE.add("docx");
        OFFICE_TYPE.add("xls");
        OFFICE_TYPE.add("xlsx");
        OFFICE_TYPE.add("ppt");
        OFFICE_TYPE.add("pptx");
        OFFICE_TYPE.add("txt");
    }

    public static boolean canReadOnLine(String filePath)
    {
        boolean flag = false;
        int index = filePath.lastIndexOf(".");
        String extName = filePath.substring(index + 1);
        flag = isPdfFile(extName) || isOfficeFile(extName) || isVisioFile(extName);
        return flag;
    }

    public static boolean convertFile2Swf(String fileFullName)
    {
        boolean flag = false;
        if (null != fileFullName)
        {
            String tmpFileName = null;
            int index = fileFullName.lastIndexOf(".");
            String fileName = fileFullName.substring(0, index);
            String extName = fileFullName.substring(index + 1);
            if (isOfficeFile(extName))
            {
                tmpFileName = fileName + ".pdf";
                boolean opFlag = convertOffice2Pdf(fileFullName, tmpFileName,
                        fileFullName.substring(index + 1, fileFullName.length()));
                if (opFlag)
                {
                    try
                    {
                        flag = convertPdf2Swf(tmpFileName, fileName + ".swf");
                    }
                    catch (Exception e)
                    {
                        logger.error("将Office文档的PDF文档转换成SWF文件失败!", e);
                    }
                }
            }
            else if (isPdfFile(extName))
            {
                flag = convertPdf2Swf(fileFullName, fileName + ".swf");
            }
            else if (isVisioFile(extName))
            {
                tmpFileName = fileName + ".pdf";
                boolean opFlag = convertVisio2Pdf(fileFullName);
                if (opFlag)
                {
                    try
                    {
                        flag = convertPdf2Swf(tmpFileName, fileName + ".swf");
                    }
                    catch (Exception e)
                    {
                        logger.error("将VISIO创建的PDF文档转换成SWF文件失败!", e);
                    }
                }
            }
        }
        return flag;
    }

    /**
     * 将Office文档转换成PDF文档（新）。
     * 
     * @param inFileName
     * @param outFileName
     */
    private static boolean convertOffice2Pdf(String inFileName, String outFileName, String extName)
    {
        boolean flag = false;
        if ("doc".equals(extName.toLowerCase()) || "docx".equals(extName.toLowerCase()))
        {
            // flag = FileWordToPdf.wordToPDF(inFileName, outFileName, 17);
        }
        else
        {
            return convertOffice2Pdf(inFileName, outFileName);
        }
        return flag;
    }

    /**
     * 将Office文档转换成PDF文档。
     * 
     * @param inFileName
     * @param outFileName
     */
    private static boolean convertOffice2Pdf(String inFileName, String outFileName)
    {
        boolean flag = false;
        OpenOfficeConnection connection = null;
        try
        {
            File inFile = new File(inFileName);
            File outFile = new File(outFileName);
            connection = new SocketOpenOfficeConnection(8100);
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inFile, outFile);
            flag = true;
        }
        catch (Exception e)
        {
            logger.error("将Office文档转换为PDF文档失败!", e);
        }
        finally
        {
            if (null != connection)
            {
                connection.disconnect();
            }
        }
        return flag;
    }

    /**
     * 将VISIO文档转换成PDF文档。 分为两步： 1、将VSD各页转换成emf图片文件 2、将所有的emf转换成PDF
     * 
     * @param inFileName
     * @param outFileName
     */
    private static boolean convertVisio2Pdf(String inFileName)
    {
        boolean flag = false;
        try
        {
            ComThread.InitSTA();
            java.util.ArrayList<String> tmpFileName = null;
            int index = inFileName.lastIndexOf(".");
            String tmpDescPath = inFileName.substring(0, index);
            ActiveXComponent vsd = null;
            StringBuffer command = null;
            vsd = new ActiveXComponent("Visio.Application");
            vsd.setProperty("Visible", new Variant(false));
            Dispatch docs = vsd.getProperty("Documents").toDispatch();
            Dispatch doc = Dispatch.call(docs, "Open", inFileName).toDispatch();
            Dispatch pages = Dispatch.get(doc, "Pages").toDispatch();
            if (null != pages)
            {
                tmpFileName = new java.util.ArrayList<String>();
                command = new StringBuffer("im_convert ");
                int count = Integer.parseInt(Dispatch.get(pages, "Count").toString());
                for (int i = 1; i <= count; i++)
                {
                    Dispatch page = Dispatch.call(pages, "item", new Object[]
                    { new Integer(i) }).toDispatch();
                    String name = Dispatch.get(page, "Name").toString();
                    String emfName = tmpDescPath + "_" + name + ".emf";
                    tmpFileName.add(emfName);
                    Dispatch.call(page, "Export", new Object[]
                    { emfName });
                    command.append(emfName + " ");
                }
            }
            Dispatch.put(doc, "Saved", new Variant(true));
            vsd.invoke("Quit", new Variant[] {});
            command.append(tmpDescPath + ".pdf");
            Process pro = Runtime.getRuntime().exec(command.toString());
            StreamGobbler errGob = new StreamGobbler(pro.getErrorStream(), "ERROR");
            StreamGobbler outGob = new StreamGobbler(pro.getInputStream(), "OUTINFO");
            errGob.start();
            outGob.start();
            if (0 == pro.waitFor())
            {
                flag = true;
            }
            // 删除临时的图片文件
            File file = null;
            if (null != tmpFileName)
            {
                for (String name : tmpFileName)
                {
                    file = new File(name);
                    if (null != file && file.exists())
                    {
                        // file.delete();
                    }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("将VISIO文档转换为PDF文档失败!", e);
        }
        finally
        {
            ComThread.Release();
        }
        return flag;
    }

    /**
     * 调用activex组件将visio文件导出为paf文件 描述：TODO
     * 
     * @param inputFile
     * @param pdfFile
     * @throws Exception 
     */
    public static void convertVisio2Pdf(String inputFile, String pdfFile) throws Exception
    {
        // 打开word应用程序
        try
        {
            ActiveXComponent app = new ActiveXComponent("FileToPdfConverter.VisioToPdfConverter");
            // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
            Dispatch.call(app, "Exec", inputFile, pdfFile);
        }
        catch (Exception e)
        {
            throw new Exception("visio文件转换失败！");
        }
        /*
         * // 调用Document对象的SaveAs方法，将文档保存为pdf格式 Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF //word保存为pdf格式宏，值为17
         * ); Dispatch.call(doc, "SaveAs", pdfFile // word保存为pdf格式宏，值为17 ); // 关闭文档 Dispatch.call(doc, "Close", false);
         * // 关闭word应用程序 app.invoke("Quit", 0);
         */

    }

    /**
     * 将PDF转换为SWF文档。
     * 
     * @param inFileName
     * @param outFileName
     */
    private static boolean convertPdf2Swf(String inFileName, String outFileName)
    {
        boolean flag = false;
        try
        {
            if (hasFileDir(outFileName))
            {
                StringBuffer command = new StringBuffer("pdf2swf -G -f -T 9 -t -s storeallcharacters ")
                        .append(inFileName).append(" -o ").append(webPath + webFileName);
                // 执行命令是在单独的线程中完成。不等完成就直接返回。
                Process pro = Runtime.getRuntime().exec(command.toString());
                StreamGobbler errGob = new StreamGobbler(pro.getErrorStream(), "ERROR");
                StreamGobbler outGob = new StreamGobbler(pro.getInputStream(), "OUTINFO");
                errGob.start();
                outGob.start();
                if (0 == pro.waitFor())
                {
                    flag = true;
                    // if(hasFileDir(outFileName)){
                    // System.out.println("-------webPath:"+webPath + webFileName);
                    // String str = "copy "+ outFileName + " "+webPath + webFileName;
                    // System.out.println("-------str:"+str);
                    // //forChannel(outFileName,webPath + webFileName);
                    // pro = Runtime.getRuntime().exec(str);
                    // }
                }
            }
        }
        catch (Exception e)
        {
            logger.error("将PDF文档转换为SWF文档失败或者拷贝文件失败!", e);
        }
        return flag;
    }

    /**
     * 根据文件判断是否为Office文档，doc、docx、xls、xlsx、ppt、pptx
     * 
     * @param fileName
     * @return
     */
    private static boolean isOfficeFile(String extName)
    {
        if (null != extName)
        {
            if (OFFICE_TYPE.contains(extName.toLowerCase()))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据文件扩展名判断是否为PDF文件。
     * 
     * @param fileName
     * @return
     */
    private static boolean isPdfFile(String extName)
    {
        if ("pdf".equalsIgnoreCase(extName))
        {
            return true;
        }
        return false;
    }

    /**
     * 根据文件扩展名判断是否为VISIO文件。
     * 
     * @param fileName
     * @return
     */
    private static boolean isVisioFile(String extName)
    {
        if (VISIO_TYPE.equalsIgnoreCase(extName))
        {
            return true;
        }
        return false;
    }

    public static boolean hasFileDir(String fileFullName)
    {
        boolean flag = false;
        if (fileFullName == null || "".equals(fileFullName))
        {
            return flag;
        }
        int totalen = fileFullName.length();
        int len = fileFullName.lastIndexOf("\\");
        String cfile = fileFullName.substring(0, len);
        webFileName = fileFullName.substring(len + 1);
        int tlen = cfile.lastIndexOf("\\");
        String filedirName = fileFullName.substring(tlen + 1, len);
        // String urls = ClassLoader.getSystemResource("\\").getPath();
        // int wlen = urls.indexOf("WEB-INF");
        // String absolutePath = urls.substring(1,wlen);
        String userdir = System.getProperty("user.dir");
        String tomPath = userdir.replace("bin", "");
        String absolutePath = tomPath + "webapps\\smis\\tempdocument\\attachment\\";
        // String absolutePath = "D:\\work\\smis\\WebRoot\\tempdocument\\attachment\\";
        webPath = absolutePath + filedirName + "\\";
        // System.out.println("-----------:"+webPath);
        File file = new File(webPath);
        if (file.exists())
        {
            flag = true;
        }
        else
        {
            file.mkdirs();
            flag = true;
        }
        return flag;
    }

    public static boolean forChannel(String s1, String s2) throws Exception
    {
        boolean flag = false;
        int length = 2097152;
        File f1 = new File(s1);
        File f2 = new File(s2);
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        FileChannel inC = in.getChannel();
        FileChannel outC = out.getChannel();
        ByteBuffer b = null;
        while (true)
        {
            if (inC.position() == inC.size())
            {
                inC.close();
                outC.close();
                flag = true;
            }
            b = ByteBuffer.allocateDirect(length);
            b.flip();
            outC.write(b);
            outC.force(false);
            outC.close();
        }

    }

    /**
     * @author aaron_ye
     * 
     */
    public static class StreamGobbler extends Thread
    {

        private InputStream  is   = null;
        private OutputStream os   = null;
        private String       type = null;

        public StreamGobbler(InputStream _is, String _type, OutputStream _os)
        {
            this.is = _is;
            this.os = _os;
            this.type = _type;
        }

        public StreamGobbler(InputStream _is, String _type)
        {
            this.is = _is;
            this.type = _type;
        }

        public void run()
        {
            try
            {
                // System.out.println("begin: "+this.type);
                PrintWriter pw = null;
                if (os != null)
                {
                    pw = new PrintWriter(os);
                }
                else
                {
                    pw = new PrintWriter(System.out);
                }
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null)
                {
                    if (pw != null)
                    {
                        pw.println(line);
                    }
                }
                if (pw != null)
                {
                    pw.flush();
                }
                // System.out.println("end: "+this.type);
            }
            catch (IOException ioe)
            {
                ioe.printStackTrace();
            }
        }

        /**
         * @return the is
         */
        public InputStream getIs()
        {
            return is;
        }

        /**
         * @param is
         *            the is to set
         */
        public void setIs(InputStream is)
        {
            this.is = is;
        }

        /**
         * @return the os
         */
        public OutputStream getOs()
        {
            return os;
        }

        /**
         * @param os
         *            the os to set
         */
        public void setOs(OutputStream os)
        {
            this.os = os;
        }

        /**
         * @return the type
         */
        public String getType()
        {
            return type;
        }

        /**
         * @param type
         *            the type to set
         */
        public void setType(String type)
        {
            this.type = type;
        }

    }

}
