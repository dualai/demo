package com.xzl.demo1.swaggerUI2Postman;

import com.google.gson.JsonArray;

import java.util.LinkedHashMap;
import java.util.List;

public class PostMan {


    /**
     * info : {"_postman_id":"c3208a3f-a311-4bf5-beaa-58889a3835c4","name":"梯口横屏","schema":"https://schema.getpostman.com/json/collection/v2.1.0/collection.json"}
     * item : [{"name":"物业通知","item":[{"name":"获取物业通知(0x20000062)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getPlayText"]}},"response":[]},{"name":"设置物业通知(0x20000064)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"textList\": [\r\n\t  {\"uuid\":\"1\",\"text\":\"一\"},\r\n\t  {\"uuid\":\"2\",\"text\":\"二\"},\r\n\t  {\"uuid\":\"3\",\"text\":\"三\"},\r\n\t  {\"uuid\":\"4\",\"text\":\"四\"},\r\n\t  {\"uuid\":'5\",\"text\":\"五\"}\r\n  ]\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/setPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","setPlayText"]}},"response":[]},{"name":"停止物业通知(0x2000007C)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"uuidList\": [\r\n\t  {\"uuid\":\"6\"},\r\n\t  {\"uuid\":\"5\"},\r\n\t  {\"uuid\":\"4\"},\r\n\t  {\"uuid\":\"3\"},\r\n\t  {\"uuid\":\"2\"},\r\n\t  {\"uuid\":\"1\"}\r\n  ]\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/stopPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","stopPlayText"]}},"response":[]}]},{"name":"位置信息","item":[{"name":"获取位置信息(0x2000007E)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getLocation","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getLocation"]}},"response":[]},{"name":"设置位置信息(0x20000060)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"latitude\": \"53.563490\",\r\n  \"longitude\": \"123.269180\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/setLocationInfo","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","setLocationInfo"]}},"response":[]}]},{"name":"电梯基本信息","item":[{"name":"获取电梯基本信息(0x2000006E)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getLiftBaseInfo","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getLiftBaseInfo"]}},"response":[]},{"name":"设置电梯基本信息(0x20000070)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"liftRegisterCode\": \"1314520\",\r\n  \"liftId\": \"10001\",\r\n  \"liftDesc\": \"我家电梯\",\r\n  \"liftBrand\": \"东方红牌\",\r\n  \"floorNum\": 0,\r\n  \"manufacturer\": \"格力制造\",\r\n  \"rateSpeed\": 10,\r\n  \"limitWeight\": 800,\r\n  \"limitPerson\": 10,\r\n  \"ratedShake\": 1,\r\n  \"speedThreshold\": 2,\r\n  \"quakeThreshold\": 3,\r\n  \"districtName\": \"采荷小区\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/setLiftBaseInfo","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","setLiftBaseInfo"]}},"response":[]}]},{"name":"实时截屏","item":[{"name":"实时截屏(0x2000005A)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/runtimeScreenshot","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","runtimeScreenshot"]}},"response":[]}]},{"name":"VAC鉴权","item":[{"name":"vac鉴权1","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{ \n \"mac\":\"B44F96030B14\",\n \"moduleType\":303,\n \"protocolType\":1\n}"},"url":{"raw":"https://vac.yun-ti.com:1992/vac/authResource","protocol":"https","host":["vac","yun-ti","com"],"port":"1992","path":["vac","authResource"]}},"response":[]},{"name":"vac鉴权2","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{ \n \"mac\":\"B44F96030B14\",\n \"moduleType\":303,\n \"protocolType\":1\n}"},"url":{"raw":"https://vac.yun-ti.com:1992/vac/authResource","protocol":"https","host":["vac","yun-ti","com"],"port":"1992","path":["vac","authResource"]}},"response":[]}]}]
     * event : [{"listen":"prerequest","script":{"id":"2b8c40c5-6b29-45f1-a98d-bbb324185510","type":"text/javascript","exec":[""]}},{"listen":"test","script":{"id":"53e796e5-025e-402a-8b94-b73802249564","type":"text/javascript","exec":[""]}}]
     */

    private InfoBean info;
    private LinkedHashMap<String,ItemGroup> itemMap;
    private List<ItemGroup> item;
    private List<EventBean> event;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public LinkedHashMap<String, ItemGroup> getItemMap() {
        return itemMap;
    }

    public void setItemMap(LinkedHashMap<String, ItemGroup> itemMap) {
        this.itemMap = itemMap;
    }

    public List<ItemGroup> getItem() {
        return item;
    }

    public void setItem(List<ItemGroup> item) {
        this.item = item;
    }

    public List<EventBean> getEvent() {
        return event;
    }

    public void setEvent(List<EventBean> event) {
        this.event = event;
    }

    public static class InfoBean {
        /**
         * _postman_id : c3208a3f-a311-4bf5-beaa-58889a3835c4
         * name : 梯口横屏
         * schema : https://schema.getpostman.com/json/collection/v2.1.0/collection.json
         */

        private String _postman_id;
        private String name;
        private String schema;

        public String get_postman_id() {
            return _postman_id;
        }

        public void set_postman_id(String _postman_id) {
            this._postman_id = _postman_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }
    }

