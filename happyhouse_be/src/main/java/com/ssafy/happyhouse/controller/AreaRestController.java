package com.ssafy.happyhouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.happyhouse.model.dto.AreaDto;
import com.ssafy.happyhouse.model.dto.QuestionDto;
import com.ssafy.happyhouse.model.service.AreaService;

import io.swagger.annotations.ApiOperation;

//http://localhost:7779/swagger-ui.html
@CrossOrigin("*")
@RestController
@RequestMapping("/gudong")
public class AreaRestController {

	@Autowired
	AreaService aservice;

	@ApiOperation(value = "구 선택", response = List.class)
	@GetMapping
	public ResponseEntity<List<AreaDto>> selectGu() {
		return new ResponseEntity<>(aservice.selectGu(), HttpStatus.OK);
	}

	@ApiOperation(value = "동 선택", response = List.class)
	@GetMapping("/{gugunName}")
	public ResponseEntity<List<AreaDto>> selectDong(@PathVariable String gugunName) {
		return new ResponseEntity<>(aservice.selectDong(gugunName), HttpStatus.OK);
	}
}
