package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// @controller 어노테이션의 네임스페이스
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {
	
	@Autowired
	// 서비스 빈 의존성 주입 
	// BookService 인터페이스가 사용되었음 
	BookService bookService;
	
	// /create가 get방식으로 들어왔을 때 (주소창에 쳐서 입력했을 때) book/create 화면 보여줌 
	// view 폴더 밑의 create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return new ModelAndView("book/create");	
	}
	
	// post 방식으로 들어왔을 때 
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ModelAndView createPost(@RequestParam Map<String, Object> map) {
		
		ModelAndView mav = new ModelAndView();
		
		String bookId = this.bookService.create(map);
		
		if (bookId == null) {
			mav.setViewName("redirect:/create");
		}
		
		else {
			mav.setViewName("redirect:/detail?bookId=" +bookId);
		}
		
		return mav;
	}
	
	/*
	 쿼리 스트링
	 예를 들어 /sample/test?a=1&b=2 웹 주소가 있다고 해 보면 아래와 같다.

	URL : /sample/test
	쿼리 스트링 : ?a=1&b=2
	쿼리 스트링의 시작 : ?
	쿼리 스트링의 항목 구분 : &
	쿼리 스트링의 항목들 : a=1 ,b=2
	URI : /sample/test?a=1&b=2
	 * 
	 */
	
	// 상세조회 버튼 클릭하면 /detail?bookId=1 로 들어갈 것
	// 즉, bookId=1
	// 이 어노테이션에 의해 쿼리 스트링 파라미터를 읽을 수 있음 
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public ModelAndView detail(@RequestParam Map<String, Object> map) {
		
		// 데이터 받아옴
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		
		String bookId = map.get("bookId").toString();
		mav.addObject("bookId", bookId);
		
		// book/detail.jsp
		mav.setViewName("/book/detail");
		
		return mav;
		
		
	}
	
	// 순서가 DaO에 메서드 추가 -> Service 인터페이스, 구현체에 추가 -> 컨트롤러에 메서드 추가 
	// 책 수정 화면
	// 책 입력 + 책 상세 화면임
	// 즉, 책 입력 화면의 형식이지만 내용은 데이터베이스에서 조회한 내용이 들어가 있어야 함 
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public ModelAndView update(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("book/update");
		return mav;
	}
	
	// 책 수정 화면에서 책 수정 기능으로 보내는 파라미터: 총 4개
	// bookId : GET 파라미터로 전달 /update?bookId=1
	// form 태그에서 전달되는 title, category, price
	// 스프링은 http 메서드가 GET인지 POST인지 상관하지 않고 @RequestMapping 어노테이션이 있으면
	// 무조건 파라미터를 넣어 준다 
	// 파라미터 map 안에는 4개의 데이터가 다 들어가 있음 ! 
	/*
	 * 아마도 map의 구조? 
	 * {  
		"bookId": 1,  
		"title": "제목 수정",  
		"category": "IT",  
		"price", "10000"  
		}  
	 */
	// method 조심!!!! 위에 동일한 value로 같은 메서드가 있음!!!! 
	@RequestMapping(value="update", method=RequestMethod.POST)
	public ModelAndView updatePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		boolean isUpdateSuccess = this.bookService.edit(map);
		
		// 성공하면 수정사항 반영하여 다시 상세페이지 업데이트
		if (isUpdateSuccess) {
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId=" + bookId);
			
		} else {
			mav = this.update(map); // 갱신 화면 다시 보여주기 (갱신이 안 됐을 경우)
		}
		
		return mav;
	}
	
	// 삭제 버튼 클릭 -> POST (form)
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public ModelAndView deletePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		// 삭제 성공했는지 
		boolean isDeleteSuccess = this.bookService.remove(map);
		
		if(isDeleteSuccess) {
			// 성공하면 상세 페이지가 없으므로 목록으로 리다이렉트 
			mav.setViewName("redirect:/list");
		} else {
			
			// 삭제 실패시 다시 상세 정보 페이지로 
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId="+bookId);
		}
		
		return mav;
	}
	
	@RequestMapping(value="list")
	public ModelAndView list(@RequestParam Map<String, Object> map) {
		List<Map<String, Object>> list = this.bookService.list(map);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("data", list); // book/list.jsp에서 data라는 이름으로 이 list 참조
		
		
		// 검색 기능 추가
		// 파라미터로 keyword가 넘어왔으면 검색, 아니면 그냥 목록 조회 
		if (map.containsKey("keyword")) {
			mav.addObject("keyword", map.get("keyword"));
		}
		
		mav.setViewName("/book/list");
		return mav;
	}
	
	
	
	
}
