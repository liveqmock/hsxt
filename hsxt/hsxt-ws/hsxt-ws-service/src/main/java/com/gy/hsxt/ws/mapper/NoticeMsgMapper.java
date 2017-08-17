package com.gy.hsxt.ws.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gy.hsxt.ws.bean.NoticeMsg;

public interface NoticeMsgMapper {

	int insertSelective(NoticeMsg record);

	NoticeMsg selectByPrimaryKey(String msgId);

	int updateByPrimaryKeySelective(NoticeMsg record);

	List<NoticeMsg> queryNotice(@Param("msgType") Integer msgType,
			@Param("sendStatus") Integer sendStatus);

	void handSendMsg();
}