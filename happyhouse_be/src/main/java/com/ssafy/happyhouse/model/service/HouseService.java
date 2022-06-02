package com.ssafy.happyhouse.model.service;

import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.ChartDto;
import com.ssafy.happyhouse.model.dto.HouseDto;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface HouseService {
	// 매물 상세 조회
	HouseDto selectOne(int no);

	// 매물 리스트 조회
	PageResponse selectList(String keyword, int page, int size);

	// 매물 동코드 조회
	List<HouseDto> selectDongCodeList(String dongCode);

	// 매물 아파트코드 조회
	List<HouseDto> selectAptCodeList(int aptCode);

	// 관심매물 등록
	boolean insertLikeHouse(int mno, int hno);

	// 회원의 관심매물 조회
	List<HouseDto> findByLikeHouse(int mno);

	// 회원의 관심매물 삭제
	boolean deleteLikeHouse(int mno, int hno);

	List<HouseDto> selectCurrent();

	// 회원의 관심매물 조회
	List<HouseDto> findByLikeHouseRank();

	// 월별 매물 개수 조회
	List<Integer> countByMonth();

	// 매물 많은 순 by 동
	List<ChartDto> maxAmountByDong();

	// 매물 많은 순 by 구
	List<ChartDto> maxAmountByGu();

	// 서울 전체 평형 별 집 값 정보
	List<ChartDto> seoul(int start, int end);

	// 서울 전체 평형 별 집 값 정보 40평이상
	List<ChartDto> seoul40(@Param("start") int start);

	// 선택한 동 평형 별 집 값 정보
	List<ChartDto> dongper(@Param("dongName") String dongName, @Param("start") int start, @Param("end") int end);

	// 선택한 동 평형 별 집 값 정보 40평이상
	List<ChartDto> dongper40(@Param("dongName") String dongName, @Param("start") int start);

	// 선택한 구 평형 별 집 값 정보
	List<ChartDto> guper(@Param("gugunName") String gugunName, @Param("start") int start, @Param("end") int end);

	// 선택한 구 평형 별 집 값 정보 40평이상
	List<ChartDto> guper40(@Param("gugunName") String gugunName, @Param("start") int start);
}
