package com.gy.hsxt.rabbitmq.common.enums;




public enum LevelSwitchEnum {
	DEBUG(1,"DEBUG"),INFO(2,"INFO"),WARN(3,"WARN"),ERROR(4,"ERROR"),FATAL(5,"FATAL");
	private Integer type;       
	private String name;
	LevelSwitchEnum(Integer type,String name){
		this.type = type;
		this.name = name;
	}
	
	public Integer getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public static Integer getType(String name){
		LevelSwitchEnum[] levels = LevelSwitchEnum.values();
		for(LevelSwitchEnum level : levels){
			if(name.equalsIgnoreCase(level.getName())){
				return level.getType();
			}
		}
		return -1;
	}
}
