package com.merit.meritShop.common.controller;

import com.merit.meritShop.common.domain.Result;
import org.json.simple.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class AbstractController {
    protected JSONObject return2JSON(Result result) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("rtnCd", result.getResultCode().getCode());
        jsonObject.put("rtnMsg", result.getResultCode().getMessage());
        if (result.getResultObject() != null) {
            jsonObject.put("rtnObj", result.getResultObject());
        }
        return jsonObject;
    }

    protected Map<String, Object> return2Map(Result result) {
        Map<String, Object> map = new HashMap<>();
        map.put("rtnCd", result.getResultCode().getCode());
        map.put("rtnMsg", result.getResultCode().getMessage());
        if (result.getResultObject() != null) {
            map.put("rtnObj", result.getResultObject());
        }
        return map;
    }
}
