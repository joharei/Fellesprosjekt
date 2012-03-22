package synclogic;

import java.util.Comparator;

public class SyncListenerComparator implements Comparator<SyncListener> {

	@Override
	public int compare(SyncListener o1, SyncListener o2) {
		try {
			return Integer.parseInt(o1.getObjectID()) - Integer.parseInt(o2.getObjectID());
		} catch (NumberFormatException e) {
			return 0;
		}
	}

}
