package Controller;

import Model.Cidade;
import Model.Cliente;
import Model.Motorista;
import Model.Viagem;
import Utils.ManipuladorArquivos;
import Utils.SemMotoristasException;
import java.io.IOException;
import java.util.ArrayList;

public class UberController {

    // Representação da cidade em forma de objeto
    private Cidade cidade;
    //Lista com todos os motoristas cadastrasdos 
    private ArrayList<Motorista> motoristas;

    // Lista com todos os clientes cadastrados inicialmente
    private ArrayList<Cliente> clientes;
    // Listas com todas as viagens realizadas durante a simulação
    private ArrayList<Viagem> viagens;
    // Licro total da empresa
    private float lucro;

    public UberController() throws IOException {

        // A Classe manipulador de arquivos tem  o papel de manipular os arquivos de texto e serializar estes valores para uso dentro do programa
        ManipuladorArquivos arquivos = new ManipuladorArquivos();

        // O Método carrega cidade espera como argumento o caminho para o arquivo de texto contendo as cidades
        int[][] matriz = arquivos.carregaCidade("src/Model/cidade.txt");

        // Inicializa a variavél cidade da classe passando a matriz serializada 
        this.cidade = new Cidade(matriz);
        // O Método carrega Clientes retorna um ArrayList<Cliente> e espera com argumento o caminho para o arquivo
        this.clientes = arquivos.carregaClientes("src/Model/clientes.txt");
        // O Método carrega Clientes retorna um ArrayList<Motorista> e espera com argumento o caminho para o arquivo
        this.motoristas = arquivos.carregaMotoristas("src/Model/motoristas.txt");
        //Inicializa o Array de viagens 
        this.viagens = new ArrayList<Viagem>();
        // Lucro inicial da empresa
        this.lucro = 0;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public ArrayList<Motorista> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(ArrayList<Motorista> motoristas) {
        this.motoristas = motoristas;
    }

    public ArrayList<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    /*
        O Método iniciaSimulação tem o papel de gerenciar as fases a simulação
        tendo como principal foco o controle das Listas e Threads utilizadas dutante o processo
        Este método tem nele uma cláusura synchronized para controle de leitura e escrita entre as threads e o controlador
     */
    public void iniciaSimulacao() {
        synchronized (this) {
            while (true) {
                /*O vetor finalizados tem o papel de marcar as viagens finalizadas
                    Com a seguinte lógica :
                    se a posicao i no vetor finalizados é igual a verdadeiro
                    então a viagem na posição i do vetor de viagens foi finalizada
                    logo ela será removida do vetor de viagens
                    (Comentários para as Linhas 85 a 98)
                 */
                int finalizado = -1;
                
                for (int v = 0; v < this.viagens.size(); v++) {
                    if (!this.viagens.get(v).isAlive()) {
                        finalizado = v;
                        lucro += this.viagens.get(v).getValor() * (float) 0.25;
                        clientes.remove(this.viagens.get(v).getCliente());
                        break;
                    }
                }
                if(finalizado != -1)
                    this.viagens.remove(finalizado);
                /*
                    O Laço de repetição principal do método inicaSimulação em o papel
                    de verificar quais clientes não estão em viagem e realizar a requisição
                    de uma viagem para eles, caso não seja a primeira requisição então
                    o programar irá incrementar o tempo de espera do cliente para uma nova requisição,
                    quando o tempo de espera for reiniciado uma nove requisição será feita
                    ou o programa dormirá´por 1 seguando.
                    Caso Contrário o será realizada a requisição diretamente
                    (Comentários das Linhas 110 a 124)
                
                 */
                for (int c = 0; c < this.clientes.size(); c++) {
                    if (!this.clientes.get(c).isEmViagem()) {
                        try {
                            if (this.clientes.get(c).getRequisicoes() > 0) {
                                this.clientes.get(c).InctempoEspera();
                                if (this.clientes.get(c).getTempoEspera() == 0) {
                                    this.geraRequisicao(c);
                                } else {
                                    Thread.sleep(1000);
                                }

                            } else {
                                this.geraRequisicao(c);
                            }
                        /*
                            Tratamento de excessões
                            Devido a chamada Thread.seleep(1000) (linha 118)
                            temos que capturar a excessão InterruptedException
                            
                            A outra excessão diz respeito a quando não há motoristas disponivéis para atender o cliente
                            
                         */
                        } catch (SemMotoristasException ex) {
                            System.out.println(ex.getMessage());
                        } catch (InterruptedException ex) {

                        }
                        // Para o Laço e reinicia o While
                        break;
                    }

                }
                // Caso não tenha mais viagens e clientes a simulação irá acabar
                if (this.viagens.isEmpty() && this.getClientes().isEmpty()) {
                    break;
                }
            }

        }
        // Impressão final do relátório após o final da simulação
        for (Motorista m : this.motoristas) {
            System.out.println("Nome: " + m.getNome());
            System.out.println("Lucro: " + m.getLucro() + "\n");

        }
        System.out.println("Lucro Empresa: " + this.lucro);

    }

    /*
        O Método gera viagem tem o papel de realizar o processo para cadastar uma nova viagem no sistema
        Os parametros do método são a posição do motorista no vetor motoristas e a posição do cliente no vetor cliente
        
        Se o cliente e o Motorista não estejam em viagem então
        será calculada o valor da viagem com a seguite regra:
            valor = 40% da distancia em Km entre a posição inicial e a posição final do cliente 
            ex cidade[1][3] = 50 então,  valor = 50 * 0.40
        o método também é responsavel por atualizar o estado do cliente e do motorista
        definindo o valor da variavél emViagem para verdadeiro
        e inicializar a thread para controlar o processo de viagem durante a simulação
        após este porcesso o metodo irá retornar true ou false caso o cliente ou o motorista já estejam em viagem
        (Comentários das Linhas 174 a 190)
    */

    private boolean geraViagem(int pm, int pc) {
        Motorista m = this.motoristas.get(pm);
        Cliente c = this.clientes.get(pc);

        if (!m.isEmViagem() && !c.isEmViagem()) {

            int distancia = this.cidade.getMatriz()[c.getPosicao()][c.getPosicaoFinal()];
            m.setEmViagem(true);
            c.setEmViagem(true);
            Viagem v = new Viagem(m, c, distancia);
            this.viagens.add(v);
            v.start();
            return true;
        }
        return false;

    }
    
    /*
        O método getMotoristasProximos retorna um vetor com as distâncias entre o ponto de partida do cliente
        na posição id do vetor clientes e o restante do grafo
    
        O Processo ábaixo é baseado no algorítimo de Dijkstra para busca em grafos com pesos não negativos
    
    */
    private int[] getMotoristasProximos(int id) {
        int inicio = this.clientes.get(id).getPosicao();
        int[][] matriz = this.cidade.getMatriz().clone();
        int[] vertices = new int[this.cidade.getMatriz().length];
        int[] distancias = new int[this.cidade.getMatriz().length];

        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = 0;
            distancias[i] = Integer.MAX_VALUE;
        }
        distancias[0] = 0;
        for (int i = this.cidade.getMatriz().length; i > 0; i--) {
            int no = -1;
            for (int j = 0; j < this.cidade.getMatriz().length; j++) {
                if (vertices[j] == 0 && (no == -1 || distancias[j] < distancias[no])) {
                    no = j;
                }
            }
            vertices[no] = -1;
            if (distancias[no] == Integer.MAX_VALUE) {
                break;
            }
            for (int k = 0; k < this.cidade.getMatriz().length; k++) {
                if (distancias[k] > distancias[no] + this.cidade.getMatriz()[no][k]) {
                    distancias[k] = distancias[no] + this.cidade.getMatriz()[no][k];
                }
            }
        }
        return distancias;
    }
    /*
        O Método geraRequisicao recebe como parametro a posição de um cliente no vetor de clientes
        e retorna verdadeiro caso a requisição seja atendida ou falso caso contrario
    */
    
    private boolean geraRequisicao(int id) throws SemMotoristasException {
        // Incrementa a quantide de requisições em 1
        this.clientes.get(id).setRequisicoes(this.clientes.get(id).getRequisicoes() + 1);
        // verifica se foram atingidas as 10 requisições máximas 
        if (this.clientes.get(id).getRequisicoes() > 10) {
            System.out.println("O Cliente " + this.clientes.get(id).getNome() + " Saiu do Programa");
            this.clientes.remove(id);
            return false;
        } else {
            /* caso contrário, pega os motoristas mais proximos 
                e realiza a requisição para eles 
                caso a requisição seja bem sucedida o cliente será removido da lista de clientes
                e o metodo retornará verdadeiro
                caso contrário o metodo lançará uma excessão do tipo SemMotoristasException
            */
            int[] distancias = this.getMotoristasProximos(id);
            for (Motorista m : this.motoristas) {

                for (int p = 0; p < distancias.length; p++) {
                    if (m.getPosicao() == p) {
                        if (this.geraViagem(this.motoristas.indexOf(m), id)) {
                            this.clientes.remove(id);
                            return true;
                        };
                    }
                }
            }
            throw new SemMotoristasException();

        }

    }

}
