package com.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.dao.MySqlDaoFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
//		System.out.println(mf.save(Author.class, a));
	}
	
	public void TestFind() {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		Map<String, Object> query = new HashMap<>();
		query.put("url", "http://blog.csdn.net/yfkiss/article/details/39966087");
		Dumper.dump(mf.find(Article.class, query));
	}
	
	public static void main(String[] args) {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		Article article = (Article) mf.findById(Article.class, 1);
		
		Author author = new Author();
		author.setId("yfkiss");
		Type type = new Type();
		type.setId(1);
		
		article.setAuthorId(author);
		article.setTypeId(type);
		
		MultiValueMap<String, String> header = new LinkedMultiValueMap<String, String>();
		header.add("token", "tangtaiming");
		header.add("Content-Type", "application/json; charset=UTF-8");
		header.add("Accept", "application/json; charset=UTF-8");
		
		ObjectMapper omb = new ObjectMapper();
		String json = null;
		try {
			json = omb.writeValueAsString(article);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		Dumper.dump(json);
		
		HttpEntity entity = new HttpEntity(json, header);
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = restTemplate.postForObject("http://127.0.0.1:8001/a/article", entity, LinkedHashMap.class);
		System.out.println(response);
	}
	
	
}
