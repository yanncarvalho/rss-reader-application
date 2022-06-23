package br.dev.yann.rssreader.job;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.ejb.Stateless;

import okhttp3.OkHttpClient;
import okhttp3.Request;


@Stateless
public class RequestXmlFromHttp {

  private final OkHttpClient client = new OkHttpClient().newBuilder()
                                        .connectTimeout(1L, TimeUnit.MINUTES)
                                        .readTimeout(1L, TimeUnit.MINUTES)
                                        .callTimeout(1L, TimeUnit.MINUTES)
                                        .build();

  public InputStream getXml(String url) throws IOException {

    var request = new Request.Builder().url(url)
                                      .header("Content-Type", "application/xml")
                                      .build();

    var response = this.client.newCall(request).execute();

    return response.body().byteStream();
  }
}









