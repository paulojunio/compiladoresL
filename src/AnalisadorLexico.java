/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 17/04/2019
*/

import java.io.*;
//Classe que le um arquivo e pega digito por digito para formar o lexema
public class AnalisadorLexico{

    public BufferedReader codigo;
    public TabelaSimbolos tabelaSimbolos;

    public String lexema;
    public char ultimaLetra;
    public int linha;
    public String tipoConst;

    public boolean errorCompilacao;
    public boolean devolve;
    public boolean fimDeArquivo;


    
    /**
     * Metodo construtor analisador lexico
     * @param bufferedReader arquivo que sera lido
     * @param tabelaSimbolos tabela de simbolos
     */
    public AnalisadorLexico(BufferedReader bufferedReader, TabelaSimbolos tabelaSimbolos){
        this.codigo = bufferedReader;
        this.tabelaSimbolos = tabelaSimbolos;
        ultimaLetra = ' ';
        linha = 1;
        errorCompilacao = devolve = fimDeArquivo = false;
    }

    /* Teste
    public void read(){
        try{
            int intcode = this.codigo.read();
            while (intcode != -1){
                System.out.println( " " + (char)intcode);
                if (!(Controle.EDelimitador((char)intcode))){
                    char_formato((char)intcode);
                }
                intcode = this.codigo.read();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void char_formato(char c){
        if (Controle.ELetra(c)){
            System.out.println("É letra");
        }else if(Controle.EDigito(c)){

        }
    } */

