package com.xzl.demo1.swagger;

import android.content.Context;
import android.os.Environment;
import android.support.v4.os.EnvironmentCompat;
import android.text.TextUtils;
import android.util.ArraySet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.xzl.demo1.LLog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class ParseModule {
    public ParseModule() {
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    private static Context mContext;
    private static PostMan postMan;
    private final static String BaseUrl = "http://kt-dag.yun-ti.com:6520";
    private final static String protocol = "http";
    private final static String[] host = {"kt-dag", "yun-ti", "com"};
    private final static String port = "6520";


//    swapUI
//
//###设置根URL
//    http://kt-dag.yun-ti.com:6520
//
// ###分类方式
//
//
//###配置消息类型和消息描述的键值对
///api/device/v1/screen/getAlarmBroadcastCfg
//
//
//###代码段
//
//    参数类型，
//type
//1、string, none,uint64,
//2、integer int32,int64
//3、number double
//4、array，那么同一个节点，一定有个items，索引到相应的定义去
//5、 "$ref"：直接找索引
//
//    如果是array，那么必然有个"items":{"$ref":"..."}
//###软件怎么做？(应该画流程图)


    private final static JsonArray header = new JsonArray();
    static {
        try {
            JsonObject subHeadObj = new JsonObject();
            subHeadObj.addProperty("key","Content-Type");
            subHeadObj.addProperty("name","Content-Type");
            subHeadObj.addProperty("value","application/json");
            subHeadObj.addProperty("type","text");
            header.add(subHeadObj);
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static JSONObject swaggerData;

    public static void start(Context context) {
        mContext = context;
        postMan = new PostMan();
        PostMan.InfoBean infoBean = new PostMan.InfoBean();
        infoBean.setName("http://kt-dag.yun-ti.com:6520/swagger-ui");
        infoBean.setSchema("https://schema.getpostman.com/json/collection/v2.1.0/collection.json");
        infoBean.set_postman_id("c3208a3f-a311-4bf5-beaa-58889a3835c4");
        postMan.setInfo(infoBean);

        List<PostMan.EventBean> eventBeans = new ArrayList<>();
        PostMan.EventBean eventBean0 = new PostMan.EventBean();
        eventBean0.setListen("prerequest");
        PostMan.EventBean.ScriptBean scriptBean0 = new PostMan.EventBean.ScriptBean();
        scriptBean0.setId("2b8c40c5-6b29-45f1-a98d-bbb324185510");
        scriptBean0.setType("text/javascript");
        String[] exeArray0 = new String[]{""};
        scriptBean0.setExec(Arrays.asList(exeArray0));
        eventBean0.setScript(scriptBean0);

        PostMan.EventBean eventBean1 = new PostMan.EventBean();
        eventBean1.setListen("test");
        PostMan.EventBean.ScriptBean scriptBean1 = new PostMan.EventBean.ScriptBean();
        scriptBean1.setId("53e796e5-025e-402a-8b94-b73802249564");
        scriptBean1.setType("text/javascript");
        String[] exeArray1 = new String[]{""};
        scriptBean1.setExec(Arrays.asList(exeArray1));
        eventBean1.setScript(scriptBean1);

        eventBeans.add(eventBean0);
        eventBeans.add(eventBean1);
        postMan.setEvent(eventBeans);
        postMan.setItemMap(new LinkedHashMap<>());
        postMan.setItem(new ArrayList<>());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    loadOriginFile();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }).start();
    }

    private static void loadOriginFile() throws Exception {
        LLog.d("start loadOriginFile...");
        StringBuilder result = new StringBuilder();
        try {
            InputStreamReader inputReader = new InputStreamReader(mContext.getResources().getAssets().open("swaggerdata.json"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line;
            while ((line = bufReader.readLine()) != null)
                result.append(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (TextUtils.isEmpty(result.toString())) {
            return;
        }

        swaggerData = new JSONObject(result.toString());
//        //所有定义的url路径
        JSONObject apiPaths = swaggerData.optJSONObject("paths");
        JSONObject definitions = swaggerData.optJSONObject("definitions");
        try {
            Iterator<String> iterator = apiPaths.keys();
            while (iterator.hasNext()) {
                String urlSubKey = iterator.next();
                int lastSplitIndex = urlSubKey.lastIndexOf("/");
                String groupUrlKey = urlSubKey.substring(0, lastSplitIndex);
                String lastUrlDesc = urlSubKey.substring(lastSplitIndex, urlSubKey.length());
                PostMan.ItemGroup requestGroup = postMan.getItemMap().get(groupUrlKey);
                if (requestGroup == null) {
                    requestGroup = new PostMan.ItemGroup();
                    requestGroup.setName(groupUrlKey);
                    List<PostMan.ItemGroup.ItemBean> subRequests = new ArrayList<PostMan.ItemGroup.ItemBean>();
                    requestGroup.setItem(subRequests);
                    postMan.getItemMap().put(groupUrlKey, requestGroup);
                    postMan.getItem().add(requestGroup);
                }

                JSONObject subObj = apiPaths.optJSONObject(urlSubKey);
                JSONObject postObj = subObj.optJSONObject("post");

                PostMan.ItemGroup.ItemBean itemBean = new PostMan.ItemGroup.ItemBean();
                itemBean.setName(postObj.optString("summary") + lastUrlDesc);
                itemBean.setResponse(new ArrayList<>());

                PostMan.ItemGroup.ItemBean.RequestBean requestBean = new PostMan.ItemGroup.ItemBean.RequestBean();
                requestBean.setMethod("POST");
                requestBean.setHeader(header);

                PostMan.ItemGroup.ItemBean.RequestBean.UrlBean urlBean = new PostMan.ItemGroup.ItemBean.RequestBean.UrlBean();
                urlBean.setRaw(BaseUrl + urlSubKey);
                urlBean.setProtocol(protocol);
                urlBean.setHost(Arrays.asList(host));
                urlBean.setPort(port);
                String apiPathExcludeFirstChar = urlSubKey.substring(1);
                urlBean.setPath(Arrays.asList(apiPathExcludeFirstChar.split("/")));
                requestBean.setUrl(urlBean);
                PostMan.ItemGroup.ItemBean.RequestBean.BodyBean bodyBean = new PostMan.ItemGroup.ItemBean.RequestBean.BodyBean();
                bodyBean.setMode("raw");


                /***
                 * 形成bodyBean的req string
                 */
                JSONObject tmpReqObj = null;
                JSONArray tmpReqArray = postObj.optJSONArray("parameters");
                if (tmpReqArray == null || tmpReqArray.length() == 0) {
                    throw new RuntimeException("!!!Error0");
                }
                for (int i = 0; i < tmpReqArray.length(); i++) {
                    JSONObject obj = tmpReqArray.optJSONObject(i);
                    if (obj.optString("name").equals("body") && obj.optString("in").equals("body")) {
                        tmpReqObj = obj;
                        break;
                    }
                }

                if (tmpReqObj == null) {
                    throw new RuntimeException("!!!Error2");
                }

                JSONObject requestSchemaObj = tmpReqObj.optJSONObject("schema");
                if (requestSchemaObj == null) {
                    throw new RuntimeException("!!!Error3");
                }

                String $ref = requestSchemaObj.optString("$ref");
                if ($ref == null) {
                    throw new RuntimeException("!!!Error4");
                }

                String[] splits = $ref.split("/");
                String rootReqParamPath = splits[splits.length - 1];
                JSONObject finalRequestObj = new JSONObject();
                generateReqObj(finalRequestObj, definitions, rootReqParamPath);
                /************bodyBean req string end**************/
                bodyBean.setRaw(toPrettyFormat(finalRequestObj.toString()));
                requestBean.setBody(bodyBean);
                itemBean.setRequest(requestBean);
                requestGroup.getItem().add(itemBean);
            }

            postMan.setItemMap(null);
            Gson gson = new Gson();
            String jsonString = gson.toJson(postMan);
            trans2PostmanFile(jsonString);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        LLog.d("end loadOriginFile...");
    }


    private static void generateReqObj(JSONObject generateObj, JSONObject definitions, String rootReqParamPath) throws Exception {

        JSONObject defineObj = definitions.optJSONObject(rootReqParamPath);
        JSONObject dataNode = defineObj.optJSONObject("properties");

        Iterator<String> iterator = dataNode.keys();
        while (iterator.hasNext()) {
            String propertyName = iterator.next();
            JSONObject descObj = dataNode.getJSONObject(propertyName);
            Iterator<String> typeDes = descObj.keys();
            Set<String> typesSet = new HashSet<>();
            while (typeDes.hasNext()) {
                typesSet.add(typeDes.next());
            }

            if (typesSet.contains("type")) { // 如果有type节点
                String typeName = descObj.optString("type");
                if (typeName.equals("string")) {
                    generateObj.put(propertyName, "string");
                } else if (typeName.equals("integer")) {
                    generateObj.put(propertyName, 0);
                } else if (typeName.equals("number")) {
                    generateObj.put(propertyName, 0.01);
                } else if (typeName.equals("array")) {
                    JSONObject itemTo = descObj.optJSONObject("items");
                    String $ref = itemTo.optString("$ref");
                    String[] splits = $ref.split("/");
                    String subRootReqParamPath = splits[splits.length - 1];
                    JSONObject finalRequestObj = new JSONObject();
                    generateReqObj(finalRequestObj, definitions, subRootReqParamPath);
                    JSONArray jsonArray = new JSONArray();
                    jsonArray.put(finalRequestObj);
                    generateObj.put(propertyName, jsonArray);
                }
            } else if (typesSet.contains("$ref")) { //如果没有type节点，一般肯定有$ref节点
                String $ref = descObj.optString("$ref");
                String[] splits = $ref.split("/");
                String subRootReqParamPath = splits[splits.length - 1];
                JSONObject finalRequestObj = new JSONObject();
                generateReqObj(finalRequestObj, definitions, subRootReqParamPath);
                generateObj.put(propertyName,finalRequestObj);
            }
        }
    }

    public static String toPrettyFormat(String json) {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(json).getAsJsonObject();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(jsonObject);
    }

    private static void trans2PostmanFile(String jsonString) throws Exception
    {
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Postman.json");
        if (file.exists()) {
            file.delete();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
        bufferedOutputStream.write(jsonString.getBytes(Charset.forName("UTF-8")));
        bufferedOutputStream.flush();
        bufferedOutputStream.close();
    }
}
