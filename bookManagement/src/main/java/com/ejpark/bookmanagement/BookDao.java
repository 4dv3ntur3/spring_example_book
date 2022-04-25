package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

// DAO(Data Access Object)
@Repository
public class BookDao {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	// 책 데이터 입력 쿼리 실행하는 메서드
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("book.insert", map);
		// book 매퍼의 insert 태그 찾아서 실행? 
		
	}
	
	// 책 데이터 상세 조회 쿼리 실행하는 메서드
	public Map<String, Object> selectDetail(Map<String, Object> map) {
		// selectOne 메서드: 데이터를 1개만 가져올 때 사용
		// 쿼리 행 수가 0개면 null 반환, 여러 개면 TooManyResultsException 예외 반환
		return this.sqlSessionTemplate.selectOne("book.select_detail", map);
	}
	
	// 책 상세정보 수정
	public int update(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("book.update", map);
	}
	
	
	// 순서
	// 쿼리 매핑 (book_sql.xml에 작성) -> DAO에 메서드 추가 -> 서비스 클래스에 메서드 추가 -> 마지막으로 컨트롤러에 메서드 추가 
	public int delete(Map<String, Object> map) {
		return this.sqlSessionTemplate.delete("book.delete", map);
	}
	
	// 조회
	public List<Map<String, Object>> selectList(Map<String, Object> map) {  
		return this.sqlSessionTemplate.selectList("book.select_list", map);  
		// 첫 번째 파라미터는 쿼리의 id, 두 번쨰 파라미터는 쿼리 파라미터
		// 결과 집합 목록 반환. Map들의 list로 읽어올 수 있다.
	}  

}


