package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.mapper.QuestionMapper;

@Service
public class QuestionServiceImpl implements QuestionService{

	@Autowired
	QuestionMapper qmapper;

	@Override
	public boolean insert(QuestionDto questionDto) {
		return qmapper.insert(questionDto)==1;
	}

	@Override
	public boolean delete(int qnum) {
		return qmapper.delete(qnum)==1;
	}

	@Override
	public boolean modify(QuestionDto questionDto) {
		return qmapper.modify(questionDto)==1;
	}

	@Override
	public QuestionDto selectOne(int qnum) {
		return qmapper.selectOne(qnum);
	}

	//페이징
	@Override
	public PageResponse selectAll(int page, int size) {
		List<QuestionDto> contents = qmapper.selectAll(size, (page-1) * size);
		int count = qmapper.countAll();
		PageResponse pageResponse = new PageResponse();
		for (QuestionDto content : contents) {
			pageResponse.getContent().add(content);
		}
		pageResponse.makePageList(count, page, size);
		return pageResponse;
	}

	@Override
	public List<QuestionDto> findByMyQuestion(String qmid) {
		return qmapper.findByMyQuestion(qmid);
	}


}
