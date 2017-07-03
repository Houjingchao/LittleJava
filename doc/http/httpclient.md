# HttpClient

```java
HttpCient client = new DefaultHttpClient();
HttpGet httpGet = new HttpGet();
HttpResponse response = client.execute(httpGet);
HttpEntity entity = response.getEntity();
byte[] bytes = EntityUtils.toByteArray(entity);
String result = new String(bytes,"utf-8");
```