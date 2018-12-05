package Model;


public class Cliente extends Pessoa{
    private int posicaoFinal;
    private int requisicoes;
    
    private int tempoEspera;
    public Cliente(int posicaoFinal, String nome, int posicaoInicial) {
        super(nome, posicaoInicial);
        this.setEmViagem(false);
        this.posicaoFinal = posicaoFinal;
        this.requisicoes = 0;
    }

    public int getPosicaoFinal() {
        return posicaoFinal;
    }

    public void setPosicaoFinal(int posicaoFinal) {
        this.posicaoFinal = posicaoFinal;
    }

    public int getRequisicoes() {
        return requisicoes;
    }

    public void setRequisicoes(int requisicoes) {
        this.requisicoes = requisicoes;
    }

    public int getTempoEspera() {
        return tempoEspera;
    }

    public void InctempoEspera(){
        
        this.tempoEspera++;
        // altetar este valor para saber o limite em segundos para o tempo de espera
        // 60  = 1 minuto
        // 1 = 1 segundo
        if(this.tempoEspera == 3){
            this.tempoEspera = 0;
        }
    }

    
    
}
