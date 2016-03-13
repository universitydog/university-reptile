package webSimple;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import webSimple.util.ScriptUtil;

public class HGDemo2 extends ScriptUtil implements PageProcessor {

	private Site site = Site.me().setRetrySleepTime(3).setSleepTime(5000);
	
	//http://www.hnpu.edu.cn/hnit/xxwNews.jsp?newsid=37867
	private final static String GET = "http://www\\.hnpu\\.edu\\.cn/hnit/xxwNews\\.jsp\\?newsid\\=(\\d+)";
	
	//http://www.hnpu.edu.cn/hnit/xxwMore.jsp?id=4
	private final static String GET_LIST = "http://www\\.hnpu\\.edu\\.cn/hnit/xxwMore\\.*";
	
	public static List<String> TITLE = new ArrayList<>();
	
	private int x = 1;
	
	public void process(Page page) {
		if (page.getUrl().regex(GET_LIST).match()) {
//			String curPage = page.getHtml().xpath("//input[@name=\"cur_page\"]").toString();
//			System.out.println(curPage);
//			System.out.println(page.getHtml().xpath("//div[@class=\"newsContent\"]/ul").toString());
//			System.out.println(page.getHtml().xpath("//div[@class=\"newsContent\"]/ul").links().regex("(" + GET + ")").all());
//			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"newsContent\"]").links().regex(GET).all());
			
			System.out.println(x++);
			
			List<String> urlList = new ArrayList<>();
			//��ȡ��ҳ�ı�
			String strPage = page.getHtml().xpath("//form[@name=\"page_form\"]/table/tbody/tr/td/div/tidyText()").toString();
			String reg = "[\\d]+";
			Pattern pattern = Pattern.compile(reg);
			Matcher matcher = pattern.matcher(strPage);
			String[] a = new String[2];
			for (int x = 0; matcher.find(); x++) {
				a[x] = matcher.group(0);
			}
			for (int x = 1; x <= Integer.parseInt(a[1]); x++) {
				String url = "http://www.hnpu.edu.cn/hnit/xxwMore.jsp?id=4&cur_page=" + x + "&total_page=" + a[1] + "&record_num=40&set_curpage=1";
				urlList.add(url);
			}
			page.addTargetRequests(urlList);
			//ok
			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"newsContent\"]/ul").links().regex("(" + GET + ")").all());
		} else {
			page.putField("title", page.getHtml().xpath("//div[@class='newsTitle']/span/text()").toString());
			TITLE.add(page.getHtml().xpath("//div[@class='newsTitle']/span/text()").toString());
		}
	}

	public Site getSite() {
		return site;
	}
	
	public static void main(String[] args) {
		Spider.create(new HGDemo2())
		.addUrl("http://www.hnpu.edu.cn/hnit/xxwMore.jsp?id=4")
		.thread(5)
		.run();
		HGDemo2 h2 = new HGDemo2();
		List<String> list = HGDemo2.TITLE;
		String fileName = "E:\\HGData\\hg_" + System.currentTimeMillis() + ".xls";
		ArrayList<ArrayList<String>> ll = h2.operationalDataToArray(list);
		h2.arrayToXSL(ll, fileName);
		
	}

	@Override
	public <T> ArrayList<ArrayList<String>> operationalDataToArray(List<T> list) {
		ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
		ArrayList<String> head = new ArrayList<String>();
		head.add("����");
		temp.add(head);

		for (int x = 0; x < list.size(); x++) {
			ArrayList<String> body = new ArrayList<String>();
			String title = (String) list.get(x);
			body.add(title);
			temp.add(body);
		}
		return temp;
	}
	
	public File arrayToXSL(HashMap<String, ArrayList<ArrayList<String>>> data, String fileName) {
		String path = fileName;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			FileOutputStream file = new FileOutputStream(path);
			for (String key : data.keySet()) {
				HSSFSheet sheet = workbook.createSheet(key);
				ArrayList<ArrayList<String>> listData = data.get(key);
				int i = 0;
				for (Iterator<ArrayList<String>> iterator = listData.iterator(); iterator.hasNext();) {
					ArrayList<String> r = (ArrayList<String>) iterator.next();
					HSSFRow row = sheet.createRow(i++);
					int j = 0;
					Iterator<String> iterator1 = r.iterator();
					while (iterator1.hasNext()) {
						Object column = iterator1.next();
						HSSFCell cell = row.createCell(j++);
						if (null == column)
							cell.setCellValue("");
						else
							cell.setCellValue(column.toString());
					}
				}
				workbook.write(file);
				file.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(path);
	}
	
	@Override
	public File arrayToXSL(ArrayList<ArrayList<String>> data, String fileName) {
		String path = fileName;
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("demo1");
			FileOutputStream file = new FileOutputStream(path);
			int i = 0;
			for (Iterator<ArrayList<String>> iterator = data.iterator(); iterator.hasNext();) {
				ArrayList<String> r = (ArrayList<String>) iterator.next();
				HSSFRow row = sheet.createRow(i++);
				int j = 0;
				Iterator<String> iterator1 = r.iterator();
				while (iterator1.hasNext()) {
					Object column = iterator1.next();
					HSSFCell cell = row.createCell(j++);
					if (null == column)
						cell.setCellValue("");
					else
						cell.setCellValue(column.toString());
				}
			}
			workbook.write(file);
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new File(path);
	}
	
}
