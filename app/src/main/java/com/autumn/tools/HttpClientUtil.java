package com.autumn.tools;

import android.icu.util.Output;
import android.util.Log;

import com.autumn.pojo.GPSPojo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class HttpClientUtil {
	public static void main(String[] args) throws IOException,
			KeyManagementException, NoSuchAlgorithmException {
		//post(URL,"Android项目HttpClient手动发送post()");
		//postAndroid(URL,"Android项目HttpClient手动发送postAndroid()");

		GPSPojo gpsPojo = new GPSPojo();
		gpsPojo.setUserId("qyid1");
		gpsPojo.setUserName("autumn");
		postObjectAndroid(URL,gpsPojo);
	}
	//通用记录到日志url
	public static String URL = "http://www.52zt.online:8088/Bookkeeping/HttpInfoController/getAndroid";
	public static String URL_LOGIN = "http://www.52zt.online:8088/Bookkeeping/manager/loginController/loginForAndroid";
	/**
	 * 正常环境中使用
	 * @param data
	 */
	public static void post(String url,String data) {
		// 实例化httpclient
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建post实例
		if(url==null||url.isEmpty()){
			url = URL;
		}
		HttpPost httpPost = new HttpPost(URL);
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

		// 添加参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("data", data));

		CloseableHttpResponse response = null;
		// 将参数放入post实例
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// httpclient执行操作返回response
			response = httpclient.execute(httpPost);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 安卓使用放入data:json格式的对象
	 * @param data
	 */
	public static void postAndroid(final String url,final String data) {
		Runnable runnable = new Runnable() {
			public void run() {

				// 实例化httpclient
				// CloseableHttpClient httpclient = HttpClients.createDefault();   使用会与android核心包起冲突
				org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
				// 创建post实例
				HttpPost httpPost = null;
				if(url==null||url.isEmpty()){
					httpPost = new HttpPost(URL);
				}else{
					httpPost = new HttpPost(url);
				}
				httpPost.setHeader("Content-type",
						"application/x-www-form-urlencoded");

				// 添加参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("data", data));

				// CloseableHttpResponse response = null;   使用会与android核心包起冲突
				HttpResponse response = null;
				// 将参数放入post实例
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
					response = httpclient.execute(httpPost);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * 安卓使用放Http发送对象
	 * @param url   对象
	 * @param data  Object对象中的每一个字段作为参数,值作为参数值
	 */
	public static void postObjectAndroid(final String url,final Object data) {
		Runnable runnable = new Runnable() {
			public void run() {

				// 实例化httpclient
				// CloseableHttpClient httpclient = HttpClients.createDefault();   使用会与android核心包起冲突
				org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
				// 创建post实例
				HttpPost httpPost = null;
				if(url==null||url.isEmpty()){
					httpPost = new HttpPost(URL);
				}else{
					httpPost = new HttpPost(url);
				}
				httpPost.setHeader("Content-type",
						"application/x-www-form-urlencoded");

				// 添加参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				Class dataClass = data.getClass();
				Field[] declaredFields = dataClass.getDeclaredFields();
				for (Field field:declaredFields){
					try {
						field.setAccessible(true);
						if(field.get(data)!=null){   //字段值不为null,则作为参数
							params.add(new BasicNameValuePair(field.getName(),field.get(data).toString()));
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				//params.add(new BasicNameValuePair("data", data));

				// CloseableHttpResponse response = null;   使用会与android核心包起冲突
				HttpResponse response = null;
				// 将参数放入post实例
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
					response = httpclient.execute(httpPost);
                    if (response.getStatusLine().getStatusCode() == 200) {//如果返回成功则解析
                        HttpEntity responseEntity = response.getEntity();
						//方法一
						/*InputStream inputStream = responseEntity.getContent();
						StringBuilder stringBuilder = new StringBuilder();
						byte[] temp = new byte[1024];
						OutputStream outputStream = new ByteArrayOutputStream();
						int len =0;
						while((len=inputStream.read(temp))!=-1){
							outputStream.write(temp,0,len);
							String result = new String(temp);
							stringBuilder.append(result);
						}
						Log.i("stringBuilder",stringBuilder.toString());*/
						//方法二
                        String result = EntityUtils.toString(responseEntity, HTTP.UTF_8);
                    }
                } catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		new Thread(runnable).start();
	}

	/**
	 * 安卓使用放Http发送对象
	 * @param url   对象
	 * @param data  Object对象中的每一个字段作为参数,值作为参数值
	 * @return 线程的返回结果
	 */
	public static String postObjectAndroidByCall(final String url,final Object data) {
		Callable<String> callable = new Callable<String>() {
			public String call() throws Exception {
				// 实例化httpclient
				// CloseableHttpClient httpclient = HttpClients.createDefault();   使用会与android核心包起冲突
				org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
				// 创建post实例
				HttpPost httpPost = null;
				if(url==null||url.isEmpty()){
					httpPost = new HttpPost(URL);
				}else{
					httpPost = new HttpPost(url);
				}
				httpPost.setHeader("Content-type",
						"application/x-www-form-urlencoded");

				// 添加参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				Class dataClass = data.getClass();
				Field[] declaredFields = dataClass.getDeclaredFields();
				for (Field field:declaredFields){
					try {
						field.setAccessible(true);
						if(field.get(data)!=null){   //字段值不为null,则作为参数
							params.add(new BasicNameValuePair(field.getName(),field.get(data).toString()));
						}
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
				//params.add(new BasicNameValuePair("data", data));

				// CloseableHttpResponse response = null;   使用会与android核心包起冲突
				HttpResponse response = null;
				// 将参数放入post实例
				try {
					httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
					response = httpclient.execute(httpPost);
					if (response.getStatusLine().getStatusCode() == 200) {//如果返回成功则解析
						HttpEntity responseEntity = response.getEntity();
						//方法一
						/*InputStream inputStream = responseEntity.getContent();
						StringBuilder stringBuilder = new StringBuilder();
						byte[] temp = new byte[1024];
						OutputStream outputStream = new ByteArrayOutputStream();
						int len =0;
						while((len=inputStream.read(temp))!=-1){
							outputStream.write(temp,0,len);
							String result = new String(temp);
							stringBuilder.append(result);
						}
						Log.i("stringBuilder",stringBuilder.toString());*/
						//方法二
						String result = EntityUtils.toString(responseEntity, HTTP.UTF_8);
						Log.i("result",result);
						return result;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}
		};
		FutureTask<String> future = new FutureTask<String>(callable);
		new Thread(future).start();  //执行Callable线程
		try {
			return future.get();  //线程的返回结果
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}
}
