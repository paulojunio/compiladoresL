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
    public String tipo;
    public int tamanho;
    public Simbolo(){
        
    }
    public Simbolo(byte token, String lexema){
        this.token = token;
        this.lexema = lexema;
    }

    public Simbolo(byte token, String lexema, String tipo) {
        this.token = token;
        this.lexema = lexema;
        this.tipo = tipo;
    }
}