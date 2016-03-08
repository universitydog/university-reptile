package webSimple;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dao.MySqlDaoFactory;
import com.orm.Article;
import com.orm.Author;
import com.orm.Type;
import com.ttm.util.Dumper;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class FeeLangSimple implements PageProcessor {
	private Site site = Site
            .me()
            .setDomain("http://blog.csdn.net")
            .setSleepTime(3000)
            .setUserAgent(
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");

	//http://blog.csdn.net/yfkiss/article/list/1
	private static final String GET_LIST = "http://blog\\.csdn\\.net/FeeLang/article/list/\\d+";
	
	//http://blog.csdn.net/yfkiss/article/details/23775917
	private static final String GET_CONTENT = "http://blog\\.csdn\\.net/FeeLang/article/details/\\d+";
	
	private List<String> urlList = new ArrayList<>();
	
	private static List<Article> csdnList = new ArrayList<>();
	
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
			String tags = page.getHtml().xpath("//div[@class=\"article_l\"]//span[@class=\"link_categories\"]").toString();

			Document document = page.getHtml().getDocument();
			Element element = document.getElementById("article_details");
			Elements element2 = element.getElementsByClass("article_manage");
			Element element3 = element2.get(0);
			
			StringBuffer tagsBuffer = new StringBuffer();
			for (int x = 0; x < element3.children().size(); x++)
			{
				String classA = element3.children().get(x).className();
				if (classA.equals("article_l")) {
					Elements aElement = element3.children().get(x).getElementsByTag("a");
					System.out.println("^^^^^^^^^^^^^^^^^^^^^^:" + aElement.size());
					for (int y = 0; y < aElement.size(); y++) {
						String t = aElement.get(y).text();
						System.out.println((y + 1) + ":" + t);
						tagsBuffer.append(t + ",");
					}
				} else {
					continue;
				}
			}
			tags = StringUtils.removeEnd(tagsBuffer.toString(), ",");
			
			Author author = new Author();
			author.setId("FeeLang");
			Type type = new Type();
			type.setId(2);
			Article article = new Article();
			article.setAuthorId(author);
			article.setTypeId(type);
			article.setUrl(content_url);
			article.setInputDate(inputDate);
			article.setTitle(title);
			article.setTitleDate(titleDate);
			if (StringUtils.isEmpty(tags)) {
				article.setTags(null);
			} else {
				article.setTags(tags);
			}
			Dumper.dump(article);
			csdnList.add(article);
			
//			page.putField("url", content_url);
//			page.putField("title", page.getHtml().xpath("//span[@class=\"link_title\"]/a/allText()"));
//			page.putField("titleDate", page.getHtml().xpath("//span[@class=\"link_postdate\"]/allText()"));
//			page.putField("inputDate", inputDate);
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new FeeLangSimple())
		.addUrl("http://blog.csdn.net/FeeLang/article/list/1")
		.thread(5)
		.run();
		FeeLangSimple.addObj(csdnList);
	}
	
	@SuppressWarnings("unchecked")
	public static void addObj(List<?> obj) {
		List<Article> ceList = (List<Article>) obj;
		MySqlDaoFactory mf = new MySqlDaoFactory();
		
		List<Article> newList = new ArrayList<>();
		for (Article a : ceList) {
			Map<String, Object> query = new HashMap<>();
			query.put("url", a.getUrl());
			if (mf.find(Article.class, query) == null) {
				newList.add(a);
			};
		}
		
		for (int x = 0; x < newList.size(); x++) {
			mf.save(Article.class, newList.get(x));
		};
	}
}
