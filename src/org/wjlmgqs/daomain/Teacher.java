/**
 **作者：翁加林
 **时间：2012-7-17
 **文件名：Teacher.java
 **包名：org.wjlmgqs.daomain
 **工程名：OnLineTest01
 */

package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.wjlmgqs.enums.UserAccountStatus;

/**
 * @author Administrator
 *
 */
public class Teacher implements Serializable{

	private static final long serialVersionUID = 1L;
	@Pattern(message="错误信息：姓名不能为空！",regexp=".*\\S+.*")
	private String name = null;
	@Pattern(message="错误信息：密码至少5位，由数字/字母/下划线组成！",regexp="\\w{5,}")
	private String password = null;
	@Pattern(message="错误信息：用户权限不存在！",regexp="[0|1]")
	private String power ;
	@Pattern(message="错误信息：性别只能为男或女，不能为空！",regexp="[0|1]")
	private String sex;
	
	@NotNull(message="错误信息：用户账户状态不能为空！")
	private UserAccountStatus status;
	@NotNull(message="错误信息：学科信息不能为空！")
	private Subject subject = null;
	@Pattern(message="错误信息：电话由数字和-组成,不能为空！",regexp="[\\d-]+")
	private String telephone = null;
	@Pattern(message="错误信息：账号为11位数字组成！",regexp="\\d{11}")
	private String userId = null;


	public String getName() {
		return name;
	}

	public String getPassword() {
		return password;
	}

	public String getPower() {
		return power;
	}

	public String getSex() {
		return sex;
	}

	public UserAccountStatus getStatus() {
		return status;
	}

	public Subject getSubject() {
		return subject;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getUserId() {
		return userId;
	}


	@Override
	public int hashCode() {
		return this.userId.hashCode();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public void setPower(String power) {
		this.power = power;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStatus(UserAccountStatus status) {
		this.status = status;
	}

	public void setSubject(Subject subject) {
		this.subject = subject;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
