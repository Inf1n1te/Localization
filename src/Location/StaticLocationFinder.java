package Location;

import java.util.HashMap;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	private HashMap<Long, Position> knownLocations;
	
	public StaticLocationFinder() {
		knownLocations = Utils.getKnownLocations();
	}
	
	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		Position[] positions = new Position[data.length];
		double x = 0;
		double y = 0;
		int numberOfNodes = 0;
		
		printMacs(data);
		
		for (int i = 0; i < data.length; i++) {

			if (knownLocations.containsKey(data[i].getMacAsLong())) {
				positions[i] = knownLocations.get(data[i].getMacAsLong());
			} else {
				positions[i] = new Position(0, 0);
			}
			x += positions[i].getX();
			y += positions[i].getY();
		}
		x = x / numberOfNodes;
		y = y / numberOfNodes;
		
		return new Position(x, y);
	}
	
	private void printMacs(MacRssiPair[] data) {
		for (MacRssiPair pair : data) {
			System.out.println(pair);
		}
	}


}
