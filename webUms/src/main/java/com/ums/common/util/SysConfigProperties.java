
package com.ums.common.util;


public interface SysConfigProperties {
    
    /**
     * 导出(EXCEL)功能最大限制
     */
    int EXP_LIMIT = 60000;
    
    /**
     * 导入文件类型错误报错信息
     */
    String EXCEL_IMPORT_FILE_ERROR = "无法解析导入的文件，请检查导入文件是否正确";
    
    /**
     * 添加数据时出现唯一键冲突的报错信息
     */
    String UK_ERROR_MESSAGE = "该数据已经存在，无法重复添加!";
    
    /**
     * 导入公共错误
     */
    String EXCEL_IMPORT_FILE_COMMON_ERROR = "导入失败，导入数据存在错误。";
    
    /**
     * 导入数据为空错误
     */
    String EXCEL_IMORT_FILE_NULL_DATA = "导入模板数据不能为空!";

    /**
     * 导入（EXCEL）最大限制条数
     */
    int EXCEL_IMPORT_MAX_COUNT = 1000;
    
    /**
     * 总部网点
     */
    String DEPT_CODE_001 = "001";
    
    /**
     * 模板目录
     */
    String TPL_PATH = "tpl";
    
    /**
     * 导出功能使用的临时文件夹
     */
    String EXCEL_CHILD_DIR  = "export/excel_tmp";
    
    /**
     * 定时清除导出功能使用的临时文件夹下修改时间大于15分钟的文件(15分钟前创建的文件)
     */
    
    long EXCEL_TIMEOUT_MILLIS = 15 * 60 * 1000L;
    
    /**
     * 定时清除ESB使用的临时文件夹下修改时间大于7天(7天前创建的文件)
     */
    
    long ESB_FILE_TIMEOUT_MILLIS = 7 * 24 * 60 * 60 * 1000L;
    
    /**
     * ESB配置信息资源文件位置
     */
    String ESB_CONFIG_RESOURCE_FILE = "/com/sf/module/tcasinterface/META-INF/config/ESBInfo.properties";
    
    
}