    public static class ItemGroup {
        /**
         * name : 物业通知
         * item : [{"name":"获取物业通知(0x20000062)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getPlayText"]}},"response":[]},{"name":"设置物业通知(0x20000064)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"textList\": [\r\n\t  {\"uuid\":\"1\",\"text\":\"一\"},\r\n\t  {\"uuid\":\"2\",\"text\":\"二\"},\r\n\t  {\"uuid\":\"3\",\"text\":\"三\"},\r\n\t  {\"uuid\":\"4\",\"text\":\"四\"},\r\n\t  {\"uuid\":'5\",\"text\":\"五\"}\r\n  ]\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/setPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","setPlayText"]}},"response":[]},{"name":"停止物业通知(0x2000007C)","request":{"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\",\r\n  \"uuidList\": [\r\n\t  {\"uuid\":\"6\"},\r\n\t  {\"uuid\":\"5\"},\r\n\t  {\"uuid\":\"4\"},\r\n\t  {\"uuid\":\"3\"},\r\n\t  {\"uuid\":\"2\"},\r\n\t  {\"uuid\":\"1\"}\r\n  ]\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/stopPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","stopPlayText"]}},"response":[]}]
         */

        private String name;
        private List<ItemBean> item;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ItemBean> getItem() {
            return item;
        }

        public void setItem(List<ItemBean> item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * name : 获取物业通知(0x20000062)
             * request : {"method":"POST","header":[],"body":{"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"},"url":{"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getPlayText"]}}
             * response : []
             */

            private String name;
            private RequestBean request;
            private List<?> response;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public RequestBean getRequest() {
                return request;
            }

            public void setRequest(RequestBean request) {
                this.request = request;
            }

            public List<?> getResponse() {
                return response;
            }

            public void setResponse(List<?> response) {
                this.response = response;
            }

            public static class RequestBean {
                /**
                 * method : POST
                 * header : []
                 * body : {"mode":"raw","raw":"{\r\n  \"bindDevId\": \"\",\r\n  \"hardwareId\": \"8CF7105D1815\"\r\n}"}
                 * url : {"raw":"http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getPlayText","protocol":"http","host":["kt-dag","yun-ti","com"],"port":"6520","path":["api","lift","v1","screen","getPlayText"]}
                 */

                private String method;
                private BodyBean body;
                private UrlBean url;
                private JsonArray header;

                public String getMethod() {
                    return method;
                }

                public void setMethod(String method) {
                    this.method = method;
                }

                public BodyBean getBody() {
                    return body;
                }

                public void setBody(BodyBean body) {
                    this.body = body;
                }

                public UrlBean getUrl() {
                    return url;
                }

                public void setUrl(UrlBean url) {
                    this.url = url;
                }

                public JsonArray getHeader() {
                    return header;
                }

                public void setHeader(JsonArray header) {
                    this.header = header;
                }

                public static class BodyBean {
                    /**
                     * mode : raw
                     * raw : {
                     "bindDevId": "",
                     "hardwareId": "8CF7105D1815"
                     }
                     */

                    private String mode;
                    private String raw;

                    public String getMode() {
                        return mode;
                    }

                    public void setMode(String mode) {
                        this.mode = mode;
                    }

                    public String getRaw() {
                        return raw;
                    }

                    public void setRaw(String raw) {
                        this.raw = raw;
                    }
                }

                public static class UrlBean {
                    /**
                     * raw : http://kt-dag.yun-ti.com:6520/api/lift/v1/screen/getPlayText
                     * protocol : http
                     * host : ["kt-dag","yun-ti","com"]
                     * port : 6520
                     * path : ["api","lift","v1","screen","getPlayText"]
                     */

                    private String raw;
                    private String protocol;
                    private String port;
                    private List<String> host;
                    private List<String> path;

                    public String getRaw() {
                        return raw;
                    }

                    public void setRaw(String raw) {
                        this.raw = raw;
                    }

                    public String getProtocol() {
                        return protocol;
                    }

                    public void setProtocol(String protocol) {
                        this.protocol = protocol;
                    }

                    public String getPort() {
                        return port;
                    }

                    public void setPort(String port) {
                        this.port = port;
                    }

                    public List<String> getHost() {
                        return host;
                    }

                    public void setHost(List<String> host) {
                        this.host = host;
                    }

                    public List<String> getPath() {
                        return path;
                    }

                    public void setPath(List<String> path) {
                        this.path = path;
                    }
                }
            }
        }
    }

    public static class EventBean {
        /**
         * listen : prerequest
         * script : {"id":"2b8c40c5-6b29-45f1-a98d-bbb324185510","type":"text/javascript","exec":[""]}
         */

        private String listen;
        private ScriptBean script;

        public String getListen() {
            return listen;
        }

        public void setListen(String listen) {
            this.listen = listen;
        }

        public ScriptBean getScript() {
            return script;
        }

        public void setScript(ScriptBean script) {
            this.script = script;
        }

        public static class ScriptBean {
            /**
             * id : 2b8c40c5-6b29-45f1-a98d-bbb324185510
             * type : text/javascript
             * exec : [""]
             */

            private String id;
            private String type;
            private List<String> exec;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public List<String> getExec() {
                return exec;
            }

            public void setExec(List<String> exec) {
                this.exec = exec;
            }
        }
    }
}
