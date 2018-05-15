package com.ums.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PushbackInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.UUID;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description excel操作
 */
public class POIUtils extends POICommon {

    private static final Logger logger = LoggerFactory.getLogger(POIUtils.class.getName());
    protected Workbook workbook;
    private Row templateRow;
    private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
    private int lastRowIndex = -1;
    

    
    public POIUtils(String filePath, Integer sheetIndex, Integer templateRowIndex) {
        super();
        InputStream is = null;

        try {
            is = POIUtils.class.getResourceAsStream(new StringBuilder("/").append(filePath).toString());
			if (!is.markSupported()) {
				is = new PushbackInputStream(is, 8);
			}
			if (POIFSFileSystem.hasPOIFSHeader(is)) {
				workbook = new HSSFWorkbook(is);
			}else if (POIXMLDocument.hasOOXMLHeader(is)) {
				workbook = new XSSFWorkbook(OPCPackage.open(is));
			}
            sheet1 = workbook.getSheetAt(0);
            //intValue():把Integer类型转化为int类型
            //valueOf():把Integer类型转化为Integer类型
            Sheet sheet = workbook.getSheetAt(sheetIndex.intValue());
            templateRow = sheet.getRow(templateRowIndex.intValue());
        } catch (Exception e) {
            logger.info(e.toString());
        } 
        finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info(e.toString());
                }
            }
        }
    }

    
    public POIUtils(File excel, int lastCellNum) {
        super();
        InputStream is = null;

        try {
				is = new FileInputStream(excel);
        	
			if (!is.markSupported()) {
				is = new PushbackInputStream(is, 8);
			}
				if (POIFSFileSystem.hasPOIFSHeader(is)) {
					workbook = new HSSFWorkbook(is);
				}else if (POIXMLDocument.hasOOXMLHeader(is)) {
					workbook = new XSSFWorkbook(OPCPackage.open(is));
				}
            
			sheet1 = workbook.getSheetAt(0);
			lastRowNum = sheet1.getLastRowNum();
			this.lastCellNum = lastCellNum;
        }catch(IllegalArgumentException e){
            logger.info(e.toString());
			throw new IllegalArgumentException("excel can not be resolve");
		} catch(FileNotFoundException e) {
			  logger.info(e.toString());
		} catch(Exception e ) {
			  logger.info(e.toString());
		}
		finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.info(e.toString());
                }
            }
        }
    }
    
    private int lastRowNum;
    private int lastCellNum;
    
    
   
    public int getLastCellNum() {
        return lastCellNum;
    }

    public int getLastRowNum() {
        return lastRowNum;
    }

    private Sheet sheet1;
    
    public Object[] getNewRowAt(int rowIndex) {
        Object [] obj = null;
        Row row = sheet1.getRow(rowIndex);
        if (row == null) {
            return obj;
        }
        Object[] values = new Object[lastCellNum];
        for (int i=0; i<values.length; i++) {
            values[i] = getNewCellValue(row.getCell(i));
        }
        return values;
    }
    
    public Object[] getRowAt(int rowIndex) {
        Object[] obj = null;
        Row row = sheet1.getRow(rowIndex);
        if (row == null) {
            return obj;
        }
        Object[] values = new Object[lastCellNum];
        for (int i=0; i<values.length; i++) {
            values[i] = getCellValue(row.getCell(i));
        }
        return values;
    }
    

    
    
    public static Object getDealCellTypeFormula (Cell cell) {
        Object value = null;
        try {
            FormulaEvaluator e = new HSSFFormulaEvaluator((HSSFWorkbook) cell.getSheet().getWorkbook());
            e.evaluateFormulaCell(cell);
            value = cell.getNumericCellValue();
        } catch (IllegalStateException e) {
            logger.info(e.toString());
        }
        
        return value;
    }
    
    public static Object getDealCellTypeNumber (Cell cell) {
        Object value;
        if (DateUtil.isCellDateFormatted(cell)) {
            value = cell.getDateCellValue();
        } else {
            value = cell.getNumericCellValue();
        }
        return value;
    }
  
    private static Object getCellValue(Cell cell) {
    	
        if (cell == null) {
            return null;
        }

        Object value = null;
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_FORMULA:
               value = getDealCellTypeFormula (cell);
                break;
            case Cell.CELL_TYPE_NUMERIC:
               value = getDealCellTypeNumber(cell);
                break;
            case Cell.CELL_TYPE_BLANK:
                
                break;
            default:
                value = cell.toString().trim();
        }
        return value;
    }
    
    
    
    public static Object getNewCellValueTypeNumber(Cell cell) {
        Object value;
        if (DateUtil.isCellDateFormatted(cell)) {
            value = cell.getDateCellValue();
        } else {
            //设置为文本类型
            cell.setCellType(Cell.CELL_TYPE_STRING);
            value = cell.toString().trim();
        }
        return value;
    }
    /**
     * 获取cell value
     * 
     * @author 柯迪裕 (625387)
     * @date 2012-7-17
     * @param cell
     * @return
     */
    private static Object getNewCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        Object value = null;
        switch (cell.getCellType()) {
//            case Cell.CELL_TYPE_FORMULA:
//                try {
//                    FormulaEvaluator e = new HSSFFormulaEvaluator((HSSFWorkbook) cell.getSheet().getWorkbook());
//                    e.evaluateFormulaCell(cell);
//                    value = cell.getNumericCellValue();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                }
//                break;
            case Cell.CELL_TYPE_NUMERIC:
               value = getNewCellValueTypeNumber(cell);
                break;
            case Cell.CELL_TYPE_BLANK:
                break;
            default:
            	//设置为文本类型
                cell.setCellType(Cell.CELL_TYPE_STRING);
                value = cell.toString().trim();
        }
        return value;
    }
    
    
    private Sheet getSheet(Integer sheetIndex) {
        Sheet sheet;
        if (sheetIndex == null) {
            sheet = workbook.getSheetAt(0);
        } else {
            sheet = workbook.getSheetAt(sheetIndex.intValue());
        }
        return sheet;
    }
    
    public void fullRow(Integer rowIndex, Object[] values) {
        fullRow(null, rowIndex, values);
    }
    
    public void fullRow(Integer sheetIndex,  Integer rowIndex, Object[] values) {
        if (values == null) {
            return;
        }
        Sheet sheet = getSheet(sheetIndex);
       
        /**
         * Sheet.RemoveRow.ShiftRows(int startRow, int endRow, int n); 
         * 这3个参数的含义是从startRow行到endRow行整体往上或者往下移动N行，
         * n如果为负数则往上移，n为正数则向下移（例如下向下2行，n不能为2，必须为+2）。
         * 往上或往下移动，原来所在位置上的行被移动过来的行覆盖。
         * 比如总共有10行，我想删掉第二行。设置startRow=1,endRow=9,n=-1即可 。
         */
//        if (lastRowIndex != -1) {
//            sheet.shiftRows(rowIndex, rowIndex, 1);
//        }
        lastRowIndex =  (rowIndex + 1);
        
        Row r = sheet.createRow(rowIndex);
        int valuesIndex = 0;
        for(Iterator<Cell> it = templateRow.iterator();it.hasNext();) {
            Cell sourceCell = it.next();
            
            valuesIndex = fullCellValue(values, r, valuesIndex, sourceCell);
        }
    }

    
  
    protected int fullCellValue(Object[] values, Row r, int valuesIndex, Cell sourceCell) {
        if (sourceCell != null) {
            int columnIndex = sourceCell.getColumnIndex();
            Cell targetCell = r.createCell(columnIndex);
            targetCell.setCellStyle(sourceCell.getCellStyle());
            targetCell.setCellType(sourceCell.getCellType());
            //Object value = columnIndex < valus.length ? valus[columnIndex] : null;
            caseCellTypeFormula(sourceCell,targetCell);
            dealExcelNumber(values, sourceCell, targetCell,valuesIndex);
            getDealExcelDefault(values, targetCell,valuesIndex);
            valuesIndex++;
        }
        return valuesIndex;
    }
    
    public void caseCellTypeFormula(Cell sourceCell, Cell targetCell) {
        if(sourceCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
          //targetCell.setCellFormula(sourceCell.getCellFormula().replaceAll(REG_EX, replacement));
            cloneCellFormula(sourceCell, targetCell);
            return;
        }
    }
    

    public void getDealExcelDefault(Object[] values,Cell targetCell,int valuesIndex) {
        Object value = null;
        if(valuesIndex < values.length) {
          value  = values[valuesIndex];
        }
        if (value instanceof Date) {
            targetCell.setCellValue(sdf.format((Date)value));
        } else if(value instanceof Number) {
            targetCell.setCellValue(BigDecimal.valueOf(((Number) value).doubleValue()).stripTrailingZeros().toPlainString());
        } else {
            targetCell.setCellValue(value == null ? null : value.toString());
        }
    }
    
    public void dealExcelNumber(Object[] values,Cell sourceCell,Cell targetCell,int valuesIndex) {
        if (Cell.CELL_TYPE_NUMERIC == sourceCell.getCellType()) {
            Object value = values[valuesIndex];
            if (DateUtil.isCellDateFormatted(sourceCell)) {
                //KEDIYU 2014-6-6 Date类型为空则不显示，不能报错
                if(value != null) {
                    targetCell.setCellValue((Date)value);
                }
            } else {
                if (value != null && value instanceof Number) {
                    Number v = (Number)value;
                        targetCell.setCellValue(v.doubleValue());
                } 
                // KEDIYU 2014-6-9 当如果赋值为String类型时，直接设置
                else {
                     if (value != null) {
                         targetCell.setCellValue(value.toString());
                     }
                }
            }
        }
    }
    
    public void shiftCollectRow() {
        shiftCollectRow(0, lastRowIndex);
    }
    public void shiftCollectRow(int sheet, int lastRowIndex) {
        Row collectRow = workbook.getSheetAt(sheet).getRow(lastRowIndex);
        if (collectRow != null) {
            //String replacement = "$2" + lastRowIndex;
            for(Iterator<Cell> it = collectRow.iterator();it.hasNext();) {
                Cell sourceCell = it.next();
                if (sourceCell != null && sourceCell.getCellType() == Cell.CELL_TYPE_FORMULA) {
                    //sourceCell.setCellFormula(sourceCell.getCellFormula().replaceAll(REG_EX, replacement));
                    cloneCellFormula(sourceCell);
                }
            }
        }
    }
    
   
