package de.zabuza.sparkle.selectors;

import de.zabuza.sparkle.freewar.EWorld;

public final class Paths {
	public final static String PROTOCOL = "http://";
	public final static String DE_DOMAIN = "freewar.de";
	public final static String EN_DOMAIN = "freewar.com";
	public final static String DOMAIN_SEPARATOR = ".";
	public final static String URL_SEPARATOR = "/";
	public final static String REGULAR_WORLD = "welt";
	public final static String EN_WORLD = "world";
	public final static String ACTION_WORLD = "afsrv";
	public final static String ROLEPLAY_WORLD = "rpsrv";
	public final static String LOGIN = "freewar";
	
	private Paths() {
		
	}
	
	public static String getFullWorldDomain(final EWorld world) {
		StringBuilder sb = new StringBuilder();
		sb.append(PROTOCOL);
		
		if (world == EWorld.ACTION) {
			sb.append(ACTION_WORLD);
		} else if (world == EWorld.ROLEPLAY) {
			sb.append(ROLEPLAY_WORLD);
		} else if (world == EWorld.ONE_EN) {
			sb.append(EN_WORLD).append(1);
		} else {
			sb.append(REGULAR_WORLD);
			int worldNumber;
			if (world == EWorld.ONE) {
				worldNumber = 1;
			} else if (world == EWorld.TWO) {
				worldNumber = 2;
			} else if (world == EWorld.THREE) {
				worldNumber = 3;
			} else if (world == EWorld.FOUR) {
				worldNumber = 4;
			} else if (world == EWorld.FIVE) {
				worldNumber = 5;
			} else if (world == EWorld.SIX) {
				worldNumber = 6;
			} else if (world == EWorld.SEVEN) {
				worldNumber = 7;
			} else if (world == EWorld.EIGHT) {
				worldNumber = 8;
			} else if (world == EWorld.NINE) {
				worldNumber = 9;
			} else if (world == EWorld.TEN) {
				worldNumber = 10;
			} else if (world == EWorld.ELEVEN) {
				worldNumber = 11;
			} else if (world == EWorld.TWELVE) {
				worldNumber = 12;
			} else if (world == EWorld.THIRTEEN) {
				worldNumber = 13;
			} else {
				worldNumber = 14;
			}
			sb.append(worldNumber);
		}
		sb.append(DOMAIN_SEPARATOR);
		
		if (world != EWorld.ONE_EN) {
			sb.append(DE_DOMAIN);
		} else {
			sb.append(EN_DOMAIN);
		}
		
		sb.append(URL_SEPARATOR);
		
		return sb.toString();
	}
}
