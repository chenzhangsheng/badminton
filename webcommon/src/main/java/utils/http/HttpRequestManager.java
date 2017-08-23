package utils.http;

import utils.JsonUtil;
import exception.PlatformRuntimeException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.SyncBasicHttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HttpRequestManager {
	private static final Logger logger = LoggerFactory.getLogger(HttpRequestManager.class);
	public static final String DEFAULT_CHARSET = "UTF-8";
	protected static final String JsonContentType = "application/json; charset=UTF-8";

	protected static final Integer BUFFER_SIZE = 4048;
	protected ScheduledExecutorService idleConnectionCloseExecutor = Executors.newSingleThreadScheduledExecutor();
	
	protected PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
	protected HttpClient httpClient;

	public HttpRequestManager(int connectionTimeOut, int soTimeOut) {
		HttpParams params = new SyncBasicHttpParams();
        DefaultHttpClient.setDefaultHttpParams(params);
        HttpConnectionParamBean paramsBean = new HttpConnectionParamBean(params);
        paramsBean.setConnectionTimeout(connectionTimeOut);
        paramsBean.setSoTimeout(soTimeOut);
        paramsBean.setSocketBufferSize(BUFFER_SIZE);
        this.setMaxTotal(5000);
        httpClient = new DefaultHttpClient(cm, params);  
	}
	
	public HttpRequestManager() {
		
	}
	
	
	public void init(){
		idleConnectionCloseExecutor.scheduleWithFixedDelay(new Runnable() {
			public void run() {
				if(logger.isTraceEnabled()){
					logger.trace("start to close expire and idle connections");
				}
				
				cm.closeExpiredConnections();
				cm.closeIdleConnections(60, TimeUnit.SECONDS);
			}
		}, 60, 60, TimeUnit.SECONDS);
		logger.info("HttpClientManager close idel every 60 seconds");
	}
	
	public void destory(){
		logger.trace("destory httpclient manager");
		idleConnectionCloseExecutor.shutdown();
		cm.shutdown();
	}
	
	public void setMaxTotal(int maxTotal) {
		cm.setMaxTotal(maxTotal);
		cm.setDefaultMaxPerRoute(maxTotal);
	}
	
	/**
	 * 执行http请求,支持POST,GET,DELETE,PUT方法,也支持https
	 * @param url			请求地址
	 * @param mapQuery		参数map
	 * @param contentType  contenttype,如果没有可输入null
	 * @param method		HttpMethod.PUT, HttpMethod.DELETE,HttpMethod.GET,HttpMethod.POST
	 * @param httptype		请输入http或者https 注意:目前https只支持post方法
	 * @return
	 */
	public String execHttpMutiText(String url, Map<String, String> mapQuery, Map<String,String> heads,HttpMethod httpMethod,String httptype){
		URI uri = URI.create(url);
		MultipartEntity mutiEntity = new MultipartEntity(); 
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();
		
		HttpResponse response;
		HttpPut put = null;
		HttpPost request = null;
		HttpDelete delete = null;
		HttpGet get = null;
		try {
			if(null != mapQuery){
				for (Entry<String, String> entry : mapQuery.entrySet()) {
					mutiEntity.addPart(entry.getKey(), new StringBody(entry.getValue()));
					pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
			}
			UrlEncodedFormEntity urlformentity = new UrlEncodedFormEntity(pairs, DEFAULT_CHARSET);
			if(httpMethod == HttpMethod.PUT){
				put = new HttpPut(uri);
				put.setEntity(urlformentity);
				if(heads != null){
					for (Entry<String, String> entry : heads.entrySet()) {
						put.addHeader(entry.getKey(), entry.getValue());
					}
				}
				response = httpClient.execute(put);
			}else if(httpMethod == HttpMethod.DELETE){
				delete = new HttpDelete(uri);
				delete.setEntity(urlformentity);
				if(heads != null){
					for (Entry<String, String> entry : heads.entrySet()) {
						delete.addHeader(entry.getKey(), entry.getValue());
					}
				}
				response = httpClient.execute(delete);
			}else if(httpMethod == HttpMethod.GET){
				get = new HttpGet(uri);
				if(heads != null){
					for (Entry<String, String> entry : heads.entrySet()) {
						get.addHeader(entry.getKey(), entry.getValue());
					}
				}
				if("https".equals(httptype)){
					HttpClient hsc = HttpsClient.newHttpsClient();
					response = hsc.execute(get);
				}else{
					response = httpClient.execute(get);
				}
			} else{
				request = new HttpPost(uri);
				request.setEntity(urlformentity);
				if(heads != null){
					for (Entry<String, String> entry : heads.entrySet()) {
						request.addHeader(entry.getKey(), entry.getValue());
					}
				}
				response = httpClient.execute(request);
			}
			
			
			int status = response.getStatusLine().getStatusCode();
			if(status == 404){
				throw new PlatformRuntimeException("http post error.httpstatus:404  url: "+url);
			}
			HttpEntity entity = response.getEntity();
			
			String reponse = EntityUtils.toString(entity, DEFAULT_CHARSET);
			if( status >= 400 ) {
				StringBuilder msgSb = new StringBuilder();
				msgSb.append("post error, status:").append(status).append(", reponse:" ).
				append(reponse).append(", url:").append(uri).append(", txt=").append(URLEncodedUtils.format(pairs, DEFAULT_CHARSET));
				
				String errorMsg = msgSb.toString();
				logger.error(errorMsg);
				throw new PlatformRuntimeException(errorMsg);
			}
			
			return reponse;
		} catch (Exception e) {
			logger.error("post error, url={}", uri, e);
			throw new PlatformRuntimeException("post error, url=" + uri, e);
		} finally {
			if(request != null){
				request.abort();
			}
			if(put != null){
				put.abort();
			}
		}	
	}
	
	
	public String execJsonPost(String url,Object obj){
		String json = JsonUtil.getJsonFromObject(obj);
		return this.execJsonStringPost(url, json);
	}
	
	public String execJsonStringPost(String url,String json){
		HttpPost request = new HttpPost(url);
		request.addHeader("Content-Type", JsonContentType);
		
		try {
			StringEntity myEntity = new StringEntity(json, DEFAULT_CHARSET);
			request.setEntity(myEntity);
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,20000);//连接时间
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,20000);
			HttpResponse response = httpClient.execute(request);

			int status = response.getStatusLine().getStatusCode();
			if(status == 404 || status >= 500){
				throw new PlatformRuntimeException("http post error.httpstatus:"+status+" url: "+url);
			}
			HttpEntity resEntity = response.getEntity();
			InputStreamReader reader = new InputStreamReader(resEntity.getContent(), DEFAULT_CHARSET);
			char[] buff = new char[1024];
			int length = 0;
			StringBuffer sb = new StringBuffer();
			while ((length = reader.read(buff)) != -1) {
			        sb.append(new String(buff, 0, length));
			}
			return sb.toString();
		} catch (IOException e) {
			throw new PlatformRuntimeException("http post error. url: "
					+ url, e);
		} catch (RuntimeException e) {
			throw new PlatformRuntimeException("http post error. url: "
					+ url, e);
		} finally {
			request.abort();
		}
	}
	
	public String execJsonPostStatus(String url,Object obj){
		HttpPost request = new HttpPost(url);
		request.addHeader("Content-Type", JsonContentType);
		String json = JsonUtil.getJsonFromObject(obj);
		
		try {
			StringEntity myEntity = new StringEntity(json, DEFAULT_CHARSET);
			request.setEntity(myEntity);
			HttpResponse response = httpClient.execute(request);
			
			int status = response.getStatusLine().getStatusCode();
			if(status == 404){
				throw new PlatformRuntimeException("http post error.httpstatus:404  url: "+url);
			}
				HttpEntity resEntity = response.getEntity();
				InputStreamReader reader = new InputStreamReader(resEntity.getContent(), DEFAULT_CHARSET);
				char[] buff = new char[1024];
				int length = 0;
				StringBuffer sb = new StringBuffer();
				while ((length = reader.read(buff)) != -1) {
				        sb.append(new String(buff, 0, length));
				}
				return sb.toString();
		} catch (IOException e) {
			throw new PlatformRuntimeException("http post error. url: "
					+ url, e);
		} catch (RuntimeException e) {
			throw new PlatformRuntimeException("http post error. url: "
					+ url, e);
		} finally {
			request.abort();
		}
	}
}
