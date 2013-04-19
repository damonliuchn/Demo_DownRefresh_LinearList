package com.mentor.firstdemo.util;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class HttpUtil {
	private static final String TAG = "HttpClientUtils";
	private static final String CHARSET = "UTF-8";
	private static final String PREFIX = "--", LINEND = "\r\n";
	private static final String MULTIPART_FROM_DATA = "multipart/form-data";
	private static final String CMWAP_HOST = "10.0.0.172";
	private static final int CMWAP_PORT = 80;
	private static final String PROXY_NAME = "http.route.default-proxy";
	public static boolean IS_CMWAP = false;

	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * 
	 * @return byte[]
	 */
	public static byte[] httpByGet2Bytes(String url, String paramsCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		byte[] a = null;
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			httpClient = getDefaultHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			a = EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return a;
	}

	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @return byte[]
	 */
	public static byte[] httpByGet2Bytes(String url,
			Map<String, String> params, String paramsCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		byte[] a = null;
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			List<NameValuePair> qparams = getParamsList(params);
			if (qparams != null && qparams.size() > 0) {
				paramsCharset = (paramsCharset == null ? CHARSET
						: paramsCharset);
				String formatParams = URLEncodedUtils.format(qparams,
						paramsCharset);// URLEncodeUtils
				url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
						: (url.substring(0, url.indexOf("?") + 1) + formatParams);
			}
			httpClient = getDefaultHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			a = EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return a;
	}

	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @param resultCharset
	 *            返回结果编码集可为null、默认UTF-8
	 * @return String
	 */
	public static String httpByGet2String(String url, String paramsCharset,
			String resultCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		String responseStr = null;
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			httpClient = getDefaultHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			if (resultCharset == null || "".equals(resultCharset)) {
				responseStr = EntityUtils.toString(response.getEntity(),
						CHARSET);
			} else {
				responseStr = EntityUtils.toString(response.getEntity(),
						resultCharset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return responseStr;
	}

	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @param resultCharset
	 *            返回结果编码集可为null、默认UTF-8
	 * @return String
	 */
	public static String httpByGet2String(String url,
			Map<String, String> params, String paramsCharset,
			String resultCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		String responseStr = null;
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			List<NameValuePair> qparams = getParamsList(params);
			if (qparams != null && qparams.size() > 0) {
				paramsCharset = (paramsCharset == null ? CHARSET
						: paramsCharset);
				String formatParams = URLEncodedUtils.format(qparams,
						paramsCharset);// URLEncodeUtils
				url = (url.indexOf("?")) < 0 ? (url + "?" + formatParams)
						: (url.substring(0, url.indexOf("?") + 1) + formatParams);
			}
			httpClient = getDefaultHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			if (resultCharset == null || "".equals(resultCharset)) {
				responseStr = EntityUtils.toString(response.getEntity(),
						CHARSET);
			} else {
				responseStr = EntityUtils.toString(response.getEntity(),
						resultCharset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return responseStr;
	}

	/**
	 * Post方式提交 返回结果是byte[]
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @param resultCharset
	 *            返回结果编码集可为null、默认UTF-8
	 * @return String
	 * 
	 */
	public static byte[] httpByPost2Bytes(String url,
			Map<String, String> params, String paramsCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		byte[] a = null;
		HttpClient httpClient = null;
		HttpPost hp = null;
		try {
			httpClient = getDefaultHttpClient();
			hp = new HttpPost(url);
			UrlEncodedFormEntity entity = null;
			if (paramsCharset == null || "".equals(paramsCharset)) {
				entity = new UrlEncodedFormEntity(getParamsList(params),
						CHARSET);
			} else {
				entity = new UrlEncodedFormEntity(getParamsList(params),
						paramsCharset);
			}
			hp.setEntity(entity);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hp);
			a = EntityUtils.toByteArray(response.getEntity());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hp, httpClient);
		}
		return a;
	}

	/**
	 * Post方式提交 返回结果是String
	 * 
	 * @param url
	 *            提交地址
	 * @param params
	 *            提交参数集, 键/值对
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @param resultCharset
	 *            返回结果编码集可为null、默认UTF-8
	 * @return String
	 * 
	 */
	public static String httpByPost2String(String url,
			Map<String, String> params, String paramsCharset,
			String resultCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		String responseStr = null;
		HttpClient httpClient = null;
		HttpPost hp = null;
		try {
			httpClient = getDefaultHttpClient();
			hp = new HttpPost(url);

			UrlEncodedFormEntity entity = null;
			if (paramsCharset == null || "".equals(paramsCharset)) {
				entity = new UrlEncodedFormEntity(getParamsList(params),
						CHARSET);
			} else {
				entity = new UrlEncodedFormEntity(getParamsList(params),
						paramsCharset);
			}
			hp.setEntity(entity);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hp);
			if (resultCharset == null || "".equals(resultCharset)) {
				responseStr = EntityUtils.toString(response.getEntity(),
						CHARSET);
			} else {
				responseStr = EntityUtils.toString(response.getEntity(),
						resultCharset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hp, httpClient);
		}
		return responseStr;
	}

	// ******************************************************************************************************//

	/**
	 * 将传入的键/值对参数转换为NameValuePair参数集
	 * 
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	public static List<NameValuePair> getParamsList(
			Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
		}
		return params;
	}

	/**
	 * 将传入的键/值对参数转换为String
	 * 
	 * @param paramsMap
	 *            参数集, 键/值对
	 * @return NameValuePair参数集
	 */
	public static String getParamsString(Map<String, String> paramsMap) {
		if (paramsMap == null || paramsMap.size() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, String> map : paramsMap.entrySet()) {
			try {
				sb.append(URLEncoder.encode(map.getKey(), HTTP.UTF_8) + "="
						+ URLEncoder.encode(map.getValue(), HTTP.UTF_8) + "&");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String a = sb.toString();
		a = a.substring(0, a.length() - 1);
		return a;
	}

	/**
	 * 释放HttpClient连接
	 * 
	 * @param hrb
	 *            请求对象
	 * @param httpclient
	 *            client对象
	 */
	private static void abortConnection(final HttpRequestBase hrb,
			final HttpClient httpclient) {
		if (hrb != null) {
			try {
				hrb.abort();
			} catch (Exception e) {
			}
		}
		if (httpclient != null) {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 判断当前网络
	 * 
	 * @param
	 * @return 0 :无网络 1：wifi 2：cmwap 3:cmnet
	 * @author liumeng 2012-8-17下午01:06:10
	 */
	public static int checkNet(Context context) {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) {
				// 获取网络连接管理的对象
				NetworkInfo info = connectivity.getActiveNetworkInfo();
				// 判断当前网络是否已经连接
				if (info != null && info.isConnected()) {
					// 判断当前网络是否是WIFI
					if (info.getTypeName().equalsIgnoreCase("WIFI")) {
						IS_CMWAP = false;
						return 1;
					} else if (info.getTypeName().equalsIgnoreCase("MOBILE")) {
						String apn = info.getExtraInfo();
						if (apn != null && apn.equals("cmwap")) {
							IS_CMWAP = true;
							return 2;
						} else {
							IS_CMWAP = false;
							return 3;
						}
					} else {// 未知网络类型统一识别为wifi
						IS_CMWAP = false;
						return 1;
					}
				} else {
					IS_CMWAP = false;
					return 0;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			IS_CMWAP = false;
			return 0;
		}
		return 0;
	}

	/**
	 * 获取DefaultHttpClient实例
	 * 
	 * @return DefaultHttpClient 对象
	 */
	private static DefaultHttpClient getDefaultHttpClient() {
		HttpParams httpParams = new BasicHttpParams();

		// DefaultHttpClient httpclient = new DefaultHttpClient();

		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小
		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);
		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

		// 设置重定向，缺省为 true
		HttpClientParams.setRedirecting(httpParams, true);

		// 设置 user agent
		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
		HttpProtocolParams.setUserAgent(httpParams, userAgent);
		DefaultHttpClient httpclient = new DefaultHttpClient(httpParams);

		// 设置cmwap代理
		if (IS_CMWAP) {
			HttpHost proxy = new HttpHost(CMWAP_HOST, CMWAP_PORT);
			httpclient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
					proxy);
		}

		return httpclient;
	}

	/************************************* 已下用于ssl get方式访问网络 *******************************************/
	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 * @param paramsCharset
	 *            参数提交编码集 可为null、默认UTF-8
	 * @param resultCharset
	 *            返回结果编码集可为null、默认UTF-8
	 * @return String
	 */
	public static String httpByGet2StringSSL(String url, String paramsCharset,
			String resultCharset) {
		if (url == null || "".equals(url)) {
			return null;
		}
		String responseStr = null;
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			httpClient = getNewHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			if (resultCharset == null || "".equals(resultCharset)) {
				responseStr = EntityUtils.toString(response.getEntity(),
						CHARSET);
			} else {
				responseStr = EntityUtils.toString(response.getEntity(),
						resultCharset);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return responseStr;
	}
	/**
	 * Get方式提交
	 * 
	 * @param url
	 *            提交地址
	 */
	public static InputStream httpByGet2InputStreamSSL(String url) {
		if (url == null || "".equals(url)) {
			return null;
		}
		HttpClient httpClient = null;
		HttpGet hg = null;
		try {
			httpClient = getNewHttpClient();
			hg = new HttpGet(url);
			// 发送请求，得到响应
			HttpResponse response = httpClient.execute(hg);
			return response.getEntity().getContent();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			abortConnection(hg, httpClient);
		}
		return null;
	}
	/**
	 * 
	 * javax.net.ssl.SSLPeerUnverifiedException: No peer certificate
	 * 
	 * @author Administrator
	 * 
	 */
	public static class SSLSocketFactoryEx extends SSLSocketFactory {

		SSLContext sslContext = SSLContext.getInstance("TLS");

		public SSLSocketFactoryEx(KeyStore truststore)
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkClientTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}

				@Override
				public void checkServerTrusted(
						java.security.cert.X509Certificate[] chain,
						String authType)
						throws java.security.cert.CertificateException {
				}
			};
			sslContext.init(null, new TrustManager[] { tm }, null);
		}

		@Override
		public Socket createSocket(Socket socket, String host, int port,
				boolean autoClose) throws IOException, UnknownHostException {
			return sslContext.getSocketFactory().createSocket(socket, host,
					port, autoClose);
		}

		@Override
		public Socket createSocket() throws IOException {
			return sslContext.getSocketFactory().createSocket();
		}
	}

	public static HttpClient getNewHttpClient() {
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);

			SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
			sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));

			ClientConnectionManager ccm = new ThreadSafeClientConnManager(
					params, registry);

			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			return new DefaultHttpClient();
		}
	}
}