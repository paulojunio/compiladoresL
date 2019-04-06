//Verificacoes a serem feitas dos digitos
public class controle{

    public static char sublinhado = '_';
    public static char pontoFinal = '.';
    public static char virgula = ',';
    public static char pontoEVirgula = ';';
    public static char eComercial = '&';
    public static char doisPontos = ':';
    public static char abreParenteses = '(';
    public static char fechaParenteses = ')';
    public static char abreColchetes = '[';
    public static char fechaColchetes = ']';
    public static char abreChaves = '{';
    public static char fechaChaves = '}';
    public static char mais = '+';
    public static char menos = '-';
    public static char aspas = '"';
    public static char apostofro = '\'';
    public static char barra = '/';
    public static char porcentagem = '%';
    public static char circunflexo = '^';
    public static char arroba = '@';
    public static char esclamacao = '!';
    public static char interrogacao = '?';
    public static char menor = '<';
    public static char maior = '>';
    public static char igual = '=';
    public static char asterisco = '*';

    public static char tab = 9;
    public static char barraN = 10;
    public static char novalinha = 11;
    public static char cursoInicio = 13;
    public static char fimDeArquivo = 23;
    public static char espaco = 32;
    
    
    public static boolean ELetra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'); //Letras
    }

    public static boolean EDigito(char c){
        return c >= '0' && c <= '9'; //Digitos
    }

    public static boolean EHexadecimal(char c) {
        return c >= 'A' && c <= 'F'; //Hexadecimais
    }
    
    public static boolean ECaracterEspecial(char c) { // Caracteres especiais
        return c == sublinhado || c == pontoFinal || c == virgula || c == pontoEVirgula || c == eComercial ||
        c == doisPontos || c == abreParenteses || c == fechaParenteses || c == abreColchetes || c == fechaColchetes ||
        c == abreChaves || c == fechaChaves || c == mais || c == menor || c == aspas || c == apostofro ||
        c == barra || c == porcentagem || c == circunflexo || c == arroba || c == esclamacao || c == interrogacao ||
        c == menor || c == maior || c == igual || c == asterisco;
    }

    public static boolean ECaracterEspecialEToken(char c) { // Caracteres especiais
        return c == sublinhado || c == pontoFinal || c == virgula || c == pontoEVirgula ||
        c == abreParenteses || c == fechaParenteses || c == abreColchetes || c == fechaColchetes ||
        c == abreChaves || c == fechaChaves || c == mais || c == menor || c == aspas || c == apostofro ||
        c == barra || c == porcentagem || c == menor || c == maior || c == igual || c == asterisco;
    }
    public static boolean quebraDeLinhas(char c) { // formatacao de texto
        return c == tab || c == barraN || c == novalinha || c == cursoInicio || c == espaco;
    }        

    public static boolean ECaracterValido(char c) {
        return ( ELetra(c) || EDigito(c) || ECaracterEspecial(c) || quebraDeLinhas(c));
    }

}