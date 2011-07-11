package br.com.convergeti.operacoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public abstract class Operacao implements OperacaoInt {

	private String columnsHeader[] = {};
	private String columns[] = {};
	private FileWriter out;
	private FileReader in;

	public void openIn(String file) {
		openIn(new File(file));
	}

	public void openIn(File file) {
		this.in = new FileReader();
		try {
			this.in.open(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String readInLine() {
		try {
			return this.in.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void closeIn() {
		try {
			this.in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void openOut(String file) {
		openOut(new File(file));
	}

	public void openOut(File file) {
		 this.out = new FileWriter();
		 try {
			this.out.open(file);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void writeOutLine(String line) {
		try {
			this.out.writeLine(line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void closeOut() {
		try {
			this.out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static public String truncateLeft(String value, int size) {
		if (value.length()>size) {
			int start = value.length()-size;
			return new String(value).substring(start, value.length());
		}
		return value;
	}

	static public String customFormat(String pattern, double value ) {
		DecimalFormat myFormatter = new DecimalFormat(pattern);
		String output = myFormatter.format(value);
		return output;
	}

	static public String zeroComplete(String value, int size) {
		String ret = "";
		String v = value.trim();
		if (v.length()!=size) {
			int max = size-v.length();
			for (int i = 0; i<max; i++) {
				ret += "0";
			}
		}
		return new String(ret+v).substring(0, size);
	}

	static public String blankComplete(String value, int size) {
		String ret = "";
		String v = value.trim();
		if (v.length()!=size) {
			int max = size-v.length();
			for (int i = 0; i<max; i++) {
				ret += " ";
			}
		}
		return new String(ret+v).substring(0, size);
	}

	static public String blankCompleteD(String value, int size) {
		String ret = "";
		String v = value.trim();
		if (v.length()!=size) {
			int max = size-v.length();
			for (int i = 0; i<max; i++) {
				ret += " ";
			}
		}
		return new String(v+ret).substring(0, size);
	}

	static public String currencyZeroComplete(Double value, int size) {
		String ret = Operacao.customFormat("0.00", value);
		return Operacao.zeroComplete(ret.replaceAll("[^0-9\\-]", ""), size);
	}

	public int getColumnPos(String busca) {
		int i = 0;
		for (i=0; i<columnsHeader.length; i++) {
			if (columnsHeader[i].equalsIgnoreCase(busca)) {
				return i;
			}
		}
		return -1;
	}

	public void setColumnsHeader(String colunasHeader[]) {
		this.columnsHeader = colunasHeader;
	}

	public String[] getColumnsHeader() {
		return columnsHeader;
	}

	public void setColumns(String line) {
		/**
		 * quebra de colunas por ; e filtrando aspas no inicio e fim
		 */
		this.columns = line.split(";");
		for (int i=0; i<this.columns.length; i++) {
			this.columns[i] = this.columns[i].trim().
				replaceFirst("^\"", "").
				replaceFirst("([^\\\\])\"$", "$1");
		}
	}

	public String[] getColumns() {
		return columns;
	}

	public String getLineData(String column) {
		return this.columns[this.getColumnPos(column)].trim();
	}

	public Double getCurrency(String column) {
		String currency = getLineData(column).replace(",", ".");
		if (currency.length()==0) currency = "0.0";
		return Double.parseDouble(currency);
	}

	public Integer getInt(String column) {
		String num = this.columns[this.getColumnPos(column)].trim();
		if (num.length()==0) return new Integer(0);
		else return Integer.parseInt(num);
	}

	public String numericToDb(String value) {
		if (value.length()==0) return "NULL";
		else return value;
	}

	public String stringToDb(String value) {
		return StringUtil.getToDb(value);
	}

	public String getSubLayout(String line, int pos, int tam) {
		int npos = pos-1;
		return line.substring(npos, npos+tam);
	}

	public String getSubLayoutDb(String line, int pos, int tam) {
		return stringToDb(getSubLayout(line, pos, tam));
	}

	public String getSubLayoutDbTrim(String line, int pos, int tam) {
		return stringToDb(getSubLayout(line, pos, tam).trim());
	}

	public String getSubLayoutDbName(String line, int pos, int tam) {
		return stringToDb(StringUtil.getRegularChars(getSubLayout(line, pos, tam).trim()));
	}

	public Integer getSubLayoutNumber(String line, int pos, int tam) {
		return Integer.parseInt(getSubLayout(line, pos, tam));
	}

	public Long getSubLayoutLong(String line, int pos, int tam) {
		return Long.parseLong(getSubLayout(line, pos, tam));
	}

	public Double getSubLayoutDouble(String line, int pos, int tam) {
		Double ret = new Double(Integer.parseInt(getSubLayout(line, pos, tam)));
		return (ret/100.0);
	}

}
