
import java.io.*;

public class MensagemdeErro{
    public static String LEXEMA_NAO_IDENTIFICADO = "Lexema nao identificado";
    public static String FIM_DE_ARQUIVO_INESPERADO = "Fim de arquivo inesperado";
    public static String TOKEN_INESPERADO = "Token nao esperado";


    public static void printErro ( String erro , int linha, String lexema){
    	if (lexema == null){
    		System.out.println(linha + " : " + erro);
    	}else{
    		System.out.println(linha + " : " + erro + " : [" + lexema + "]");
    	}
    	System.exit(0);

    }

}