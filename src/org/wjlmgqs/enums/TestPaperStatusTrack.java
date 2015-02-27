package org.wjlmgqs.enums;

/** 
 * @see 教师对试卷的控制状态
 * @category	
 * 		0：CREATED		创建但是尚未审核
 * 		1：VERIFING		提交审核中
 * 		2：UNAUTHORIZED	审核没有通过被返回
 *  	3：UNPUBLISH		审核通过还没有发布
 *  	4：PUBLISHED		审核通过已经发布
 *  	5：ALL			所有
 *  * */
public enum TestPaperStatusTrack{
	CREATED {
		@Override
		public String getName() {
			return "未审核";
		}
	},
	VERIFING {
		@Override
		public String getName() {
			return "审核中";
		}
	},
	UNAUTHORIZED {
		@Override
		public String getName() {
			return "审核失败";
		}
	},
	UNPUBLISH {
		@Override
		public String getName() {
			return "未发布";
		}
	},
	PUBLISHED {
		@Override
		public String getName() {
			return "已发布";
		}
	},
	ALL {
		@Override
		public String getName() {
			return "所有";
		}
	};;

	public int getIndex() {
		return this.ordinal();
	}
	
	@Override
	public String toString() {
		return this.ordinal()+"";
	}
	public abstract String getName();

}