package com.ums.common.util;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sourceforge.pinyin4j.PinyinHelper;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static final Logger logger = LoggerFactory.getLogger(StringUtil.class.getName());
	   private static final String DATE_FORMAT = "yyyy-MM-dd";
	   // 系统默认空字符串
	   public static final String EMPTY_STRING = "";
	   public static DecimalFormat  df   = new DecimalFormat("######0.00");   
	   static String [] familyArr ={"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许","何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦","章","云","段","苏","潘","葛","奚","范","彭","郎","鲁","韦","昌","马","苗","凤","花","方","俞","任","袁","柳","酆","鲍","史","唐","费","廉","岑","薛","雷","贺","倪","汤"}; 
	   static String [] nameArr ={"敏","锐","真","善","美","成","红","杰","仁","佳","豪","芳","春","辉","顶","峰","连杰","杰伦","腾","花","康","双江","涛"}; 
	   static String [] phoneHead = {"137","186","139","188","158","138","133","146","189","135"};
	   static String [] deptCodeArr = {"755Y","E431D002","574DD","222Y","E412D003","111Y","731A","432M","411A","010AQ","577DA","577DE","318D"};
	   static String [] districtArr = {"罗湖区","福田区","南山区","宝安区","龙岗区","盐田区"};
	   static String [] countyArr = {"荔村","上林村","福强村","石厦村","新洲村","沙咀村","下沙村","上沙村","新华村","益田村","金城村","天安村","新港村","金地村","沙尾村","明月村","翠湾村"};
	   static String [] adressArr = {"深圳市南山区软件产业基地B座4楼","深圳市南山区软件产业基地C座11楼","深圳市南山区软件产业基地A座2楼","深圳市南山区软件产业基地北科大厦3楼"};
	   static String [] commisDateArr = {"2017-12-01","2017-11-01","2018-01-01","2018-02-01","2018-03-01"};
	   public static boolean isEmpty(String str) {
		   if(str == null || str.equals("") || str.length() == 0) {
			   return true;
		   }
		   return false;
	   }
	   
	   private static String getNotNullString(String str) {
	       return isEmpty(str) ? EMPTY_STRING : str;
	   }
	   
	   public static String connect(String... strs) {

	       if (strs != null && strs.length > 0) {
	           StringBuilder sb = new StringBuilder(getNotNullString(strs[0]));
	           for (int i = 1; i < strs.length; i++) {
	               sb.append(getNotNullString(strs[i]));
	           }
	           return sb.toString();
	       }

	       return EMPTY_STRING;
	   }
	   
	   public static String append(String ... strs) {
		   StringBuilder builders = new StringBuilder();
		   for(int i = 0; i<strs.length;i++) {
			   builders.append(strs[i]);
		   }
		return builders.toString();  
	   }

	   public static String append(String sql, String string, int offset,
			String string2, int limit) {
		return new StringBuilder().append(sql).append(" ").append(string).append(" ").append(String.valueOf(offset)).append(string2).append(String.valueOf(limit)).toString();
	  }
	   
	   //随机生成6位工号
	   
	   public static String getEmpCode() {
		String ranCode =  Math.random() + "";  
		return isEmpty(ranCode.substring(ranCode.length()-8, ranCode.length()-2))?"123456":ranCode.substring(ranCode.length()-8, ranCode.length()-2);
	   }
	   
	   //随机生成姓名
	   public static String getEmpName(String empCode) {
		   String name = null;
		   List<String> sets = new ArrayList<String>();
		   if(empCode != null) {
			   for(int i = 0;i<familyArr.length;i++) {
					   for(int j = 0; j<nameArr.length;j++) {
						   name = familyArr[i] + nameArr[j];
						   sets.add(name);
					   }
			   }
		   } 
		   Random r = new Random();
		 return sets.get(r.nextInt(sets.size()));
		   
	   }
	   
	   //根据参数转换毫秒数
	   public static String formatMilli(long mss,String params) {
		   if ("day".equals(params)) {
			   return String.valueOf(mss / (1000 * 60 * 60 * 24));
		   } else if("hour".equals(params)) {
			   return String.valueOf((mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
		   } else if("minute".equals(params)) {
			   return String.valueOf((mss % (1000 * 60 * 60)) / (1000 * 60));
		   } else if("second".equals(params)) {
			   return String.valueOf((mss % (1000 * 60)) / 1000);
		   } else {
			   return "0";
		   }
	   }
	   
	   
	   public static String getLocalMac() {
			//获取网卡，获取地址
		   InetAddress ia = null;
		   try {
			   ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		   
			byte[] mac = null;
			try {
				mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
			} catch (SocketException e) {
				e.printStackTrace();
			}
			StringBuffer sb = new StringBuffer("");
			for(int i=0; i<mac.length; i++) {
				if(i!=0) {
					sb.append("-");
				}
				//字节转换为整数
				int temp = mac[i]&0xff;
				String str = Integer.toHexString(temp);
				if(str.length()==1) {
					sb.append("0"+str);
				}else {
					sb.append(str);
				}
			}
			return sb.toString().toUpperCase();
		}
	   
	   public static void main(String[] args) {
		System.out.println(getLocalMac());
	}
	   
	  
	   
	   
	   //随机生成邮件
	   public static String getEmpMail(String empName) {
		   StringBuilder name = new StringBuilder("");
		   for(int i = 0; i<empName.length();i++) {
			   name.append(converPinYin(empName.charAt(i)+" "));
		   }
		   return name.toString();
	   }
	   
	   public static String toyyyymmddStr(Date date) {
			if (date != null) {
				return new SimpleDateFormat(DATE_FORMAT).format(date);
			}
			return null;
		}
	   
	   
	   public static String getExcelString(Object obj) {
			if (obj != null) {
				if (obj instanceof String) {
					return obj.toString();
				} else if (obj instanceof Date) {
					return toyyyymmddStr((Date) obj);
				} else if (obj instanceof BigDecimal) {
					return ((BigDecimal) obj).toPlainString();
				}
			}
			return null;
		}
	   

		@SuppressWarnings("deprecation")
		public static Date getExcelDate(Object obj, int type) {
			if (obj != null) {
				if (obj instanceof Date) {
					Date date = (Date) obj;

					// 设置天数为1
					if (type == 2) {
						date.setSeconds(0);
						date.setMinutes(0);
						date.setHours(0);
						date.setDate(1);
					}
					return date;
				} else if (obj instanceof String) {
					if (type == 1) {
						return toyyyymmddDate(obj.toString());
					}
					return toyyyymmDate(obj.toString());
				}
			}
			return null;
		}
		

		public static Date toyyyymmDate(String dateStr) {
			if (isNotEmpty(dateStr)) {
				try {
					return DateUtil.DateFormatYYYYMM.parse(dateStr);
				} catch (ParseException e) {
				    logger.info(e.toString());
				}
			}
			return null;
		}
		

		public static boolean isNotEmpty(String str) {
			return !isEmpty(str);
		}
		

		public static Date toyyyymmddDate(String dateStr) {
			if (isNotEmpty(dateStr)) {
				try {
					return new SimpleDateFormat(DATE_FORMAT).parse(dateStr);
				} catch (ParseException e) {
				    logger.info(e.toString());
				}
			}
			return null;
		}
		
		
		public static String connectsObject(Object... objects) {

			StringBuilder sb = new StringBuilder();

			for (Object obj : objects) {
				if (obj != null && isNotEmpty(obj.toString())) {
					sb.append(obj);
				}
			}

			if (sb.length() > 0) {
				return sb.toString();
			}

			return null;
		}


		public static Double getExcelDouble(Object obj, int scale) {
			if (obj != null) {
				if (obj instanceof Double) {

					Double d = (Double) obj;

					// 如果大于INT最大值直接返回不做转换
					if (scale < 0) {
						return d;
					}

					return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_FLOOR).doubleValue();
				} else if (obj instanceof String) {

					Double d = null;

					try {
						d = Double.valueOf(obj.toString());
					} catch (NumberFormatException e) {
						return null;
					}

					// 如果大于INT最大值直接返回不做转换
					if (scale < 0) {
						return d;
					}

					return new BigDecimal(d).setScale(scale, BigDecimal.ROUND_HALF_DOWN).doubleValue();
				}
			}
			return null;
		}


		public static BigDecimal getExcelNumber(Object obj) {
			if (obj != null) {
				if (obj instanceof BigDecimal) {
					return (BigDecimal) obj;
				} else if (obj instanceof String) {
					try {
						return new BigDecimal(obj.toString()).stripTrailingZeros();
					} catch (NumberFormatException e) {
						return null;
					}
				}
			}
			return null;
		}


		public static Long getExcelLong(Object obj) {
			if (obj != null) {
				if (obj instanceof BigDecimal) {
					BigDecimal num = ((BigDecimal) obj);
					if (num.scale() == 0) {
						return num.longValue();
					}
				} else if (obj instanceof String) {

					Long num = null;

					try {
						num = Long.valueOf(obj.toString());
					} catch (NumberFormatException e) {
						return null;
					}

					return num;
				}
			}
			return null;
		}
	   
	  
	   // 随机生成网点代码
	   public static String getDeptCode() {
		   return deptCodeArr[getRangeNumber(1,12)];
	   }
	   
	   //随机生成转计提日期
	   
	   public static Date getCommisDate() {
		   return DateUtil.parse(commisDateArr[getRangeNumber(1,4)], "yyyy-MM-dd");
	   }
	   
	   //随机生成地址
	   public static String getEmpAdress() {
		   List<String> lists = new ArrayList<String>();
		   String name = null;
		   for(int i = 0; i<districtArr.length; i++) {
			   for(int j = 0;j<countyArr.length;j++) {
				   name = "深圳市" + districtArr[i] + countyArr[j] + (j==0?1:j) +"街道" + (10%(j+1) +1) + "号";
				   lists.add(name);
			   }
		   }
		   return  lists.get(new Random().nextInt(lists.size()));
	   }
	   
	   //随机生成性别
	   
	   public static String getSex(String empCode) {
		   return Integer.valueOf(empCode.substring(0,1)) >= 5 ? "1":"2";
	   }
	   
	   //随机生成人员类型
	   public static String getPersonTypeCode(String empCode) {
		   return Integer.valueOf(empCode.substring(0,1)) >= 5 ? "W":"A";
	   }
	   
	   
	   
	   // 随机生成电话号码
	   public static String getPhoneNo(String empCode) {
		   String phone = null;
		   for(int i = 0;i<empCode.length();i++) {
				   phone = phoneHead[Integer.valueOf(empCode.substring(0,1))] + empCode + getSex(empCode) + empCode.substring(1,2);
		   }
		   return phone;
	   }
	   
	   //随机生成转计提日期
	   
	   
	   
	   private static int getRangeNumber(int min,int max) {
		   return new Random().nextInt(max)%(max-min+1) + min;
	   }
	   
	   public static String getIpAdress() {
		   InetAddress ia = null;
		   try {
			   ia = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		   return ia.getHostAddress();
		   
	   }
	   
	   
	   private static String getBirthDate() {
		   // 22岁 - 60岁之间
		   Calendar c = Calendar.getInstance();
		   c.set(Calendar.YEAR, getRangeNumber(22,60));
		   c.set(Calendar.MONTH, getRangeNumber(1,12));
		   c.set(Calendar.DATE, getRangeNumber(1,31));
		   Date d = c.getTime();
		   String month = d.getMonth() >= 10?d.getMonth()+"":"0"+ d.getMonth();
		   String date = d.getDate() >= 10?d.getDate()+"":"0"+ d.getDate();
		   return (d.getYear()+""+month + date).replace("-", "");
	   }
	   
	   public static int getUserAge() {
		   Calendar c = Calendar.getInstance();
		   c.setTime(DateUtil.parse(getBirthDate(), "yyyyMMdd"));
		   return - c.getTime().getYear();
	   }
	   
	 
	   
	   //随机生成身份证号码
	   public static String getIdNo(String empCode) {
		 return getRangeNumber(11,82) +""+ getRangeNumber(11,82) + dealNumber(getRangeNumber(1,100)) + getBirthDate() + empCode.substring(0,2) + getBirthDate().substring(2,2) ;
	   }
	   
	   private static String dealNumber(int i) {
		   String rs = "";
		   if(i < 10) {
			   rs = "0" + i;
		   } else {
			   rs = i + "";
		   }
		   
		   return rs;
	   }
	   

		public static String getPinYinHeadChar(String str) {
			if (isEmpty(str)) {
				return "";
			}
			StringBuilder convert = new StringBuilder();
			str = str.trim();
			for (int j = 0; j < str.length(); j++) {
				char word = str.charAt(j);
				// 提取汉字的首字母
				String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				if (pinyinArray != null) {
					convert.append(pinyinArray[0].charAt(0));
				} else {
					convert.append(word);
				}
			}
			return convert.toString().toUpperCase();
		}
		
		//将汉字转换为拼音
		public static String converPinYin(String str) {
			if (isEmpty(str)) {
				return "";
			}
			StringBuilder convert = new StringBuilder("");
			for (int j = 0; j < str.length(); j++) {
				char word = str.charAt(j);
				// 提取汉字的首字母
				String [] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(word);
				if (pinyinArray != null) {
					convert.append(pinyinArray[j]);
				} 
			}
			return convert.toString().toUpperCase().substring(0,convert.toString().length()-1);
		}
		
		
		
		public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
	        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
	        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
	            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
	                return (o1.getValue()).compareTo(o2.getValue());
	            }
	        });

	        Map<K, V> result = new LinkedHashMap<K, V>();
	        for (Map.Entry<K, V> entry : list) {
	            result.put(entry.getKey(), entry.getValue());
	        }
	        return result;
	    }
		
		
		public static Workbook createWorkBook(List<Map<String, Object>> list,
	            String[] keys, String[] columnNames) {
	           // 创建excel工作簿
	           Workbook wb = new HSSFWorkbook();
	           // 创建第一个sheet（页），并命名
	           Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
	           // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
	           for (int i = 0; i < keys.length; i++) {
	                   sheet.setColumnWidth((short) i, (short) (35.7 * 150));
	           }

	           // 创建第一行
	           Row row = sheet.createRow((short) 0);

	           // 创建两种单元格格式
	           CellStyle cs = wb.createCellStyle();
	           CellStyle cs2 = wb.createCellStyle();

	           // 创建两种字体
	           Font f = wb.createFont();
	           Font f2 = wb.createFont();

	           // 创建第一种字体样式（用于列名）
	           f.setFontHeightInPoints((short) 10);
	           f.setColor(IndexedColors.BLACK.getIndex());
	           f.setBoldweight(Font.BOLDWEIGHT_BOLD);

	           // 创建第二种字体样式（用于值）
	           f2.setFontHeightInPoints((short) 10);
	           f2.setColor(IndexedColors.BLACK.getIndex());

	           // Font f3=wb.createFont();
	           // f3.setFontHeightInPoints((short) 10);
	           // f3.setColor(IndexedColors.RED.getIndex());

	           // 设置第一种单元格的样式（用于列名）
	           cs.setFont(f);
	           cs.setBorderLeft(CellStyle.BORDER_THIN);
	           cs.setBorderRight(CellStyle.BORDER_THIN);
	           cs.setBorderTop(CellStyle.BORDER_THIN);
	           cs.setBorderBottom(CellStyle.BORDER_THIN);
	           cs.setAlignment(CellStyle.ALIGN_CENTER);

	           // 设置第二种单元格的样式（用于值）
	           cs2.setFont(f2);
	           cs2.setBorderLeft(CellStyle.BORDER_THIN);
	           cs2.setBorderRight(CellStyle.BORDER_THIN);
	           cs2.setBorderTop(CellStyle.BORDER_THIN);
	           cs2.setBorderBottom(CellStyle.BORDER_THIN);
	           cs2.setAlignment(CellStyle.ALIGN_CENTER);
	           // 设置列名
	           for (int i = 0; i < columnNames.length; i++) {
	                   Cell cell = row.createCell(i);
	                   cell.setCellValue(columnNames[i]);
	                   cell.setCellStyle(cs);
	           }
	           // 设置每行每列的值
	           for (short i = 1; i < list.size(); i++) {
	                   // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
	                   // 创建一行，在页sheet上
	                   Row row1 = sheet.createRow((short) i);
	                   // 在row行上创建一个方格
	                   for (short j = 0; j < keys.length; j++) {
	                           Cell cell = row1.createCell(j);
	                           cell.setCellValue(list.get(i).get(keys[j]) == null ? " " 
	           : list.get(i).get(keys[j]).toString());
	                           cell.setCellStyle(cs2);
	                   }
	           }
	           return wb;
	   }
}
