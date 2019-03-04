//Verificacoes a serem feitas dos digitos
public class controle{

    public static boolean ELetra(char c){
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }

    public static boolean EDigito(char c){
        return c >= '0' && c <= '9';
    }

    public static boolean EDelimitador(char c){
        return c == ' ' || c == '\n';
    }

    public static boolean EComentario(char c){
        return c == '/';
    }
        
}