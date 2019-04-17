/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 17/04/2019
*/

//Verificacoes a serem feitas dos digitos
public class Controle{

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
    public static char fimDeArquivo = 65535;
    public static char espaco = 32;
    
    /**
     * Metodo para verificar se e' letra ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' uma letra
     */
    public static boolean ELetra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'); //Letras
    }

    /**
     * Metodo para verificar se e' digito ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um digito
     */
    public static boolean EDigito(char c){
        return c >= '0' && c <= '9'; //Digitos
    }

    /**
     * Metodo para verificar se e' hexadecimal ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um hexadecimal
     */
    public static boolean EHexadecimal(char c) {
        return c >= 'A' && c <= 'F'; //Hexadecimais
    }
    
    /**
     * Metodo para verificar se e' caracter especial ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um caracter especial
     */
    public static boolean ECaracterEspecial(char c) { // Caracteres especiais
        return c == sublinhado || c == pontoFinal || c == virgula || c == pontoEVirgula || c == eComercial ||
        c == doisPontos || c == abreParenteses || c == fechaParenteses || c == abreColchetes || c == fechaColchetes ||
        c == abreChaves || c == fechaChaves || c == mais || c == menor || c == aspas || c == apostofro ||
        c == barra || c == porcentagem || c == circunflexo || c == arroba || c == esclamacao || c == interrogacao ||
        c == menor || c == maior || c == igual || c == asterisco || c == menos;
    }

    /**
     * Metodo para verificar se e' caracter especial e token ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um caracter especial e token
     */
    public static boolean ECaracterEspecialEToken(char c) { // Caracteres especiais
        return c == sublinhado || c == pontoFinal || c == virgula || c == pontoEVirgula ||
        c == abreParenteses || c == fechaParenteses || c == abreColchetes || c == fechaColchetes ||
        c == abreChaves || c == fechaChaves || c == mais || c == menor || c == aspas || c == apostofro ||
        c == barra || c == porcentagem || c == menor || c == maior || c == igual || c == asterisco|| c == menos;
    }

    /**
     * Metodo para verificar se e' quebra de linhas ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um quebra de linha
     */
    public static boolean quebraDeLinhas(char c) { // formatacao de texto
        return c == tab || c == barraN || c == novalinha || c == cursoInicio || c == espaco;
    }        

    /**
     * Metodo para verificar se e' caracter valido ou nao
     * @param c caracter que sera avaliado
     * @return boolean caracter e' um caracter valido
     */
    public static boolean ECaracterValido(char c) {
        return ( ELetra(c) || EDigito(c) || ECaracterEspecial(c) || quebraDeLinhas(c));
    }

}
