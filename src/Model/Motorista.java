package Model;

public class Motorista extends Pessoa {
    private float lucro;

    public Motorista(String nome, int posicaoInicial) {
        super(nome, posicaoInicial);
        this.setEmViagem(false);
        this.lucro = lucro;
    }

    public float getLucro() {
        return lucro;
    }

    public void setLucro(float lucro) {
        this.lucro = lucro;
    }

}
