package ifc.sisdi.aula16.model;

public class Saudacao {
    private int id;
    private String nome;
    public Saudacao(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
}
