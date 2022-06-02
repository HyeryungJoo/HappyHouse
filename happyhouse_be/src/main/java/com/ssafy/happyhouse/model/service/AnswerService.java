package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface AnswerService {
	
	AnswerDto selectOne(int anum);
	
	
	//답변 리스트 조회
	List<AnswerDto> list(int qnum);
	
	// 답변 등록
	boolean create(AnswerDto answerDto);
	
	// 답변 수정 
	boolean modify(AnswerDto answerDto);
	
	
	// 답변 삭제
	boolean delete(int anum);
}
