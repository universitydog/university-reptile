package com.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dao.MySqlDaoFactory;
import com.orm.Article;
import com.orm.Author;
import com.orm.Type;
import com.ttm.util.Dumper;

public class Demo {

	@Test
	public void testA() {
//		MySqlDaoFactory mf = new MySqlDaoFactory();
//		Map<String, Object> query = new HashMap<>();
//		query.put("title", "");
//		Dumper.dump(mf.find(CsdnEntity.class, query));
		
		
	}
	
	public void TestType() {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		
		Type type = new Type();
		type.setName("csdn");
		type.setIntroduce("http://www.csdn.net");
		mf.save(Type.class, type);
		
	}
	
	@Test
	public void TestAuthor() {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		Type type = (Type) mf.findById(Type.class, 1);
		Dumper.dump(type);
		Author a = new Author();
		a.setId("FeeLang");
		a.setRealName("");
		a.setNickName("feelang");
		a.setIntroduce("");
		a.setRank(2371);
		a.setLove(0);
		a.setCreateTime("2016-03-08 00:00:00");
		a.setImgurl("http://img.jsp");
		a.setTypeId(type);
		System.out.println(mf.save(Author.class, a));
	}
	
	public void TestFind() {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		Map<String, Object> query = new HashMap<>();
		query.put("url", "http://blog.csdn.net/yfkiss/article/details/39966087");
		Dumper.dump(mf.find(Article.class, query));
	}
	
	
}
