package webSimple.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public abstract class ScriptUtil {
	public static final int EXPRT_NUMBER = 50000;


	public <T> List<List<T>> burstList(List<T> list, Integer number) {
		List<List<T>> result = new ArrayList<List<T>>();
		if (list != null) {
			if (list.size() < number) {
				result.add(list);
				return result;
			}
			int i = 0;
			List<T> alist = new ArrayList<T>();
			Integer size = list.size();
			for (T t : list) {
				alist.add(t);
				i++;
				if (i == number) {
					result.add(alist);
					alist = new ArrayList<T>();
					i = 0;
				}
			}
			if ((size % number) != 0) {
				result.add(alist);
			}
		}
		return result;
	}


	public abstract <T> ArrayList<ArrayList<String>> operationalDataToArray(List<T> list);


	public abstract File arrayToXSL(ArrayList<ArrayList<String>> data, String fileName);
}
