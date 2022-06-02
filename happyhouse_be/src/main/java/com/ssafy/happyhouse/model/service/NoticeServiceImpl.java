package com.ssafy.happyhouse.model.service;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Autowired
	NoticeMapper nmapper;
	
	@Override
	public boolean insert(NoticeDto noticedto) {
		return nmapper.insert(noticedto)==1;
	}

	@Override
	public boolean delete(int nnum) {
		return nmapper.delete(nnum)==1;
	}

	@Override
	public boolean modify(NoticeDto noticedto) {
		return nmapper.modify(noticedto)==1;
	}

	@Override
	public PageResponse selectAll(int page, int size) {
		List<NoticeDto> contents = nmapper.selectAll(size, (page-1) * size);
		int count = nmapper.countAll();
		PageResponse pageResponse = new PageResponse();
		for (NoticeDto content : contents) {
			pageResponse.getContent().add(content);
		}
		pageResponse.makePageList(count, page, size);
		return pageResponse;
	}

	@Override
	public NoticeDto selectOne(int nnum) {
		return nmapper.selectOne(nnum);
	}

}
