package com.ums.common.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import jdk.nashorn.internal.ir.RuntimeNode.Request;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.context.support.ServletContextResourceLoader;

/**
 * 
 * @author 文俊 (337291) Jun 18, 2014 
 */

public abstract class POICommon extends HttpServlet  {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(POICommon.class.getName());      
    /**
     * 分割符
     */
    public static final String SEPARATOR        = "~";
    /**
     * 临时文件的后缀
     * 
     */
    public static final String TEMP_FILE_SUFFIX = ".tmp";
    
    public static final int defaultColumnWidth = 12;

    /**
     * 临时文件夹
     */
   // public static final String EXCEL_CHILD_DIR  = SysConfigProperties.EXCEL_CHILD_DIR;

    public static final String CELL_FONT_NAME   = "宋体";

    /**
     * 日期格式
     */
    public static final String  DATE_FORMAT = "yyyy-MM-dd";
    /**
     * double 格式
     */
    public  String       DOUBLE_FORMAT    = "0.000";

    /**
     * 临时文件夹path
     */
    public static  String tempdirPath      = null;

    /**
     * 获取excel临时保存目录
     * 
     * @author 文俊 (337291)
     * @date 2012-7-8
     * @return the tempdirPath 临时文件夹path
     */
    public static String getTempdirPath() {
        return tempdirPath;
    }
    
    /**
     * 获取servletTempdir 目录下 临时文件夹 注:非action请求无法获取
     * javax.servlet.context.tempdir
     * @return
     */
    public  File getTempdir() {
        File tempdir = null;
        File canonical = null;
        try{
        if (tempdirPath == null) {
//            tempdir = new File(com.sf.module.tcmscommon.util.CommonUtil.getProperty("exportSavePath"), EXCEL_CHILD_DIR);
//            tempdir = new File((File) 
//                               .getAttribute("javax.servlet.context.tempdir"), EXCEL_CHILD_DIR);
//                tempdirPath = tempdir.getCanonicalPath();
        	 tempdir = new File("/");
        } else {
            tempdir = new File(tempdirPath);
        }

        if (!tempdir.exists()) {
            tempdir.mkdirs();
        }
         canonical = tempdir.getCanonicalFile();
        } catch (IOException e) {
            logger.error(e.toString());
        }

        return canonical;
    }
}
