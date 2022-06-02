package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.NoticeDto;

public interface NoticeMapper {

	int insert(NoticeDto noticedto);

	int delete(int nnum);

	int modify(NoticeDto noticedto);

	// 페이징
	List<NoticeDto> selectAll(int size, int start);

	// 페이징에 필요한 카운트
	int countAll();

	NoticeDto selectOne(int nnum);
}
