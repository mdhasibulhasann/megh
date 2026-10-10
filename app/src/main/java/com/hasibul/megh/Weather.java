package com.hasibul.megh;
import org.json.*;import java.net.*;import java.io.*;import android.content.*;
final class Weather {
 static String fetch(double lat,double lon) throws Exception {
 URL u=new URL("https://api.open-meteo.com/v1/forecast?latitude="+lat+"&longitude="+lon+"&current=temperature_2m,apparent_temperature,relative_humidity_2m,weather_code,wind_speed_10m,is_day&hourly=temperature_2m,precipitation_probability,weather_code&daily=temperature_2m_max,temperature_2m_min,weather_code,uv_index_max&timezone=auto&forecast_days=7");
 HttpURLConnection c=(HttpURLConnection)u.openConnection();c.setConnectTimeout(15000);c.setReadTimeout(15000);
 try {if(c.getResponseCode()!=200)throw new IOException("Weather unavailable");try(InputStream in=c.getInputStream()){ByteArrayOutputStream out=new ByteArrayOutputStream();byte[] buf=new byte[8192];int n;while((n=in.read(buf))!=-1)out.write(buf,0,n);return new String(out.toByteArray(),java.nio.charset.StandardCharsets.UTF_8);}}finally{c.disconnect();}
 }
 static String advice(JSONObject j,boolean bn) throws Exception {
 JSONObject h=j.getJSONObject("hourly"),cur=j.getJSONObject("current");String now=cur.getString("time").substring(0,13);JSONArray times=h.getJSONArray("time"),probs=h.getJSONArray("precipitation_probability");int start=0;while(start<times.length()&&!times.getString(start).startsWith(now))start++;
 int rain=0;for(int i=start;i<Math.min(start+6,probs.length());i++)rain=Math.max(rain,probs.optInt(i));
 if(rain>=50)return bn?"আগামী কয়েক ঘণ্টায় বৃষ্টির সম্ভাবনা আছে। ছাতা সঙ্গে নিও।":"Rain is possible in the next few hours. Take an umbrella.";
 if(cur.getDouble("apparent_temperature")>=35)return bn?"অনেক গরম লাগতে পারে। পানি সঙ্গে রাখো, ছায়ায় বিশ্রাম নাও।":"It may feel very hot. Carry water and take breaks in the shade.";
 if(j.getJSONObject("daily").getJSONArray("uv_index_max").optDouble(0)>=6)return bn?"আজ UV বেশি হতে পারে। বাইরে গেলে রোদ থেকে সুরক্ষা নাও।":"UV may be high today. Use sun protection outdoors.";
 if(cur.getDouble("wind_speed_10m")>=35)return bn?"বাতাস বেশি। বাইরে সাবধানে চলাফেরা করো।":"It is windy. Take care outdoors.";
 if(cur.getDouble("temperature_2m")<=12)return bn?"আজ ঠান্ডা। গরম পোশাক সঙ্গে রেখো।":"It is chilly. Keep a warm layer with you.";
 return bn?"বের হওয়ার আগে ঘণ্টাভিত্তিক পূর্বাভাস দেখে নিও।":"Check the hourly forecast before heading out.";
 }
}
