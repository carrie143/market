package com.gop.api.cloud.client;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by zhangjinyang on 2018/6/20.
 */
public class SimpleHttpClient {

  private static final Logger log = LoggerFactory.getLogger(SimpleHttpClient.class);

  private CloseableHttpClient provider;
  private String nodeUrl;

  public SimpleHttpClient(CloseableHttpClient provider) {
    log.info("** SimpleHttpClientImpl(): initiating the HTTP communication layer");
    this.provider = provider;
  }

  public SimpleHttpClient(CloseableHttpClient provider, String nodeUrl) {
    log.info("** SimpleHttpClientImpl(): initiating the HTTP communication layer");
    this.provider = provider;
    this.nodeUrl = nodeUrl;
  }

  public void setNodeUrl(String nodeUrl) {
    this.nodeUrl = nodeUrl;
  }

  public String execute(String reqMethod, String reqPayload, Header[] headers) throws HttpLayerException {
    CloseableHttpResponse response = null;
    try {
      response = provider.execute(getNewRequest(reqMethod, reqPayload, headers), new BasicHttpContext());
      response = checkResponse(response);
      HttpEntity respPayloadEntity = response.getEntity();
      String respPayload = Constants.STRING_EMPTY;
      if(respPayloadEntity != null) {
        respPayload = EntityUtils.toString(respPayloadEntity);
        EntityUtils.consume(respPayloadEntity);
      }
      log.debug("-- execute(..): '{}' response payload received for HTTP '{}' request with "
              + "status line '{}'", ((respPayloadEntity == null) ? "null" : "non-null"),
          reqMethod, response.getStatusLine());
      return respPayload;
    } catch (ClientProtocolException e) {
      throw new HttpLayerException(Errors.REQUEST_HTTP_FAULT, e);
    } catch (IOException e) {
      throw new HttpLayerException(Errors.IO_UNKNOWN, e);
    } catch (URISyntaxException e) {
      throw new HttpLayerException(Errors.PARSE_URI_FAILED, e);
    } finally {
      if(response != null) {
        try {
          log.debug("-- execute(..): attempting to recycle old HTTP response (reply to a "
              + "'{}' request) with status line '{}'", reqMethod, response
              .getStatusLine());
          response.close();
        } catch (IOException e) {
          log.warn("<< execute(..): failed to recycle old HTTP response, message was: "
              + "'{}'", e.getMessage());
        }
      }
    }
  }

  public void close() {
    try {
      log.info(">> close(..): attempting to shut down the underlying HTTP provider");
      provider.close();
    } catch (IOException e) {
      log.warn("<< close(..): failed to shut down the underlying HTTP provider, message was: "
          + "'{}'", e.getMessage());
    }
  }

  private HttpRequestBase getNewRequest(String reqMethod, String reqPayload, Header[] headers)
      throws URISyntaxException, UnsupportedEncodingException {

    HttpRequestBase request;
    if(reqMethod.equals(HttpConstants.REQ_METHOD_POST)) {
      HttpPost postRequest = new HttpPost();
      postRequest.setEntity(new StringEntity(reqPayload, ContentType.create(
          DataFormats.JSON.getMediaType(), Constants.UTF_8)));
      request = postRequest;
    } else {
      request = new HttpGet();
    }
    request.setURI(new URI(nodeUrl));
    request.setHeaders(headers);
    log.debug("<< getNewRequest(..): returning a new HTTP '{}' request with target endpoint "
        + "'{}' and headers '{}'", reqMethod, request.getURI(), request.getAllHeaders());
    return request;

  }

  private CloseableHttpResponse checkResponse(CloseableHttpResponse response)
      throws HttpLayerException {
    log.debug(">> checkResponse(..): checking HTTP response for non-OK status codes & "
        + "unexpected header values");
    StatusLine statusLine = response.getStatusLine();
    if((statusLine.getStatusCode() >= 400) && (statusLine.getStatusCode() <= 499)) {
      throw new HttpLayerException(Errors.RESPONSE_HTTP_CLIENT_FAULT, statusLine.toString());
    }
    if((statusLine.getStatusCode() >= 500) && (statusLine.getStatusCode() <= 599)) {
      throw new HttpLayerException(Errors.RESPONSE_HTTP_SERVER_FAULT, statusLine.toString());
    }
    return response;
  }


}
