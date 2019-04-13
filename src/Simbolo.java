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