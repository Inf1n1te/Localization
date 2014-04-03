package Location;

import java.util.HashMap;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	private HashMap<Long, Position> knownLocations = Utils.getKnownLocations();
	
	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		Position[] positions = new Position[data.length];
		double x = 0;
		double y = 0;
		
		for (int i = 0; i < data.length; i++) {
			positions[i] = knownLocations.get(data[i].getMacAsLong());
			x += positions[i].getX();
			y += positions[i].getY();
		}
		x = x / positions.length;
		y = y / positions.length;
		
		return new Position(x, y);
	}

}
