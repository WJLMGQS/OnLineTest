package org.wjlmgqs.enums;
/**
 * @see 试卷的使用方向
 * @category EXAM:仅参加真正考试的组卷；IMITATE：仅参加模拟考试的组卷；ALL：既能参加模拟考试又能参加真正的考试；
 * */
public enum TestPaperUseType{
	ALL {
		@Override
		public String getName() {
			return "考试_模拟";
		}
	},
	EXAM {
		@Override
		public String getName() {
			return "模拟";
		}
	},
	IMITATE {
		@Override
		public String getName() {
			return "考试";
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