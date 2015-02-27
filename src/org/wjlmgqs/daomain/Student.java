/**
 **作者：翁加林
 **时间：2012-7-26
 **文件名：Student.java
 **包名：org.wjlmgqs.daomain
 **工程名：OnLineTest03
 */

package org.wjlmgqs.daomain;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.wjlmgqs.enums.UserAccountStatus;

public class Student implements Serializable{
	private static final long serialVersionUID = 1L;
	@NotNull(message="错误信息：班级信息不能为空！")
	private Classes classes  = null;
	@Pattern(regexp=".*\\S+.*",message="错误信息：姓名不能为空！")
	private String name = null;
	@Pattern(regexp="\\w{5,}",message="错误信息：密码至少5位，由数字/字母/下划线组成！")
	private String password = null;
	private String photo = null;
	@Pattern(regexp="['1'|'0']",message="错误信息：性别只能为男或女，不能为空！")
	private String sex ;
	@NotNull(message="错误信息：账户状态不能为空！")
	private UserAccountStatus status;
	@Pattern(regexp="[\\d-]+",message="错误信息：电话由数字和-组成,不能为空！")
	private String telephone = null;
	@Pattern(regexp="\\d{11}",message="错误信息：学生编号格式不正确！")
	private String userId = null;
	
	public Classes getClasses() {
		return classes;
	}

	public String getName() {
		return name;
	}
	public String getPassword() {
		return password;
	}
	public String getPhoto() {
		return photo;
	}
	public String getSex() {
		return sex;
	}

	public UserAccountStatus getStatus() {
		return status;
	}
	public String getTelephone() {
		return telephone;
	}
	public String getUserId() {
		return userId;
	};
	public void setClasses(Classes classes) {
		this.classes = classes;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setStatus(UserAccountStatus status) {
		this.status = status;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}


}
