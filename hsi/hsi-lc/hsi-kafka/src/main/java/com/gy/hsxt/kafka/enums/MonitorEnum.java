package com.gy.hsxt.kafka.enums;


public enum MonitorEnum {
	HIGHT("3","高"),MIDDLE("2","中"),LOW("1","低");
	String level;
	String name;
	private MonitorEnum(String level,String name){
		this.level = level;
		this.name = name;
	}
	
	 public static String getName(String level) {
		 MonitorEnum[] monitors = MonitorEnum.values();
		 for(MonitorEnum m : monitors){
			 if(m.getLevel().equals(level)){
				 return m.getName();
			 }
		 }
		 return null;
	 }

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	 
	 
}
