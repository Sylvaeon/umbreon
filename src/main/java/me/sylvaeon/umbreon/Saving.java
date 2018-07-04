package me.sylvaeon.umbreon;

import com.google.common.collect.Table;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class Saving {
	private static void saveField(Object inObject) throws NoSuchFieldException, IllegalAccessException {
		Class c = inObject.getClass();
		Field field = c.getDeclaredField(nName);
		Object object = field.get(inObject);
		if(object instanceof String) {
			jsonObject.put(name, object);
		} else if(object instanceof Number) {
			jsonObject.put(name, object);
		} else if(object instanceof Object[]) {
			JSONArray array = new JSONArray();
			for(Object obj : (Object[]) object) {
				array.add(obj);
			}
			jsonObject.put(name, array);
		} else if(object instanceof Collection) {
			JSONArray array = new JSONArray();
			for(Object obj : (Collection) object) {
				array.add(obj);
			}
			jsonObject.put(name, array);
		} else if(object instanceof Map) {
			Map map = (Map) object;
			JSONObject json = new JSONObject();
			Set<Map.Entry> entrySet = map.entrySet();
			for(Map.Entry entry : entrySet) {
				jsonObject.put(toJSONFormat(entry.getKey()), toJSONFormat(entry.getValue()));
			}
			jsonObject.put(name, json);
		} else if(object instanceof Table) {
			Table table = (Table) object;
			JSONObject jsonObject = new JSONObject();
			Set<Table.Cell> cellSet = table.cellSet();
			for(Table.Cell cell : cellSet) {
				jsonObject.put(new KeyPair(cell.getRowKey(), cell.getColumnKey()).toString(), toJSONFormat(cell.getValue()));
			}
		}
		return object.toString();
	}

	private static <T> T fromJSONFormat(Field field) {

	}

	public static void saveObject(Object object, String fileName, List<String> fields) {
		File file = new File("src/main/resources/" + fileName + ".json");
		JSONObject jsonObject = new JSONObject();
		Class c = object.getClass();
		if(fields == null) {
			fields = new ArrayList<>();
			for(Field field : c.getDeclaredFields()) {
				fields.add(field.getName());
			}
		}
		for(String string : fields) {
			try {
				Field field = c.getField(string);
				Object value = field.get(object);
				jsonObject.put(field.getName(), toJSONFormat(value));
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public static Object loadObject(Object object, String fileName, List<String> fields) {

	}

}
