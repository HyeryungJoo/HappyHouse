package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import com.ssafy.happyhouse.model.dto.AreaDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;

public interface AreaMapper {

	List<AreaDto> selectGu();

	List<AreaDto> selectDong(String gugunName);
	
}
