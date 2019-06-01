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
	byte EXP_tipo;
	int EXP_valor;
	int EXP_tamanho;
	byte F_tipo;
	int F_tamanho;
  byte F1_tipo;
	int F1_tamanho;
	byte G_tipo;
	int G_tamanho;
  byte G1_tipo;
	int G1_tamanho;
	byte EXPS_tipo;
	int EXPS_tamanho;
  byte EXPS1_tipo;
	int EXPS1_tamanho;
  
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
				Exp();
				/*Acao semantica 13*/
				if(idDeclarado.tamanho == 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else if(EXP_tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					possicaoVetor = EXP_valor;
					flag = true;
				}
				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			}
			CasaToken(this.tabelasimbolos.IGUAL);
			Exp();
			/*Acao semantica 14*/
			if(flag == false) {
				if(EXP_tamanho != 0 && EXP_tipo == simbolo.Inteiro_tipo) {
					//ERRO
				}else if(EXP_tipo != idDeclarado.tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((EXP_tipo == simbolo.Caracter_tipo && EXP_tamanho != 0) && (idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho != 0) ) {
						if(EXP_tamanho > idDeclarado.tamanho) {
							System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
							System.exit(0);
						}
				}
			}else{
				if(EXP_tipo != idDeclarado.tipo ) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho == 0) && EXP_tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(idDeclarado.tipo == simbolo.Inteiro_tipo && EXP_tamanho != 0) {
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
			Exp();
			/*Acao semantica 17*/
			if(EXP_tipo != simbolo.Inteiro_tipo || EXP_tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			CasaToken(this.tabelasimbolos.TO);
			Exp();
			/*Acao semantica 17*/
			if(EXP_tipo != simbolo.Inteiro_tipo || EXP_tamanho != 0) {
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
			Exp();
			/*Acao semantica 20*/
			if(EXP_tipo != simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			CasaToken(this.tabelasimbolos.THEN);
			C();
			if (this.simbolo.token == this.tabelasimbolos.ELSE){
				CasaToken(this.tabelasimbolos.ELSE);
				C();
			}


		}else if(this.simbolo.token == this.tabelasimbolos.READLN){
			CasaToken(this.tabelasimbolos.READLN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			
			/*Acao semantica 21*/
			if(simbolo.classe != simbolo.Nenhum_tipo) {
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
			Exp();
			/*Acao semantica 22*/
			if(EXP_tipo == simbolo.Inteiro_tipo && EXP_tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				Exp();
				/*Acao semantica 22*/
				if(EXP_tipo == simbolo.Inteiro_tipo && EXP_tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}
			}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITELN){
			CasaToken(this.tabelasimbolos.WRITELN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			Exp();
			/*Acao semantica 22*/
			if(EXP_tipo == simbolo.Inteiro_tipo && EXP_tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				Exp();
				/*Acao semantica 22*/
				if(EXP_tipo == simbolo.Inteiro_tipo && EXP_tamanho != 0) {
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

		if(this.simbolo.token == this.tabelasimbolos.CHAVES_ABERTO){
			CasaToken(this.tabelasimbolos.CHAVES_ABERTO);
			while(this.simbolo.token == this.tabelasimbolos.identificador || 
			  this.simbolo.token == this.tabelasimbolos.FOR || 
			  this.simbolo.token == this.tabelasimbolos.IF || 
			  this.simbolo.token == this.tabelasimbolos.READLN ||
			  this.simbolo.token == this.tabelasimbolos.WRITE || 
			  this.simbolo.token == this.tabelasimbolos.WRITELN ){
				B();
			}
			CasaToken(this.tabelasimbolos.CHAVES_FECHADO);
		}else{
			B();
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica Exp
	* EXP -> EXPS [ ( '=' | "<>" | '<' | '>' | "<=" | ">=" ) EXPS ]
	*/
	public void Exp(){
		ExpS();
		if(this.simbolo.token == this.tabelasimbolos.IGUAL){
			CasaToken(this.tabelasimbolos.IGUAL);
			ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.DIFERENTE){
			CasaToken(this.tabelasimbolos.DIFERENTE);
			ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR){
			CasaToken(this.tabelasimbolos.MAIOR);
			ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MENOR){
			CasaToken(this.tabelasimbolos.MENOR);
			ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MENOR_IGUAL){
			CasaToken(this.tabelasimbolos.MENOR_IGUAL);
			ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR_IGUAL){
			CasaToken(this.tabelasimbolos.MAIOR_IGUAL);
			ExpS();
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica ExpS
	* EXPS -> [ '+' | '-' ] G { ( '+' | '-' | or ) G }*
	*/
	public void ExpS(){
		if(this.simbolo.token == this.tabelasimbolos.MAIS){
			CasaToken(this.tabelasimbolos.MAIS);
		}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
			CasaToken(this.tabelasimbolos.MENOS);
		}
		G();

		while(this.simbolo.token == this.tabelasimbolos.MAIS ||
			  this.simbolo.token == this.tabelasimbolos.MENOS ||
			  this.simbolo.token == this.tabelasimbolos.OR){
			if(this.simbolo.token == this.tabelasimbolos.MAIS){
				CasaToken(this.tabelasimbolos.MAIS);
				G();
			}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);
				G();
			}else if(this.simbolo.token == this.tabelasimbolos.OR){
				CasaToken(this.tabelasimbolos.OR);
				G();
			}
		}
	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica G
	* G -> F { ( '*' | '/' | '%' | and } F )
	*/
	public void G(){
		F();
		while(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO ||
			  this.simbolo.token == this.tabelasimbolos.DIVICAO ||
			  this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO ||
			  this.simbolo.token == this.tabelasimbolos.AND){

			if(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO){
				CasaToken(this.tabelasimbolos.MULTIPLICACAO);
				F();
			}else if(this.simbolo.token == this.tabelasimbolos.DIVICAO){
				CasaToken(this.tabelasimbolos.DIVICAO);
				F();
			}else if(this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO){
				CasaToken(this.tabelasimbolos.RESTO_DIVISAO);
				F();
			}else if(this.simbolo.token == this.tabelasimbolos.AND){
				CasaToken(this.tabelasimbolos.AND);
				F();
			}
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica F
	* F -> not F | '(' EXP ')' | constante | id [ '[' EXP ']' ]
	*/
	public void F(){
		if(this.simbolo.token == this.tabelasimbolos.NOT){
			CasaToken(this.tabelasimbolos.NOT);
			F();
			if(F1_tipo != simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				F_tipo = F1_tipo;
				F_tamanho = F1_tamanho;
			}
		}else if(this.simbolo.token == this.tabelasimbolos.PARENTESES_ABERTO){
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			Exp();
			F1_tipo = EXP_tipo;
			F_tipo = EXP_tipo;
			F1_tamanho = EXP_tamanho;
			F_tamanho = EXP_tamanho;
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
		}else if(this.simbolo.token == this.tabelasimbolos.constante){
			F1_tipo = simbolo.tipo;
			F_tipo = simbolo.tipo;
			F1_tamanho = simbolo.tamanho;
			F_tamanho = simbolo.tamanho;
			CasaToken(this.tabelasimbolos.constante);
		}else if(this.simbolo.token == this.tabelasimbolos.identificador){
			
			//acoesSemanticas.verificarID(simbolo,(byte)0);
			if(simbolo.tipo == simbolo.Nenhum_tipo) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + simbolo.lexema + "].");
				System.exit(0);
			}else{
				F1_tipo = simbolo.tipo;
				F_tipo = simbolo.tipo;
				F1_tamanho = simbolo.tamanho;
				F_tamanho = simbolo.tamanho;
			}
			Simbolo idDeclarado = simbolo;
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
				if(EXP_tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else if (idDeclarado.tamanho == 0 ){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else{
					F1_tamanho = 0;
					F_tamanho = 0;
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
	}


}