package Archivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataSet {
    private static HashMap<String,ArrayList<Integer>> dataset = new HashMap<>();
    private static HashMap<String,Integer> gini = new HashMap<>();
    
    
    public static void cargarDatos(String nombre){
        try {
            List<String> lineas = Files.readAllLines(Paths.get("src/Archivos/"+nombre+".csv"));
            String[] parametros = lineas.remove(0).split(";");
            for(String par: parametros){
                dataset.put(par, new ArrayList<Integer>());
                gini.put(par, 0);
            }
            for(String linea: lineas){
                String[] lineaSep = linea.split(";");
                for(int i=0 ; i<lineaSep.length ; i++){
                    dataset.get(parametros[i]).add(Integer.parseInt(lineaSep[i]));
                }
            }
        } catch (IOException ex) {
                System.out.println(ex);
        }
    }

    public static HashMap<String, ArrayList<Integer>> getDataset() {
        return dataset;
    }

    public static HashMap<String, Integer> getGini() {
        return gini;
    }
    
    
}
