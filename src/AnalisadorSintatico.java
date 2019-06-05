/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 03/06/2019
*/

import java.io.BufferedReader;
import java.io.FileReader;

//Classe que executa o analisador sintatico
public class AnalisadorSintatico{
	AnalisadorLexico analisadorlexico;
	TabelaSimbolos tabelasimbolos;
	Simbolo simbolo;
	GeracaoDeCodigo geracaoDeCodigo;
	BufferedReader file;
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
	public AnalisadorSintatico(BufferedReader file, String arquivoAsm){
		this.file = file;
		this.tabelasimbolos = new TabelaSimbolos();
		this.analisadorlexico = new AnalisadorLexico(this.file, this.tabelasimbolos);
		this.geracaoDeCodigo = new GeracaoDeCodigo(arquivoAsm);
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
		geracaoDeCodigo.inicioPrograma();
		if (this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
			while(this.simbolo.token == this.tabelasimbolos.VAR || this.simbolo.token == this.tabelasimbolos.CONST){
				D();
			}
		}
		geracaoDeCodigo.inicioCodigo();
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
		//tabelasimbolos.printTabelaCerta();
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
		geracaoDeCodigo.fimDoAsm();
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

			Simbolo idDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.identificador);
			
			/*Acao semantica 1*/
			if(idDeclarado.classe != simbolo.Nenhuma_classe) {
				System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + idDeclarado.lexema + "].");
				//Identificador ja declarado.
				System.exit(0); 
			}else{
				idDeclarado.classe = simbolo.Constante_classe;
			}
			//System.out.println(simbolo.lexema + " Lexema do id 2.");

			CasaToken(this.tabelasimbolos.IGUAL);
				
			if (this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);
				/*Acao semantica 2*/
				negativo = true;
			}

			Simbolo constanteDeclarada = simbolo;

			CasaToken(this.tabelasimbolos.constante);
			
			/*Acao semantica 3*/
			if(constanteDeclarada.tipo == this.simbolo.Caracter_tipo && negativo == true) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				//Erro de menor com caracter.
				System.exit(0); 
			}else if(constanteDeclarada.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":classe de identificador incompativel [" + constanteDeclarada.lexema + "]."); 
				//Colocar vetor/string dentro de constante.
				System.exit(0); 
			}else{
				idDeclarado.tipo = constanteDeclarada.tipo;
				if(idDeclarado.tipo == this.simbolo.Caracter_tipo) {
					geracaoDeCodigo.adicionarCaracter(idDeclarado, constanteDeclarada.lexema);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}else{
					geracaoDeCodigo.adicionarInteiro(idDeclarado, constanteDeclarada.lexema);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}
			}

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
			
			CasaToken(this.tabelasimbolos.INTEGER);
			/*Acao semantica 5*/
			tipoDoId = 1;
			//Tipo do id e inteiro
			//System.out.println(" " + this.simbolo.token);

		}else if(this.simbolo.token == this.tabelasimbolos.CHAR){
			
			CasaToken(this.tabelasimbolos.CHAR);
			/*Acao semantica 6*/
			tipoDoId = 2;
			//Tipo do id e caracter
		}else{
			if(analisadorlexico.fimDeArquivo){
				System.out.println(analisadorlexico.linha + " : fim de arquivo nao esperado");
				System.exit(0);
			}else{
				System.out.println(analisadorlexico.linha + " : token nao esperado [ " + this.simbolo.lexema + " ]");
				System.exit(0);
			}
		}
		//System.out.println(" " + this.simbolo.lexema);
		
		Simbolo idDeclarado = simbolo;

		CasaToken(this.tabelasimbolos.identificador);
		
		/*Acao semantica 7*/
		if(idDeclarado.tipo != simbolo.Nenhuma_classe) {
			System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + idDeclarado.lexema+ "].");
			System.exit(0); 
		}else{
			idDeclarado.classe = simbolo.Variavel_classe;
			idDeclarado.tipo = tipoDoId;
		}

		if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			/*Acao semantica 8*/
			E(idDeclarado);
		}else{
			if(idDeclarado.tipo == this.simbolo.Caracter_tipo) {
				geracaoDeCodigo.adicionarCaracter(idDeclarado,null);
				//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
			}else{
				geracaoDeCodigo.adicionarInteiro(idDeclarado,null);
				//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
			}
		}
		while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
			CasaToken(this.tabelasimbolos.VIRGULA);

			idDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.identificador);

			/*Acao semantica 7.1*/
			if(idDeclarado.tipo != simbolo.Nenhuma_classe) {
				System.out.println(this.analisadorlexico.linha + ":identificador ja declarado [" + idDeclarado.lexema+ "].");
				System.exit(0); 
			}else{
				idDeclarado.classe = simbolo.Variavel_classe;
				idDeclarado.tipo = tipoDoId;
			}

			if (this.simbolo.token == this.tabelasimbolos.IGUAL || this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				/*Acao semantica 8.1*/
				E(idDeclarado);
			}else{
				if(idDeclarado.tipo == this.simbolo.Caracter_tipo) {
					geracaoDeCodigo.adicionarCaracter(idDeclarado,null);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}else{
					geracaoDeCodigo.adicionarInteiro(idDeclarado,null);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}
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
				
				CasaToken(this.tabelasimbolos.MENOS);

				/*Acao semantica 9*/
				if(idDeclarado.tipo != idDeclarado.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else{
					negativo = true;
				}

				Simbolo constanteDeclarada = simbolo;
			
				CasaToken(this.tabelasimbolos.constante);

				/*Acao semantica 10*/
				if(constanteDeclarada.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}

				geracaoDeCodigo.adicionarInteiro(idDeclarado,"-"+constanteDeclarada.lexema);
			}else{

				Simbolo constanteDeclarada = simbolo;

				CasaToken(this.tabelasimbolos.constante);
				/*Acao semantica 10*/
				if(idDeclarado.tipo != constanteDeclarada.tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else if(constanteDeclarada.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}
				if(idDeclarado.tipo == this.simbolo.Caracter_tipo) {
					geracaoDeCodigo.adicionarCaracter(idDeclarado,constanteDeclarada.lexema);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}else{
					geracaoDeCodigo.adicionarInteiro(idDeclarado,constanteDeclarada.lexema);
					//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
				}
			}
		}else if (this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
			CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);	
			
			Simbolo constanteDeclarada = simbolo;

			CasaToken(this.tabelasimbolos.constante);

			/*Acao semantica 11*/
			if(constanteDeclarada.tipo != simbolo.Inteiro_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0); 
			}else if(idDeclarado.tipo == simbolo.Caracter_tipo && ( Integer.parseInt(constanteDeclarada.lexema) > 4096)) {
				System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o maximo permitido.");
				System.exit(0); 
			}else if(idDeclarado.tipo == simbolo.Inteiro_tipo && ( Integer.parseInt(constanteDeclarada.lexema) > 2048)) {
				System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o maximo permitido.");
				System.exit(0); 
			}else{
				idDeclarado.tamanho = Integer.parseInt(constanteDeclarada.lexema);
				geracaoDeCodigo.adicionarVetor(idDeclarado);
				//System.out.println("Simbolo: " + idDeclarado.lexema + " endereco: " + idDeclarado.endereco);
			}

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
			
			Simbolo idDeclarado = simbolo;
			CasaToken(this.tabelasimbolos.identificador);

			if(idDeclarado.classe == simbolo.Nenhuma_classe) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else if(idDeclarado.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else{
				flag = false;
			}


			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				EXP = Exp();
				geracaoDeCodigo.contadorVariaveis = 0;
				//System.out.println("Volto Exp1" + " Tipo: " + EXP.tipo + " Tamnho:" + EXP.tamanho + " lexema: " + EXP.lexema); PRINT DEBUG
				//System.out.println("IdDeclarado" + idDeclarado.lexema); PRINT DEBUG
				/*Acao semantica 13*/
				if(idDeclarado.tamanho == 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0); 
				}else if(EXP.tipo != simbolo.Inteiro_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					possicaoVetor = EXP.endereco;
					flag = true;
				}
				CasaToken(this.tabelasimbolos.COLCHETE_FECHADO);
			}
			CasaToken(this.tabelasimbolos.IGUAL);
			EXP = Exp();
			geracaoDeCodigo.contadorVariaveis = 0;
			//System.out.println("Volto Exp2" + " Tipo: " + EXP.tipo + " Tamnho:" + EXP.tamanho + " lexema: " + EXP.lexema); PRINT DEBUG
			
			/*Acao semantica 14*/
			//System.out.println("Tamanho id" + idDeclarado.tipo); PRINT DEBUG
			if(flag == false) {
				if(idDeclarado.tamanho != 0 && idDeclarado.tipo == simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(EXP.tamanho != 0 && EXP.tipo == simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(EXP.tipo != idDeclarado.tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0) || (idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho != 0) ) {
					if(EXP.tamanho > idDeclarado.tamanho) {
						System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
						System.exit(0);
					}
				}

				if (idDeclarado.tipo == simbolo.Inteiro_tipo){ 
					geracaoDeCodigo.escreverComandos("mov ax, DS:[" + EXP.endereco + "]");
					geracaoDeCodigo.escreverComandos("mov DS:[" + idDeclarado.endereco + "], ax");
				}else if (idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho == 0){ 
					geracaoDeCodigo.escreverComandos("mov ax, DS:[" + EXP.endereco + "]");
					geracaoDeCodigo.escreverComandos("mov DS:[" + idDeclarado.endereco + "], ax");
				}else if (idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho != 0){
					int rotulo1 = geracaoDeCodigo.novoRotulo();
					geracaoDeCodigo.escreverComandos("mov di, " + idDeclarado.endereco);
					geracaoDeCodigo.escreverComandos("mov si, " + EXP.endereco);
					geracaoDeCodigo.escreverComandos("R" + rotulo1 + ":");
					geracaoDeCodigo.escreverComandos("mov ax, DS:[si]");
					geracaoDeCodigo.escreverComandos("mov ah, 0");
					geracaoDeCodigo.escreverComandos("mov DS:[di], al");
					geracaoDeCodigo.escreverComandos("add di, 1");
					geracaoDeCodigo.escreverComandos("add si, 1");
					geracaoDeCodigo.escreverComandos("cmp ax, 024h");
					geracaoDeCodigo.escreverComandos("jne R" + rotulo1);
				}

			}else{
				if(EXP.tipo != idDeclarado.tipo ) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if((idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho != 0) && EXP.tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(idDeclarado.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}
				

				/*if (possicaoVetor > idDeclarado.tamanho){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o maximo permitido.");
					System.exit(0); 
				}*/
				
				geracaoDeCodigo.escreverComandos("mov di, " + "DS:["+possicaoVetor + "] ; foi feito aqui");
				if (idDeclarado.tipo == simbolo.Inteiro_tipo){ 
					geracaoDeCodigo.escreverComandos("add di, di");
					geracaoDeCodigo.escreverComandos("add di, " + idDeclarado.endereco);
					geracaoDeCodigo.escreverComandos("mov ax," +  "DS:["+EXP.endereco+"]");
					
					geracaoDeCodigo.escreverComandos("mov DS:[di], ax; foi aqui tb");
				}else if (idDeclarado.tipo == simbolo.Caracter_tipo){ 
					geracaoDeCodigo.escreverComandos("add di, " + idDeclarado.endereco);
					geracaoDeCodigo.escreverComandos("mov ax," +  "DS:["+EXP.endereco+"]");
					geracaoDeCodigo.escreverComandos("mov DS:[di], ax");
				}
				//geracaoDeCodigo.escreverComandos();
            	//geracaoDeCodigo.escreverComandos("cmp ax , bx");
            	//geracaoDeCodigo.escreverComandos("jg R"+ String.valueOf(RotuloFim));

			}
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.FOR){
			CasaToken(this.tabelasimbolos.FOR);
			
			Simbolo idDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.identificador);

			/*Acao semantica 16*/
			if(idDeclarado.classe == simbolo.Nenhuma_classe) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else if(idDeclarado.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else if(idDeclarado.tipo != simbolo.Inteiro_tipo || idDeclarado.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			CasaToken(this.tabelasimbolos.IGUAL);
			EXP = Exp();
			geracaoDeCodigo.contadorTemporarios = 0;
			/*Acao semantica 17*/
			if(EXP.tipo != simbolo.Inteiro_tipo || EXP.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}


			geracaoDeCodigo.escreverComandos(";Inicio for");
			geracaoDeCodigo.escreverComandos("mov ax, DS:[" + EXP.endereco + "]");
      geracaoDeCodigo.escreverComandos("mov DS:[" + idDeclarado.endereco + "] , ax"  );
      geracaoDeCodigo.escreverComandos(";Acabou atribuicao");

			int RotuloInicio = geracaoDeCodigo.novoRotulo();
			int RotuloFim = geracaoDeCodigo.novoRotulo();

			geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloInicio) +":" );


			CasaToken(this.tabelasimbolos.TO);
			EXP = Exp();
			geracaoDeCodigo.contadorTemporarios = 0;
			/*Acao semantica 17*/
			if(EXP.tipo != simbolo.Inteiro_tipo || EXP.tamanho != 0) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			geracaoDeCodigo.escreverComandos(";Inicio comparacao");
			geracaoDeCodigo.escreverComandos("mov ax," + "DS:[" + idDeclarado.endereco + "]");
			geracaoDeCodigo.escreverComandos("mov bx," + "DS:[" + EXP.endereco + "]");
			//geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloInicio) +":" );
      geracaoDeCodigo.escreverComandos("cmp ax, bx");
      geracaoDeCodigo.escreverComandos("jg R"+ String.valueOf(RotuloFim));

			if(this.simbolo.token == this.tabelasimbolos.STEP){
				CasaToken(this.tabelasimbolos.STEP);

				Simbolo constanteDeclarada = simbolo;

				CasaToken(this.tabelasimbolos.constante);

				/*Acao semantica 19*/
				if(constanteDeclarada.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}

				geracaoDeCodigo.escreverComandos("mov cx, " + constanteDeclarada.lexema);

			}else{
				geracaoDeCodigo.escreverComandos("mov cx, 1");
			}

			/*geracaoDeCodigo.escreverComandos("R " + RotuloInicio + ":");*/
			
            
			CasaToken(this.tabelasimbolos.DO);
			C();

			geracaoDeCodigo.escreverComandos("add DS:[" + idDeclarado.endereco + "]"+ ", cx");
			geracaoDeCodigo.escreverComandos("jmp R" + String.valueOf(RotuloInicio));
      geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloFim) + ":");

		}else if(this.simbolo.token == this.tabelasimbolos.IF){
			CasaToken(this.tabelasimbolos.IF);
			int RotuloElse = geracaoDeCodigo.novoRotulo();
			int RotuloFim = geracaoDeCodigo.novoRotulo();
			EXP = Exp();
			geracaoDeCodigo.contadorTemporarios = 0;
			/*Acao semantica 20*/
			if(EXP.tipo != simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			geracaoDeCodigo.escreverComandos("mov ax, DS:["+ EXP.endereco +"]" + "; comeco if");
      geracaoDeCodigo.escreverComandos("cmp ax, 1");
      geracaoDeCodigo.escreverComandos("jne R" + String.valueOf(RotuloElse) + "; jne if");

			CasaToken(this.tabelasimbolos.THEN);
			C();
			
			if (this.simbolo.token == this.tabelasimbolos.ELSE){
				CasaToken(this.tabelasimbolos.ELSE);
				
				geracaoDeCodigo.escreverComandos("jmp R" + String.valueOf(RotuloFim));
        geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloElse) + ":");

				C();

			}else{
				geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloElse) + ":");
			}

			geracaoDeCodigo.escreverComandos("R" + String.valueOf(RotuloFim) + ":");

		}else if(this.simbolo.token == this.tabelasimbolos.READLN){
			CasaToken(this.tabelasimbolos.READLN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			
			Simbolo idDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.identificador);

			/*Acao semantica 21*/
			if(idDeclarado.classe == simbolo.Nenhum_tipo) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else if(idDeclarado.classe != simbolo.Variavel_classe){
				System.out.println(analisadorlexico.linha +":classe de identificador incompativel " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else if(idDeclarado.tamanho != 0 && idDeclarado.tipo == simbolo.Inteiro_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}

			if(idDeclarado.tipo == simbolo.Inteiro_tipo) {
				geracaoDeCodigo.lerInteiro(idDeclarado.endereco);
			}else if(idDeclarado.tipo == simbolo.Caracter_tipo && idDeclarado.tamanho == 0){
				geracaoDeCodigo.lerString(idDeclarado.endereco,1);
			}else{
				geracaoDeCodigo.lerString(idDeclarado.endereco,idDeclarado.tamanho);
			}
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
			//System.out.println("Passo aqui");
			geracaoDeCodigo.contadorTemporarios = 0;
			//geracaoDeCodigo.imprimirString(EXP);
			/*Acao semantica 22*/
			if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0) || EXP.tipo == simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			if(EXP.tipo == simbolo.Inteiro_tipo) {
				geracaoDeCodigo.imprimirInteiro(EXP.endereco);
			}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho == 0) {
				geracaoDeCodigo.imprimirChar(EXP.endereco);
			}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0 && EXP.lexema.charAt(0) != '\"') {
				geracaoDeCodigo.imprimirVetorChar(EXP.endereco, EXP.tamanho);
				//System.out.println("Vetor");
			}else{
				geracaoDeCodigo.imprimirString(EXP.endereco);
				//System.out.println("String");
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				EXP = Exp();
				geracaoDeCodigo.contadorTemporarios = 0;
				/*Acao semantica 22*/
				if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}
				if(EXP.tipo == simbolo.Inteiro_tipo) {
					geracaoDeCodigo.imprimirInteiro(EXP.endereco);
				}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho == 0) {
					geracaoDeCodigo.imprimirChar(EXP.endereco);
				}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0 && EXP.lexema.charAt(0) != '\"') {
					geracaoDeCodigo.imprimirVetorChar(EXP.endereco, EXP.tamanho);
					//System.out.println("Vetor");
				}else{
					geracaoDeCodigo.imprimirString(EXP.endereco);
					//System.out.println("String");
				}
			}
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.WRITELN){
			CasaToken(this.tabelasimbolos.WRITELN);
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);
			EXP = Exp();
			geracaoDeCodigo.contadorTemporarios = 0;
			/*Acao semantica 22*/
			if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}
			if(EXP.tipo == simbolo.Inteiro_tipo) {
				geracaoDeCodigo.imprimirInteiro(EXP.endereco);
			}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho == 0) {
				geracaoDeCodigo.imprimirChar(EXP.endereco);
			}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0 && EXP.lexema.charAt(0) != '\"') {
				geracaoDeCodigo.imprimirVetorChar(EXP.endereco, EXP.tamanho);
				//System.out.println("Vetor");
			}else{
				geracaoDeCodigo.imprimirString(EXP.endereco);
				//System.out.println("String");
			}
			while(this.simbolo.token == this.tabelasimbolos.VIRGULA){
				CasaToken(this.tabelasimbolos.VIRGULA);
				EXP = Exp();
				geracaoDeCodigo.contadorTemporarios = 0;
				/*Acao semantica 22*/
				if((EXP.tipo == simbolo.Inteiro_tipo && EXP.tamanho != 0 ) || EXP.tipo == simbolo.Logico_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}	
				if(EXP.tipo == simbolo.Inteiro_tipo) {
					geracaoDeCodigo.imprimirInteiro(EXP.endereco);
				}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho == 0) {
					geracaoDeCodigo.imprimirChar(EXP.endereco);
				}else if(EXP.tipo == simbolo.Caracter_tipo && EXP.tamanho != 0 && EXP.lexema.charAt(0) != '\"') {
					geracaoDeCodigo.imprimirVetorChar(EXP.endereco, EXP.tamanho);
					//System.out.println("Vetor");
				}else{
					geracaoDeCodigo.imprimirString(EXP.endereco);
					//System.out.println("String");
				}
			}
			geracaoDeCodigo.barraN();
			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
			CasaToken(this.tabelasimbolos.PONTO_VIRGULA);

		}else if(this.simbolo.token == this.tabelasimbolos.PONTO_VIRGULA){

			CasaToken(this.tabelasimbolos.PONTO_VIRGULA); //Nenhum geracao de codigo

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
	public Simbolo Exp(){
		//System.out.println("Estamos no Exp"); PRINT DEBUG
		//System.out.println(this.simbolo.lexema); PRINT DEBUG
		Simbolo EXP = new Simbolo();
		Simbolo EXPS = ExpS();

		Simbolo EXPS1 = new Simbolo();

		/*Acao semantica 41*/
		EXP.tipo = EXPS.tipo;
		EXP.tamanho = EXPS.tamanho;
		EXP.lexema = EXPS.lexema;
		/*Geracao de codigo*/
		EXP.endereco = EXPS.endereco;
		String operador = "";
		boolean passou = false;


		if(this.simbolo.token == this.tabelasimbolos.IGUAL){
			CasaToken(this.tabelasimbolos.IGUAL);

			/*Acao semantica 42*/
			if((EXP.tipo == simbolo.Inteiro_tipo) && (EXP.tamanho != 0)){ //Concertado erro (EXP.tipo != simbolo.Inteiro_tipo) || (EXP.tamanho != 0)
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis"); 
				System.exit(0);
			}else if(EXP.tipo == simbolo.Logico_tipo){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "igual";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.DIFERENTE){
			CasaToken(this.tabelasimbolos.DIFERENTE);
			
			/*Acao semantica 43*/
			if((EXP.tipo == simbolo.Logico_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "diferente";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR){
			CasaToken(this.tabelasimbolos.MAIOR);
			/*Acao semantica 45*/
			if((EXP.tipo == simbolo.Logico_tipo) || (EXP.tamanho != 0)){

				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "maior";
			}
			passou = true;
			EXPS1 = ExpS();
		}else if(this.simbolo.token == this.tabelasimbolos.MENOR){
			CasaToken(this.tabelasimbolos.MENOR);

			/*Acao semantica 44*/
			if((EXP.tipo == simbolo.Logico_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "menor";
			}
			passou = true;
			EXPS1 = ExpS();

		}else if(this.simbolo.token == this.tabelasimbolos.MENOR_IGUAL){
			CasaToken(this.tabelasimbolos.MENOR_IGUAL);

			/*Acao semantica 46*/
			if((EXP.tipo == simbolo.Logico_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "menorIgual";
			}
			passou = true;
			EXPS1 = ExpS();

		}else if(this.simbolo.token == this.tabelasimbolos.MAIOR_IGUAL){
			CasaToken(this.tabelasimbolos.MAIOR_IGUAL);

			/*Acao semantica 47*/
			if((EXP.tipo == simbolo.Logico_tipo) || (EXP.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				operador = "maiorIgual";
			}
			passou = true;
			EXPS1 = ExpS();
		}
		//EXPS1 = ExpS();

		if (passou){
			if((EXPS1.tipo == simbolo.Inteiro_tipo) && (EXPS1.tamanho != 0)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if((EXPS1.tipo == simbolo.Logico_tipo) || (EXP.tipo == simbolo.Logico_tipo)){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if( operador.equals("igual")){

				if((EXP.tipo != EXPS1.tipo)){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(EXP.tamanho == 0 && EXPS1.tamanho != 0 || EXP.tamanho != 0 && EXPS1.tamanho == 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					if(EXP.tamanho == 0) {
						int rotulo1 = geracaoDeCodigo.novoRotulo();
						int rotulo2 = geracaoDeCodigo.novoRotulo();

						if(EXP.tipo == simbolo.Inteiro_tipo) {
							geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXP.endereco + "]");
							geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + EXPS1.endereco + "]");
							geracaoDeCodigo.escreverComandos("cmp ax, bx");
							geracaoDeCodigo.escreverComandos("je R" + rotulo1);
						}else if(EXP.tamanho == 0 && EXPS.tamanho == 0){
							geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXP.endereco + "]");
							geracaoDeCodigo.escreverComandos("mov ah, 0");
							geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + EXPS1.endereco + "]");
							geracaoDeCodigo.escreverComandos("mov bh, 0");
							geracaoDeCodigo.escreverComandos("cmp ax, bx");
							geracaoDeCodigo.escreverComandos("je R" + rotulo1);
						}
						
						geracaoDeCodigo.escreverComandos("mov ax, 0");
						geracaoDeCodigo.escreverComandos("jmp R" + rotulo2);
						geracaoDeCodigo.escreverComandos("R" + rotulo1 + ":");
						geracaoDeCodigo.escreverComandos("mov ax, 1");
						geracaoDeCodigo.escreverComandos("R" + rotulo2 + ":");
						int temporario = geracaoDeCodigo.novoTemp(1);
						geracaoDeCodigo.escreverComandos("mov DS:[" + temporario + "], ax");
						EXP.endereco = temporario;
						EXP.tipo = simbolo.Logico_tipo;
					}else{ //Verificar vetor char / string
						int rotulo1 = geracaoDeCodigo.novoRotulo();
						int rotulo2 = geracaoDeCodigo.novoRotulo();
						int rotulo3 = geracaoDeCodigo.novoRotulo();
						geracaoDeCodigo.escreverComandos("mov di," + EXP.endereco); // String 1
						geracaoDeCodigo.escreverComandos("mov si," + EXPS1.endereco); // String 2
						geracaoDeCodigo.escreverComandos("mov ax, 1"); // Resposta true
						geracaoDeCodigo.escreverComandos("R" + rotulo1 + ":");
						geracaoDeCodigo.escreverComandos("mov bx, DS:[di]");
						geracaoDeCodigo.escreverComandos("mov bh, 0");
						geracaoDeCodigo.escreverComandos("mov cx, DS:[si]");
						geracaoDeCodigo.escreverComandos("mov ch, 0");
						geracaoDeCodigo.escreverComandos("cmp bx, cx");
						geracaoDeCodigo.escreverComandos("jne R" + rotulo2);
						geracaoDeCodigo.escreverComandos("cmp bx, 024h");
						geracaoDeCodigo.escreverComandos("je R" + rotulo3);
						geracaoDeCodigo.escreverComandos("cmp cx, 024h");
						geracaoDeCodigo.escreverComandos("je R" + rotulo3);
						geracaoDeCodigo.escreverComandos("add di, 1");
						geracaoDeCodigo.escreverComandos("add si, 1");
						geracaoDeCodigo.escreverComandos("jmp R" + rotulo1);
						geracaoDeCodigo.escreverComandos("R"+ rotulo2 + ":");
						geracaoDeCodigo.escreverComandos("mov ax, 0");
						geracaoDeCodigo.escreverComandos("R"+ rotulo3 + ":");
						int temporario = geracaoDeCodigo.novoTemp(1);
						geracaoDeCodigo.escreverComandos("mov DS:[" + temporario + "], ax");
						EXP.endereco = temporario;
						EXP.tipo = simbolo.Logico_tipo;
					}
				}
			}else if( !operador.equals("igual") ){

				if(EXPS1.tipo == simbolo.Logico_tipo || EXPS1.tamanho != 0){

					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					int rotulo1 = geracaoDeCodigo.novoRotulo();
					int rotulo2 = geracaoDeCodigo.novoRotulo();
					if(EXP.tipo == simbolo.Inteiro_tipo) {
						geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXP.endereco + "]");
						geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + EXPS1.endereco + "]");
						geracaoDeCodigo.escreverComandos("cmp ax, bx");
					}else if(EXP.tamanho == 0 && EXPS.tamanho == 0){
						geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXP.endereco + "]");
						geracaoDeCodigo.escreverComandos("mov ah, 0");
						geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + EXPS1.endereco + "]");
						geracaoDeCodigo.escreverComandos("mov bh, 0");
						geracaoDeCodigo.escreverComandos("cmp ax, bx");
					}
					if(operador.equals("diferente")) {
						geracaoDeCodigo.escreverComandos("jne R" + rotulo1);
					}else if(operador.equals("maior")) {
						geracaoDeCodigo.escreverComandos("jg R" + rotulo1);
					}else if(operador.equals("menor")) {
						geracaoDeCodigo.escreverComandos("jl R" + rotulo1);
					}else if(operador.equals("menorIgual")) {
						geracaoDeCodigo.escreverComandos("jle R" + rotulo1);
					}else if(operador.equals("maiorIgual")) {
						geracaoDeCodigo.escreverComandos("jge R" + rotulo1);
					}
					geracaoDeCodigo.escreverComandos("mov ax, 0");
					geracaoDeCodigo.escreverComandos("jmp R" + rotulo2);
					geracaoDeCodigo.escreverComandos("R" + rotulo1 + ":");
					geracaoDeCodigo.escreverComandos("mov ax, 1");
					geracaoDeCodigo.escreverComandos("R" + rotulo2 + ":");
					int temporario = geracaoDeCodigo.novoTemp(1);
					geracaoDeCodigo.escreverComandos("mov DS:[" + temporario + "], ax");
					EXP.endereco = temporario;
					EXP.tipo = simbolo.Logico_tipo;
				}
			}
		}
		
		return EXP;
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica ExpS
	* EXPS -> [ '+' | '-' ] G { ( '+' | '-' | or ) G }*
	*/
	public Simbolo ExpS(){
		Simbolo G1 = new Simbolo(); 
		Simbolo EXPS = new Simbolo(); 
		boolean entrou = false;
		boolean positivo = false;
		boolean negativo = false;
		String operacao = "";

		if(this.simbolo.token == this.tabelasimbolos.MAIS){
			CasaToken(this.tabelasimbolos.MAIS);

			/*Acao semantica 34*/
			positivo = true;
			entrou = true;
		}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
			CasaToken(this.tabelasimbolos.MENOS);

			/*Acao semantica 35*/
			negativo = true;
			entrou = true;
		}
		Simbolo G = G();
		
		/*Acao semantica 36*/
		if((negativo || positivo)){
			//System.out.println("entrou no if"); PRINT DEBUG
			if(G.tipo != simbolo.Inteiro_tipo){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else if(G.tamanho != 0){
				System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				System.exit(0);
			}else{
				if(negativo) {
					geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + G.endereco + "]");
					G.endereco = geracaoDeCodigo.novoTemp(2);
					geracaoDeCodigo.escreverComandos("neg ax");
					geracaoDeCodigo.escreverComandos("mov DS:[" + G.endereco + "]" + " , ax");
				}
				EXPS.tipo = G.tipo;
				EXPS.tamanho = G.tamanho;
				EXPS.lexema = G.lexema;
				/*Geracao de codigo*/
				EXPS.endereco = G.endereco;
			}
	  }else{
			EXPS.tipo = G.tipo;
			EXPS.tamanho = G.tamanho;
			EXPS.lexema = G.lexema;
			/*Geracao de codigo*/
			EXPS.endereco = G.endereco;
			
		}
	  
		while(this.simbolo.token == this.tabelasimbolos.MAIS ||
			  this.simbolo.token == this.tabelasimbolos.MENOS ||
			  this.simbolo.token == this.tabelasimbolos.OR){
			if(this.simbolo.token == this.tabelasimbolos.MAIS){
				CasaToken(this.tabelasimbolos.MAIS);

				/*Acao semantica 37*/
				if(EXPS.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(EXPS.tipo == simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					operacao = "somar";
				}

				G1 = G();

			}else if(this.simbolo.token == this.tabelasimbolos.MENOS){
				CasaToken(this.tabelasimbolos.MENOS);

				/*Acao semantica 38*/
				if(EXPS.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(EXPS.tipo == simbolo.Logico_tipo){
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
			if (operacao.equals("somar") || operacao.equals("subtrair")){
				if(G1.tipo != EXPS.tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G1.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXPS.tipo = G1.tipo;
					/*Geracao de codigo*/
					if(EXPS.tipo == this.simbolo.Caracter_tipo) {
						if(EXPS.lexema.charAt(0) == '\'') {
							geracaoDeCodigo.escreverComandos("mov ax , " + (int)EXPS.lexema.charAt(1));
						}else{
							geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXPS.endereco + "]");
						}
						if(G1.lexema.charAt(0) == '\'') {
							geracaoDeCodigo.escreverComandos("mov bx , " + (int)G1.lexema.charAt(1));
						}else{
							geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + G1.endereco + "]");
						}
						
					}else{
						geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXPS.endereco + "]");
					  geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + G1.endereco + "]");
					}
					if(operacao.equals("somar")) {
						geracaoDeCodigo.escreverComandos("add ax , bx");
					}else if(operacao.equals("subtrair")) {
						//geracaoDeCodigo.escreverComandos("neg bx");
						geracaoDeCodigo.escreverComandos("sub ax , bx");
					}
					if(EXPS.tipo == simbolo.Inteiro_tipo) {
						EXPS.endereco = geracaoDeCodigo.novoTemp(2);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + EXPS.endereco + "]" + ", ax");
					}else{
						EXPS.endereco = geracaoDeCodigo.novoTemp(1);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + EXPS.endereco + "]" + ", ax");
					}
				}
			}else if(operacao == "or"){
				if(G1.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					EXPS.tipo = G1.tipo;
					//Como fazer o or em 0x8086
					int rotulo1 = geracaoDeCodigo.novoRotulo();
					int rotulo2 = geracaoDeCodigo.novoRotulo();
					int rotulo3 = geracaoDeCodigo.novoRotulo();
					geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + EXPS.endereco + "]");
					geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + G1.endereco + "]");
					geracaoDeCodigo.escreverComandos("cmp ax, 1");
					geracaoDeCodigo.escreverComandos("je R" + rotulo1);
					geracaoDeCodigo.escreverComandos("cmp bx, 1");
					geracaoDeCodigo.escreverComandos("je R" + rotulo1);
					geracaoDeCodigo.escreverComandos("jmp R" + rotulo2);
					geracaoDeCodigo.escreverComandos("R:" + rotulo1);
					geracaoDeCodigo.escreverComandos("mov ax, 1"); //Resultado positivo
					geracaoDeCodigo.escreverComandos("jmp R" + rotulo3);
					geracaoDeCodigo.escreverComandos("R:" + rotulo2);
					geracaoDeCodigo.escreverComandos("mov ax, 0"); //Resultado negativo
					geracaoDeCodigo.escreverComandos("R:" + rotulo3);
					EXPS.endereco = geracaoDeCodigo.novoTemp(1); 
					geracaoDeCodigo.escreverComandos("mov " + "DS:[" + EXPS.endereco + "]" + ", ax");
				}
			}
		}
		return EXPS;

	}

	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica G
	* G -> F { ( '*' | '/' | '%' | and } F )
	*/
	public Simbolo G(){
		Simbolo F = F();
		Simbolo F1 = new Simbolo();
		Simbolo G = new Simbolo();
		String operacao = "";
		
		/*Acao semantica 28*/
		G.tipo = F.tipo;
		G.tamanho = F.tamanho;
		G.lexema = F.lexema;
		/*Geracao de codigo*/
		G.endereco = F.endereco;

		while(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO ||
			  this.simbolo.token == this.tabelasimbolos.DIVICAO ||
			  this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO ||
			  this.simbolo.token == this.tabelasimbolos.AND){

			if(this.simbolo.token == this.tabelasimbolos.MULTIPLICACAO){
				CasaToken(this.tabelasimbolos.MULTIPLICACAO);

				/*Acao semantica 29*/
				if (G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tipo == simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "multiplicar";
				}
				F1 = F();
			}else if(this.simbolo.token == this.tabelasimbolos.DIVICAO){
				CasaToken(this.tabelasimbolos.DIVICAO);

				/*Acao semantica 30*/
				if (G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tipo == simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					operacao = "divisao";
				}
				F1 = F();
			}else if(this.simbolo.token == this.tabelasimbolos.RESTO_DIVISAO){
				CasaToken(this.tabelasimbolos.RESTO_DIVISAO);

				/*Acao semantica 31*/
				if (G.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if(G.tipo == simbolo.Logico_tipo){
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
				}else{
					operacao = "and";
				}
				F1 = F();
			}
			/*Acao semantica 32*/

			
			if(operacao.equals("multiplicar") || operacao.equals("divisao") || operacao.equals("modulo")){
				if(F1.tipo != G.tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else if (F1.tamanho != 0){
					System.out.println(this.analisadorlexico.linha + ":tamanho do vetor excede o máximo permitido.");
					System.exit(0);
				}else{
					G.tipo = F1.tipo;
					if(G.tipo == this.simbolo.Caracter_tipo) {
						if(G.lexema.charAt(0) == '\'') {
							geracaoDeCodigo.escreverComandos("mov ax , " + (int)G.lexema.charAt(1));
						}else{
							geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + G.endereco + "]");
						}
						if(F1.lexema.charAt(0) == '\'') {
							geracaoDeCodigo.escreverComandos("mov bx , " + (int)F1.lexema.charAt(1));
						}else{
							geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + F1.endereco + "]");
						}
						
					}else{
						geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + G.endereco + "]");
						geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + F1.endereco + "]");
					}
					if(operacao.equals("multiplicar")) {
						geracaoDeCodigo.escreverComandos("imul bx");
					}else if(operacao.equals("divisao")){
						geracaoDeCodigo.escreverComandos("mov dx , 0");
						geracaoDeCodigo.escreverComandos("idiv bx");
					}else if(operacao.equals("modulo")) {
						geracaoDeCodigo.escreverComandos("mov dx , 0");
						geracaoDeCodigo.escreverComandos("idiv bx");
						geracaoDeCodigo.escreverComandos("mov ax , dx");
					}
					if(G.tipo == this.simbolo.Caracter_tipo) {
						G.endereco = geracaoDeCodigo.novoTemp(2);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + G.endereco + "]" + ", ax");
					}else{
						G.endereco = geracaoDeCodigo.novoTemp(1);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + G.endereco + "]" + ", ax");
					}
				}
			}else if(operacao.equals("and")){
				if(F1.tipo != simbolo.Logico_tipo){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
					System.exit(0);
				}else{
					G.tipo = F1.tipo;
					//Verificar como faz and em 0x8086?
					geracaoDeCodigo.escreverComandos("mov ax , " + "DS:[" + G.endereco + "]");
					geracaoDeCodigo.escreverComandos("mov bx , " + "DS:[" + F1.endereco + "]");
					geracaoDeCodigo.escreverComandos("imul bx");
					G.endereco = geracaoDeCodigo.novoTemp(1); //Tipo logico 1 byte?
					geracaoDeCodigo.escreverComandos("mov " + "DS:[" + G.endereco + "]" + ", ax");
				}
			}
		}
		return G;
	}


	/**
	* Metodo correspondente ao simboolo nao-terminal da gramatica F
	* F -> not F | '(' EXP ')' | constante | id [ '[' EXP ']' ]
	*/
	public Simbolo F(){
		//System.out.println("Estamos no F"); DEBUG PRINT
		//System.out.println(this.simbolo.lexema); DEBUG PRINT
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
				F.lexema = F1.lexema;
				/*Geracao de codigo*/
				F.endereco = geracaoDeCodigo.novoTemp(1); //Tipo logico 1 byte ?
				geracaoDeCodigo.escreverComandos("mov ax, " + F1.endereco);
				geracaoDeCodigo.escreverComandos("neg ax");
				geracaoDeCodigo.escreverComandos("add ax, 1");
				geracaoDeCodigo.escreverComandos("mov DS:[" + F.endereco + "] " + ", ax");
			}
		}else if(this.simbolo.token == this.tabelasimbolos.PARENTESES_ABERTO){
			CasaToken(this.tabelasimbolos.PARENTESES_ABERTO);

			Simbolo EXP = Exp();
			geracaoDeCodigo.contadorVariaveis = 0;
			/*Acao semantica 24*/
			F.tipo = EXP.tipo;
			F.tamanho = EXP.tamanho;
			F.lexema = EXP.lexema;
			/*Geracao codigo*/
			F.endereco = EXP.endereco;

			CasaToken(this.tabelasimbolos.PARENTESES_FECHADO);
		}else if(this.simbolo.token == this.tabelasimbolos.constante){

			Simbolo constanteDeclarado = simbolo;

			CasaToken(this.tabelasimbolos.constante);	
			/*Acao semantica 25*/
			F.tipo = constanteDeclarado.tipo;
			F.tamanho = constanteDeclarado.tamanho;
			F.lexema = constanteDeclarado.lexema;
			/*Geracao de codigo*/
			if(F.tipo == simbolo.Caracter_tipo && F.tamanho != 0) {
				F.endereco = geracaoDeCodigo.contadorVariaveis; 
				String valor = F.lexema.substring(1,F.tamanho);
				int tamanho = F.tamanho - 2;
				valor += '$';
				geracaoDeCodigo.contadorVariaveis += F.tamanho;
				geracaoDeCodigo.declararString(valor);
			}else{
				if(F.tipo == simbolo.Caracter_tipo) {
					F.endereco = geracaoDeCodigo.novoTemp(1);
					//System.out.println(F.endereco + " aa ");
					geracaoDeCodigo.escreverComandos("mov ax , " + F.lexema); 
					geracaoDeCodigo.escreverComandos("mov DS:[" + F.endereco + "] " + ", ax");
					//geracaoDeCodigo.contadorVariaveis += 1;
				}else if(F.tipo == simbolo.Inteiro_tipo){
					F.endereco = geracaoDeCodigo.novoTemp(2);
					geracaoDeCodigo.escreverComandos("mov ax , " + F.lexema); 
					geracaoDeCodigo.escreverComandos("mov DS:[" + F.endereco + "] " + ", ax");
					//geracaoDeCodigo.contadorVariaveis += 2;
				}
			}

		}else if(this.simbolo.token == this.tabelasimbolos.identificador){
			
			//acoesSemanticas.verificarID(simbolo,(byte)0);
			//boolean passou2 = false;

			Simbolo idDeclarado = simbolo;
			CasaToken(this.tabelasimbolos.identificador);

			/*Acao semantica 26*/
			if(idDeclarado.tipo == simbolo.Nenhum_tipo) {
				System.out.println(analisadorlexico.linha +":identificador nao declarado " + '[' + idDeclarado.lexema + "].");
				System.exit(0);
			}else{
				F.tipo = idDeclarado.tipo;
				F.tamanho = idDeclarado.tamanho;
				F.lexema = idDeclarado.lexema;
				/*Geracao de codigo*/
				F.endereco = idDeclarado.endereco;
			}

			if(this.simbolo.token == this.tabelasimbolos.COLCHETE_ABERTO){
				CasaToken(this.tabelasimbolos.COLCHETE_ABERTO);
				//passou2 = true;
				/*Acao semantica 27*/
				Simbolo EXP = Exp();
				geracaoDeCodigo.contadorVariaveis = 0;
				if(EXP.tipo != simbolo.Inteiro_tipo) {
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else if (idDeclarado.tamanho == 0 ){
					System.out.println(this.analisadorlexico.linha + ":tipos incompativeis");
				  System.exit(0);
				}else{
					F.tamanho = 0;
					/*Geracao de codigo*/
					if(idDeclarado.tipo == this.simbolo.Inteiro_tipo) {
						geracaoDeCodigo.escreverComandos("mov di , " + "DS:[" + EXP.endereco + "]");
						geracaoDeCodigo.escreverComandos("add di , di");
						geracaoDeCodigo.escreverComandos("add di , " + idDeclarado.endereco);
						geracaoDeCodigo.escreverComandos("mov ax , DS:[di]");
						F.endereco = geracaoDeCodigo.novoTemp(2); 
						//System.out.println("F.endereco " + F.endereco + " Exp.enreco" + EXP.endereco + " idDE: " + idDeclarado.endereco);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + F.endereco + "]" + " , ax");
					}else if(idDeclarado.tipo == this.simbolo.Caracter_tipo){
						geracaoDeCodigo.escreverComandos("mov di , " + "DS:[" + EXP.endereco + "]");
						geracaoDeCodigo.escreverComandos("add di , " + idDeclarado.endereco);
						geracaoDeCodigo.escreverComandos("mov ax , DS:[di]");
						F.endereco = geracaoDeCodigo.novoTemp(1);
						//System.out.println(" Aqui F.endereco " + F.endereco + " Exp.enreco" + EXP.endereco + " idDE: " + idDeclarado.endereco);
						geracaoDeCodigo.escreverComandos("mov " + "DS:[" + F.endereco + "]" + " , ax");
					}
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