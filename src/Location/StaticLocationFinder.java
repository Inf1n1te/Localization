package Location;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		Position[] positions = new Position[data.length];
		double x = 0;
		double y = 0;
		
		for (int i = 0; i < data.length; i++) {
			positions[i] = Utils.getKnownLocations().get(data[i].getMac());
			x += positions[i].getX();
			y += positions[i].getY();
		}
		x = x / positions.length;
		y = y / positions.length;
		
		return new Position(x, y);
	}

}
