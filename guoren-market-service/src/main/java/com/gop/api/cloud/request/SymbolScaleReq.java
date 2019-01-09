package com.gop.api.cloud.request;

import com.alibaba.fastjson.JSONObject;
import com.gop.domain.ConfigSymbol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * Created by YAO on 2018/8/1.
 */
@Data
public class SymbolScaleReq {
  private Long nanoTime;
  private List<Map<String,String>> symbolList;

  public SymbolScaleReq(Long nanoTime, List<Map<String,String>> symbols){
    this.nanoTime = nanoTime;
    this.symbolList = symbols;
  }

  public static void main(String[] args) {
    List<ConfigSymbol> configSymbols = new ArrayList<>();
    ConfigSymbol symbol = new ConfigSymbol();
    symbol.setPriceAsset("BTC");
    symbol.setTradeAsset("ACT");
    configSymbols.add(symbol);
    Map map = new HashMap<String,String>();
    configSymbols.forEach(v -> {
                            map.put("priceAsset",v.getPriceAsset());
                            map.put("tradeAsset",v.getTradeAsset());}
                         );
    List<Map<String,String>> list = new ArrayList<>();
    list.add(map);

    SymbolScaleReq s = new SymbolScaleReq(System.nanoTime(),list);
    System.out.println(JSONObject.toJSONString(s));
  }
}
