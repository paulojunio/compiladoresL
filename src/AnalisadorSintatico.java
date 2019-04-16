import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileReader;


public class AnalisadorSintatico{
	AnalisadorLexico analisadorlexico;
	TabelaSimbolos tabelasimbolos;
	Simbolo simbolo;
	BufferedReader file;


	public AnalisadorSintatico(BufferedReader file){
		this.file = file;
		this.tabelasimbolos = new TabelaSimbolos();
		this.analisadorlexico = new AnalisadorLexico(this.file, this.tabelasimbolos);
		this.simbolo = analisadorlexico.maquinaDeEstados();
	}

	public void CasaToken(byte tokenesperado){
		if(this.simbolo.token == tokenesperado){
			this.simbolo = analisadorlexico.maquinaDeEstados();
		}else{
			if(analisadorlexico.fimDeArquivo){
<<<<<<< HEAD
				System.out.println(analisadorlexico.linha + " : fim de arquivo não esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
				System.exit(0);
=======
				MensagemdeErro.printErro( MensagemdeErro.FIM_DE_ARQUIVO_INESPERADO , analisadorlexico.linha, null);
			}else{
				MensagemdeErro.printErro( MensagemdeErro.TOKEN_INESPERADO, analisadorlexico.linha, String.valueOf(this.simbolo.lexema));
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
			}
		}
	}

	public void S(){
<<<<<<< HEAD
		//System.out.println("Comecou denovo");
		if (this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
			while(this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
				System.out.println("Entrou");
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
				System.out.println("OII");
				B();
			}
		}
		if ((this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST) || 
			!(this.simbolo.token == this.tabelasimbolos.identificador || 
			this.simbolo.token == this.tabelasimbolos.FOR || 
		    this.simbolo.token == this.tabelasimbolos.IF || 
	 	    this.simbolo.token == this.tabelasimbolos.READLN ||
		    this.simbolo.token == this.tabelasimbolos.WRITE || 
			this.simbolo.token == this.tabelasimbolos.WRITELN ||
			this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA)){
			
			System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
			System.exit(0);
=======
		while(this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
			System.out.println("Entrou");
			D();
		}
  
		while(this.simbolo.token == this.tabelasimbolos.identificador || 
			  this.simbolo.token == this.tabelasimbolos.FOR || 
			  this.simbolo.token == this.tabelasimbolos.IF || 
			  this.simbolo.token == this.tabelasimbolos.READLN ||
			  this.simbolo.token == this.tabelasimbolos.WRITE){
			System.out.println("OII");
			//B();
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
		}
	}

	public void D(){
		if (this.simbolo.token == this.tabelasimbolos.VAR){
			CasaToken(this.tabelasimbolos.VAR);
			while(this.simbolo.token == this.tabelasimbolos.INTEGER || this.simbolo.token == this.tabelasimbolos.CHAR){
<<<<<<< HEAD
=======
				System.out.println("Entrou 2!!");
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
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
<<<<<<< HEAD
		}else{
			System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
			System.exit(0);
=======
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
		}
	}

	/*
	T -> (integer|char) id [E] {,id[E]}*;
	*/
	public void T(){
		//(char | id)
		if (this.simbolo.token == this.tabelasimbolos.INTEGER){
			CasaToken(this.tabelasimbolos.INTEGER);
			System.out.println(" " + this.simbolo.token);

		}else if(this.simbolo.token == this.tabelasimbolos.CHAR){
			CasaToken(this.tabelasimbolos.CHAR);
		}
		System.out.println(" " + this.simbolo.lexema);
		CasaToken(this.tabelasimbolos.identificador);

		if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			E();
		}
		while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
			CasaToken(this.tabelasimbolos.VIRGULA);
			CasaToken(this.tabelasimbolos.identificador);
<<<<<<< HEAD
			if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				E();
			}
		}
		CasaToken(this.tabelasimbolos.PONTO_VIRGULA);
		//System.out.println("AQUI");
=======
			E();
		}
		CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		
		//if (this.simbolo.token == this.)
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
	}

	/*
	E -> = [-] const | "[" const "]"
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
<<<<<<< HEAD
		}else{
			System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
			System.exit(0);
=======
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
		}
	}

	public void B(){
<<<<<<< HEAD
		//System.out.println("AHAHAHA");
		if(this.simbolo.token == this.tabelasimbolos.identificador){
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
=======
		if(this.simbolo.token == this.tabelasimbolos.identificador){
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.tooken == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
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
<<<<<<< HEAD
			CasaToken(this.tabelasimbolos.DO);
=======
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
			C();


		}else if(this.simbolo.token == this.tabelasimbolos.IF){
<<<<<<< HEAD

			CasaToken(this.tabelasimbolos.IF);
			Exp();
			//System.out.println("ESTOU e sOU 1");
			CasaToken(this.tabelasimbolos.THEN);
			C();
			//System.out.println("ESTOU e sOU");
=======
			CasaToken(this.tabelasimbolos.IF);
			Exp();
			CasaToken(this.tabelasimbolos.THEN);
			C();
>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de
			if (this.simbolo.token == this.tabelasimbolos.ELSE){
				CasaToken(this.tabelasimbolos.ELSE);
				C();
			}


		}else if(this.simbolo.token == this.tabelasimbolos.READLN){
<<<<<<< HEAD
			CasaToken(this.tabelasimbolos.READLN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			CasaToken(this.tabelasimbolos.identificador);
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITE){
			//System.out.println("Entrou aquiiiii!!! NAOOO");
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
			System.out.println("Entrou aquiiiii!!!");
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
			System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
			System.exit(0);
		}
	}


	public void C(){
		//System.out.println("Entrei no C");
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
			System.out.println(analisadorlexico.linha + " : token não esperado [ " + this.simbolo.lexema + " ] ");
			System.exit(0);
		}
	}



=======

		}else if(this.simbolo.token == this.tabelasimbolos.WRITE){

		}else if(this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){

		}
	}

>>>>>>> 9c1a3efa2fd420661dcb926511350429a6be15de

}