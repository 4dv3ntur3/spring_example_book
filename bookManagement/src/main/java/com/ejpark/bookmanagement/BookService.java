package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

public interface BookService {

	// 메서드 시그니쳐 
	// 서비스의 인터페이스 메서드는 기계적으로 만들면 된다 
	String create(Map<String, Object> map);

	Map<String, Object> detail(Map<String, Object> map);

	boolean edit(Map<String, Object> map);

	boolean remove(Map<String, Object> map);

	List<Map<String, Object>> list(Map<String, Object> map);

}
