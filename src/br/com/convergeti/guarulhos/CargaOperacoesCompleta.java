package br.com.convergeti.guarulhos;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.convergeti.operacoes.Operacao;

public class CargaOperacoesCompleta extends Operacao {
    public CargaOperacoesCompleta(String in, String out) {
        openIn(in);
        openOut(out);
    }
    public void start() {
        String line = null;
        writeOutLine("CREATE TABLE carga_zetra_novo ("+
    			"id serial PRIMARY KEY,"+
    			"status integer NOT NULL,"+
    			"valor numeric(20,2),"+
    			"datacontrato date NOT NULL,"+
    			"orgao integer,"+
    			"matricula character varying(11),"+
    			"consignataria integer,"+
    			"patual integer,"+
    			"ptotal integer"+
    		");");
        for (int lineNumber = 0; (( line = readInLine()) != null);lineNumber++ ) {
        	Integer consig         = getSubLayoutNumber(line, 1, 5);
            String matricula       = zeroComplete(getSubLayout(line, 6, 11).trim(), 11);
            Integer orgao          = getSubLayoutNumber(line, 47, 3);
            Integer patual         = Integer.parseInt(getSubLayout(line, 50, 3));
            Integer ptotal         = Integer.parseInt(getSubLayout(line, 53, 3));
            SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            Date dtInicioContrato = null;
            try {
                dtInicioContrato = format.parse(getSubLayout(line, 80, 8));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            BigDecimal valorParcela   = new BigDecimal(getSubLayout(line, 68, 12).replaceAll(",", "."));
            String tpSituacao = getSubLayout(line, 100, 2);
            Integer status;
            if( tpSituacao.equals("EA")) {
                status = 22;
            } else if(tpSituacao.equals("SU")) {
                status = 19;
            } else if(tpSituacao.equals("DF")) {
                status = 21;
            } else {
                status = 1;
            }
			String output = "INSERT INTO carga_zetra_novo (status, " +
				"datacontrato, orgao, matricula, consignataria, patual, ptotal, valor) VALUES "+
				"(" + status + ", '" + format2.format(dtInicioContrato) + "', " + orgao + ", '" +
				matricula + "', " + consig + ", " + patual + ", "+ptotal+", " + valorParcela + ");";
			writeOutLine(output);
        }
        closeIn();
        closeOut();
    }

    public static void main(String[] args) {
        Operacao op = new CargaOperacoesCompleta(
            "/home/anderson/Sistemas/MargemGuarulhos/ML004.txt",
            "/home/anderson/Sistemas/MargemGuarulhos/ML004.txt.sql"
        );
        op.start();
    }
}