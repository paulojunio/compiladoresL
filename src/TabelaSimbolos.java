import java.util.HashMap;

//Criacao de tabela de simbolos
public class TabelaSimbolos{
    public HashMap<String, Simbolo> tabela = new HashMap<>();

    public final static byte identificador = 0;
    
    
    public final static byte VAR = 1;
    public final static byte INTEGER = 2;
    public final static byte CHAR = 3;
    public final static byte FOR = 4;
    public final static byte IF = 5;
    public final static byte ELSE = 6;
    public final static byte AND = 7;
    public final static byte OR = 8;
    public final static byte NOT = 9;
    public final static byte TO = 10;
    public final static byte DIFERENTE = 11;
    public final static byte IGUAL = 12;
    public final static byte MENOR_IGUAL = 13;
    public final static byte MAIOR_IGUAL = 14;
    public final static byte MAIOR = 15;
    public final static byte MENOR = 16;
    public final static byte PARENTESES_ABERTO = 17;
    public final static byte PARENTESES_FECHADO = 18;

    public final static byte VIRGULA = 19; 
    public final static byte MAIS = 20;
    public final static byte MENOS = 21;
    public final static byte MULTIPLICACAO = 22;
    public final static byte DIVICAO = 23;
    public final static byte PONTO_VIRGULA = 24;
    
    public final static byte CHAVES_ABERTO = 25;
    public final static byte CHAVES_FECHADO = 26;

    public final static byte THEN = 27;
    public final static byte READLN = 28;
    public final static byte STEP = 29;
    public final static byte WRITE = 30;
    public final static byte WRITELN = 31;
    public final static byte RESTO_DIVISAO = 32;
    public final static byte COLCHETE_ABERTO = 33;
    public final static byte COLCHETE_FECHADO = 34;
    public final static byte DO = 35;
    public final static byte CONST = 36;

    public final static byte constante = 37;

    public TabelaSimbolos(){
        tabela.put("id", new Simbolo(identificador, "id"));
        tabela.put("var", new Simbolo(VAR, "var"));
        tabela.put("integer", new Simbolo(INTEGER, "integer"));
        tabela.put("char", new Simbolo(CHAR, "char"));
        tabela.put("for", new Simbolo(FOR, "for"));
        tabela.put("if", new Simbolo(IF, "if"));
        tabela.put("else", new Simbolo(ELSE, "else"));

        tabela.put("and", new Simbolo(AND, "and"));
        tabela.put("or", new Simbolo(OR, "or"));
        tabela.put("not", new Simbolo(NOT, "not"));
        tabela.put("to", new Simbolo(TO, "to"));
        tabela.put("<>", new Simbolo(DIFERENTE, "<>"));
        tabela.put("=", new Simbolo(IGUAL, "="));
        tabela.put("<=", new Simbolo(MENOR_IGUAL, "<="));

        tabela.put(">=", new Simbolo(MAIOR_IGUAL, ">="));
        tabela.put(">", new Simbolo(MAIOR, ">"));
        tabela.put("<", new Simbolo(MENOR, "<"));
        tabela.put("(", new Simbolo(PARENTESES_ABERTO, "("));
        tabela.put(")", new Simbolo(PARENTESES_FECHADO, ")"));
        tabela.put(",", new Simbolo(VIRGULA, ","));
        tabela.put("+", new Simbolo(MAIS, "+"));

        tabela.put( "-", new Simbolo(MENOS, "-"));
        tabela.put("*", new Simbolo(MULTIPLICACAO, "*"));
        tabela.put( "/", new Simbolo(DIVICAO, "/"));
        tabela.put(";", new Simbolo(PONTO_VIRGULA, ";"));
        tabela.put( "{", new Simbolo(CHAVES_ABERTO, "{"));
        tabela.put("}", new Simbolo(CHAVES_FECHADO, "}"));
        tabela.put("then", new Simbolo(THEN, "then"));

        tabela.put("readln", new Simbolo(READLN, "readln"));
        tabela.put("step", new Simbolo(STEP, "step"));
        tabela.put("write", new Simbolo(WRITE, "write"));
        tabela.put("writeln", new Simbolo(WRITELN, "writeln"));

        tabela.put("%", new Simbolo(RESTO_DIVISAO, "%"));
        tabela.put("[", new Simbolo(COLCHETE_ABERTO, "["));
        tabela.put("]", new Simbolo(COLCHETE_FECHADO, "]"));
        tabela.put("do", new Simbolo(DO, "do"));
        tabela.put("const", new Simbolo(CONST, "const"));
    }

    public Simbolo buscaLexema(String lexema) {
        return tabela.get(lexema.toLowerCase());
    }

    public Simbolo inserirIdentificador(String lexema) {
        Simbolo simbolo = new Simbolo (identificador,lexema.toLowerCase());
        tabela.put(lexema.toLowerCase(),simbolo);
        return simbolo;
    }

    /*public Simbolo inserir_constante(String lexema) {
        Simbolo simbolo = new Simbolo (constante,lexema);
        tabela.put(lexema,simbolo);
        return simbolo;
    }*/

    public void printTabela(){
        int i = 0;
        for (String lexema : tabela.keySet()) {
            System.out.println(tabela.get(lexema).token + " " + tabela.get(lexema).lexema);
            i++;
        }
        System.out.println("São " + i + " chaves ao todo.");
    }


    


}