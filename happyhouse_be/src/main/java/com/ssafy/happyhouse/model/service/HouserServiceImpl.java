package com.ssafy.happyhouse.model.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.happyhouse.dto.response.PageResponse;
import com.ssafy.happyhouse.model.dto.ChartDto;
import com.ssafy.happyhouse.model.dto.HouseDto;
import com.ssafy.happyhouse.model.mapper.HouseMapper;

@Service
public class HouserServiceImpl implements HouseService {

	@Autowired
	HouseMapper hmapper;

	@Override
	public HouseDto selectOne(int no) {
		HouseDto house = hmapper.selectOne(no);
		String temp = house.getDealAmount();

		String piece = temp.substring(0, temp.length() - 4);
		String piece2 = temp.substring(temp.length() - 4, temp.length());
		String s = piece + "억 " + piece2 + "만원";
		house.setDealAmount(s);

		float tmp = Float.parseFloat(house.getArea());
		tmp = (int) (tmp / 3.3058);
		house.setArea(String.valueOf(Math.ceil(tmp)));

		return house;
	}

	@Override
	public PageResponse selectList(String keyword, int page, int size) {
		System.out.println("HouserServiceImpl " + keyword + " " + size + " " + page);
		List<HouseDto> contents = hmapper.selectList(keyword, size, (page - 1) * size);
		int count = hmapper.selectTotalCount(keyword);
		PageResponse pageResponse = new PageResponse();
		for (HouseDto content : contents) {
			String temp = content.getDealAmount();

			String piece = temp.substring(0, temp.length() - 4);
			String piece2 = temp.substring(temp.length() - 4, temp.length());
			String s = piece + "억 " + piece2 + "만원";
			content.setDealAmount(s);

			float tmp = Float.parseFloat(content.getArea());
			tmp = (int) (tmp / 3.3058);
			content.setArea(String.valueOf(Math.ceil(tmp)));
			pageResponse.getContent().add(content);
		}
		pageResponse.makePageList(count, page, size);
		return pageResponse;
	}

	@Override
	public List<HouseDto> selectDongCodeList(String dongCode) {
		return hmapper.selectDongCodeList(dongCode);
	}

	@Override
	public List<HouseDto> selectAptCodeList(int aptCode) {
		return hmapper.selectAptCodeList(aptCode);
	}

	@Override
	public boolean insertLikeHouse(int mno, int hno) {
		return hmapper.insertLikeHouse(mno, hno) == 1 && hmapper.updateLikeCountPlus(hno) ==1;
	}

	@Override
	public List<HouseDto> findByLikeHouse(int mno) {
		return hmapper.findByLikeHouse(mno);
	}

	@Override
	public boolean deleteLikeHouse(int mno, int hno) {
		return hmapper.deleteLikeHouse(mno, hno) == 1 && hmapper.updateLikeCountMinus(hno) ==1;
	}

	@Override
	public List<HouseDto> selectCurrent() {
		List<HouseDto> tempList = hmapper.selectCurrent();
		List<HouseDto> contents = new ArrayList<HouseDto>();
		for (HouseDto content : tempList) {
			String temp = content.getDealAmount();

			String piece = temp.substring(0, temp.length() - 4);
			String piece2 = temp.substring(temp.length() - 4, temp.length());
			String s = piece + "억 " + piece2 + "만원";
			content.setDealAmount(s);

			contents.add(content);
		}
		return contents;
	}

	@Override
	public List<HouseDto> findByLikeHouseRank() {
		List<HouseDto> tempList = hmapper.findByLikeHouseRank();
		List<HouseDto> contents = new ArrayList<HouseDto>();
		for (HouseDto content : tempList) {
			String temp = content.getDealAmount();

			String piece = temp.substring(0, temp.length() - 4);
			String piece2 = temp.substring(temp.length() - 4, temp.length());
			String s = piece + "억 " + piece2 + "만원";
			content.setDealAmount(s);

			contents.add(content);
		}
		return contents;
	}

	@Override
	public List<Integer> countByMonth() {
		return hmapper.countByMonth();
	}

	@Override
	public List<ChartDto> maxAmountByDong() {
		return hmapper.maxAmountByDong();
	}

	@Override
	public List<ChartDto> maxAmountByGu() {
		return hmapper.maxAmountByGu();
	}

	@Override
	public List<ChartDto> seoul(int start, int end) {
		return hmapper.seoul(start, end);
	}

	@Override
	public List<ChartDto> seoul40(int start) {
		return hmapper.seoul40(start);
	}

	@Override
	public List<ChartDto> dongper(String dongName, int start, int end) {
		return hmapper.dongper(dongName, start, end);
	}

	@Override
	public List<ChartDto> dongper40(String dongName, int start) {
		return hmapper.dongper40(dongName, start);
	}

	@Override
	public List<ChartDto> guper(String gugunName, int start, int end) {
		return hmapper.guper(gugunName, start, end);
	}

	@Override
	public List<ChartDto> guper40(String gugunName, int start) {
		return hmapper.guper40(gugunName, start);
	}

}
