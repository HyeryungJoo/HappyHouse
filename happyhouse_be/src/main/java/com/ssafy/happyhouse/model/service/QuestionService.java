package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface QuestionService {

	boolean insert(QuestionDto questionDto);

	boolean delete(int qnum);

	boolean modify(QuestionDto questionDto);

	QuestionDto selectOne(int qnum);

	PageResponse selectAll(int page, int size);

	List<QuestionDto> findByMyQuestion(String qmid);
}
