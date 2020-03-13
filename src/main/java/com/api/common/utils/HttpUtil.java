package com.api.common.utils;

import lombok.extern.slf4j.Slf4j;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;

/**
 * get,post 请求util类 
 */
@Slf4j
public class HttpUtil {
	
	private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(30000).setConnectTimeout(30000)
			.setConnectionRequestTimeout(30000).build();


	public static void main(String[] args) {
	}


	/**
	 * post请求
	 */
	public static String post(String url, Map<String, String> paramMap) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (paramMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
			}
			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + "," + (paramMap != null ? paramMap.toString() : "null") + "," + e.getMessage(), e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null){
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + "," + paramMap.toString() + "," + e.getMessage(), e);
			}
		}
		return result;
	}

	public static String postWithHead(String url, Map<String, String> paramMap, Map<String, String> headMap) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (headMap != null) {
				for (String key : headMap.keySet()) {
					httpPost.addHeader(key, headMap.get(key));
				}
			} else {
				httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			}
			if (paramMap != null) {
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				for (Entry<String, String> entry : paramMap.entrySet()) {
					nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(nvps, StandardCharsets.UTF_8));
			}

			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",headMap=" + headMap + ",paramMap:" + paramMap, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",headMap=" + headMap + ",paramMap:" + paramMap, e);
			}
		}
		return result;
	}

	/**
	 * http请求 可设置头 body(json)
	 * @param url 路径
	 * @param headMap 头
	 * @param params json格式
	 * @return String
	 */
	public static String postWithHead(String url, Map<String, String> headMap, String params) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (headMap != null) {
				for (String key : headMap.keySet()) {
					httpPost.addHeader(key, headMap.get(key));
				}
			} else {
				httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			}
			StringEntity se = new StringEntity(params, "utf-8");
			httpPost.setEntity(se);
			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",headMap=" + headMap + ",params:" + params, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",headMap=" + headMap + ",params:" + params, e);
			}
		}
		return result;
	}

	/**
	 * posn请求
	 *
	 * @param url
	 * @param jsonParam json格式参数
	 * @return
	 */
	public static String postJSON(String url, JSONObject jsonParam) {
		return postJSON(url, jsonParam.toString());
	}

	public static String postJSON(String url, String jsonParam) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);

			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			StringEntity se = new StringEntity(jsonParam, "utf-8");
			se.setContentEncoding("text/json;charset=utf-8");
			se.setContentType("text/json;charset=utf-8");
			httpPost.setEntity(se);

			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",jsonParam:" + jsonParam, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",jsonParam:" + jsonParam, e);
			}
		}
		return result;
	}

	/**
	 * post json请求，可设置请求头
	 *
	 * @param url 请求地址
	 * @param headMap 请求头
	 * @param jsonParam 请求内容
	 * @return
	 */
	public static String postJSONWithHead(String url, Map<String, String> headMap, JSONObject jsonParam) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);
			if (headMap != null) {
				for (String key : headMap.keySet()) {
					httpPost.addHeader(key, headMap.get(key));
				}
			} else {
				httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			}
			StringEntity se = new StringEntity(jsonParam.toString(), "utf-8");
			se.setContentEncoding("text/json;charset=utf-8");
			se.setContentType("text/json;charset=utf-8");
			httpPost.setEntity(se);
			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",headMap=" + headMap + ",jsonParam:" + jsonParam, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",headMap=" + headMap + ",jsonParam:" + jsonParam, e);
			}
		}
		return result;
	}

	/**
	 * get请求
	 * 
	 * @param url 请求路径+参数
	 * @return 响应文本
	 */
	public static String get(String url) {
		return get(url, null);
	}

	/**
	 * get请求
	 * 
	 * @param url 请求路径+参数
	 * @param headerParamMap header参数,例如token等
	 * @return
	 */
	public static String get(String url, Map<String, String> headerParamMap) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.setConfig(requestConfig);
			if (headerParamMap != null) {
				for (Entry<String, String> entry : headerParamMap.entrySet()) {
					httpGet.setHeader(entry.getKey(), entry.getValue());
				}
			}
			response = httpclient.execute(httpGet);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",headerParamMap:" + headerParamMap, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",headerParamMap:" + headerParamMap, e);
			}
		}
		return result;
	}

	private static String getParam() {
		String orderNum = "AB" + System.currentTimeMillis() + String.valueOf(Math.random() * 100000 + 100000).substring(0, 6);
		String json = "{\"advanceRatio\":\"0\",\"agingType\":\"pukuai\",\"arriveDate\":\"\",\"cargoName\":\"\",\"createTime\":\"2017-09-09 18:29:36\",\"deliveryType\":\"sanfang\",\"dkhCode\":\"ZDGZ\",\"freight\":\"0\",\"goodsHeight\":\"0\",\"goodsLength\":\"0\",\"goodsList\":[{\"goodsCount\":\"1\",\"goodsName\":\"古龙丁香鱼110g\",\"goodsPic\":\"http://img2.zhidianlife.com/image/2017/03/06/5de167356f914f30aab7ee17da73207b.jpg\",\"goodsTotalPrice\":\"7.50\",\"goodsType\":\"1\",\"goodsUnit\":\"罐\",\"goodsUnitPrice\":\"7.50\",\"goodsVolume\":\"0\",\"goodsWeight\":\"0\"}],\"goodsTotalCount\":\"1\",\"goodsTotalWeight\":\"0\",\"goodsWidth\":\"0\",\"orderAmount\":\"0.00\",\"orderNum\":\""
				+ orderNum
				+ "\",\"origin\":\"ERPZHCZYLS\",\"premiumAmount\":\"0\",\"receiveAdd\":\"金地朗悦4期4-2-501\",\"receiveArea\":\"房山区\",\"receiveCity\":\"北京市\",\"receiveDigest\":\"自提\",\"receiveLatitude\":\"0\",\"receiveLongitude\":\"0\",\"receiveProvince\":\"北京市\",\"receiver\":\"梁景云\",\"receiverPhone\":\"18612189841\",\"reqVersion\":\"31\",\"sendAdd\":\"拱辰街道金隅糖6号院4号楼1单元101\",\"sendArea\":\"房山区\",\"sendCity\":\"北京市\",\"sendDate\":\"\",\"sendDigest\":\"拱辰街道金隅糖6号院4号楼1单元101\",\"sendLatitude\":\"39.73869700000\",\"sendLongitude\":\"116.18714300000\",\"sendProvince\":\"北京市\",\"sender\":\"北京房山金隅·糖仓\",\"senderPhone\":\"13001113531\",\"storageNo\":\"ZD028718\"}";
		return json;
	}

	public static String postEncode(String url, String encode) {
		String result = null;
		CloseableHttpClient httpclient = null;
		CloseableHttpResponse response = null;
		try {
			httpclient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.setConfig(requestConfig);

			httpPost.addHeader("Content-Type", "application/json;charset=utf-8");
			StringEntity se = new StringEntity(encode, "utf-8");
			se.setContentEncoding("text/json;charset=utf-8");
			se.setContentType("text/json;charset=utf-8");
			httpPost.setEntity(se);

			response = httpclient.execute(httpPost);
			result = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
			return result;
		} catch (Exception e) {
			log.error("url:" + url + ",jsonParam:" + encode, e);
		} finally {
			try {
				if (response != null) {
					response.close();
				}
				if (httpclient != null) {
					httpclient.close();
				}
			} catch (IOException e) {
				log.error("url:" + url + ",jsonParam:" + encode, e);
			}
		}
		return result;
	}

	/**
	 * 调用中通接口的方法
	 * 
	 * @param interfaceUrl
	 * @param headers
	 * @param queryString
	 * @return
	 * @throws IOException
	 */
	public static String ztPost(String interfaceUrl, Map<String, String> headers, String queryString) throws IOException {
		int DEFAULT_TIMEOUT = 3000;
		URL url = new URL(interfaceUrl);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		con.setDoOutput(true);
		con.setConnectTimeout(DEFAULT_TIMEOUT);
		con.setReadTimeout(DEFAULT_TIMEOUT);
		for (Map.Entry<String, String> e : headers.entrySet()) {
			con.setRequestProperty(e.getKey(), e.getValue());
		}
		DataOutputStream out = null;

		BufferedReader in = null;
		try {
			out = new DataOutputStream(con.getOutputStream());
			out.write(queryString.getBytes(StandardCharsets.UTF_8));
			out.flush();
			in = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
			String inputLine;
			StringBuilder content = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
			return content.toString();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (Exception ignored) {
				}
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception ignored) {
				}
			}
		}
	}

	public static String kdNiaoPost(String url, Map<String, String> params) {
		OutputStreamWriter out = null;
		BufferedReader in = null;
		StringBuilder result = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// POST方法
			conn.setRequestMethod("POST");
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
			conn.connect();
			// 获取URLConnection对象对应的输出流
			out = new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.UTF_8);
			// 发送请求参数
			if (params != null) {
				StringBuilder param = new StringBuilder();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					if (param.length() > 0) {
						param.append("&");
					}
					param.append(entry.getKey());
					param.append("=");
					param.append(entry.getValue());
					// System.out.println(entry.getKey()+":"+entry.getValue());
				}
				// System.out.println("param:"+param.toString());
				out.write(param.toString());
			}
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

}
