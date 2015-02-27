package org.wjlmgqs.enums;

/** 
 * @see 教师对试卷的控制状态
 * @category	
 * 		0：UNOPEN		待开放的考试
 * 		1：OPENING		开放中的考试
 * 		2：OPENDED		已结束的考试
 *  	3：ALL			所有的考试
 *  * */
public enum ExamStatus{
	UNOPEN {
		@Override
		public String getName() {
			return "待开放";
		}
	},
	OPENING {
		@Override
		public String getName() {
			return "开放中";
		}
	},
	OPENDED {
		@Override
		public String getName() {
			return "已结束";
		}
	},
	ALL {
		@Override
		public String getName() {
			return "所有";
		}
	};
	public int getIndex() {
		return this.ordinal();
	}
	
	@Override
	public String toString() {
		return this.ordinal()+"";
	}
	public abstract String getName();

}