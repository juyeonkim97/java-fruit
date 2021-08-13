package com.example.fruit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.fruit.service.SearchService;

@RestController
public class SearchController {
	@Autowired
	SearchService searchService;

	@RequestMapping(value = "/search")
	public ModelAndView search(@RequestParam("category") String category, @RequestParam("keyword") String keyword,
			ModelAndView mv) {
		String price = "";
		String resultMsg = "";
		
		//검색어를 입력하지 않았을 시 api 호출하지 않습니다.
		if (keyword.equals("")) { 
			resultMsg = "검색어를 입력해주세요.";
		} else {
			if (category.equals("fruit")) {// 카테고리 과일 선택
				price = searchService.getFruitPrice(keyword);
			} else {// 카테고리 채소 선택
				price = searchService.getVegetablePrice(keyword);
			}
			//price의 정보가 찾지 못했을 때 넘어오는 값은 fail
			if(price.equals("fail")) resultMsg = keyword + "의 가격 정보를 찾지 못 했습니다.";
			else resultMsg = keyword + "의 가격은 " + price + "원입니다.";
		}
		mv.addObject("result", resultMsg);
		mv.setViewName("index");
		return mv;
	}
}
