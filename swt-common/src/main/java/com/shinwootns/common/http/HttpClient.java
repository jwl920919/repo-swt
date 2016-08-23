package com.shinwootns.common.http;

import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClient {

	//region Variables
	private final Logger _logger = LoggerFactory.getLogger(getClass());

	private String _baseURL;
	private String _id;
	private String _pwd;

	private int _connect_timeout = 10000;
	private int _read_timeout = 10000;
	private String _encoding = "UTF-8";
	private String _agent = "RestClient (shinwootns.com)";

	private CloseableHttpClient _httpClient = null;
	//endregion
	
	//region Connect / Close
	public boolean Connect_Https(String baseURL, String id, String pwd) {

		this._baseURL = baseURL;
		this._id = id;
		this._pwd = pwd;

		boolean bResult = false;

		if (_httpClient != null)
			Close();

		try {

			// SSLContext
			SSLContext sslContext = SSLContext.getInstance("TLS");

			sslContext.init(null, new TrustManager[] { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(X509Certificate[] certs, String authType) {
				}
			} }, new SecureRandom());

			// HostnameVerifier
			HostnameVerifier allHostsValid = new HostnameVerifier() {
				public boolean verify(String hostname, SSLSession session) {
					return true;
				}
			};

			// SSLConnectionSocketFactory
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, allHostsValid);

			// CredentialsProvider
			CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
			credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(this._id, this._pwd));

			// HttpClient
			_httpClient = HttpClients.custom().setDefaultCredentialsProvider(credentialsProvider)
					.setSSLSocketFactory(sslsf).build();

			// Check
			if (_httpClient != null)
				bResult = true;

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
			bResult = false;
		}

		return bResult;
	}

	public void Close() {
		try {
			if (_httpClient != null)
				_httpClient.close();

		} catch (IOException e) {
			//e.printStackTrace();
		} finally {
			_httpClient = null;
		}
	}
	//endregion
	
	//region Http Get
	public String Get(String subURL, Map<String,String> params) {

		if (_httpClient == null)
			return null;

		try {

			// Add parameters
			if (params != null && params.size() > 0)
			{
				List<NameValuePair> paramList = convertParam(params);
				
				subURL += "?" + URLEncodedUtils.format(paramList, _encoding);
			}
			
			return Get(subURL);

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		}

		return null;
	}
	
	public String Get(String subURL) {

		if (_httpClient == null)
			return null;

		String value = null;

		CloseableHttpResponse response = null;
		
		try {

			// Full URL
			String url = makeFullURL(subURL);
			
			// Create HttpGet
			HttpGet httpGet = new HttpGet(url);
			httpGet.setHeader("User-Agent", _agent);

			// Execute
			response = _httpClient.execute(httpGet);

			if (response != null) {

				// Status Code
				int nStatusCode = response.getStatusLine().getStatusCode();
				// System.out.println(response.getStatusLine());

				if (nStatusCode == HttpStatus.SC_OK || nStatusCode == HttpStatus.SC_ACCEPTED) {

					// HttpEntity
					HttpEntity entity = response.getEntity();

					if (entity != null) {

						// Response Body
						value = EntityUtils.toString(entity, _encoding);
					
						// Consume
						EntityUtils.consume(entity);
					}
				}
				else
				{
					System.out.println(response.getStatusLine());
				}
			}

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
			return null;
		} finally {
			
			try {
				 if ( response != null)
					response.close();
			} catch (IOException e) {}

			response = null;
		}

		return value;
	}
	//endregion

	//region Http Post
	public String Post(String subURL, ContentType contentType, Map<String,String> params) {
		if (_httpClient == null)
			return null;

		String value = null;

		CloseableHttpResponse response = null;
		
		try {
			
			// Full URL
			String url = makeFullURL(subURL);

			// Create HttpPost
			HttpPost postRequest = new HttpPost(url);
			postRequest.setHeader("User-Agent", _agent);
			
			if (contentType != null)
				postRequest.setHeader("Context-Type", contentType.toString());
			
			if (params != null) {
				List<NameValuePair> paramList = convertParam(params);
				postRequest.setEntity(new UrlEncodedFormEntity(paramList, _encoding));
			}
			
			// String Entity
			// StringEntity inputEntity = new StringEntity(inputValue);
			// inputEntity.setContentType(contentType);
			// postRequest.setEntity(inputEntity);

			// Execute
			response = _httpClient.execute(postRequest);

			if (response != null) {

				// Status Code
				int nStatusCode = response.getStatusLine().getStatusCode();
				// System.out.println(response.getStatusLine());

				// SC_OK=200, SC_ACCEPTED=202, SC_CREATED=201
				if (nStatusCode == HttpStatus.SC_CREATED) {

					// HttpEntity
					HttpEntity entity = response.getEntity();

					if (entity != null) {

						// Response Body
						value = EntityUtils.toString(entity, _encoding);
						
						// Consume
						EntityUtils.consume(entity);
					}
				}
				else
				{
					System.out.println(response.getStatusLine());
				}
			}

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {}
			
			response = null;
		}

		return value;
	}
	//endregion
	
	//region Http Delete
	public String Delete(String subURL, ContentType contentType, Map<String,String> params)
	{
		if (_httpClient == null)
			return null;

		String value = null;
		CloseableHttpResponse response = null;

		try {
			
			// Full URL
			String url = makeFullURL(subURL);

			// Create HttpPost
			HttpDelete deleteRequest = new HttpDelete(url);
			deleteRequest.setHeader("User-Agent", _agent);
			if (contentType != null)
				deleteRequest.setHeader("Context-Type", contentType.toString());

			// Add parameters
			if (params != null && params.size() > 0)
			{
				List<NameValuePair> paramList = convertParam(params);
				
				url += "?" + URLEncodedUtils.format(paramList, _encoding);
			}
	
			// Execute
			response = _httpClient.execute(deleteRequest);

			if (response != null) {

				// Status Code
				int nStatusCode = response.getStatusLine().getStatusCode();
				// System.out.println(response.getStatusLine());

				if (nStatusCode == HttpStatus.SC_OK || nStatusCode == HttpStatus.SC_ACCEPTED) {

					// HttpEntity
					HttpEntity entity = response.getEntity();

					if (entity != null) {

						// Response Body
						value = EntityUtils.toString(entity, _encoding);

						// Consume
						EntityUtils.consume(entity);
					}
				}
				else
				{
					System.out.println(response.getStatusLine());
				}
			}

		} catch (Exception ex) {
			_logger.error(ex.getMessage(), ex);
		} finally {
			
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {}
			
			response = null;
		}

		return value;
	}
	//endregion

	//region Make URL
	private String makeFullURL(String subURL)
	{
		// Make URL
		String url = _baseURL + "/" + subURL;

		// Remove double slash
		url = url.replaceAll("(?<!(http:|https:))[//]+", "/");
		
		return url;
	}
	//endregion
	
	//region Convert Parameter
	private List<NameValuePair> convertParam(Map<String,String> params) {
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();

		if (params != null && params.keySet() != null) {
			Iterator<String> keys = params.keySet().iterator();
			while (keys.hasNext()) {

				String key = keys.next();

				paramList.add(new BasicNameValuePair(key, params.get(key).toString()));
			}
		}

		return paramList;
	}
	//endregion

	//region Get / Set Attributes
	public int getConnect_timeout() {
		return _connect_timeout;
	}

	public void setConnect_timeout(int connect_timeout) {
		this._connect_timeout = connect_timeout;
	}

	public int getRead_timeout() {
		return _read_timeout;
	}

	public void setRead_timeout(int read_timeout) {
		this._read_timeout = read_timeout;
	}

	public String getAgent() {
		return _agent;
	}

	public void setAgent(String _agent) {
		this._agent = _agent;
	}
	
	public String getEncoding() {
		return _encoding;
	}

	public void setEncoding(String _encoding) {
		this._encoding = _encoding;
	}
	//endregion
}
