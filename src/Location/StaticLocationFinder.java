package Location;

import java.util.HashMap;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	private HashMap<Long, Position> knownLocations;
	private Position lastPosition;
	
	public StaticLocationFinder() {
		knownLocations = Utils.getKnownLocations();
		lastPosition = new Position(0, 0);
	}
	
	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		MacRssiPair[] filteredData = filterData(data);
		Position[] positions = new Position[filteredData.length];
		double x = 0;
		double y = 0;
		printMacs(filteredData);
		for (int i = 0; i < filteredData.length; i++) {
			positions[i] = knownLocations.get(filteredData[i].getMacAsLong());
			x += positions[i].getX();
			y += positions[i].getY();
		}
		x = x / positions.length;
		y = y / positions.length;
		
		if (!(Double.isNaN(x) || Double.isNaN(y))) {
			lastPosition = new Position(x, y);
		}
		return lastPosition;
	}
	
	private MacRssiPair[] filterData(MacRssiPair[] data) {
		int dataLength = data.length;
		for (int i = 0; i < data.length; i++) {
			if (!knownLocations.containsKey(data[i].getMacAsLong())) {
				data[i] = null;
				dataLength--;
			}
		}
		MacRssiPair[] ret = new MacRssiPair[dataLength];
		int counter = 0;
		for (int i = 0; i < data.length; i++) {
			if (data[i] != null) {
				ret[counter] = data[i];
				counter++;
			}
		}
		return ret;
	}

	private void printMacs(MacRssiPair[] data) {
		for (MacRssiPair pair : data) {
			System.out.println(pair);
		}
	}
}
