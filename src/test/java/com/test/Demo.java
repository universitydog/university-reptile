package com.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.dao.MySqlDaoFactory;
import com.orm.CsdnEntity;
import com.ttm.util.Dumper;

public class Demo {

	@Test
	public void testA() {
		MySqlDaoFactory mf = new MySqlDaoFactory();
		Map<String, Object> query = new HashMap<>();
		query.put("title", "Kafka(Ò»)£º»ù´¡");
		Dumper.dump(mf.find(CsdnEntity.class, query));
	}
	
}
