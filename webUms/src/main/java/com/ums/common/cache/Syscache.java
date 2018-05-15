package com.ums.common.cache;

import java.util.HashMap;
import java.util.Map;

public class Syscache {
	 //验证码缓存
	  public static Map<String,Object> VALIDATE_CODE_CATCH =
			                                new HashMap<String,Object>();
	  public static Map<String,Object> USER_INFO_CATCH =
	          new HashMap<String,Object>();
	  
	  public static Object getValidCode(String key) {
		 if(VALIDATE_CODE_CATCH.containsKey(key)) {
			 return VALIDATE_CODE_CATCH.get(key);
		 }
		return null;
	  }
	  
	  public static Object getUserInfo(String key) {
			 if(USER_INFO_CATCH.containsKey(key)) {
				 return USER_INFO_CATCH.get(key);
			 }
			return null;
		  }
	  
	  public static void setValidCode(String key,Object value) {
		     VALIDATE_CODE_CATCH.clear();
			 if(!VALIDATE_CODE_CATCH.containsKey(key)) {
				  VALIDATE_CODE_CATCH.put(key, value);
		  }
	  }
	  
	  public static void setUserInfo(String key,Object value) {
		     USER_INFO_CATCH.clear();
			 if(!USER_INFO_CATCH.containsKey(key)) {
				 USER_INFO_CATCH.put(key, value);
			 }
	   }
}