    /**
     * Metodo da maquina de estados do analisador lexico
     * @return Simbolo retorna o simbolo criado
     */
    public Simbolo maquinaDeEstados() {
        int estadoInicial = 0;
        int estado = estadoInicial;
        int estadoFinal = 16;
        lexema = "";
        
        while(estado != estadoFinal) {
            if(estado == 0) {
                estado = estado0();
            }else if(estado == 1) {
                estado = estado1();
            }else if(estado == 2) {
                estado = estado2();
            }else if(estado == 3) {
                estado = estado3();
            }else if(estado == 4) {
                estado = estado4();
            }else if(estado == 5) {
                estado = estado5();
            }else if(estado == 6) {
                estado = estado6();
            }else if(estado == 7) {
                estado = estado7();
            }else if(estado == 8) {
                estado = estado8();
            }else if(estado == 9) {
                estado = estado9();
            }else if(estado == 10) {
                estado = estado10();
            }else if(estado == 11) {
                estado = estado11();
            }else if(estado == 12) {
                estado = estado12();
            }else if(estado == 13) {
                estado = estado13();
            }else if(estado == 14) {
                estado = estado14();
            }else if(estado == 15) {
                estado = estado15();
            }else if(estado == 16) {
                //final
            }
        }

        if(errorCompilacao) {
            System.exit(0); //Erro, parar programa.
        }
        

        if(fimDeArquivo == false) {
            if(tabelaSimbolos.buscaLexema(lexema) == null) {
                if(lexema.charAt(0) == '"' || lexema.charAt(0) == '\'' || Controle.EDigito(lexema.charAt(0))) {
                    //System.out.println(lexema + " Achou uma constante.");
                    Simbolo simboloConst = new Simbolo((byte)37,lexema,tipoConst);
                    System.out.println("Lexema(CONST) : " + lexema + " Tipo : " + tipoConst + " Tamanho = 0");
                    return simboloConst;
                }else {
                    //System.out.println(lexema + " Achou um indetificador.");
                    Simbolo simboloIdent = tabelaSimbolos.inserirIdentificador(lexema);
                    System.out.println("Lexema(ID) : " + lexema);
                    return simboloIdent;
                }
            }else{
                System.out.println("Lexema(PR) : " + lexema);
                return tabelaSimbolos.buscaLexema(lexema);
            }
        }else{
            System.out.println("Fim de arquivo : " + linha);
            System.exit(0);
            return null;
        }
    }
    /**
     * Metodo para analisar o estado 0 da maquina de estados
     * @return int o proximo estado
     */
    public int estado0(){
        lexema = "";
        char caracter = lerCaracter();
        if(Controle.ECaracterEspecialEToken(caracter) == true) {
            lexema += caracter;
            if(Controle.barra == caracter) {
                return 1;      
            }else if(Controle.apostofro == caracter) {
                tipoConst = "Character";
                return 5;
            }else if(Controle.aspas == caracter) {
                tipoConst = "String";
                return 10;
            }else if(Controle.menor == caracter) {
                return 12;
            }else if(Controle.maior == caracter) {
                return 13;
            }else if (Controle.sublinhado == caracter || Controle.pontoFinal == caracter) {
                return 14;
            }
            return 16;
        }else if(Controle.EDigito(caracter) == true) {
            lexema += caracter;
            if(caracter == '0') {
                tipoConst = "Character";
                return 7;
            }
            tipoConst = "Integer";
            return 4;
        }else if(Controle.ELetra(caracter) == true ) {
            lexema += caracter;
            return 15;
        }else if(Controle.quebraDeLinhas(caracter) == true) {
            if(Controle.barraN == caracter || Controle.novalinha == caracter) {
                linha++;
            }
            return 0;
        }else if(Controle.fimDeArquivo == caracter){
            fimDeArquivo = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 1 da maquina de estados
     * @return int o proximo estado
     */
    public int estado1() {
        char caracter = lerCaracter();
        if(Controle.asterisco == caracter) {
            return 2;
        }else if(Controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 13;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 2 da maquina de estados
     * @return int o proximo estado
     */
    public int estado2() {
        char caracter = lerCaracter();
        if(Controle.asterisco == caracter) {
            return 3;
        }else if(Controle.ECaracterValido(caracter) == true) {
            return 2;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 3 da maquina de estados
     * @return int o proximo estado
     */
    public int estado3() {
        char caracter = lerCaracter();
        if(Controle.asterisco == caracter){
            return 3;
        }else if(Controle.barra == caracter) {
            return 0;
        }else if(Controle.ECaracterValido(caracter) == true) {
            return 2;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 4 da maquina de estados
     * @return int o proximo estado
     */
    public int estado4() {
        char caracter = lerCaracter();
        if(Controle.EDigito(caracter) == true) {
            lexema += caracter;
            return 4;
        }else if(Controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 5 da maquina de estados
     * @return int o proximo estado
     */
    public int estado5() {
        char caracter = lerCaracter();
        if(Controle.EDigito(caracter) || Controle.ELetra(caracter) || Controle.ECaracterEspecial(caracter)) {
            lexema += caracter;
            return 6;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 6 da maquina de estados
     * @return int o proximo estado
     */
    public int estado6() {
        char caracter = lerCaracter();
        if(Controle.apostofro == caracter) {
            lexema += caracter;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 7 da maquina de estados
     * @return int o proximo estado
     */
    public int estado7() {
        char caracter = lerCaracter();
        if(caracter == 'x' || caracter == 'X') {
            lexema += caracter;
            return 8;
        }else if(Controle.EDigito(caracter) == true) {
            lexema += caracter;
            return 4;
        }else if(Controle.ECaracterValido(caracter)) {
            devolve = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 8 da maquina de estados
     * @return int o proximo estado
     */
    public int estado8() {
        char caracter = lerCaracter();
        if(Controle.EDigito(caracter) || Controle.EHexadecimal(caracter)) {
            lexema += caracter;
            return 9;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 9 da maquina de estados
     * @return int o proximo estado
     */
    public int estado9() {
        char caracter = lerCaracter();
        if(Controle.EDigito(caracter) || Controle.EHexadecimal(caracter)) {
            lexema += caracter;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 10 da maquina de estados
     * @return int o proximo estado
     */
    public int estado10() {
        char caracter = lerCaracter();
        if(Controle.EDigito(caracter) || Controle.ELetra(caracter) || Controle.ECaracterEspecial(caracter) || caracter == Controle.espaco) {
            if(Controle.aspas != caracter && Controle.barraN != caracter && Controle.novalinha != caracter) {
                lexema += caracter;
                return 11;
            }
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 11 da maquina de estados
     * @return int o proximo estado
     */
    public int estado11() {
        char caracter = lerCaracter();
        if(Controle.aspas == caracter) {
            lexema += caracter;
            return 16;
        }else if(Controle.EDigito(caracter) || Controle.ELetra(caracter) || Controle.ECaracterEspecial(caracter) || caracter == Controle.espaco) {
            if(Controle.aspas != caracter && Controle.barraN != caracter && Controle.novalinha != caracter) {
                lexema += caracter;
                return 11;
            }
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 12 da maquina de estados
     * @return int o proximo estado
     */
    public int estado12() {
        char caracter = lerCaracter();
        if(Controle.maior == caracter) {
            lexema += caracter;
            return 16;
        }else if(Controle.igual == caracter) {
            lexema += caracter;
            return 16;
        }else if(Controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 13 da maquina de estados
     * @return int o proximo estado
     */
    public int estado13() {
        char caracter = lerCaracter();
        if(Controle.igual == caracter) {
            lexema += caracter;
            return 16;
        }else if(Controle.ECaracterValido(caracter) == true) {
            devolve = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 14 da maquina de estados
     * @return int o proximo estado
     */
    public int estado14() {
        char caracter = lerCaracter();
        if (Controle.sublinhado == caracter || Controle.pontoFinal == caracter) {
            lexema += caracter;
            return 14;
        }else if(Controle.ELetra(caracter) == true || Controle.EDigito(caracter) == true) {
            lexema += caracter;
            return 15;
        }
        mostrarErro(caracter);
        return 16;
    }
    /**
     * Metodo para analisar o estado 15 da maquina de estados
     * @return int o proximo estado
     */
    public int estado15() {
        char caracter = lerCaracter();
        if(Controle.ELetra(caracter) == true || Controle.EDigito(caracter) == true || Controle.sublinhado == caracter || Controle.pontoFinal == caracter) {
            lexema += caracter;
            return 15;
        }else if(Controle.ECaracterValido(caracter) == true) {
            
            devolve = true;
            return 16;
        }
        mostrarErro(caracter);
        return 16;
    }

    /**
     * Metodo para ler uma caracter
     * @return retorna a caracter lida
     */
    public char lerCaracter() {
        try {
            if(devolve == true) {
                devolve = false; //Ultimo caracter
                
            }else{
                ultimaLetra = (char) codigo.read(); //Novo caracter 
            }
        }catch(Exception e){
            System.out.println("Error ao acessar arquivo");
            System.out.println(e);
        }
        return ultimaLetra;
    }

    /**
     * Metodo para mostrar erros durante a execucao do analisador lexico
     * @param char caracter e' o caracter que gerou o erro
     */
    public void mostrarErro(char caracter) {
        lexema += caracter;
        if(Controle.ECaracterValido(caracter) == true) {
            System.out.println(linha + ":" + "lexema nao identificado" + '[' + lexema + "].");
        }else{
            System.out.println(linha + ":" + "caractere invalido.");
        }

        if(Controle.fimDeArquivo == caracter) {
            System.out.println(linha + ":" + "fim de arquivo nao esperado.");
        }
        errorCompilacao = true;
    }
}
