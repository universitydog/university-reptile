package webSimple;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class HGDemo implements PageProcessor {

	private Site site = Site.me().setRetrySleepTime(3).setSleepTime(1000);
	
	public static List<String> l = new ArrayList<>();

	public void process(Page page) {
		page.putField("name", page.getHtml().xpath("//div[@class='newsTitle']/span/text()").toString());
		page.putField("content", page.getHtml().xpath("//div[@class='newsContent']/div/allText()").toString());
		
		l.add(page.getHtml().xpath("//div[@class='newsTitle']/span/text()").toString());
		if (page.getResultItems().get("name") == null) {
			page.setSkip(true);
		}
		page.addTargetRequests(page.getHtml().regex("(http://www\\.hnpu.edu\\.cn/\\w+/\\w+)").all());
	}

	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new HGDemo())
		.addUrl("http://www.hnpu.edu.cn/hnit/xxwNews.jsp?newsid=37867")
		.thread(5)
		.run();
		System.out.println(HGDemo.l.toString());
	}

}
