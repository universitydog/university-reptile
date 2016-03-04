package webSimple;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.dao.MySqlDaoFactory;
import com.orm.CsdnEntity;
import com.ttm.util.Dumper;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * http://blog.csdn.net 数据爬取
 * 
 * @author 唐太明
 *
 */
public class CsdnSimple implements PageProcessor {
	
	private Site site = Site
            .me()
            .setDomain("http://blog.csdn.net")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	//http://blog.csdn.net/yfkiss/article/list/1
	private static final String GET_LIST = "http://blog\\.csdn\\.net/yfkiss/article/list/\\d+";
	
	//http://blog.csdn.net/yfkiss/article/details/23775917
	private static final String GET_CONTENT = "http://blog\\.csdn\\.net/yfkiss/article/details/\\d+";
	
	private List<String> urlList = new ArrayList<>();
	
	private static List<CsdnEntity> csdnList = new ArrayList<>();
	
	public void process(Page page) {
		if (page.getUrl().regex(GET_LIST).match()) {
//			Dumper.dump(page.getHtml().xpath("//span[@class=\"link_title\"]").links().regex(GET_CONTENT).all());
			page.addTargetRequests(page.getHtml().xpath("//span[@class=\"link_title\"]").links().regex(GET_CONTENT).all());
			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"pagelist\"]").links().regex(GET_LIST).all());
		} else {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String title = page.getHtml().xpath("//span[@class=\"link_title\"]/a/allText()").toString();
			String titleDate = page.getHtml().xpath("//span[@class=\"link_postdate\"]/allText()").toString();
			String content_url = page.getUrl().toString();
			String inputDate = LocalDateTime.now(ZoneId.of("Asia/Shanghai")).format(formatter);
			String writer = "周晓敏";
			
			CsdnEntity ce = new CsdnEntity();
			ce.setTitle(title);
			ce.setTitleDate(titleDate);
			ce.setUrl(content_url);
			ce.setWriter(writer);
			ce.setInputDate(inputDate);
			Dumper.dump(ce);
			csdnList.add(ce);
			
//			page.putField("url", content_url);
//			page.putField("title", page.getHtml().xpath("//span[@class=\"link_title\"]/a/allText()"));
//			page.putField("titleDate", page.getHtml().xpath("//span[@class=\"link_postdate\"]/allText()"));
//			page.putField("inputDate", inputDate);
//			page.putField("writer", writer);
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new CsdnSimple())
		.addUrl("http://blog.csdn.net/yfkiss/article/list/1")
		.thread(3)
		.run();
		CsdnSimple.addObj(csdnList);
	}
	
	@SuppressWarnings("unchecked")
	public static void addObj(List<?> obj) {
		List<CsdnEntity> ceList = (List<CsdnEntity>) obj;
		MySqlDaoFactory mf = new MySqlDaoFactory();
		for (CsdnEntity ce : ceList) {
			mf.save(CsdnEntity.class, ce);
		}
	}
	
}
