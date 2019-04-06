//Verificacoes a serem feitas dos digitos
public class controle{

    public static char pontoFinal = '.';
    public static char virgula = ',';
    public static char asterisco = '*';
    public static char igual = '=';
    public static char pontoEVirgula = ';';
    public static char mais = '+';
    public static char menos = '-';
    public static char underline = '_';
    public static char porcentagem = '%';
    public static char barra = '/';
    

    public static char menor = '<';
    public static char maior = '>';

    public static char abreColchetes = '[';
    public static char fechaColchetes = ']';
    public static char abreParenteses = '(';
    public static char fechaParenteses = ')';
    public static char abreChaves = '{';
    public static char fechaChaves = '}';
    
     
    public static boolean ELetra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean EDigito(char c){
        return c >= '0' && c <= '9';
    }

    public static boolean EHexadecimal(char c) {
        return c >= 'A' && c <= 'F';
    }
    public static boolean EDelimitador(char c){
        return c == ' ' || c == '\n';
    }

    public static boolean EComentario(char c){
        return c == '/';
    }
        
}