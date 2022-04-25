package com.ejpark.bookmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// ����Ͻ� Ŭ������ ��ġ�ϴ� ��
// ���� Ŭ����: controller�� DAO �����ϴ� ����
@Service
public class BookServiceImpl implements BookService {
	@Autowired
	BookDao bookDao;
	
	@Override
	// ���� override ������̼� �ް� �Լ� ���� ������ quick fix�� �������̽� �ڵ忡 �߰��� ���� ����
	public String create(Map<String, Object> map) {
		int affectRowCount = this.bookDao.insert(map);
		
		if (affectRowCount == 1) {
			return map.get("book_id").toString();
		}
		
		return null;
		
	}

	@Override
	public Map<String, Object> detail(Map<String, Object> map) {
		// ���񽺴� DAO�� ȣ���� ����� �ٷ� �����ϴ� �ϸ� �Ѵ� 
		return this.bookDao.selectDetail(map); // DAO ȣ���� ��� ����

	}
	
	// å ������ ����
	// ���� �������ų� �� �ʿ� ���� ����� �����Ǿ����� Ȯ�� 
	@Override
	public boolean edit(Map<String, Object> map) {
		int affectRowCount = this.bookDao.update(map);
		return affectRowCount == 1;
	}
	
	// sql -> ���� ���� (book_sql.xml) -> bookDAO�� �޼��� �ۼ� -> ���� Ŭ������ �޼��� �߰�
	@Override
	public boolean remove(Map<String, Object> map) {
		int affectRowCount = this.bookDao.delete(map);
		return affectRowCount == 1; // ������ �����Ǿ����� Ȯ�� 
	}
	
	@Override
	public List<Map<String, Object>> list(Map<String, Object> map) {
		return this.bookDao.selectList(map);
	}
	
	

	
	
	
	

}
