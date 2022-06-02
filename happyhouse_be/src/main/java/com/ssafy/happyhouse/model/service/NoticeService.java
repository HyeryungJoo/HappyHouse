package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.NoticeDto;

public interface NoticeService {
	
	boolean insert(NoticeDto noticedto);

	boolean delete(int nnum);

	boolean modify(NoticeDto noticedto);

	PageResponse selectAll(int page, int size);

	NoticeDto selectOne(int nnum);
}
