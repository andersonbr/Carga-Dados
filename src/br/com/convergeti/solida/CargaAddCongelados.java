package br.com.convergeti.solida;

import br.com.convergeti.operacoes.Operacao;

public class CargaAddCongelados extends Operacao {
	public CargaAddCongelados(String in, String out) {
		openIn(in);
		openOut(out);
	}
	public void start() {
		String line = null;
		for (int lineNumber = 0; (( line = readInLine()) != null);lineNumber++ ) {
			setColumns(line);
			if (lineNumber>0) {
//				String servidor      = getLineData("servidor");
				String matricula     = getLineData("matricula");
				String orgao         = getLineData("orgao");
				String consignataria = getLineData("consignataria");
//				String output = "select * from web.associado_consignataria WHERE "+
//				"idassociado = (SELECT id FROM web.associado where matricula = '"+matricula+"' and idorgao = "+orgao+") and "+
//				"idconsignataria = "+consignataria+";";
				String output = "INSERT into web.associado_consignataria (idassociado, idconsignataria) "+
					"(SELECT id, "+consignataria+" FROM web.associado where matricula = '"+matricula+"' and idorgao = "+orgao+");";
				writeOutLine(output);	
			} else {
				this.setColumnsHeader(getColumns().clone());
			}
//			String nome = stringToDb(getSubLayout(line, 1, 30));
//			String ident = stringToDb(getSubLayout(line, 31, 13));
//			String orgaoExp = stringToDb(getSubLayout(line, 44, 4));
//			String ufExp = stringToDb(getSubLayout(line, 48, 2));
//			String CPF = stringToDb(getSubLayout(line, 50, 11));
//			String orgao = stringToDb(getSubLayout(line, 61, 3));
//			String matricula = stringToDb(getSubLayout(line, 64, 11));
//			String dfuncao = defaultChars(stringToDb(getSubLayout(line, 75, 15)));
//			String dataAdm = stringToDb(dataToPostgreSQL(getSubLayout(line, 90,
//					8)));
//			String situacao = stringToDb(getSubLayout(line, 98, 2).substring(0,
//					1));
//			String enderecoRua = stringToDb(getSubLayout(line, 100, 40));
//			Integer enderecoNum = Integer.parseInt(getSubLayout(line, 140, 5));
//			String enderecoCompl = stringToDb(getSubLayout(line, 145, 20));
//			String enderecoBairro = stringToDb(getSubLayout(line, 165, 20));
//			String municipio = stringToDb(getSubLayout(line, 185, 15));
//			String cep = stringToDb(getSubLayout(line, 200, 8));
//			String fone = stringToDb(getSubLayout(line, 208, 8));
//			String dataNasc = stringToDb(dataToPostgreSQL(getSubLayout(line,
//					216, 8)));
//			Integer estadoCivil = Integer.parseInt(getSubLayout(line, 224, 1));
//			String tituloEleitNr = stringToDb(getSubLayout(line, 225, 12));
//			String tituloEleitZn = stringToDb(getSubLayout(line, 237, 3));
//			String tituloEleitSes = stringToDb(getSubLayout(line, 240, 4));
//			Integer sexo = (getSubLayout(line, 244, 1).equals("M")) ? 1 : 2;
//			Integer grauInstr = Integer.parseInt(getSubLayout(line, 245, 1));
//			BigDecimal valorMargem = new BigDecimal(getSubLayout(line, 246, 11))
//					.divide(dez, 2, RoundingMode.HALF_EVEN);
//			String output = "INSERT INTO associado (cd_associado, cd_sexo, id_orgao, cd_estado_civil, nm_associado, dt_nascimento, "
//					+ "ds_tel_residencia, cep, ds_endereco, nr_endereco, ds_complemento, ds_bairro, rg, cpf, ds_org_exp, "
//					+ "cd_estado_org_exp, dt_admissao, fl_situacao, nr_matricula, margem, funcao, municipio, instrucao) VALUES "
//					+ "(nextval('seq_associado'), "
//					+ sexo
//					+ ", 57, "
//					+ estadoCivil
//					+ ", "
//					+ nome
//					+ ", "
//					+ dataNasc
//					+ ","
//					+ fone
//					+ ", "
//					+ cep
//					+ ", "
//					+ enderecoRua
//					+ ", "
//					+ enderecoNum
//					+ ", "
//					+ enderecoCompl
//					+ ", "
//					+ enderecoBairro
//					+ ", "
//					+ ident
//					+ ", "
//					+ CPF
//					+ ", "
//					+ orgaoExp
//					+ ","
//					+ ufExp
//					+ ", "
//					+ dataAdm
//					+ ", "
//					+ situacao
//					+ ", "
//					+ matricula
//					+ ", "
//					+ valorMargem
//					+ ", "
//					+ dfuncao
//					+ ", " + municipio + ", " + grauInstr + ");";
//			writeOutLine(output);
		}
		closeIn();
		closeOut();
	}

	public static void main(String[] args) {

		Operacao op = new CargaAddCongelados(
			"/home/anderson/Sistemas/Solida/Variacoes/201107/Passo2/Reativacao_CAFAZ.csv",
			"/home/anderson/Sistemas/Solida/Variacoes/201107/Passo2/Reativacao_CAFAZ.csv.sql"
		);
		op.start();
	}
}
