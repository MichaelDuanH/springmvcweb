package com.ums.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ums.common.util.DateUtil;
import com.ums.common.util.StringUtil;
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long userId;  
    private String userName; 
	private String passWord;
	private String verCode;
	private String inputVerCode;
    private int userAge;
    private String pas;
    private String sex;
    private String deptCode;
    private String userCode;
    private String userMail;
    private String phone;
    public String adress;
    public String idNo;
    public String month;
    private String createTm;
    private String newPass;
    private double amount;
    private String remark;
    private String loginStatus;
    private String macAdr;
    private String loginId;
    private String errorMsg;
    private String isCommis;
    private String personTypeCode;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date commisDate;
    private String commisDateStr;
    private Integer logTimes;
    //是否同步
    private String sync;
    
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getPas() {
		return pas;
	}
	public void setPas(String pas) {
		this.pas = pas;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCreateTm() {
		return createTm;
	}
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public void setCreateTm(String createTm) {
		this.createTm = createTm;
	}
	public String getIdNo() {
		return idNo;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public String getNewPass() {
		return newPass;
	}
	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getVerCode() {
		return verCode;
	}
	public void setVerCode(String verCode) {
		this.verCode = verCode;
	}
	public String getInputVerCode() {
		return inputVerCode;
	}
	public void setInputVerCode(String inputVerCode) {
		this.inputVerCode = inputVerCode;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public String getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(String loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	public String getMacAdr() {
		return macAdr;
	}
	public void setMacAdr(String macAdr) {
		this.macAdr = macAdr;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	
	public String getIsCommis() {
		return isCommis;
	}
	public void setIsCommis(String isCommis) {
		this.isCommis = isCommis;
	}
	public String getPersonTypeCode() {
		return personTypeCode;
	}
	public void setPersonTypeCode(String personTypeCode) {
		this.personTypeCode = personTypeCode;
	}
	

	public Date getCommisDate() {
		return commisDate;
	}
	
	public String getCommisDateStr() {
		return commisDate == null?commisDateStr:DateUtil.formate(commisDate, "yyyy-MM-dd");
	}
	
	
	public void setCommisDateStr(String commisDateStr) {
		this.commisDateStr = commisDateStr;
	}
	
	
	public void setCommisDate(Date commisDate) {
		this.commisDate = commisDate;
	}
	
	
	public String getSync() {
		return sync;
	}
	public void setSync(String sync) {
		this.sync = sync;
	}
	
	public Integer getLogTimes() {
		return logTimes;
	}
	public void setLogTimes(Integer logTimes) {
		this.logTimes = logTimes;
	}
	public User createUser(User u) {
		u.setUserAge(StringUtil.getUserAge());
		u.setDeptCode(StringUtil.getDeptCode());
		u.setUserCode(StringUtil.getEmpCode());
		u.setUserName(StringUtil.getEmpName(u.getUserCode()));
		u.setUserMail(StringUtil.getEmpMail(u.getUserName()) + "@QQ.COM");
		u.setCommisDate(StringUtil.getCommisDate());
		u.setPersonTypeCode(StringUtil.getPersonTypeCode(u.getUserCode()));
		u.setSex(StringUtil.getSex(u.getUserCode()));
		u.setPhone(StringUtil.getPhoneNo(u.getUserCode()));
		u.setIdNo(StringUtil.getIdNo(u.getUserCode()));
		u.setAdress(StringUtil.getEmpAdress());
		u.setMonth(DateUtil.getCurrMonth());
		u.setCreateTm(DateUtil.dateToTimeStam(new Date()));
		return u;
	}
}
