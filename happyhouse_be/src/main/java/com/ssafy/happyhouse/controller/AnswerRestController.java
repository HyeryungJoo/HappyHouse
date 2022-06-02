package com.ssafy.happyhouse.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.AnswerDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.service.AnswerService;

import io.swagger.annotations.ApiOperation;

//http://localhost:7777/swagger-ui.html
@CrossOrigin("*")
@RestController
@RequestMapping("/answer")
public class AnswerRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	AnswerService aservice;
	
	@ApiOperation(value = "하나 선택", response = AnswerDto.class)
	@GetMapping("/search/{anum}")
	public ResponseEntity<AnswerDto> selectOne(@PathVariable("anum") int anum) {
		return new ResponseEntity<>(aservice.selectOne(anum), HttpStatus.OK);
	}
	
	@ApiOperation(value = "답변 리스트", response = List.class)
	@GetMapping("{qnum}")
	public ResponseEntity<List<AnswerDto>> list(@PathVariable("qnum") int qnum) {
		return new ResponseEntity<>(aservice.list(qnum), HttpStatus.OK);
	}

	@ApiOperation(value = "답변등록", response = String.class)
	@PostMapping
	public ResponseEntity<String> create(@RequestBody AnswerDto answerDto) {
		if(aservice.create(answerDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "답변 수정", response = String.class)
	@PutMapping("{anum}")
	public ResponseEntity<String> modify(@RequestBody AnswerDto answerDto) {
		if(aservice.modify(answerDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "답변삭제", response = String.class)
	@DeleteMapping("{anum}")
	public ResponseEntity<String> delete(@PathVariable("anum") int anum) {
		if(aservice.delete(anum)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	
}
