package webSimple;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpConnectionManagerParams;

public class HtmlSimple {
	
	public String myGetHttpFile2(String url) {  
		  
        String urlSource = url;  
        StringBuffer htmlBuffer = new StringBuffer();  
        String returnStr = null;  
        try {  
            InputStream imageSource = new URL(urlSource).openStream();  
            int ch;  
            while ((ch = imageSource.read()) > -1) {  
                htmlBuffer.append((char) ch);  
            }  
            imageSource.close();  
            returnStr = new String(htmlBuffer);  
            returnStr = new String(returnStr.getBytes("ISO8859_1"), "GBK");  
        } catch (Exception e) {  
            System.out.println("error>>>>");  
            e.printStackTrace();  
        }  
  
        //System.out.println("@@@:" + returnStr);  
        if (returnStr != null) {  
            return returnStr;  
        } else {  
            return "nothing";  
        }  
  
    }  
  
    public void doit(String content, int depth) throws Exception {  
          
        depth--;  
        if (depth < 1) {  
            //System.out.println("break::::");  
            return;  
        }  
  
        HtmlSimple search = new HtmlSimple();  
        ArrayList list = new ArrayList();  
        int j = 0;  
        String start = "href=";  
        String end = "\"";  
        String url = "";  
        String type = "http";  
        String[] urls;  
        while (content.indexOf(start, j) > -1) {  
  
                url = content.substring(content.indexOf(start, j) + 6, content.indexOf(end, content.indexOf(start, j) + 6));//+6 href="  
                if (url.indexOf(type) > -1) {  
                    if (url.indexOf(".css") == -1&&url.indexOf(".ico") == -1&&url.indexOf(".exe") == -1) {  
                        System.out.println(url);  
                          
                        list.add(url);  
  
                        if (list != null && list.size() > 0) {  
  
                            for (int k = 0; k < list.size(); k++) {  
                                doit(search.myGetHttpFile2(String.valueOf(list.get(k))), depth);  
                          
                            }  
  
                        }  
                    }  
  
                }  
  
              
            j = content.indexOf(start, j) + 1;  
              
        }  
  
    }  
  
    public static void main(String arg[]) {  
  
  
    } 
	
}
