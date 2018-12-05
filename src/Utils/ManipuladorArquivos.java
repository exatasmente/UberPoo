package Utils;

import Model.Cliente;
import Model.Motorista;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ManipuladorArquivos {

    public ManipuladorArquivos() {
    }

    public int[][] carregaCidade(String caminho) throws IOException {
        // Buffer de leitura 
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = "";
        String[] valores;
        int matriz[][];
        // le a primeira linha do arquivo
        linha = buffRead.readLine();
        // separa os valores por espaço
        valores = linha.split(" ");
        // declaraçãodas variavéis de auxilio
        int i, j, distancia;
        // inicializa uma matriz com o valor passado no arquivo
        matriz = new int[Integer.valueOf(valores[0])][Integer.valueOf(valores[0])];
        // enquanto tiver conteudo no arquivo
        while ((linha = buffRead.readLine()) != null) {
            valores = linha.split(" ");
            i = Integer.valueOf(valores[0]);
            j = Integer.valueOf(valores[1]);
            distancia = Integer.valueOf(valores[2]);
            matriz[i - 1][j - 1] = distancia;
            matriz[j - 1][i - 1] = distancia;
        }

        buffRead.close();
        return matriz;
    }

    public ArrayList<Cliente> carregaClientes(String caminho) throws IOException {
        BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = "";
        String[] valores;

        String nome;
        int posicaoInicial;
        int posicaoFinal;
        ArrayList<Cliente> clientes = new ArrayList<>();

        while ((linha = buffRead.readLine()) != null) {
            valores = linha.split(" ");
            nome = valores[0];
            posicaoInicial = Integer.valueOf(valores[1]);
            posicaoFinal = Integer.valueOf(valores[2]);
            clientes.add(new Cliente(posicaoFinal-1,nome,posicaoInicial-1));
        }

        buffRead.close();
        return clientes;
    }

    public ArrayList<Motorista> carregaMotoristas(String caminho) throws IOException {
     BufferedReader buffRead = new BufferedReader(new FileReader(caminho));
        String linha = "";
        String[] valores;

        String nome;
        int posicaoInicial;
        
        ArrayList<Motorista> motoristas = new ArrayList<>();

        while ((linha = buffRead.readLine()) != null) {
            valores = linha.split(" ");
            nome = valores[0];
            posicaoInicial = Integer.valueOf(valores[1]);
            motoristas.add(new Motorista(nome,posicaoInicial-1));
        }

        buffRead.close();
        return motoristas;
    }

}

