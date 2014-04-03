package Location;

import java.util.HashMap;

import Utils.*;

public class StaticLocationFinder implements LocationFinder {

	private HashMap<Long, Position> knownLocations;
	private Position lastPosition;
	private int approximationCounter;
	private HashMap<Long, Integer> bestData;
	
	public StaticLocationFinder() {
		knownLocations = Utils.getKnownLocations();
		lastPosition = new Position(0, 0);
		approximationCounter = 1;
		bestData = new HashMap<Long, Integer>();
	}
	
	@Override
	public Position locate(MacRssiPair[] data) {
		return triangulate(data);
	}
	
	
	public Position triangulate(MacRssiPair[] data) {
		MacRssiPair[] filteredData = filterData(data);
//		Position[] positions = new Position[filteredData.length];
		double x = 0;
		double y = 0;

		
		
		for (int i = 0; i < filteredData.length; i++) {
			if ((bestData.containsKey(filteredData[i].getMacAsLong()) 
					&& bestData.get(filteredData[i].getMacAsLong()) < filteredData[i].getRssi()) 
					|| !bestData.containsKey(filteredData[i].getMacAsLong())) {
				System.out.println("OLD  --  " + bestData.get(filteredData[i].getMacAsLong()) + "\n"
						+ "NEW  --  " + filteredData[i].getRssi());
				bestData.put(filteredData[i].getMacAsLong(), filteredData[i].getRssi());
			}
		}
		
		Position[] positions = new Position[bestData.size()];
		double[] factors = new double[bestData.size()];
		double sumOfFactors = 0;
		int iterationCounter = 0;
		
		for (Long mac : bestData.keySet()) {
			positions[iterationCounter] = knownLocations.get(mac);
			factors[iterationCounter] = Math.pow(-bestData.get(mac), -3);
			sumOfFactors += factors[iterationCounter];
			iterationCounter++;
		}
		
		for (int i = 0; i < positions.length; i++) {
			x += positions[i].getX() * (factors[i] / sumOfFactors);
			y += positions[i].getY() * (factors[i] / sumOfFactors);
		}
		
		if (x > 1 || y > 1) {
			lastPosition = new Position(x, y);
		}
		
		
		
		
		
//		double[] factors = new double[filteredData.length];
//		double sumOfFactors = 0;
//		
//		for (int i = 0; i < filteredData.length; i++) {
//			positions[i] = knownLocations.get(filteredData[i].getMacAsLong());
//			factors[i] = Math.pow(-filteredData[i].getRssi(), -3);
//			sumOfFactors += factors[i];
//		}
//		
//		for (int i = 0; i < filteredData.length; i++) {
//			x += positions[i].getX() * (factors[i] / sumOfFactors);
//			y += positions[i].getY() * (factors[i] / sumOfFactors);
//		}
//		
//		if (x > 1 || y > 1) {
//			lastPosition = positionApproximation(new Position(x, y));
//		}
		
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
