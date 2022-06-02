package com.ssafy.happyhouse.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.AreaDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.mapper.AreaMapper;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	AreaMapper amapper;

	@Override
	public List<AreaDto> selectGu() {
		return amapper.selectGu();
	}

	@Override
	public List<AreaDto> selectDong(String gugunName) {
		return amapper.selectDong(gugunName);
	}
}
