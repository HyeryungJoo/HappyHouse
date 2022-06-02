package com.ssafy.happyhouse.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ssafy.happyhouse.model.dto.ChartDto;
import com.ssafy.happyhouse.model.dto.HouseDto;

public interface HouseMapper {
	// 매물 전체 조회 (페이징)
	List<HouseDto> selectList(@Param("keyword") String keyword, @Param("size") int size, @Param("start") int start);

	int selectTotalCount(@Param("keyword") String keyword);

	// 매물 1개(상세) 조회
	HouseDto selectOne(int no);

	// 매물 동별 리스트 조회
	List<HouseDto> selectDongCodeList(String dongCode);

	// 아파트별 리스트 조회
	List<HouseDto> selectAptCodeList(int aptCode);

	// 관심 매물 등록
	int insertLikeHouse(@Param("mno") int mno, @Param("hno") int hno);

	int updateLikeCountPlus(int hno);

	// 관심 매물 조회
	List<HouseDto> findByLikeHouse(int mno);

	// 관심 매물 삭제
	int deleteLikeHouse(@Param("mno") int mno, @Param("hno") int hno);

	int updateLikeCountMinus(int hno);

	// 최근 등록 매물 조회
	List<HouseDto> selectCurrent();

	List<HouseDto> findByLikeHouseRank();

	// 월별 매물 개수 조회
	List<Integer> countByMonth();

	// 매물 많은 순 by 동
	List<ChartDto> maxAmountByDong();

	// 매물 많은 순 by 구
	List<ChartDto> maxAmountByGu();

	// 서울 전체 평형 별 집 값 정보
	List<ChartDto> seoul(@Param("start") int start, @Param("end") int end);

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
