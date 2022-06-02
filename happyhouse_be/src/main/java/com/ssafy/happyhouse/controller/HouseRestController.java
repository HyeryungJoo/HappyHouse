package com.ssafy.happyhouse.controller;

import java.util.List;

import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.ChartDto;
import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

//http://localhost:7777/swagger-ui.html
@CrossOrigin("*")
@RestController
@RequestMapping("/house")
public class HouseRestController {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	private static final int ResponseEntity = 0;

	@Autowired
	HouseService houseService;

	@ApiOperation(value = "매물 상세 조회", response = HouseDto.class)
	@GetMapping("/search/{no}")
	public ResponseEntity<HouseDto> selectOne(@PathVariable("no") int no) {
		return new ResponseEntity<>(houseService.selectOne(no), HttpStatus.OK);
	}

	// 페이징
	@ApiOperation(value = "매물 전체조회", response = List.class)
	@GetMapping("/list")
	public ResponseEntity<PageResponse> selectList(@RequestParam("keyword") String keyword,
			@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
		System.out.println("HouseRestController  " + keyword + ", " + size + ", " + page);
		return new ResponseEntity<>(houseService.selectList(keyword, page, size), HttpStatus.OK);
	}

	@ApiOperation(value = "최근 등록 매물 조회", response = List.class)
	@GetMapping("/current")
	public ResponseEntity<List<HouseDto>> selectCurrent() {
		return new ResponseEntity<>(houseService.selectCurrent(), HttpStatus.OK);
	}

	@ApiOperation(value = "동코드 조회", response = List.class)
	@GetMapping("/area/{dongCode}")
	public ResponseEntity<List<HouseDto>> selectDongCodeList(@PathVariable("dongCode") String dongCode) {
		return new ResponseEntity<>(houseService.selectDongCodeList(dongCode), HttpStatus.OK);
	}

	@ApiOperation(value = "아파트코드로 조회", response = List.class)
	@GetMapping("/apt/{aptCode}")
	public ResponseEntity<List<HouseDto>> selectAptCodeList(@PathVariable("aptCode") int aptCode) {
		return new ResponseEntity<>(houseService.selectAptCodeList(aptCode), HttpStatus.OK);
	}

	// 관심 매물 등록
	@ApiOperation(value = "관심지역 등록", response = String.class)
	@PostMapping("/search/like/{mno}/{hno}")
	public ResponseEntity<String> insertLikeHouse(@PathVariable("mno") int mno, @PathVariable("hno") int hno) {
		if (houseService.insertLikeHouse(mno, hno))
			return new ResponseEntity<String>("등록되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	// 관심 매물 조회
	@ApiOperation(value = "관심 매물 조회", response = List.class)
	@GetMapping("/search/like/{mno}")
	public ResponseEntity<List<HouseDto>> findByLikeHouse(@PathVariable("mno") int mno) {
		return new ResponseEntity<>(houseService.findByLikeHouse(mno), HttpStatus.OK);
	}

	// 관심 매물 삭제
	@ApiOperation(value = "관심 매물 삭제", response = String.class)
	@DeleteMapping("/search/like/{mno}/{hno}")
	public ResponseEntity<String> deleteLikeHouse(@PathVariable("mno") int mno, @PathVariable("hno") int hno) {
		if (houseService.deleteLikeHouse(mno, hno))
			return new ResponseEntity<String>("삭제되었습니다.", HttpStatus.OK);
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	// 찜한 매물 순위 조회
	@ApiOperation(value = "관심 매물 순위 조회", response = List.class)
	@GetMapping("/search/like/rank")
	public ResponseEntity<List<HouseDto>> findByLikeHouseRank() {
		return new ResponseEntity<>(houseService.findByLikeHouseRank(), HttpStatus.OK);
	}

	// 월별 물량
	@ApiOperation(value = "월별 물량", response = List.class)
	@GetMapping("/countByMonth")
	public ResponseEntity<List<Integer>> countByMonth() {
		return new ResponseEntity<>(houseService.countByMonth(), HttpStatus.OK);
	}

	// 동별 매물 많이 나온 순
	@ApiOperation(value = "동별 물량", response = List.class)
	@GetMapping("/maxAmountByDong")
	public ResponseEntity<List<ChartDto>> maxAmountByDong() {
		return new ResponseEntity<>(houseService.maxAmountByDong(), HttpStatus.OK);
	}

	// 구별 매물 많이 나온 순
	@ApiOperation(value = "구별 물량", response = List.class)
	@GetMapping("/maxAmountByGu")
	public ResponseEntity<List<ChartDto>> maxAmountByGu() {
		return new ResponseEntity<>(houseService.maxAmountByGu(), HttpStatus.OK);
	}

	// 서울 평수 별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수
	@ApiOperation(value = "서울 평수 별", response = List.class)
	@GetMapping("/seoul")
	public ResponseEntity<List<ChartDto>> seoul(@RequestParam("start") int start, @RequestParam("end") int end) {
		System.out.println("start:" + start + "end:" + end);
		return new ResponseEntity<List<ChartDto>>(houseService.seoul(start, end), HttpStatus.OK);
	}

	// 서울 평수 별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수 40평 이상
	@ApiOperation(value = "서울 평수 별", response = List.class)
	@GetMapping("/seoul40")
	public ResponseEntity<List<ChartDto>> seoul40(@RequestParam("start") int start) {
		System.out.println("start:" + start);
		return new ResponseEntity<List<ChartDto>>(houseService.seoul40(start), HttpStatus.OK);
	}

	// 서울 평수 별 동별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수
	@ApiOperation(value = "서울 평수 별(동)", response = List.class)
	@GetMapping("/dongper")
	public ResponseEntity<List<ChartDto>> dongper(@RequestParam("dongName") String dongName,
			@RequestParam("start") int start, @RequestParam("end") int end) {
		return new ResponseEntity<List<ChartDto>>(houseService.dongper(dongName, start, end), HttpStatus.OK);
	}

	// 서울 평수 별 동별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수 40평이상
	@ApiOperation(value = "서울 평수 별(동)", response = List.class)
	@GetMapping("/dongper40")
	public ResponseEntity<List<ChartDto>> dongper40(@RequestParam("dongName") String dongName,
			@RequestParam("start") int start) {
		return new ResponseEntity<List<ChartDto>>(houseService.dongper40(dongName, start), HttpStatus.OK);
	}

	// 서울 평수 별 구별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수
	@ApiOperation(value = "서울 평수 별(구)", response = List.class)
	@GetMapping("/guper")
	public ResponseEntity<List<ChartDto>> guper(@RequestParam("gugunName") String gugunName,
			@RequestParam("start") int start, @RequestParam("end") int end) {
		return new ResponseEntity<List<ChartDto>>(houseService.guper(gugunName, start, end), HttpStatus.OK);
	}

	// 서울 평수 별 구 별 집 값(최고, 최저, 평균), 준공년도(최근, 오래), 평균 평수, 총 갯수 40평이상
	@ApiOperation(value = "서울 평수 별(구)", response = List.class)
	@GetMapping("/guper40")
	public ResponseEntity<List<ChartDto>> guper40(@RequestParam("gugunName") String gugunName,
			@RequestParam("start") int start) {
		return new ResponseEntity<List<ChartDto>>(houseService.guper40(gugunName, start), HttpStatus.OK);
	}
}
