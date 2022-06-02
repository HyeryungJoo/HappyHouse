package com.ssafy.happyhouse.controller;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ssafy.happyhouse.model.dto.AreaDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.service.AreaService;
import com.ssafy.happyhouse.model.service.QuestionService;

import io.swagger.annotations.ApiOperation;

//http://localhost:7779/swagger-ui.html
@CrossOrigin("*")
@RestController
@RequestMapping("/question")
public class QuestionRestController {

	private static final String fail = "오류가 발생하였습니다. 다시 시도해 주세요.";

	@Autowired
	QuestionService qservice;

	@ApiOperation(value = "질문등록", response = String.class)
	@PostMapping
	public ResponseEntity<String> insert(@RequestBody QuestionDto questionDto) {
		if (qservice.insert(questionDto))
			return new ResponseEntity<String>("질문이 등록되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "질문삭제", response = String.class)
	@DeleteMapping("{qnum}")
	public ResponseEntity<String> delete(@PathVariable("qnum") int qnum) {
		if (qservice.delete(qnum))
			return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "질문수정", response = String.class)
	@PutMapping("{qnum}")
	public ResponseEntity<String> modify(@RequestBody QuestionDto questionDto) {
		if (qservice.modify(questionDto))
			return new ResponseEntity<String>("수정되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(fail, HttpStatus.NO_CONTENT);
	}
	@ApiOperation(value = "하나 선택", response = QuestionDto.class)
	@GetMapping("/{qnum}")
	public ResponseEntity<QuestionDto> selectOne(@PathVariable("qnum") int qnum) {
		return new ResponseEntity<>(qservice.selectOne(qnum), HttpStatus.OK);
	}
	// 페이징
	@ApiOperation(value = "질문리스트", response = List.class)
	@GetMapping
	public ResponseEntity<PageResponse> selectAll(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
		return new ResponseEntity<>(qservice.selectAll(page, size), HttpStatus.OK);
	}

	@ApiOperation(value = "회원이 게시한 글 조회", response = List.class)
	@GetMapping("/search/mylist/{mid}")
	public ResponseEntity<List<QuestionDto>> findByMyQuestion(@PathVariable("mid") String mid) {
		return new ResponseEntity<>(qservice.findByMyQuestion(mid), HttpStatus.OK);
	}
}
