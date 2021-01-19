package Archivos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class DataSet {

    private static HashMap<String, ArrayList<Integer>> dataset = new HashMap<>();
    private static HashMap<String, Double> gini = new HashMap<>();
    private static HashMap<String, ArrayList<Integer>> datosP = new HashMap<>();

    public static void cargarDatos(String nombre) {
        try {
            List<String> lineas = Files.readAllLines(Paths.get("src/Archivos/" + nombre + ".csv"));
            String[] parametros = lineas.remove(0).split(";");
            for (String par : parametros) {
                dataset.put(par, new ArrayList<Integer>());
                gini.put(par, 0.0);
            }
            for (String linea : lineas) {
                String[] lineaSep = linea.split(";");
                for (int i = 0; i < lineaSep.length; i++) {
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

    public static HashMap<String, Double> getGini() {
        return gini;
    }

    private static double obtenerGini(String at1, String at2) {
        double at1TT = 0, at1TF = 0, at1FF = 0, at1FT = 0;
        ArrayList<Integer> atri1 = dataset.get(at1);
        ArrayList<Integer> atri2 = dataset.get(at2);

        for (int i = 0; i < atri1.size(); i++) {
            if (atri1.get(i) == 1) {
                if (atri2.get(i) == 1) {
                    at1TT += 1;
                } else {
                    at1TF += 1;
                }
            } else {
                if (atri2.get(i) == 1) {
                    at1FT += 1;
                } else {
                    at1FF += 1;
                }
            }
        }
        return calcularGini(at1TT, at1TF, at1FF, at1FT, atri1.size());
    }

    private static double calcularGini(double at1TT, double at1TF, double at1FF, double at1FT, double at1TOT) {
        double at1T = at1TT + at1TF, at1F = at1FF + at1FT;
        double gini = 0;
        if (at1TF == 0 && at1FT == 0) {

            gini = 1 - Math.pow((at1T / at1TOT), 2) - Math.pow((at1F / at1TOT), 2);
        } else {
            double sub1 = 1 - Math.pow((at1TT / at1T), 2) - Math.pow((at1TF / at1T), 2);
            double sub2 = 1 - Math.pow((at1FT / at1F), 2) - Math.pow((at1FF / at1F), 2);
            gini = (((at1T) / at1TOT) * sub1) + (((at1F) / at1TOT) * sub2);
        }
        return gini;
    }

    public static void actualizarGini(String at1) {
        for (String k : gini.keySet()) {
            gini.replace(k, obtenerGini(k, at1));
        }
    }

    public static HashMap<String, ArrayList<Integer>> segmentarData(String atributo) {
        actualizarGini(atributo);
        String min = buscarMin();
        System.out.println(min);

        return datosPositivos(min);
    }

    private static HashMap<String, ArrayList<Integer>> datosPositivos(String atributo) {
        datosP = (HashMap<String, ArrayList<Integer>>) dataset.clone();
        
        for(Map.Entry<String,ArrayList<Integer>> entry:datosP.entrySet()){
           ArrayList<Integer> min = datosP.get(atributo);
           for (int i : min){
               for(Integer datos:entry.getValue()){
                   if( i == 1 ){
                       datosP.put(entry.getKey(), entry.getValue());
                       System.out.println(entry.getKey() + "   "+ entry.getValue());
                   }
               }
           } 
           
        }
        return null;
    }
    
        private static HashMap<String, ArrayList<Integer>> datosNegativos(String atributo) {
        datosP = (HashMap<String, ArrayList<Integer>>) dataset.clone();
        
        for(Map.Entry<String,ArrayList<Integer>> entry:datosP.entrySet()){
           ArrayList<Integer> min = datosP.get(atributo);
           for (int i : min){
               for(Integer datos:entry.getValue()){
                   if( i == 0 ){
                       datosP.put(entry.getKey(), entry.getValue());
                   }
               }
           } 
           
        }
        return null;
    }
    
    

    private static String buscarMin() {
        Double i = 0.0;
        String Key = "";
        for (Map.Entry<String, Double> entry : gini.entrySet()) {
            if (i == 0) {
                i = entry.getValue();
                Key = entry.getKey();
            }

            if (entry.getValue() < i) {
                i = entry.getValue();
                Key = entry.getKey();
            }
        }

        return Key;
    }
}
