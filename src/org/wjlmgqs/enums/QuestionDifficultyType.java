package org.wjlmgqs.enums;



/**
 * @ses 用于指定试题的难度
 * @category 
	EASY：简单
	GENERAL_EASY:较简单
	SIMPLE：中等
	GENERAL_HARD:较困难
	HARD：困难
 * */
public enum QuestionDifficultyType {
	EASY {
		@Override
		public String getName() {
			return "简单";
		}
	},
	GENERAL_EASY {
		@Override
		public String getName() {
			return "较简单";
		}
	},
	SIMPLE {
		@Override
		public String getName() {
			return "中等";
		}
	},
	GENERAL_HARD {
		@Override
		public String getName() {
			return "较困难";
		}
	},
	HARD {
		@Override
		public String getName() {
			return "困难";
		}
	};

	public int getIndex() {
		return this.ordinal();
	}

	@Override
	public String toString() {
		System.out.println("name:"+this.getName());
		return this.getName()+"";
	}
	public abstract String getName();
	

	
}
