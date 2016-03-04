package webSimple;

import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;

public class HttpClientDemo {
	

    public static void main(String[] args) throws ClientProtocolException, IOException {
    	
    	HttpClient client = HttpClients.createSystem();
    	HttpGet get = new HttpGet("http://waimai.meituan.com/log");
    	HttpPost post = new HttpPost("http://waimai.meituan.com/log");
    	String result = "";
    	
    	
    	HttpResponse response = null;
    	response = client.execute(post);
    	result = IOUtils.toString(response.getEntity().getContent(), "utf-8");
    	post.releaseConnection();
    	System.out.println(result);
    }
	
}
