package br.com.convergeti.guarulhos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.convergeti.operacoes.Operacao;

public class CargaRetornoConsignatarias extends Operacao {
	
	public CargaRetornoConsignatarias(String in, String out) {
		openIn(in);
		openOut(out);
	}

	public void start() {
		BigDecimal cem = new BigDecimal(100);
		String line = null;
		writeOutLine("CREATE TABLE carga_retorno_consignatarias ("+
			"id serial PRIMARY KEY,"+
			"ano integer NOT NULL,"+
			"mes integer NOT NULL,"+
			"orgao integer,"+
			"matricula character varying(11),"+
			"consignataria integer,"+
			"valor numeric(20,2),"+
			"retorno character varying(2),"+
			"cpf character varying(11),"+
			"contrato character varying(18),"+
			"nome character varying(29),"+
			"patual integer,"+
			"ptotal integer,"+
			"segmento integer"+
		");");
		writeOutLine("");
		for (int lineNumber = 0; (( line = readInLine()) != null);lineNumber++ ) {
			Integer ano           = getSubLayoutNumber(line, 1, 4);
			Integer mes           = getSubLayoutNumber(line, 5, 2);
			Integer orgao         = getSubLayoutNumber(line, 7, 3);
			String matricula      = stringToDb(getSubLayout(line, 10, 11));
			Integer consignataria = getSubLayoutNumber(line, 21, 3);
			BigDecimal valor      = new BigDecimal(getSubLayout(line, 24, 11)).divide(cem, 2, RoundingMode.HALF_EVEN);
			String retorno        = stringToDb(getSubLayout(line, 35, 2));
			String cpf            = stringToDb(getSubLayout(line, 37, 11));
			String contrato       = stringToDb(getSubLayout(line, 48, 18));
			String nome           = stringToDb(getSubLayout(line, 66, 29));
			Integer patual        = getSubLayoutNumber(line, 95, 3);
			Integer ptotal        = getSubLayoutNumber(line, 98, 3);
			Integer segmento      = getSubLayoutNumber(line, 101, 2);
			String output = "INSERT INTO carga_retorno_consignatarias (ano, mes, orgao, matricula, consignataria, "+
			"valor, retorno, cpf, contrato, nome, patual, ptotal, segmento) "+
			"VALUES ("+ano+", "+mes+", "+orgao+", "+matricula+", "+consignataria+", "+valor+", "+retorno+", "+
			cpf+", "+contrato+", "+nome+", "+patual+", "+ptotal+", "+segmento+");";
			writeOutLine(output);
		}
		closeIn();
		closeOut();
	}

	public static void main(String[] args) {

		Operacao op = new CargaRetornoConsignatarias(
			"/home/anderson/Sistemas/MargemGuarulhos/52011494.txt",
			"/home/anderson/Sistemas/MargemGuarulhos/52011494.txt.sql"
		);
		op.start();
	}
}
