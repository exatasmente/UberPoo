package Model;

public class Viagem extends Thread {

    private Motorista motorista;
    private Cliente cliente;
    private float valor;
    private int distancia;

    public Viagem(Motorista motorista, Cliente cliente, int distancia) {
        this.motorista = motorista;
        this.cliente = cliente;
        this.distancia = distancia;
        this.valor = (float) distancia * (float) 0.30;
    }

    public Motorista getMotorista() {
        return motorista;
    }

    public void setMotorista(Motorista motorista) {
        this.motorista = motorista;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    @Override
    public void run() {
        synchronized (this) {
            // armazena a distântica entre o inicio e o fim da viagem 
            int dist = this.distancia;
            // impreme na tela que a requisição foi aceita 
            System.out.println("Cliente " + this.cliente.getNome() + " Foi atendido pelo Motorista " + this.motorista.getNome() + " o valor da viagem é: " + this.valor);
            // enquanto o cliente não chegar no destino
            while (dist > 0) {
                // decrementa a distancia em 50 km
                dist -= 50;
                try {
                    // a thread dorme por 1 segunddo ( 1000 milesegundos)
                    this.sleep(1000);
                } catch (InterruptedException ex) {
                }
            }
            // quando o laço acabar 
            // atualiza a posição do cliente com a posição final 
            this.cliente.setPosicao(this.cliente.getPosicaoFinal());
            
            // atualiza a posição do motorista e define que ele não está mais em viagem
            this.motorista.setPosicao(this.cliente.getPosicaoFinal());
            this.motorista.setEmViagem(false);
            // incrementa o licro do motorista baseado na regra passada na documentação
            this.motorista.setLucro((this.valor * (float) 0.75) + this.motorista.getLucro());
            // imprime que a viagem foi finalizada
            System.out.println("Vagem do Cliente " + this.cliente.getNome() + " Colcluida ");
            
        }
    }
}
