package br.com.convergeti.operacoes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public interface FileRWInt {
	public void open(String file) throws UnsupportedEncodingException, FileNotFoundException;
	public void open(String arquivo, String charset) throws UnsupportedEncodingException, FileNotFoundException;
	public String readLine() throws IOException;
	public void writeLine(String line) throws IOException;
	public void close() throws IOException;
}
