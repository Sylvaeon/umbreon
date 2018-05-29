package me.sylvaeon.umbreon.helper;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class MapHelper {
    public static <K, V extends Comparable> Map<K, V> sortByValues(Map<K, V> tempMap) {
        TreeMap<K, V> map = new TreeMap<>(buildComparator(tempMap));
        map.putAll(tempMap);
        return map;
    }

    public static <K, V extends Comparable> Comparator<? super K> buildComparator(final Map<K, V> tempMap) {
        return (o1, o2) -> tempMap.get(o1).compareTo(tempMap.get(o2));
    }
    
    public static <T extends Comparable<T>> Map<T, Integer> collectionToAmountMap(Collection<T> list) {
    	Map<T, Integer> map = new TreeMap<>();
    	if(!list.isEmpty()) {
			for (T t : list) {
				if(t == null) {
					System.out.println("null");
				}
				map.putIfAbsent(t, 0);
				map.put(t, map.get(t) + 1);
			}
		}
		return map;
	}
    
}
