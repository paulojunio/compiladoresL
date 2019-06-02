/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 17/04/2019
*/

import java.io.BufferedReader;
import java.io.FileReader;

//Classe que executa o analisador sintatico
public class AnalisadorSintatico{
	AnalisadorLexico analisadorlexico;
	TabelaSimbolos tabelasimbolos;
	Simbolo simbolo;
	BufferedReader file;
	AcoesSemanticas acoesSemanticas;
//	byte EXP_tipo;
//	int EXP_valor;
//	int EXP_tamanho;
//	byte F_tipo;
//	int F_tamanho;
//  byte F1_tipo;
//	int F1_tamanho;
//	byte G_tipo;
//	int G_tamanho;
//  byte G1_tipo;
//	int G1_tamanho;
//	byte EXPS_tipo;
//	int EXPS_tamanho;
//  byte EXPS1_tipo;
//	int EXPS1_tamanho;
  
	/**
	* Construtor da classe
	* @param BufferedReader file - arquivo a ser lido
	*/
	public AnalisadorSintatico(BufferedReader file){
		this.file = file;
		this.tabelasimbolos = new TabelaSimbolos();
		this.analisadorlexico = new AnalisadorLexico(this.file, this.tabelasimbolos);
		this.simbolo = analisadorlexico.maquinaDeEstados();
	}

	/**
	* Metodo para identificar se o simbolo e' igual ao token esperado, se for, o proximo simbolo e' lido, se nao uma mensagem de erro e' chamada
	* @param byte tokenesperado - o token que se espera
	*/
	public void CasaToken(byte tokenesperado){
		if(this.simbolo.token == tokenesperado){
			this.simbolo = analisadorlexico.maquinaDeEstados();
		}else{
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica S
	* S -> { D }* { B }*
	*/
	public void S(){
		if (this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
			while(this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
				D();
			}
		}
		if(this.simbolo.token == this.tabelasimbolos.identificador || 
			this.simbolo.token == this.tabelasimbolos.FOR || 
		    this.simbolo.token == this.tabelasimbolos.IF || 
	 	    this.simbolo.token == this.tabelasimbolos.READLN ||
		    this.simbolo.token == this.tabelasimbolos.WRITE  ||
			this.simbolo.token == this.tabelasimbolos.WRITELN ||
			this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){

			while(this.simbolo.token == this.tabelasimbolos.identificador || 
			  this.simbolo.token == this.tabelasimbolos.FOR || 
			  this.simbolo.token == this.tabelasimbolos.IF || 
			  this.simbolo.token == this.tabelasimbolos.READLN ||
			  this.simbolo.token == this.tabelasimbolos.WRITE ||
			  this.simbolo.token == this.tabelasimbolos.WRITELN ||
			  this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){
				B();
			}
		}
		tabelasimbolos.printTabelaCerta();
		//Depois que comeca o B, nao pode voltar no D, essa condicao avisa doerro caso isso ocorra
		/*if ((this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST) || 
			!(this.simbolo.token == this.tabelasimbolos.identificador || 
			this.simbolo.token == this.tabelasimbolos.FOR || 
		    this.simbolo.token == this.tabelasimbolos.IF || 
	 	    this.simbolo.token == this.tabelasimbolos.READLN ||
		    this.simbolo.token == this.tabelasimbolos.WRITE || 
			this.simbolo.token == this.tabelasimbolos.WRITELN ||
			this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA)){
			
			System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
			System.exit(0);
		}*/
		if(analisadorlexico.fimDeArquivo == false) {
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
		}//else {
				//System.out.println("Fim de arquivo");
		//}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica D
	* D -> Var { T }+ | Const id ‘=’ [ ‘-’ ] constante ‘;’
	*/
	public void D(){
		//System.out.println("Estamos no D");
		if (this.simbolo.token == this.tabelasimbolos.VAR){
			CasaToken(this.tabelasimbolos.VAR);
			do {
				T();
			}while(this.simbolo.token == this.tabelasimbolos.INTEGER || this.simbolo.token == this.tabelasimbolos.CHAR);

		}else if(this.simbolo.token == this.tabelasimbolos.CONST){
			boolean negativo = false;
			CasaToken(this.tabelasimbolos.CONST);
			//System.out.println(simbolo.lexema + " Lexema do id 1.");
			
			/*Acao semantica 1*/
			if(simbolo.classe != simbolo.Nenhuma_classe) {
				System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + simbolo.lexema+ "].");
				System.exit(0); 
			}else{
				simbolo.classe = simbolo.Constante_classe;
			}

			//acoesSemanticas.verificarID(simbolo,(byte)1);
			Simbolo idDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.identificador);
			
			//System.out.println(simbolo.lexema + " Lexema do id 2.");

			CasaToken(this.tabelasimbolos.IGUAL);
				
			if (this.simbolo.token == this.tabelasimbolos.MENOS){
				/*Acao semantica 2*/
				negativo = true;
				CasaToken(this.tabelasimbolos.MENOS);
			}

			/*Acao semantica 3*/
			if(this.simbolo.tipo == this.simbolo.Caracter_tipo && negativo == true) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0); 
			}else if(this.simbolo.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0); 
			}else{
				idDeclarado.tipo = this.simbolo.tipo;
			}
			//acoesSemanticas.alocandoTiposEmIds(idDeclarado, this.simbolo.tipo);

