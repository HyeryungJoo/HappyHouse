package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface AnswerMapper {
	
	AnswerDto selectOne(int anum);
	
	List<AnswerDto> list(int qnum);
	
	int create(AnswerDto answerDto);
	
	int modify(AnswerDto answerDto);
	
	int delete(int anum);
}
