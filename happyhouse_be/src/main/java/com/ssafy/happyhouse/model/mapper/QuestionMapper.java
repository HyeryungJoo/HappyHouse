package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface QuestionMapper {

	int insert(QuestionDto questionDto);

	int delete(int qnum);

	int modify(QuestionDto questionDto);

	QuestionDto selectOne(int qnum);

	// 페이징
	List<QuestionDto> selectAll(int size, int start);

	// 페이징에 필요한 카운트
	int countAll();

	List<QuestionDto> findByMyQuestion(String qmid);
}
