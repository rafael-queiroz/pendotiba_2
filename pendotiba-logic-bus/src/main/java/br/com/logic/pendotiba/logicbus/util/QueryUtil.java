package br.com.logic.pendotiba.logicbus.util;

public class QueryUtil {
	
	private static final String LIKE_SIMBOL = "%";
	
	private static String nvl(String str) {
		return str==null ? "" : str;
	}
	
	
	
	public static String toLikeMatchModeANY(String value) {
		return LIKE_SIMBOL + nvl(value) + LIKE_SIMBOL;
	}

}
