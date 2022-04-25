package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 비즈니스 클래스가 위치하는 곳
// 서비스 클래스: controller와 DAO 연결하는 역할
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookDao bookDao;
	
	@Override
	// 먼저 override 어노테이션 달고 함수 만든 다음에 quick fix로 인터페이스 코드에 추가할 수도 있음
	public String create(Map<String, Object> map) {
		int affectRowCount = this.bookDao.insert(map);
		
		if (affectRowCount == 1) {
			return map.get("book_id").toString();
		}
		
		return null;
		
	}

	@Override
	public Map<String, Object> detail(Map<String, Object> map) {
		// 서비스는 DAO를 호출한 결과를 바로 리턴하는 일만 한다 
		return this.bookDao.selectDetail(map); // DAO 호출한 결과 리턴

	}
	
	// 책 상세정보 수정
	// 값을 가져오거나 할 필요 없이 제대로 수정되었는지 확인 
	@Override
	public boolean edit(Map<String, Object> map) {
		int affectRowCount = this.bookDao.update(map);
		return affectRowCount == 1;
	}
	
	// sql -> 쿼리 매핑 (book_sql.xml) -> bookDAO에 메서드 작성 -> 서비스 클래스에 메서드 추가
	@Override
	public boolean remove(Map<String, Object> map) {
		int affectRowCount = this.bookDao.delete(map);
		return affectRowCount == 1; // 무사히 삭제되었는지 확인 
	}
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return this.bookDao.selectList(map);
	}
	
	

	
	
	
	

}
