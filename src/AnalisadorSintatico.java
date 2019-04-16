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
				MensagemdeErro.printErro( MensagemdeErro.FIM_DE_ARQUIVO_INESPERADO , analisadorlexico.linha, null);
			}else{
				MensagemdeErro.printErro( MensagemdeErro.TOKEN_INESPERADO, analisadorlexico.linha, String.valueOf(this.simbolo.lexema));
			}
		}
	}

	public void S(){
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
		}
	}

	public void D(){
		if (this.simbolo.token == this.tabelasimbolos.VAR){
			CasaToken(this.tabelasimbolos.VAR);
			while(this.simbolo.token == this.tabelasimbolos.INTEGER || this.simbolo.token == this.tabelasimbolos.CHAR){
				System.out.println("Entrou 2!!");
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
			E();
		}
		CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		
		//if (this.simbolo.token == this.)
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
		}
	}

	public void B(){
		if(this.simbolo.token == this.tabelasimbolos.identificador){
			CasaToken(this.tabelasimbolos.identificador);
			if(this.simbolo.tooken == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				Exp();
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

		}else if(this.simbolo.token == this.tabelasimbolos.WRITE){

		}else if(this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){

		}
	}


}