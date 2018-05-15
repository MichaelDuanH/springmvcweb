package com.ums.biz;
import java.util.List;

import com.ums.entity.User;
import com.ums.extend.Page;
public interface IUserCommisResultBiz {
	public User selectUserById(int userId);  
	public void allUsersPage(Page<User> page);
	public User selectUserByName(String userName); 
	int updateByUserName(String loginStatus, String userName); 
	public User searchByUserCode(String userCode);
	public int saveUser(User user);
	public int updateUser(User user);
	public int updateUnique(User user);
	public int delUser(String userId);
	public boolean validateRepeat(User user);
	public List<User> getUsersByMonth(String month);
	public void insert(List<User> users,String month);
	
	public List<User> getUsersByMTopNumAndId(int topNum,Long userId);
	
	public void handle(User user);
	//查询唯一判断
	boolean checkUniqueData(String empCode,String userId);
	
	int delBatch(List<User> users);
	
	int getTotalCount();
	
	int deleteAll();
	
	int deleteOriAll();
	
	int addOrgBatch(List<User> users);
}
