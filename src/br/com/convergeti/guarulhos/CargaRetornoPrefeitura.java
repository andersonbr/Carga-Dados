package br.com.convergeti.guarulhos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.convergeti.operacoes.Operacao;

public class CargaRetornoPrefeitura extends Operacao {
	
	public CargaRetornoPrefeitura(String in, String out) {
		openIn(in);
		openOut(out);
	}

	public void start() {
		BigDecimal cem = new BigDecimal(100);
		String line = null;
		writeOutLine("CREATE TABLE carga_retorno_prefeitura_guarulhos ("+
			"id serial PRIMARY KEY,"+
			"ano integer NOT NULL,"+
			"mes integer NOT NULL,"+
			"orgao integer,"+
			"matricula character varying(11),"+
			"consignataria integer,"+
			"valor numeric(20,2),"+
			"retorno character varying(2),"+
			"cpf character varying(11)"+
		");");
		writeOutLine("");
		for (int lineNumber = 0; (( line = readInLine()) != null);lineNumber++ ) {
			Integer ano           = getSubLayoutNumber(line, 1, 4);
			Integer mes           = getSubLayoutNumber(line, 5, 2);
			Integer orgao         = getSubLayoutNumber(line, 7, 3);
			String matricula      = stringToDb(getSubLayout(line, 10, 11));
			Integer consignataria = getSubLayoutNumber(line, 21, 6);
			BigDecimal valor      = new BigDecimal(getSubLayout(line, 27, 11)).divide(cem, 2, RoundingMode.HALF_EVEN);
			String retorno        = stringToDb(getSubLayout(line, 38, 2));
			String cpf            = stringToDb(getSubLayout(line, 40, 11));
			String output = "INSERT INTO carga_retorno_prefeitura_guarulhos (ano, mes, orgao, matricula, consignataria, valor, retorno, cpf) "+
			"VALUES ("+ano+", "+mes+", "+orgao+", "+matricula+", "+consignataria+", "+valor+", "+retorno+", "+cpf+");";
			writeOutLine(output);
		}
		closeIn();
		closeOut();
	}

	public static void main(String[] args) {

		Operacao op = new CargaRetornoPrefeitura(
			"/home/anderson/Sistemas/MargemGuarulhos/RETORNO_ORDENADO_GUARULHOS_201106.txt",
			"/home/anderson/Sistemas/MargemGuarulhos/RETORNO_ORDENADO_GUARULHOS_201106.txt.sql"
		);
		op.start();
	}
}
