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
	* S -> { D }+ { B }+
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
		}else {
				System.out.println("Fim de arquivo");
		}
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica D
	* D -> Var { T }* | Const id ‘=’ [ ‘-’ ] constante ‘;’
	*/
	public void D(){
		if (this.simbolo.token == this.tabelasimbolos.VAR){
			CasaToken(this.tabelasimbolos.VAR);
			while(this.simbolo.token == this.tabelasimbolos.INTEGER || this.simbolo.token == this.tabelasimbolos.CHAR){
				T();
			}

		}else if(this.simbolo.token == this.tabelasimbolos.CONST){
			CasaToken(this.tabelasimbolos.CONST);

			CasaToken(this.tabelasimbolos.identificador);
			CasaToken(this.tabelasimbolos.IGUAL);
				
			if (this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);
			}
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
		if (this.simbolo.token == this.tabelasimbolos.INTEGER){
			CasaToken(this.tabelasimbolos.INTEGER);
			//System.out.println(" " + this.simbolo.token);

		}else if(this.simbolo.token == this.tabelasimbolos.CHAR){
			CasaToken(this.tabelasimbolos.CHAR);
		}
		//System.out.println(" " + this.simbolo.lexema);
		CasaToken(this.tabelasimbolos.identificador);

		if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			E();
		}
		while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
			CasaToken(this.tabelasimbolos.VIRGULA);
			CasaToken(this.tabelasimbolos.identificador);
			if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				E();
			}
		}
		CasaToken(this.tabelasimbolos.PONTO_VIRGULA);
	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica E
	* E -> = [-] const | "[" const "]"
	*/
	public void E(){

		if (this.simbolo.token == this.tabelasimbolos.IGUAL){
			CasaToken(this.tabelasimbolos.IGUAL);
			if(this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);
				CasaToken(this.tabelasimbolos.constante);
			}else{
				CasaToken(this.tabelasimbolos.constante);
			}
		}else if (this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);	
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
			For id ‘=’ EXP to EXP [ step constante ] C |
			if EXP then C [ else C ] | ‘;’ | 
			readln ‘(‘ id ‘)’ ‘;’ | write[ ln ] ‘(‘ EXP { ‘,’ EXP }* ‘)’ ‘;’ 
	*/
	public void B(){

		if(this.simbolo.token == this.tabelasimbolos.identificador){
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			}
			CasaToken(this.tabelasimbolos.IGUAL);
			Exp();
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.FOR){
			CasaToken(this.tabelasimbolos.FOR);
			CasaToken(this.tabelasimbolos.identificador);
			CasaToken(this.tabelasimbolos.IGUAL);
			Exp();
			CasaToken(this.tabelasimbolos.TO);
			Exp();
			if(this.simbolo.token == this.tabelasimbolos.STEP){
				CasaToken(this.tabelasimbolos.STEP);
				CasaToken(this.tabelasimbolos.constante);
			}
			CasaToken(this.tabelasimbolos.DO);
			C();


		}else if(this.simbolo.token == this.tabelasimbolos.IF){
			CasaToken(this.tabelasimbolos.IF);
			Exp();
			CasaToken(this.tabelasimbolos.THEN);
			C();
			if (this.simbolo.token == this.tabelasimbolos.ELSE){
				CasaToken(this.tabelasimbolos.ELSE);
				C();
			}


		}else if(this.simbolo.token == this.tabelasimbolos.READLN){
			CasaToken(this.tabelasimbolos.READLN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			CasaToken(this.tabelasimbolos.identificador);
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITE){
			CasaToken(this.tabelasimbolos.WRITE);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			Exp();
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				Exp();
			}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITELN){
			CasaToken(this.tabelasimbolos.WRITELN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			Exp();
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				Exp();
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
		}else if(this.simbolo.token == this.tabelasimbolos.PARENTESES_ABERTO){
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			Exp();
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
		}else if(this.simbolo.token == this.tabelasimbolos.constante){
			CasaToken(this.tabelasimbolos.constante);
		}else if(this.simbolo.token == this.tabelasimbolos.identificador){
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
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