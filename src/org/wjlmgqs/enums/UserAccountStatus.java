package org.wjlmgqs.enums;

/*
 * @see 用于表示账户的状态
 * @category ACTIVE已激活，UNACTIVE尚未激活
 * */

public enum UserAccountStatus{
	
	UNACTIVE {
		@Override
		public String getName() {
			return "关闭";
		}
	},
	ACTIVE {
		@Override
		public String getName() {
			return "开启";
		}
	};
	

	@Override
	public String toString() {
		return this.ordinal()+"";
	}
	
	public int getIndex(){
		return this.ordinal();
	};
	public abstract String getName();
}
