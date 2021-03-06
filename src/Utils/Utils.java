package Utils;

import java.util.HashMap;

public class Utils {

	/**
	 * Make a long from a byte[6], useful for storing the MAC addresses
	 * 
	 * @param bytes
	 * @return
	 */
	public static long macToLong(byte[] bytes) {
		long ret = 0;
		ret += (bytes[0] & 0xFF) << 40;
		ret += (bytes[1] & 0xFF) << 32;
		ret += (bytes[2] & 0xFF) << 24;
		ret += (bytes[3] & 0xFF) << 16;
		ret += (bytes[4] & 0xFF) << 8;
		ret += (bytes[5]) & 0xFF;
		return ret;
	}

	/**
	 * Returns a HashMap of the known AP locations as a <Long, Postion> The Long
	 * is used because this is easily searchable for the hashMap
	 * 
	 * @return
	 */
	public static HashMap<Long, Position> getKnownLocations() {

		HashMap<Long, Position> knownLocations = new HashMap<Long, Position>();

		// ////////// Spiegel 4 ///////////////
		// bb-d1-f03-l
		byte[] temp = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xd0, (byte) 0xa0 };
		knownLocations.put(macToLong(temp), new Position(64, 35));
		// bb-d1-f03-r
		byte[] temp2 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb5, (byte) 0xa0 };
		knownLocations.put(macToLong(temp2), new Position(64, 28));
		// bb-d1-f04-r
		byte[] temp3 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb5, (byte) 0x60 };
		knownLocations.put(macToLong(temp3), new Position(71, 35));
		// bb-d1-f04-r
		byte[] temp4 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xc2, (byte) 0x50 };
		knownLocations.put(macToLong(temp4), new Position(71, 28));

		// /////// Spiegel 5 ////////////
		// bb-d1-f01-l
		byte[] temp5 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xc6, (byte) 0xd0 };
		knownLocations.put(macToLong(temp5), new Position(79, 35));
		// bb-d1-f01-r
		byte[] temp6 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xba, (byte) 0x90 };
		knownLocations.put(macToLong(temp6), new Position(79, 28));
		// bb-d1-f02-l
		byte[] temp7 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb8, (byte) 0xc0 };
		knownLocations.put(macToLong(temp7), new Position(86, 35));
		// bb-d1-fo2-r
		byte[] temp8 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xd1, (byte) 0x80 };
		knownLocations.put(macToLong(temp8), new Position(86, 35));

		// ////// Spiegel 3 ////////////
		// bb-d1-f10-l
		byte[] temp9 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xcd, (byte) 0x90 };
		knownLocations.put(macToLong(temp9), new Position(55.5, 28));

		// bb-d1-f10-r
		byte[] temp10 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xc2, (byte) 0x40 };
		knownLocations.put(macToLong(temp10), new Position(55.5, 35));

		// bb-d1-f11-l
		byte[] temp11 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb8, (byte) 0xb0 };
		knownLocations.put(macToLong(temp11), new Position(48.5, 28));

		// bb-d1-f11-r
		byte[] temp12 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xc6, (byte) 0x20 };
		knownLocations.put(macToLong(temp12), new Position(48.5, 35));

		// //////// Spiegel 6 ////////////////
		// bb-d1-b05-l
		byte[] temp13 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xd3, (byte) 0x40 };
		knownLocations.put(macToLong(temp13), new Position(64, 16.5));

		// bb-d1-b05-r
		byte[] temp14 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xca, (byte) 0xe0 };
		knownLocations.put(macToLong(temp14), new Position(71, 16.5));

		// ////////// Spiegel 7 ///////////////
		// bb-d1-e04-l
		byte[] temp15 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb6, (byte) 0x50 };
		knownLocations.put(macToLong(temp15), new Position(79, 16.5));

		// bb-d1-e04-r
		byte[] temp16 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xd6, (byte) 0x60 };
		knownLocations.put(macToLong(temp16), new Position(86, 16.5));

		// /////////// Spiegel 1 /////////////
		// bb-d1-f09-l 64:d9:89:43:b6:f0 (21,24)
		byte[] temp17 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb6, (byte) 0xf0 };
		knownLocations.put(macToLong(temp17), new Position(21, 24));
		// bb-d1-f09-r 64:d9:89:43:be:50 (21,14)
		byte[] temp18 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xbe, (byte) 0x50 };
		knownLocations.put(macToLong(temp18), new Position(21, 14));
		// bb-d1-e04-l 64:d9:89:43:b7:a0 (7,25)
		byte[] temp19 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb7, (byte) 0xa0 };
		knownLocations.put(macToLong(temp19), new Position(7, 24));
		// bb-d1-e04-r 64:d9:89:43:b2:20 (7,14)
		byte[] temp20 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb2, (byte) 0x20 };
		knownLocations.put(macToLong(temp20), new Position(7, 14));

		// /////////// Spiegel 2 /////////////
		// "bb-d1-e06-l" 64:d9:89:43:b2:b0	(39,14)
		byte[] temp21 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xb2, (byte) 0xb0 };
		knownLocations.put(macToLong(temp21), new Position(39, 14));
		// "bb-d1-e06-r" 64:d9:89:43:ca:f0	(39,24)
		byte[] temp22 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xca, (byte) 0xf0 };
		knownLocations.put(macToLong(temp22), new Position(39, 24));
		// "bb-d1-f08-l" 64:d9:89:43:bb:70	(25.5,24)
		byte[] temp23 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xbb, (byte) 0x70 };
		knownLocations.put(macToLong(temp23), new Position(25.5, 24));
		// "bb-d1-f08-r" 64:d9:89:43:c6:c0	(25.5,14)
		byte[] temp24 = { 0x64, (byte) 0xd9, (byte) 0x89, 0x43, (byte) 0xc6, (byte) 0xc0 };
		knownLocations.put(macToLong(temp24), new Position(25.5, 14));

		return knownLocations;
	}

}
