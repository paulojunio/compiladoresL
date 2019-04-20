/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 20/04/2019
*/

import java.io.*;
//teste do analisador lexico
public class LC{
    public static void main(String[]args){
        try{
            System.setProperty("file.encoding", "UTF-8");

            String fileName = args[0];
            TabelaSimbolos tabelaSimbolos = new TabelaSimbolos();
            BufferedReader code = new BufferedReader(new FileReader(fileName));
            AnalisadorSintatico b = new AnalisadorSintatico(code);
            b.S();

            //Teste do lexico 
            //AnalisadorLexico a = new AnalisadorLexico(code,tabelaSimbolos);
            //while(a.maquinaDeEstados() != null) {
            //    Simbolo simbolo = a.maquinaDeEstados();
            //    System.out.println(" " + simbolo.token);
            //}
            //System.out.println("Fim de arquivo");
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}