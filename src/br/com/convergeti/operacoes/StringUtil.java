package br.com.convergeti.operacoes;

public class StringUtil {
	public static String getRegularChars(String value) {
		String irregularChars =   "áéíóúãẽĩõũàèìòùâêîôûçºª°";
		String irregularUpChars = "ÁÉÍÓÚÃẼĨÕŨÀÈÌÒÙÂÊÎÔÛÇºª°";
		String regularChars   =   "AEIOUAEIOUAEIOUAEIOUCOAO";
		
		for (int i = 0; i<irregularChars.length(); i++) {
			String search = irregularChars.substring(i, i+1);
			String searchUp = irregularUpChars.substring(i, i+1);
			String replace = regularChars.substring(i, i+1);
			value = value.replaceAll(search, replace).replace(searchUp, replace);
		}
		return value.replaceAll("[^a-zA-Z0-9\\/\\-\\\\ ]", "");
	}
	
	public static String getRegularName(String value) {
		return getRegularChars(value).replaceAll("[^a-zA-Z0-9 ]", "");
	}
	
	public static String getToDb(String value) {
		if (value.length()==0) return "NULL";
		else return "'"+value+"'";
	}
	
	public static void main(String[] args) {
		System.out.println(": "+StringUtil.getRegularChars("Mª - \\/ DE JESUS ÇAÇEÇÃo").toUpperCase());
	}
}
