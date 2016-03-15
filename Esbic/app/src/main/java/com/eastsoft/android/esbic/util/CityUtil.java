package com.eastsoft.android.esbic.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.content.Context;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CityUtil
{
	private static CityUtil instance = new CityUtil();
	private Context context;
	private ArrayList<String> province_list = new ArrayList<String>();
	private ArrayList<String> city_list = new ArrayList<String>();
	public ArrayList<String> province_list_code = new ArrayList<String>();
	public ArrayList<String> city_list_code = new ArrayList<String>();
	private List<CityInfo> provinceInfo_list = new ArrayList<>();
	private HashMap<String, List<CityInfo>> city_map = new HashMap<>();

	private CityUtil()
	{
	}

	public static CityUtil getInstance() {
		if (instance == null)
		{
			synchronized (CityUtil.class)
			{
				if (instance == null)
				{
					instance = new CityUtil();
				}
			}
		}
		return instance;
	}

	public void setContext(Context context)
	{
		this.context = context;
		getaddressinfo();
	}

	private void getaddressinfo() {
		// 读取城市信息string
		JSONParser parser = new JSONParser();
		String area_str = FileUtil.readAssets(context, "area.json");
		provinceInfo_list = parser.getJSONParserResult(area_str, "province");
		city_map = parser.getJSONParserResultArray(area_str, "city");
	}

	public ArrayList<String> getProvince() {
		if (province_list_code.size() > 0) {
			province_list_code.clear();
		}
		if (province_list.size() > 0) {
			province_list.clear();
		}
		for (int i = 0; i < provinceInfo_list.size(); i++) {
			province_list.add(provinceInfo_list.get(i).getCity_name());
			province_list_code.add(provinceInfo_list.get(i).getId());
		}
		return province_list;
	}

	public ArrayList<String> getCity(String provice) {
		String provicecode = "";
		ArrayList<String> tmp = new ArrayList<>();
		for(CityInfo info : provinceInfo_list)
		{
			if(info.getCity_name().compareTo(provice) == 0)
			{
				provicecode = info.getId();
				break;
			}
		}
		if (city_list_code.size() > 0) {
			city_list_code.clear();
		}
		if (city_list.size() > 0) {
			city_list.clear();
		}
		List<CityInfo> city = city_map.get(provicecode);
		System.out.println("city--->" + city.toString());
		for (int i = 0; i < city.size(); i++) {
			city_list.add(city.get(i).getCity_name());
			tmp.add(city.get(i).getCity_name());
			city_list_code.add(city.get(i).getId());
		}
		return tmp;
	}

	public class JSONParser {
		public ArrayList<String> province_list_code = new ArrayList<>();
		public ArrayList<String> city_list_code = new ArrayList<>();

		public List<CityInfo> getJSONParserResult(String JSONString, String key) {
			List<CityInfo> list = new ArrayList<>();
			JsonObject result = new JsonParser().parse(JSONString)
					.getAsJsonObject().getAsJsonObject(key);

			Iterator<?> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
						.next();
				CityInfo cityinfo = new CityInfo();
				cityinfo.setCity_name(entry.getValue().getAsString());
				cityinfo.setId(entry.getKey());
				province_list_code.add(entry.getKey());
				list.add(cityinfo);
			}
			return list;
		}

		public HashMap<String, List<CityInfo>> getJSONParserResultArray(
				String JSONString, String key) {
			HashMap<String, List<CityInfo>> hashMap = new HashMap<>();
			JsonObject result = new JsonParser().parse(JSONString)
					.getAsJsonObject().getAsJsonObject(key);

			Iterator<?> iterator = result.entrySet().iterator();
			while (iterator.hasNext()) {
				@SuppressWarnings("unchecked")
				Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator
						.next();
				List<CityInfo> list = new ArrayList<>();
				JsonArray array = entry.getValue().getAsJsonArray();
				for (int i = 0; i < array.size(); i++) {
					CityInfo cityinfo = new CityInfo();
					cityinfo.setCity_name(array.get(i).getAsJsonArray().get(0)
							.getAsString());
					cityinfo.setId(array.get(i).getAsJsonArray().get(1)
							.getAsString());
					city_list_code.add(array.get(i).getAsJsonArray().get(1)
							.getAsString());
					list.add(cityinfo);
				}
				hashMap.put(entry.getKey(), list);
			}
			return hashMap;
		}
	}

	public class CityInfo implements Serializable
	{

		private String id;
		private String city_name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getCity_name() {
			return city_name;
		}

		public void setCity_name(String city_name) {
			this.city_name = city_name;
		}

		@Override
		public String toString() {
			return JsonUtil.toString(this);
		}

	}
}
