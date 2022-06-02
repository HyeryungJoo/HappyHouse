package com.ssafy.happyhouse.controller;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.happyhouse.model.dto.NoticeDto;
import com.ssafy.happyhouse.model.service.NoticeService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin("*")
@RestController
@RequestMapping("/notice")
public class NoticeRestController {

	private static final String fail = "오류가 발생하였습니다. 다시 시도해 주세요.";

	@Autowired
	NoticeService nservice;

	@ApiOperation(value = "공지등록", response = String.class)
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody NoticeDto noticedto) {
		if (nservice.insert(noticedto))
			return new ResponseEntity<String>("공지가 등록되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "공지삭제", response = String.class)
	@DeleteMapping("/{nnum}")
	public ResponseEntity<String> delete(@PathVariable("nnum") int nnum) {
		if (nservice.delete(nnum))
			return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "공지수정", response = String.class)
	@PutMapping("/{nnum}")
	public ResponseEntity<String> modify(@RequestBody NoticeDto noticedto) {
		if (nservice.modify(noticedto))
			return new ResponseEntity<String>("수정되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}

	// 페이징
	@ApiOperation(value = "공지리스트", response = List.class)
	@GetMapping
	public ResponseEntity<PageResponse> selectAll(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
		return new ResponseEntity<>(nservice.selectAll(page, size), HttpStatus.OK);
	}

	@ApiOperation(value = "하나 선택", response = NoticeDto.class)
	@GetMapping("/{nnum}")
	public ResponseEntity<NoticeDto> selectOne(@PathVariable("nnum") int nnum) {
		return new ResponseEntity<>(nservice.selectOne(nnum), HttpStatus.OK);
	}
}
