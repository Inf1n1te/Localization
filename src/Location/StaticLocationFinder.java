package Location;

import java.util.HashMap;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	private HashMap<Long, Position> knownLocations;
	private Position lastPosition;
	private int approximationCounter;
	
	public StaticLocationFinder() {
		knownLocations = Utils.getKnownLocations();
		lastPosition = new Position(0, 0);
		approximationCounter = 1;
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
		
		double[] factors = new double[filteredData.length];
		double sumOfFactors = 0;
		
		for (int i = 0; i < filteredData.length; i++) {
			positions[i] = knownLocations.get(filteredData[i].getMacAsLong());
			factors[i] = Math.pow(-filteredData[i].getRssi(), -3);
			sumOfFactors += factors[i];
		}
		
		for (int i = 0; i < filteredData.length; i++) {
			x += positions[i].getX() * (factors[i] / sumOfFactors);
			y += positions[i].getY() * (factors[i] / sumOfFactors);
		}
		
		if (x > 1 || y > 1) {
			lastPosition = positionApproximation(new Position(x, y));
		}
		
		return lastPosition;
	}
	
	private Position positionApproximation(Position newPosition) {
		double x = (1 - Math.pow(approximationCounter, -1)) * lastPosition.getX() 
				+ (Math.pow(approximationCounter, -1)) * newPosition.getX();
		double y = (1 - Math.pow(approximationCounter, -1)) * lastPosition.getY() 
				+ (Math.pow(approximationCounter, -1)) * newPosition.getY();
		approximationCounter++;
		return new Position(x, y);
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
