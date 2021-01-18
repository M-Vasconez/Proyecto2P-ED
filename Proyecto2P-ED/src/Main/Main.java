/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Archivos.DataSet;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jorge
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
         DataSet.cargarDatos("pacientes");
        HashMap<String,ArrayList<Integer>> dataset = DataSet.getDataset();
        for(String e : dataset.keySet()){
            System.out.println(e+" - "+dataset.get(e));
        }
        
        System.out.println("\n\n\n\n");
        HashMap<String,Double> gini = DataSet.getGini();
        
        for(String e : gini.keySet()){
            System.out.println(e+" - "+gini.get(e));
        }
        
        DataSet.actualizarGini("DEATH_EVENT");
        
        System.out.println("\n\n\n\n");
        for(String e : gini.keySet()){
            System.out.println(e+" - "+gini.get(e));
        }
    }
    
}
