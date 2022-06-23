package br.dev.yann.rssreader.util;

import java.util.Collections;
import java.util.Map;

import br.dev.yann.rssreader.model.BucketToken;

public class BucketTokenManage extends Thread{

  private Map<Long, BucketToken> bucket;


  public BucketTokenManage(Map<Long, BucketToken> bucket){
    super();
    this.bucket = Collections.synchronizedMap(bucket);
   }

  public void run(){

   var keys = bucket.keySet();
   keys.forEach(
     key -> {
      if(bucket.get(key).isRemovable()){
        bucket.remove(key);
      }
     }
   );

  }
}
