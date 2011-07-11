package br.com.convergeti.operacoes;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

public class FileWriter implements FileRWInt {

	private Writer wfile = null;

	public FileWriter() {
	}

	public void open(String arquivo) throws UnsupportedEncodingException, FileNotFoundException {
		open(arquivo, "UTF-8");
	}
	
	public void open(File arquivo) throws UnsupportedEncodingException, FileNotFoundException {
		open(arquivo, "UTF-8");
	}

	public void open(String arquivo, String charset) throws UnsupportedEncodingException, FileNotFoundException {
		this.wfile = new OutputStreamWriter(new FileOutputStream(arquivo), charset);
	}
	
	public void open(File arquivo, String charset) throws UnsupportedEncodingException, FileNotFoundException {
		this.wfile = new OutputStreamWriter(new FileOutputStream(arquivo), charset);
	}
	
	public String readLine() throws IOException {
		return null;
	}

	public void writeLine(String line) throws IOException {
		if (wfile!=null) {
			wfile.write(line+"\r\n");
		}
	}

	public void close() throws IOException {
		wfile.close();
	}
}
