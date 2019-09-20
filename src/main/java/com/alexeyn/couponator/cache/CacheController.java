package com.alexeyn.couponator.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CacheController implements ICacheController {

    private Map<Object, Object> cacheMap;

    public CacheController() {
        this.cacheMap = new HashMap<>();
    }

    @Override
    public Object get(Object key) {
        return this.cacheMap.get(key);
    }

    @Override
    public void put(Object key, Object value) {
        this.cacheMap.put(key, value);
    }


}
