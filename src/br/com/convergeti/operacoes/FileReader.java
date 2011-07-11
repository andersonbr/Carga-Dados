package br.com.convergeti.operacoes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class FileReader implements FileRWInt {

	private BufferedReader file = null;

	public FileReader() {
	}
	
	public void open(String file) throws FileNotFoundException, UnsupportedEncodingException {
		open(file, "UTF-8");
	}
	
	public void open(File file) throws FileNotFoundException, UnsupportedEncodingException {
		open(file, "UTF-8");
	}

	public void open(String file, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(file), charset);
		
		this.file = new BufferedReader(reader);
	}
	
	public void open(File file, String charset) throws FileNotFoundException, UnsupportedEncodingException {
		InputStreamReader reader = new InputStreamReader(
				new FileInputStream(file), charset);
		
		this.file = new BufferedReader(reader);
	}
	
	public String readLine() throws IOException {
		return file.readLine();
	}

	public void writeLine(String line) throws IOException {
	}

	public void close() throws IOException {
		file.close();
	}
}
