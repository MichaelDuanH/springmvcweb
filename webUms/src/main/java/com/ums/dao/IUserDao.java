package com.ums.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.ums.entity.User;
public interface IUserDao {
	public User selectUserById(int userId);
	public List<User> allUsersPage(@Param("user")User user,RowBounds rowBounds);
	public User selectUserByName(@Param("userName") String userName); 
	public User searchByUserCode(@Param("userCode") String userCode);
	public List<User> searchByUserCodeAndId(@Param("userCode") String userCode, @Param("userId") String userId);
	int saveUser(@Param("user")User user);
	int updateUser(@Param("user")User user);
	int updateUnique(@Param("user")User user);
	int delUser(@Param("userId")String userId);
	public User selectUserByConditions(@Param("user")User user);
	int addBatch(@Param("users") List<User> users);
	int addOrgBatch(@Param("users") List<User> users);
	int delUsersByMonth(@Param("month") String month);
	
	int updateOfMarkProcessed(@Param("userId") Long userId);
	
	List<User> getUsersByMTopNumAndId(@Param("topNum") int topNum, @Param("userId") Long userId);
	
	int updateByUserName(@Param("loginId") String loginId, @Param("logTimes") Integer logTimes, @Param("userName") String userName);
	
	public List<User> getUsersByMonthAndUserCode(@Param("userCode") String userCode,
			@Param("month") String month);
	int batchDeleteUsers(@Param("users") List<User> users);
	
	int getTotalCount();
	
	int deleteAll();
	
	int reCalc();
	
	int copyAllUsers();
	
	int  deleteOriAll();
	
	int delBatchOriUsers(@Param("users") List<User> users);
	
}
