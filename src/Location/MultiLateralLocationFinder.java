package Location;

import java.util.HashMap;

import Utils.MacRssiPair;
import Utils.Position;
import Utils.Utils;

public class MultiLateralLocationFinder implements LocationFinder {

	public static final double UNITS_PER_METER = 0.6;
	public static final int SIGNAL_OFFSET = 45;
	
	private HashMap<Long, Position> knownLocations;
	private Position lastPosition;
	private int approximationCounter;
	private HashMap<Long, Integer> bestData;
	
	public MultiLateralLocationFinder() {
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
		double x = 0;
		double y = 0;
		
		for (int i = 0; i < filteredData.length; i++) {
			if ((bestData.containsKey(filteredData[i].getMacAsLong()) 
					&& bestData.get(filteredData[i].getMacAsLong()) < filteredData[i].getRssi()) 
					|| !bestData.containsKey(filteredData[i].getMacAsLong())) {
				bestData.put(filteredData[i].getMacAsLong(), filteredData[i].getRssi());
			}
		}
		
		Position[] positions = new Position[bestData.size()];
		double[] distanceToAP = new double[bestData.size()];
//		double sumOfFactors = 0;

		boolean isInRanges = false;
		
		for (double errorMargin = 1; !isInRanges ; errorMargin += 0.05) {
			double sumOfFactors = 0;
			int iterationCounter = 0;
			for (Long mac : bestData.keySet()) {
				positions[iterationCounter] = knownLocations.get(mac);
				distanceToAP[iterationCounter] = getDistanceFromRSSI(bestData.get(mac)) * errorMargin;
				sumOfFactors += distanceToAP[iterationCounter];
				System.out.println("sumOfFactors = " + sumOfFactors);
				iterationCounter++;
			}
		
		
			for (int i = 0; i < positions.length; i++) {
				x += positions[i].getX() * (distanceToAP[i] / sumOfFactors);
				y += positions[i].getY() * (distanceToAP[i] / sumOfFactors);
			}
			
			isInRanges = true;
			for (int i = 0; i < positions.length; i++) {
				if (Math.sqrt(Math.pow(x - positions[i].getX(),2) + Math.pow(y - positions[i].getY(),2)) >= distanceToAP[i]) {
					isInRanges = false;
				}
			}
			
		}
	
		if (x > 1 || y > 1) {
			lastPosition = new Position(x, y);
		}
		
		return lastPosition;
	}
	private double getDistanceFromRSSI(int rssi) {
		return -(rssi - SIGNAL_OFFSET)*UNITS_PER_METER;
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
