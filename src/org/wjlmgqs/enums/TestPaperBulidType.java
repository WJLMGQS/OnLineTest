package org.wjlmgqs.enums;

public enum TestPaperBulidType{
	AUTO {
		@Override
		public String getName() {
			return "遗传";
		}
	},
	LABOUR {
		@Override
		public String getName() {
			return "手动";
		}
	};
	@Override
	public String toString() {
		return this.ordinal()+"";
	}
	
	public int getIndex() {
		return this.ordinal();
	}
	
	public abstract String getName();
}