//    private static String columnIndex2Code(int n) {
//        int bufLen = 3;
//        char[] buf = new char[bufLen];
//        int charPos = bufLen;
//        while (n > 0) {
//            int m = n % 26;
//            if (m == 0) 
//                  m = 26;
//            buf[--charPos] = (char)(m + 64);
//            n = (n - m) / 26;
//        }
//        return new String(buf, charPos, (bufLen - charPos));
//    }
    
   
    private  static final String REG_EX = "(([a-zA-Z]+)(\\d+))";
    
    private  static final String REG_EX2 = REG_EX + "([^a-z]*)" + REG_EX;
    
    
    private  final Pattern PATTERN = Pattern.compile(REG_EX2, Pattern.CASE_INSENSITIVE);
    
    private void cloneCellFormula(Cell sourceCell, Cell targetCell) {
        String sourceFormula = sourceCell.getCellFormula();
        String targetFormula = null;
        //Pattern PATTERN = Pattern.compile(REG_EX + "([^a-z]*)" + REG_EX, Pattern.CASE_INSENSITIVE);
        Matcher matcher = PATTERN.matcher(sourceFormula);
        if (matcher.find()) {
//            int l = matcher.groupCount();
//            for (int i = 1; i <= l; i++) {
//                System.out.print( i + " == >");
//                System.out.print(matcher.group(i));
//                System.out.println();
//            }
        	/**
        	 * ([a-zA-Z]+) (\\d+) ([^a-z]*) ([a-zA-Z]+) (\\d+)
        	 * group是针对（）来说的，group（0）就是指的整个串，
        	 * group（1） 指的是第一个括号里的东西，
        	 * group（2）指的第二个括号里的东西。
        	 * 组也就是子表达式
        	 */
            String g2 = matcher.group(2);
            String g6 = matcher.group(6);
//            for(int i=matcher.groupCount();i>=0;i--) {
//                System.out.print(matcher.group(i) + " ~ ");
//            }
//            System.out.println();
            if (!g2.equals(g6)) {
                targetFormula = sourceFormula.replaceAll(REG_EX, "$2" + (targetCell.getRowIndex() + 1));
                //System.out.println(sourceFormula + " => " + targetFormula);
            } else {
                targetFormula = sourceFormula.replaceAll(REG_EX2, "$1$4$6" + targetCell.getRowIndex());
                //System.out.println(sourceFormula + " ==> " + targetFormula);
            }

        }
        targetCell.setCellFormula(targetFormula);
    }
    
    private void cloneCellFormula(Cell sourceCell) {
        cloneCellFormula(sourceCell, sourceCell);
    }
    

  
    public String write() {
        File savePath = getExcelSavePath();
        OutputStream out = null;
        try {
            out = new FileOutputStream(savePath);
            workbook.write(out);
        } catch (Exception e) {
            logger.info(e.toString());
            //throw e;
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    logger.error(e.toString());
                }
            }
        }
        return savePath.getName();
    }
    
    
    private File getExcelSavePath() {
        return new File(getTempdir(), getUniqueFileName());
    }
    
    
    public String getUniqueFileName() {
    	try {
    		String userName = "admin";
	    	StringBuilder fileName = new StringBuilder(userName);
	    	fileName.append('_');
	    	return fileName.append(UUID.randomUUID().toString()).toString();
    	} catch(Exception e) {
    	    logger.info(e.toString());
    		return "admin_excel_error_data";
    	}
    }
    
    /**
     * 按坐标获取一个Cell
     * @param rowNum
     * @param columnNum
     * @return Cell
     */
    public Cell getCellByIndex(int rowNum, int columnNum)
    {
    	if(this.sheet1==null)
    	{
    		return null;
    	}
    	
    	if(this.sheet1.getRow(rowNum)==null)
    	{
    		return null;
    	}
    	
    	return this.sheet1.getRow(rowNum).getCell(columnNum);
    }
    
   
}
