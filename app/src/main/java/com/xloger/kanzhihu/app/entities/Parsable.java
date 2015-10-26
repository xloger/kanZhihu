package com.xloger.kanzhihu.app.entities;

import android.database.Cursor;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by xloger on 10月26日.
 * Author:xloger
 * Email:phoenix@xloger.com
 */
public interface Parsable {
    void parseJson(JSONObject json) throws JSONException;

//    void parseCursor(Cursor cursor);
}
