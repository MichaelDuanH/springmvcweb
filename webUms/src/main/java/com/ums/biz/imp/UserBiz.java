package com.ums.biz.imp;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ums.biz.IUserBiz;
import com.ums.common.util.DateUtil;
import com.ums.common.util.StringUtil;
import com.ums.dao.IUserDao;
import com.ums.entity.User;
import com.ums.extend.Page;
@Service
public class UserBiz implements IUserBiz {
    //最低保底工资
	private static int guaranteeSalary = 1250;
	
	@Autowired
	private IUserDao userDao;

	
	@Override
	public User selectUserById(int userId) {
		return userDao.selectUserById(userId);
	}

	@Override
	public void allUsersPage(Page<User> page) {
		RowBounds rowBounds =  page.getRowBounds();
		page.setData(userDao.allUsersPage(page.getQueryObj(),rowBounds),rowBounds);
		page.setTotal(rowBounds);
	}

	@Override
	public User selectUserByName(String userName) {
		return userDao.selectUserByName(userName);
	}

	@Override
	public User searchByUserCode(String userCode) {
		return userDao.searchByUserCode(userCode);
	}

	@Override
	public int saveUser(User user) {
	    return userDao.saveUser(user);
	}

	@Override
	public int delUser(String userId) {
		return userDao.delUser(userId);
	}

	@Override
	public int updateUser(User user) {
		return userDao.updateUser(user);
	}

	@Override
	public boolean validateRepeat(User user) {
		User u = userDao.selectUserByConditions(user);
		if(u == null) {
			return false;
		}
		return true ;
	}

	
	private double calcTotalMin(String timeStamp,BigDecimal coeff) {
		double temp;
	   if(timeStamp != null) {
		   Calendar c = Calendar.getInstance();
		   c.setTime(DateUtil.parse(timeStamp, "yyyy-MM-dd HH:mm:ss"));
		   temp = Integer.valueOf((StringUtil.formatMilli((new Date().getTime() - c.getTime().getTime()), "minute"))) * coeff.doubleValue();
	   } else {
		   temp = 0D; 
	   }
	   return Double.valueOf(StringUtil.df.format(temp));
	}
	
	
	

	@Override
	public boolean checkUniqueData(String userCode,String userId) {
		List<User> userList = userDao.searchByUserCodeAndId(userCode,userId);
		//如果能查询出数据，说明有重复数据
		if(userList != null && !userList.isEmpty() && userList.size() >= 1) {
			return true;
		}
		return false;
	}

	@Override
	public int delBatch(List<User> users) {
		int m = 0;
//		for(int i = 0; i<users.size(); i++) {
//			m = userDao.delUser(users.get(i).getUserId());
//		}
		return m;
	}

	@Override
	public int getTotalCount() {
		return userDao.getTotalCount();
	}

	@Override
	public int deleteAll() {
		return userDao.deleteAll();
	}


	@Override
	public int deleteOriAll() {
		return userDao.deleteOriAll();
	}

	@Override
	public int addOrgBatch(List<User> users) {
		// TODO Auto-generated method stub
		return userDao.addOrgBatch(users);
	}

	@Override
	public int updateByUserName(String loginId,Integer logTimes, String userName) {
		// TODO Auto-generated method stub
		return userDao.updateByUserName(loginId,logTimes,userName);
	}

	@Override
	public int updateUnique(User user) {
		return userDao.updateUnique(user);
	}

	
	@Override
	public int reCalc() {
		return userDao.reCalc();
	}
}
