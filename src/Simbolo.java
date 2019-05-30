/**
* Trabalho de compiladores - criacao da linguagem L
* Professor: Alexei Machado
* 
* @author Giovanna Avila Riqueti
* @author Paulo Junio Reis Rodrigues
* @version 1
* Data: 17/04/2019
*/

//Classe referente ao simbolo
public class Simbolo{

    public byte token;
    public String lexema;
    public int tamanho;
    public byte tipo;
    public byte classe;
    private int endereco;

    public static final byte Nenhuma_classe = 0; // Nenhuma classe = 0
    public static final byte Constante_classe = 1;// classe constante = 1
    public static final byte Variavel_classe = 2;// classe variavel = 2
    
    public static final byte Nenhum_tipo = 0; // Nenhuma tipo = 0
    public static final byte Inteiro_tipo = 1;// Tipo inteiro = 1
    public static final byte Caracter_tipo = 2;// Tipo caracter = 2
    public static final byte Array_tipo = 3;// Tipo array = 3
    public static final byte Logico_tipo = 4;// Tipo logico = 4
    public static final byte String_tipo = 5;// Tipo String = 5

    public Simbolo(){
        
    }
    public Simbolo(byte token, String lexema){
        this.token = token;
        this.lexema = lexema;
    }

    public Simbolo(byte token, String lexema, byte tipo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
        this.classe = 0;
        this.tamanho = -1;
    }
}