//Teste da tabela de simbolos para printar
import java.io.*;
public class teste1{
    public static void main(String[]args){
        TabelaSimbolos tb = new TabelaSimbolos();
        System.out.println(tb.buscaLexema("For"));
        
        //System.out.println(s + " ");
        tb.printTabela();
    }
}