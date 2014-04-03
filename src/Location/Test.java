package Location;

import java.util.HashMap;
import java.util.LinkedList;

import Utils.*;

public class Test implements LocationFinder {

	private HashMap<Long, Position> knownLocations;
	private Position lastPosition;
	private static final byte[] MAC_ADDRESS1 = 
			new byte[]{ 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xd0, (byte) 0xa0 };
	private static final byte[] MAC_ADDRESS2 = 
			new byte[]{ 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb5, (byte) 0xa0 };
	private static final byte[] MAC_ADDRESS3 = 
			new byte[]{ 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb5, (byte) 0x60 };
	private LinkedList<Integer> rssi = new LinkedList<Integer>();
	
	public Test() {
		knownLocations = Utils.getKnownLocations();
		lastPosition = new Position(0, 0);
	}
	
	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		MacRssiPair filteredData = filterData(data);
		if (filteredData != null) {
			rssi.add(filteredData.getRssi());
			System.out.println(rssi);
		}
		return new Position(0, 0);
	}
	
	private MacRssiPair filterData(MacRssiPair[] data) {
		for (int i = 0; i < data.length; i++) {
			if (data[i].getMacAsLong() == Utils.macToLong(MAC_ADDRESS3)) {
				return data[i];
			}
		}
		return null;
	}

	private void printMacs(MacRssiPair[] data) {
		for (MacRssiPair pair : data) {
			System.out.println(pair);
		}
	}
}
