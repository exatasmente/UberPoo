package View;


import Controller.UberController;
import Utils.*;
import Model.Cidade;
import Model.Cliente;
import Model.Motorista;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    
   public static void main(String[] args){
       
       try {
          
           UberController uber = new UberController();
           uber.iniciaSimulacao();
       } catch (IOException ex) {
           
       }
       
   }
}
