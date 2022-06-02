package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.mapper.AnswerMapper;

@Service
public class AnswerServiceImpl implements AnswerService {
	
	@Autowired
	AnswerMapper amapper;
	
	@Override
	public AnswerDto selectOne(int qnum) {
		return amapper.selectOne(qnum);
	}

	@Override
	public List<AnswerDto> list(int qnum) {
		return amapper.list(qnum);
	}

	@Override
	public boolean create(AnswerDto answerDto) {
		return amapper.create(answerDto) == 1;
	}

	@Override
	public boolean modify(AnswerDto answerDto) {
		return amapper.modify(answerDto) == 1;
	}

	@Override
	public boolean delete(int anum) {
		return amapper.delete(anum) == 1;
	}

}