			CasaToken(this.tabelasimbolos.constante);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);
		}else{
			//Caso nenhum token seja os que o D espera
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica T
	* T -> (integer|char) id [E] {,id[E]}*;
	*/
	public void T(){
		//System.out.println("Estamos no T");
		//(char | id)
		byte tipoDoId = 0;
		if (this.simbolo.token == this.tabelasimbolos.INTEGER){
			/*Acao semantica 5*/
			tipoDoId = 1;
			CasaToken(this.tabelasimbolos.INTEGER);
			//System.out.println(" " + this.simbolo.token);

		}else if(this.simbolo.token == this.tabelasimbolos.CHAR){
			
			/*Acao semantica 6*/
			tipoDoId = 2;
			CasaToken(this.tabelasimbolos.CHAR);
		}
		//System.out.println(" " + this.simbolo.lexema);
		
		//acoesSemanticas.verificarID(simbolo,(byte)2);
		//acoesSemanticas.alocandoTiposEmIds(idDeclarado, tipoDoId);

		/*Acao semantica 7*/
		if(simbolo.tipo != simbolo.Nenhuma_classe) {
			System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + simbolo.lexema+ "].");
			System.exit(0); 
		}else{
			simbolo.classe = simbolo.Variavel_classe;
			simbolo.tipo = tipoDoId;
		}
		/*Acao semantica 8*/
		Simbolo idDeclarado = simbolo;

		CasaToken(this.tabelasimbolos.identificador);
		

		if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			E(idDeclarado);
		}
		while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
			CasaToken(this.tabelasimbolos.VIRGULA);

			/*Acao semantica 7.1*/
			if(simbolo.tipo != simbolo.Nenhuma_classe) {
				System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + simbolo.lexema+ "].");
				System.exit(0); 
			}else{
				simbolo.classe = simbolo.Variavel_classe;
				simbolo.tipo = tipoDoId;
			}
			//acoesSemanticas.verificarID(simbolo,(byte)2);
			/*Acao semantica 8.1*/
			idDeclarado = simbolo;
			//acoesSemanticas.alocandoTiposEmIds(idDeclarado, tipoDoId);

			CasaToken(this.tabelasimbolos.identificador);
			if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				E(idDeclarado);
			}
		}
		CasaToken(this.tabelasimbolos.PONTO_VIRGULA);
	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica E
	* E -> = [-] const | "[" const "]"
	*/
	public void E(Simbolo idDeclarado){
		//System.out.println("Estamos no E");
		boolean negativo = false;
		if (this.simbolo.token == this.tabelasimbolos.IGUAL){
			CasaToken(this.tabelasimbolos.IGUAL);
			if(this.simbolo.token == this.tabelasimbolos.MENOS){
				/*Acao semantica 9*/
				if(idDeclarado.tipo != idDeclarado.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else{
					negativo = true;
				}
				CasaToken(this.tabelasimbolos.MENOS);
				/*Acao semantica 10*/
				if(simbolo.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}
				CasaToken(this.tabelasimbolos.constante);
			}else{
				/*Acao semantica 10*/
				if(idDeclarado.tipo != simbolo.tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else if(simbolo.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}
				CasaToken(this.tabelasimbolos.constante);
			}
		}else if (this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);	
			/*Acao semantica 11*/
			if(simbolo.tipo != simbolo.Inteiro_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0); 
			}else if(idDeclarado.tipo == simbolo.Caracter_tipo && ( Integer.parseInt(simbolo.lexema) > 4096)) {
				System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o maximo permitido.");
				System.exit(0); 
			}else if(idDeclarado.tipo == simbolo.Inteiro_tipo && ( Integer.parseInt(simbolo.lexema) > 2048)) {
				System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o maximo permitido.");
				System.exit(0); 
			}else{
				idDeclarado.tamanho = Integer.parseInt(simbolo.lexema);
			}
			//acoesSemanticas.verificarVetorTamanho(this.simbolo, idDeclarado.tipo);
			//System.out.println("Teste " + this.simbolo.lexema + " tipoId: " + idDeclarado.tipo);
			CasaToken(this.tabelasimbolos.constante);
			CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
		}else{
			//Caso nenhum token seja os que o E espera
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica B
	* B -> id [ ‘[‘ EXP ‘]’ ] ‘=’ Exp ‘;’ | 
			For id ‘=’ EXP to EXP [ step constante ] do C |
			if EXP then C [ else C ] | ‘;’ | 
			readln ‘(‘ id ‘)’ ‘;’ | write ‘(‘ EXP { ‘,’ EXP }* ‘)’ ‘;’ | writeln ‘(‘ EXP { ‘,’ EXP }* ‘)’ ‘;’
	*/
	public void B(){
		//System.out.println("Estou no B");
		//System.out.println(this.simbolo.token);

		Simbolo EXP = new Simbolo();
		if(this.simbolo.token == this.tabelasimbolos.identificador){
			/*Acao semantica 12*/
			int possicaoVetor = 0;
			boolean flag = false;
			if(simbolo.classe == simbolo.Nenhuma_classe) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else if(simbolo.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else{
				flag = false;
			}
			//acoesSemanticas.verificarID(simbolo,(byte)0);
			Simbolo idDeclarado = simbolo;
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				EXP = Exp();
				/*Acao semantica 13*/
				if(idDeclarado.tamanho == 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else if(EXP.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					possicaoVetor = Integer.parseInt(EXP.lexema);
					flag = true;
				}
				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			}
			CasaToken(this.tabelasimbolos.IGUAL);
			EXP = Exp();
			/*Acao semantica 14*/
			if(flag == false) {
				if(EXP.tamanho != 0 && EXP.tipo == simbolo.Inteiro_tipo) {
					//ERRO
				}else if(EXP.tipo != idDeclarado.tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0) && (idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho != 0) ) {
						if(EXP.tamanho > idDeclarado.tamanho) {
							System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
							System.exit(0);
						}
				}
			}else{
				if(EXP.tipo != idDeclarado.tipo ) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho == 0) && EXP.tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(idDeclarado.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}
			}
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.FOR){
			CasaToken(this.tabelasimbolos.FOR);

			//acoesSemanticas.verificarID(simbolo,(byte)0);
			/*Acao semantica 16*/
			if(simbolo.classe == simbolo.Nenhuma_classe) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else if(simbolo.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else if(simbolo.tipo != simbolo.Inteiro_tipo || simbolo.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			CasaToken(this.tabelasimbolos.identificador);
			CasaToken(this.tabelasimbolos.IGUAL);
			EXP = Exp();
			/*Acao semantica 17*/
			if(EXP.tipo != simbolo.Inteiro_tipo || EXP.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			CasaToken(this.tabelasimbolos.TO);
			EXP = Exp();
			/*Acao semantica 17*/
			if(EXP.tipo != simbolo.Inteiro_tipo || EXP.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			if(this.simbolo.token == this.tabelasimbolos.STEP){
				CasaToken(this.tabelasimbolos.STEP);
				/*Acao semantica 19*/
				if(simbolo.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}
				CasaToken(this.tabelasimbolos.constante);
			}
			CasaToken(this.tabelasimbolos.DO);
			C();


		}else if(this.simbolo.token == this.tabelasimbolos.IF){
			CasaToken(this.tabelasimbolos.IF);
			EXP = Exp();
			/*Acao semantica 20*/
			if(EXP.tipo != simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			CasaToken(this.tabelasimbolos.THEN);
			//System.out.println("Voltou aqui");
			C();
			
			if (this.simbolo.token == this.tabelasimbolos.ELSE){
				CasaToken(this.tabelasimbolos.ELSE);
				C();
			}


		}else if(this.simbolo.token == this.tabelasimbolos.READLN){
			CasaToken(this.tabelasimbolos.READLN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			
			/*Acao semantica 21*/
			if(simbolo.classe == simbolo.Nenhum_tipo) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else if(simbolo.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else if(simbolo.tamanho != 0 && simbolo.tipo == simbolo.Inteiro_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			CasaToken(this.tabelasimbolos.identificador);
			//if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			//	CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
			//	Exp();
			//	CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			//}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITE){
			CasaToken(this.tabelasimbolos.WRITE);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			EXP = Exp();
			/*Acao semantica 22*/
			if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0) || EXP.tipo == simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				EXP = Exp();
				/*Acao semantica 22*/
				if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}
			}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITELN){
			CasaToken(this.tabelasimbolos.WRITELN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			EXP = Exp();
			/*Acao semantica 22*/
			if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				EXP = Exp();
				/*Acao semantica 22*/
				if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}	
			}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){

			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);
		}else{
			//Caso nenhum token seja os que o B espera
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica C
	* C -> B | ‘{‘ { B }* ‘}’
	*/
	public void C(){
		//System.out.println(("ESTAMOS NO C"));
		//System.out.println(this.simbolo.token);
		//Simbolo C = new Simbolo();
		if(this.simbolo.token == this.tabelasimbolos.CHAVES_ABERTO){
			CasaToken(this.tabelasimbolos.CHAVES_ABERTO);
			while(this.simbolo.token == this.tabelasimbolos.identificador || 
			  this.simbolo.token == this.tabelasimbolos.FOR || 
			  this.simbolo.token == this.tabelasimbolos.IF || 
			  this.simbolo.token == this.tabelasimbolos.READLN ||
			  this.simbolo.token == this.tabelasimbolos.WRITE || 
			  this.simbolo.token == this.tabelasimbolos.WRITELN ){
				//Simbolo B = B();
				B();
				//C_tipo = B_tipo;
				//C_tamanho = B_tamanho;
			}
			CasaToken(this.tabelasimbolos.CHAVES_FECHADO);
		}else{
			B();
			//C_tipo = B_tipo;
			//C_tamanho = B_tamanho;
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica Exp
	* EXP -> EXPS [ ( '=' | "<>" | '<' | '>' | "<=" | ">=" ) EXPS ]
	*/
	public Simbolo Exp(){
		System.out.println("Estamos no Exp");
		System.out.println(this.simbolo.lexema);
		Simbolo EXP = new Simbolo();
		Simbolo EXPS = ExpS();
		System.out.println("Volto no Exp");
		Simbolo EXPS1 = new Simbolo();

		/*Acao semantica 41*/
		EXP.tipo = EXPS.tipo;
		EXP.tamanho = EXPS.tamanho;

		String operador = "";
		boolean passou = false;

		//System.out.println("Verificando");
		//System.out.println(this.simbolo.token);
		if(this.simbolo.token == this.tabelasimbolos.IGUAL){
			System.out.println("IGUAL");
			CasaToken(this.tabelasimbolos.IGUAL);
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			/*Acao semantica 42*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if(EXP.tipo == simbolo.Logico_tipo){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				System.out.println("IGUAL");
				operador = "igual";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.DIFERENTE){
			CasaToken(this.tabelasimbolos.DIFERENTE);
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			/*Acao semantica 43*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "diferente";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR){
			//System.out.println("Entrou aqui");
			CasaToken(this.tabelasimbolos.MAIOR);
			//System.out.println("Comecou");
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			//System.out.println("EXPS1_tipo" + EXPS1.tipo + " ");
			//System.out.println("EXPS1_tamanho" + EXPS1.tamanho + " ");
			/*Acao semantica 45*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){

				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "maior";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MENOR){
			CasaToken(this.tabelasimbolos.MENOR);
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			/*Acao semantica 44*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "menor";
			}
			passou = true;
			EXPS1 = ExpS();

		}else if(this.simbolo.token == this.tabelasimbolos.MENOR_IGUAL){
			CasaToken(this.tabelasimbolos.MENOR_IGUAL);
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			/*Acao semantica 46*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "menorIgual";
			}
			passou = true;
			EXPS1 = ExpS();

		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR_IGUAL){
			CasaToken(this.tabelasimbolos.MAIOR_IGUAL);
			//Simbolo TEMP = ExpS();
			//EXPS1.tipo = TEMP.tipo;
			//EXPS1.tamanho = TEMP.tamanho;

			/*Acao semantica 47*/
			if((EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "maiorIgual";
			}
			passou = true;
			EXPS1 = ExpS();
		}
		//EXPS1 = ExpS();
		//System.out.println("CHEGOU ATE AQUI");
		System.out.println("Tipo final" +EXPS1.tipo);
		if (passou){
			System.out.println("Entrou?");
			if((EXPS1.tipo == simbolo.Inteiro_tipo) && (EXPS1.tamanho != 0)){
				//System.out.println("Tipo" + EXPS1.tipo);
				//System.out.println("tamanho" + EXPS1.tamanho);
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if((EXPS1.tipo == simbolo.Logico_tipo) || (EXP.tipo == simbolo.Logico_tipo)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if( operador == "igual"){
				System.out.println("IGUAL");
				if((EXP.tipo == simbolo.Caracter_tipo) && (EXPS1.tipo != simbolo.Caracter_tipo)){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXP.tipo = simbolo.Logico_tipo;
				}
			}else if( operador != "igual"){
				System.out.println(" AQI+UI" + EXPS1.tipo);
				if(EXPS1.tipo != simbolo.Inteiro_tipo){
					//System.out.println("Tipo final" +EXPS1.tipo);
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXP.tipo = simbolo.Logico_tipo;
				}
			}
		}
		
		System.out.println("Passo" + "Tipo" + EXP.tipo + ", " + EXP.tamanho);
		return EXP;
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica ExpS
	* EXPS -> [ '+' | '-' ] G { ( '+' | '-' | or ) G }*
	*/
	public Simbolo ExpS(){
		System.out.println("ESTAMOS NO EXPS");
		System.out.println("Simbolo" + this.simbolo.lexema);
		Simbolo G1 = new Simbolo(); 
		Simbolo EXPS = new Simbolo(); 

		boolean positivo = false;
		boolean negativo = false;
		String operacao = "";
		if(this.simbolo.token == this.tabelasimbolos.MAIS){
			CasaToken(this.tabelasimbolos.MAIS);

			/*Acao semantica 34*/
			positivo = true;
		}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
			CasaToken(this.tabelasimbolos.MENOS);
			/*Acao semantica 35*/
			negativo = true;
		}
		Simbolo G = G();
		System.out.println("Volto NO EXPS");
		//System.out.println("Tipo de G" + G.tipo);

		/*Acao semantica 36*/
		if((negativo || positivo) && G.tipo != simbolo.Inteiro_tipo){
			System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
			System.exit(0);
		}else if(G.tamanho != 0){
			System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
			System.exit(0);
		}else{
			//System.out.println("ENTROUUU");
			EXPS.tipo = G.tipo;
			EXPS.tamanho = G.tamanho;
		}

		while(this.simbolo.token == this.tabelasimbolos.MAIS ||
			  this.simbolo.token == this.tabelasimbolos.MENOS ||
			  this.simbolo.token == this.tabelasimbolos.OR){
			if(this.simbolo.token == this.tabelasimbolos.MAIS){
				CasaToken(this.tabelasimbolos.MAIS);

				/*Acao semantica 37*/
				if(EXPS.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					operacao = "somar";
				}

				G1 = G();

			}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);

				/*Acao semantica 38*/
				if(EXPS.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					operacao = "subtrair";
				}

				G1 = G();
			}else if(this.simbolo.token == this.tabelasimbolos.OR){
				CasaToken(this.tabelasimbolos.OR);

				/*Acao semantica 39*/
				if(EXPS.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					operacao = "or";
				}

				G1 = G();
			}
			/*Acao semantica 40*/
			if ((operacao == "somar") ||(operacao == "subtrair")){
				if(G1.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXPS.tipo = G1.tipo;
				}
			}else if(operacao == "or"){
				if(G1.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXPS.tipo = G1.tipo;
				}
			}
		}
		//System.out.println("Estamos no EXPS");
		//System.out.println("Tipo de EXPS" + EXPS.tipo);
		return EXPS;

	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica G
	* G -> F { ( '*' | '/' | '%' | and } F )
	*/
	public Simbolo G(){
		System.out.println("Estamos no G");
		System.out.println(this.simbolo.lexema);
		Simbolo F = F();
		System.out.println("Volto no G");
		Simbolo F1 = new Simbolo();
		Simbolo G = new Simbolo();
		String operacao = "";

		/*Acao semantica 28*/
		G.tipo = F.tipo;
		G.tamanho = F.tamanho;

		while(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO ||
			  this.simbolo.token == this.tabelasimbolos.DIVICAO ||
			  this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO ||
			  this.simbolo.token == this.tabelasimbolos.AND){

			if(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO){
				CasaToken(this.tabelasimbolos.MULTIPLICACAO);

				/*Acao semantica 29*/
				if (G.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "multiplicar";
				}
				F1 = F();
			}else if(this.simbolo.token == this.tabelasimbolos.DIVICAO){
				CasaToken(this.tabelasimbolos.DIVICAO);

				/*Acao semantica 30*/
				if (G.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "divisao";
				}
				F1 = F();
			}else if(this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO){
				CasaToken(this.tabelasimbolos.RESTO_DIVISAO);

				/*Acao semantica 31*/
				if (G.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "modulo";
				}
				F1 = F();
			}else if(this.simbolo.token == this.tabelasimbolos.AND){
				CasaToken(this.tabelasimbolos.AND);

				/*Acao semantica 32*/
				if (G.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "and";
				}
				F1 = F();
			}
			/*Acao semantica 32*/

			if (F1.tamanho != 0){
				System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
				System.exit(0);
			}else if((operacao == "multiplicar") || (operacao == "divisao")){
				if(F1.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					G.tipo = F1.tipo;
				}
			}else if(operacao == "and"){
				if(F1.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					G.tipo = F1.tipo;
				}
			}
		}
		//System.out.println("Tipo de G" + G.tipo);
		return G;
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica F
	* F -> not F | '(' EXP ')' | constante | id [ '[' EXP ']' ]
	*/
	public Simbolo F(){
		System.out.println("Estamos no F");
		System.out.println(this.simbolo.lexema);
		Simbolo F = new Simbolo();

		if(this.simbolo.token == this.tabelasimbolos.NOT){
			CasaToken(this.tabelasimbolos.NOT);
			Simbolo F1 = F();
			/*Acao semantica 23*/
			if(F1.tipo != simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				F.tipo = F1.tipo;
				F.tamanho = F1.tamanho;
			}
		}else if(this.simbolo.token == this.tabelasimbolos.PARENTESES_ABERTO){
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);

			Simbolo EXP = Exp();

			/*Acao semantica 24*/
			F.tipo = EXP.tipo;
			F.tamanho = EXP.tamanho;
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
		}else if(this.simbolo.token == this.tabelasimbolos.constante){

			/*Acao semantica 25*/
			F.tipo = this.simbolo.tipo;
			F.tamanho = this.simbolo.tamanho;
			CasaToken(this.tabelasimbolos.constante);
	
			
		}else if(this.simbolo.token == this.tabelasimbolos.identificador){
			
			//acoesSemanticas.verificarID(simbolo,(byte)0);
			/*Acao semantica 26*/
			if(this.simbolo.tipo == simbolo.Nenhum_tipo) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else{
				F.tipo = simbolo.tipo;
				F.tamanho = simbolo.tamanho;
			}

			Simbolo idDeclarado = simbolo;
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);

				/*Acao semantica 27*/
				Simbolo EXP = Exp();
				if(EXP.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else if (idDeclarado.tamanho == 0 ){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else{
					F.tamanho = 0;
				}

				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			}
		}else{
			//Caso nenhum token seja os que o F espera
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
		return F;
	}


}