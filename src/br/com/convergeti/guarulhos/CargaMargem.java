package br.com.convergeti.guarulhos;

import java.math.BigDecimal;
import java.math.RoundingMode;

import br.com.convergeti.operacoes.Operacao;

public class CargaMargem extends Operacao {
	public CargaMargem(String in, String out) {
		openIn(in);
		openOut(out);
	}
	private String defaultChars(String s) {
		return s.replace("�", "A");
	}
	private String dataToPostgreSQL(String date) {
		String dia = date.substring(0, 2);
		String mes = date.substring(2, 4);
		String ano = date.substring(4, 8);
		return ano+"-"+mes+"-"+dia;
	}
	@Override
	public String getSubLayout(String line, int pos, int tam) {
		return super.getSubLayout(line, pos, tam).replace("�", "O").replace("'", "\\'").trim();
	}
	public String getSubLayout2(String line, int pos, int tam) {
		return super.getSubLayout(line, pos, tam).replace("�", "O").replace("\\", "").replace("'", "").trim();
	}
	public void start() {
		BigDecimal dez = new BigDecimal(100);
		String line = null;
		String create = "CREATE TABLE associado (id bigserial NOT NULL, id_orgao integer, cd_sexo integer, cd_estado_civil integer,"+
		"nm_associado character varying(120), dt_cadastro timestamp without time zone, dt_vencimento_cartao date, dt_nascimento timestamp without time zone,"+
		"ds_tel_residencia character varying(100), ds_email character varying(100), cep character varying(8), ds_endereco character varying(100),"+
		"nr_endereco integer, ds_complemento character varying(100), ds_bairro character varying(100), rg character varying(50),"+
		"cpf character varying(11), ds_senha character varying(255), ds_observacao text, cd_municipio integer, ds_org_exp character varying(10),"+
		"cd_estado_org_exp character varying, dt_emissao_rg timestamp without time zone, nm_pai character varying(120),"+
		"nm_mae character varying(120), ds_ddd_tel character varying(2), ds_ramal character varying(10), ds_end_empresa character varying(100),"+
		"nr_end_empresa integer, ds_compl_end_empresa character varying(100), ds_bairro_empresa character varying(100), cd_municipio_empresa integer,"+
		"cep_empresa character varying(8), ds_ddd_tel_empresa character varying(2), ds_tel_empresa character varying(100),"+
		"ds_ramal_empresa character varying(10), vl_salario numeric(10,2), ds_senha_eletronica character varying(255),"+
		"cd_municipio_naturalidade integer, ds_nacionalidade character varying(100), dt_admissao date, cd_endereco_corresp integer,"+
		"ds_sindicato character varying(255), fl_situacao character(1), nr_cartao bigint, fl_senha_aleatoria character(1),"+
		"nr_matricula character varying(50) NOT NULL, margem numeric(20,2), funcao character varying(256), municipio character varying(256),"+
		"instrucao integer) WITHOUT OIDS;";
		writeOutLine(create);
		for (int lineNumber = 0; (( line = readInLine()) != null);lineNumber++ ) {
			String nome           = stringToDb(getSubLayout(line, 1, 30));
			String ident          = stringToDb(getSubLayout(line, 31, 13));
			String orgaoExp       = stringToDb(getSubLayout2(line, 44, 4));
			String ufExp          = stringToDb(getSubLayout2(line, 48, 2));
			String CPF            = stringToDb(getSubLayout(line, 50, 11));
			Integer orgao         = Integer.parseInt(getSubLayout(line, 61, 3));
			String matricula      = stringToDb(getSubLayout(line, 64, 11));
			String dfuncao        = defaultChars(stringToDb(getSubLayout(line, 75, 15)));
			String dataAdm        = stringToDb(dataToPostgreSQL(getSubLayout(line, 90, 8)));
			String situacao       = stringToDb(getSubLayout(line, 98, 2).substring(0, 1));
			String enderecoRua    = stringToDb(getSubLayout(line, 100, 40));
			Integer enderecoNum   = Integer.parseInt(getSubLayout(line, 140, 5));
			String enderecoCompl  = stringToDb(getSubLayout(line, 145, 20));
			String enderecoBairro = stringToDb(getSubLayout(line, 165, 20));
			String municipio      = stringToDb(getSubLayout(line, 185, 15));
			String cep            = stringToDb(getSubLayout(line, 200, 8));
			String fone           = stringToDb(getSubLayout(line, 208, 8));
			String dataNasc       = stringToDb(dataToPostgreSQL(getSubLayout(line, 216, 8)));
			Integer estadoCivil   = Integer.parseInt(getSubLayout(line, 224, 1));
//			String tituloEleitNr  = stringToDb(getSubLayout(line, 225, 12));
//			String tituloEleitZn  = stringToDb(getSubLayout(line, 237, 3));
//			String tituloEleitSes = stringToDb(getSubLayout(line, 240, 4));
			Integer sexo          = (getSubLayout(line, 244, 1).equals("M"))?1:2;
			Integer grauInstr     = Integer.parseInt(getSubLayout(line, 245, 1));
			BigDecimal valorMargem    = new BigDecimal(getSubLayout(line, 246, 11)).divide(dez, 2, RoundingMode.HALF_EVEN);
			String output = "INSERT INTO associado (sexo, cd_orgao, cd_estado_civil, nm_associado, dt_nascimento, "+
				"ds_tel_residencia, cep, ds_endereco, nr_endereco, ds_complemento, ds_bairro, rg, cpf, ds_org_exp, "+
				"cd_estado_org_exp, dt_admissao, fl_situacao, nr_matricula, margem, funcao, municipio, instrucao) VALUES "+
				"("+sexo+", "+orgao+", "+estadoCivil+", "+nome+", "+dataNasc+","+fone+", "+cep+", "+enderecoRua+", "+
				enderecoNum+", "+enderecoCompl+", "+enderecoBairro+", "+ident+", "+CPF+", "+orgaoExp+
				","+ufExp+", "+dataAdm+", "+situacao+", "+matricula+", "+valorMargem+", "+dfuncao+", "+municipio+", "+grauInstr+");";
			writeOutLine(output);
		}
		closeIn();
		closeOut();
	}

	public static void main(String[] args) {

		Operacao op = new CargaMargem(
			"/home/anderson/Sistemas/MargemGuarulhos/Carga/Margem_20110609.txt",
			"/home/anderson/Sistemas/MargemGuarulhos/Carga/Margem_20110609.txt.sql"
		);
		op.start();
	}
}
