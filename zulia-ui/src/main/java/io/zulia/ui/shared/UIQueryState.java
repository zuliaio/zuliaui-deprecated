package io.zulia.ui.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Payam Meyer on 3/21/17.
 * @author pmeyer
 */
public class UIQueryState {

	private static List<IndexInfo> indexes;
	private static Map<String, IndexInfo> indexesMap = new HashMap<>();

	public static List<IndexInfo> getIndexes() {
		return indexes;
	}

	public static void setIndexes(List<IndexInfo> indexes) {
		UIQueryState.indexes = indexes;
		for (IndexInfo indexInfo : indexes) {
			indexesMap.put(indexInfo.getName(), indexInfo);
		}
	}

}
