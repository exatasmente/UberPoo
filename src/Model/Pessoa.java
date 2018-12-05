package Model;

public class Pessoa {

    private String nome;
    private int posicao;
    private boolean emViagem;

    public Pessoa(String nome, int posicao) {
        this.nome = nome;
        this.posicao = posicao;
        this.emViagem = false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPosicao() {
        return posicao;
    }

    public void setPosicao(int posicao) {
        this.posicao = posicao;
    }

    public boolean isEmViagem() {
        return emViagem;
    }

    public void setEmViagem(boolean emViagem) {
        this.emViagem = emViagem;
    }

}
