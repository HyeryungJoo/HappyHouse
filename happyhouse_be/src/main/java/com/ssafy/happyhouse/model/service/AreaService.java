package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.model.dto.AreaDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface AreaService {

	List<AreaDto> selectGu();

	List<AreaDto> selectDong(String gugunName);
	
}
