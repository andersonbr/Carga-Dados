package br.com.convergeti.operacoes;

public class CPFUtil {
	static public boolean CPF ( String strCpf ) {
		int     d1, d2;
		int     digito1, digito2, resto;
		int     digitoCPF;
		String  nDigResult;
		
		d1 = d2 = 0;
		digito1 = digito2 = resto = 0;
		

		char cpfChars[] = strCpf.toCharArray(); 
		if (cpfChars.length==11) {
			if (
					cpfChars[0]==cpfChars[1] &&
					cpfChars[1]==cpfChars[2] &&
					cpfChars[2]==cpfChars[3] &&
					cpfChars[3]==cpfChars[4] &&
					cpfChars[4]==cpfChars[5] &&
					cpfChars[5]==cpfChars[6] &&
					cpfChars[6]==cpfChars[7] &&
					cpfChars[7]==cpfChars[8] &&
					cpfChars[8]==cpfChars[9] &&
					cpfChars[9]==cpfChars[10]
			) {
				return false;
			}
		} else {
			return false;
		}
		
		for (int nCount = 1; nCount < strCpf.length() -1; nCount++) {
			digitoCPF = Integer.valueOf (strCpf.substring(nCount -1, nCount)).intValue();
			//multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
			d1 = d1 + ( 11 - nCount ) * digitoCPF;
			//para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
			d2 = d2 + ( 12 - nCount ) * digitoCPF;
		};
		
		//Primeiro resto da divisão por 11.
		resto = (d1 % 11);
		
		//Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
		if (resto < 2)
			digito1 = 0;
		else
			digito1 = 11 - resto;

		d2 += 2 * digito1;
		
		//Segundo resto da divisão por 11.
		resto = (d2 % 11);
		
		//Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
		if (resto < 2)
			digito2 = 0;
		else
			digito2 = 11 - resto;
		
		//Digito verificador do CPF que está sendo validado.
		String nDigVerific = strCpf.substring (strCpf.length()-2, strCpf.length());
		
		//Concatenando o primeiro resto com o segundo.
		nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
		
		//comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
		return nDigVerific.equals(nDigResult);
	}
}
