/**
 * Copyright(C) 2012-2014 Shang hai Gener-tech Co., Ltd. All Rights Reserved.<br/>
 * 版权所有(C)2012-2014 <br/>
 * 公司名称：上海杰之能信息科技有限公司<br/>
 * 公司地址：中国上海市闸北区广中西路777弄55号启迪大厦11楼<br/>
 * 网址：http://www.gener-tech.com<br/>
 * <p>标        题：</p>
 * <p>文  件  名：com.gener.core.util.ThumbnailsUtil.java</p>
 * <p>部        门：研发一部
 * <p>版        本： 1.0</p>
 * <p>Compiler: JDK1.6.0_21</p>
 * <p>创  建  者：黄亮</p>
 * <p>创建时间：2014年9月5日上午9:26:29</p>
 * <p>修  改  者：</p>
 * <p>修改时间：</p>
 */
package com.gener.core.util;

/**
 * 
 * <p>描        述：TODO</p>
 * <p>创  建  人：黄亮</p>
 * <p>创建时间：2014年9月5日上午9:26:33</p>
 */
public class ThumbnailsUtil
{
    private static String split_character = "-_-";
    
    public static String publish(){

        String[] namez = new String[6];
        namez[0]=".jpg".toLowerCase();
        namez[1]=".png".toLowerCase();
        namez[2]=".gif".toLowerCase();
        namez[3]=".jpeg".toLowerCase();
        
        String _namez = "";
        for(String str:namez){
            if(str == null)continue;
            _namez += str ;
            _namez += split_character;
        }
        
        _namez = _namez.substring(0,_namez.lastIndexOf(split_character));
        return _namez;
    
    }
    
    static final Long max_length = 50*1024L;//byte 
    
    private static boolean isPass(String fileName){
        String imgType = publish();
        String temp[] = imgType.split(split_character);
        
        if(fileName.indexOf(".")==-1)
            return false;
        String fix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
        for(String str : temp){
            if(str.equals(fix.toLowerCase())){
                return true;
            }
        }
        return false;
        
    }
    
    /**
     * 
     * 描述：TODO
     * @param oldFilePath
     * @param newFilePath
     */
    public static void thumbnails(String oldFilePath, String newFilePath)
    {
        if(newFilePath.indexOf("/")!=-1 ){
            String temp = newFilePath.substring(0,newFilePath.lastIndexOf("/"));
            java.io.File tempf = new java.io.File(temp);
            if(!tempf.exists()){
                tempf.mkdirs();
            }
        }
        
        if(!isPass(oldFilePath))return;
        
        java.io.File imgFile = new java.io.File(oldFilePath);
        try
        {
            //每次缩小10%
            net.coobird.thumbnailator.Thumbnails.of(imgFile).scale(0.90f).toFile(newFilePath);

            java.io.File tempFile = new java.io.File(newFilePath);
            
            if(tempFile.length()>max_length){
                thumbnails(newFilePath,newFilePath);
            }
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args)
    {
//        ThumbnailsUtil.thumbnails("C:/file/IMG_1101.JPG","C:/file/thumbnails/IMG_1101.JPG");
//        smb://10.160.11.55/
//        smb://10.160.2.147/
//        System.out.println("thumbnails".toUpperCase());
//        String url="smb://192.168.2.188/testIndex/";
        String uri = "smb://Administrator:gener1234!@#$@192.168.1.104/ttt/nihao.txt";
        
        
        uri = "//WIN-O8UETIGPLPT/fff/abc.jpg";
        
        java.io.File file = new java.io.File(uri);
        System.out.println(file.exists());
    }
}
