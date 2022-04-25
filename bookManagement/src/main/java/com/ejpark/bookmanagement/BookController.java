package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
// @controller ������̼��� ���ӽ����̽�
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookController {
	
	@Autowired
	// ���� �� ������ ���� 
	// BookService �������̽��� ���Ǿ��� 
	BookService bookService;
	
	// /create�� get������� ������ �� (�ּ�â�� �ļ� �Է����� ��) book/create ȭ�� ������ 
	// view ���� ���� create
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		return new ModelAndView("book/create");	
	}
	
	// post ������� ������ �� 
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
	 ���� ��Ʈ��
	 ���� ��� /sample/test?a=1&b=2 �� �ּҰ� �ִٰ� �� ���� �Ʒ��� ����.

	URL : /sample/test
	���� ��Ʈ�� : ?a=1&b=2
	���� ��Ʈ���� ���� : ?
	���� ��Ʈ���� �׸� ���� : &
	���� ��Ʈ���� �׸�� : a=1 ,b=2
	URI : /sample/test?a=1&b=2
	 * 
	 */
	
	// ����ȸ ��ư Ŭ���ϸ� /detail?bookId=1 �� �� ��
	// ��, bookId=1
	// �� ������̼ǿ� ���� ���� ��Ʈ�� �Ķ���͸� ���� �� ���� 
	@RequestMapping(value="/detail", method=RequestMethod.GET)
	public ModelAndView detail(@RequestParam Map<String, Object> map) {
		
		// ������ �޾ƿ�
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		
		String bookId = map.get("bookId").toString();
		mav.addObject("bookId", bookId);
		
		// book/detail.jsp
		mav.setViewName("/book/detail");
		
		return mav;
		
		
	}
	
	// ������ DaO�� �޼��� �߰� -> Service �������̽�, ����ü�� �߰� -> ��Ʈ�ѷ��� �޼��� �߰� 
	// å ���� ȭ��
	// å �Է� + å �� ȭ����
	// ��, å �Է� ȭ���� ���������� ������ �����ͺ��̽����� ��ȸ�� ������ �� �־�� �� 
	@RequestMapping(value="/update", method=RequestMethod.GET)
	public ModelAndView update(@RequestParam Map<String, Object> map) {
		Map<String, Object> detailMap = this.bookService.detail(map);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("data", detailMap);
		mav.setViewName("book/update");
		return mav;
	}
	
	// å ���� ȭ�鿡�� å ���� ������� ������ �Ķ����: �� 4��
	// bookId : GET �Ķ���ͷ� ���� /update?bookId=1
	// form �±׿��� ���޵Ǵ� title, category, price
	// �������� http �޼��尡 GET���� POST���� ������� �ʰ� @RequestMapping ������̼��� ������
	// ������ �Ķ���͸� �־� �ش� 
	// �Ķ���� map �ȿ��� 4���� �����Ͱ� �� �� ���� ! 
	/*
	 * �Ƹ��� map�� ����? 
	 * {  
		"bookId": 1,  
		"title": "���� ����",  
		"category": "IT",  
		"price", "10000"  
		}  
	 */
	// method ����!!!! ���� ������ value�� ���� �޼��尡 ����!!!! 
	@RequestMapping(value="update", method=RequestMethod.POST)
	public ModelAndView updatePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		boolean isUpdateSuccess = this.bookService.edit(map);
		
		// �����ϸ� �������� �ݿ��Ͽ� �ٽ� �������� ������Ʈ
		if (isUpdateSuccess) {
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId=" + bookId);
			
		} else {
			mav = this.update(map); // ���� ȭ�� �ٽ� �����ֱ� (������ �� ���� ���)
		}
		
		return mav;
	}
	
	// ���� ��ư Ŭ�� -> POST (form)
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public ModelAndView deletePost(@RequestParam Map<String, Object> map) {
		ModelAndView mav = new ModelAndView();
		
		// ���� �����ߴ��� 
		boolean isDeleteSuccess = this.bookService.remove(map);
		
		if(isDeleteSuccess) {
			// �����ϸ� �� �������� �����Ƿ� ������� �����̷�Ʈ 
			mav.setViewName("redirect:/list");
		} else {
			
			// ���� ���н� �ٽ� �� ���� �������� 
			String bookId = map.get("bookId").toString();
			mav.setViewName("redirect:/detail?bookId="+bookId);
		}
		
		return mav;
	}
	
	@RequestMapping(value="list")
	public ModelAndView list(@RequestParam Map<String, Object> map) {
		List<Map<String, Object>> list = this.bookService.list(map);
		
		ModelAndView mav = new ModelAndView();
		
		mav.addObject("data", list); // book/list.jsp���� data��� �̸����� �� list ����
		
		
		// �˻� ��� �߰�
		// �Ķ���ͷ� keyword�� �Ѿ������ �˻�, �ƴϸ� �׳� ��� ��ȸ 
		if (map.containsKey("keyword")) {
			mav.addObject("keyword", map.get("keyword"));
		}
		
		mav.setViewName("/book/list");
		return mav;
	}
	
	
	
	
}
