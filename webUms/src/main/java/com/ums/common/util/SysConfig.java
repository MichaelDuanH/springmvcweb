package com.ums.common.util;

public class SysConfig {
	
	 //权重
	 private static Integer weight = 100;
	 
	 public static Integer getWeight() {
	        return weight;
	 }

    public static void addWeight(int _weight) {
       weight += _weight;
    }
    
    public static final int USER_THREAD_COUNT = 1;
    
    public static final int USER_WAIT_SECONDS = 5;
    
    // 队列长度
    public static final int EMPSYNC_BLOCKING_QUEUE_SIZE = 2000;
    
    // 每次获取多少条数据
    public static final int EMPSYNC_QUERY_EMP_COUNT = 1000;
    

    // 队列长度
    public static final int DEPARTMENTSYNC_BLOCKING_QUEUE_SIZE = 2000;
    
    // 处理数据线程数
    public static final int DEPARTMENTSYNC_HANDLER_COUNT = 3;

    // 处理数据线程等待获取数据时间（秒）
    public static final int DEPARTMENTSYNC_WAIT_SECONDS = 10;

}
