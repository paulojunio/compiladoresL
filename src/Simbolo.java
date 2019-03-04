//Classe referente ao simbolo
public class Simbolo{

    public byte token;
    public String lexema;

    public Simbolo(){
        
    }
    public Simbolo(byte token, String lexema){
        this.token = token;
        this.lexema = lexema;
    }
}