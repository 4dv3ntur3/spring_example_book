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
	
	// å ������ �Է� ���� �����ϴ� �޼���
	public int insert(Map<String, Object> map) {
		return this.sqlSessionTemplate.insert("book.insert", map);
		// book ������ insert �±� ã�Ƽ� ����? 
		
	}
	
	// å ������ �� ��ȸ ���� �����ϴ� �޼���
	public Map<String, Object> selectDetail(Map<String, Object> map) {
		// selectOne �޼���: �����͸� 1���� ������ �� ���
		// ���� �� ���� 0���� null ��ȯ, ���� ���� TooManyResultsException ���� ��ȯ
		return this.sqlSessionTemplate.selectOne("book.select_detail", map);
	}
	
	// å ������ ����
	public int update(Map<String, Object> map) {
		return this.sqlSessionTemplate.update("book.update", map);
	}
	
	
	// ����
	// ���� ���� (book_sql.xml�� �ۼ�) -> DAO�� �޼��� �߰� -> ���� Ŭ������ �޼��� �߰� -> ���������� ��Ʈ�ѷ��� �޼��� �߰� 
	public int delete(Map<String, Object> map) {
		return this.sqlSessionTemplate.delete("book.delete", map);
	}
	
	// ��ȸ
	public List<Map<String, Object>> selectList(Map<String, Object> map) {  
		return this.sqlSessionTemplate.selectList("book.select_list", map);  
		// ù ��° �Ķ���ʹ� ������ id, �� ���� �Ķ���ʹ� ���� �Ķ����
		// ��� ���� ��� ��ȯ. Map���� list�� �о�� �� �ִ�.
	}  

}


