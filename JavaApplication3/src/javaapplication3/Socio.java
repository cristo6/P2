/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;

import java.util.ArrayList;

/**
 *
 * @author alumno
 */
public class Socio {
    static int id_general = 0;
    private int id_socio;
    private String nombre;
    private float money;
    ArrayList<Moto> mis_motos;
    
    public Socio(int id_socio, String nombre){
        
        //this.id_socio = id_socio;
        this.nombre = nombre;
        mis_motos = new ArrayList();
        money = 0;
        id_general++;
        id_socio = id_general;
    }
    
    public Socio(){
        mis_motos = new ArrayList();
        money = 0;
    }
    
    public void setId_socio(int id_socio){
        this.id_socio = id_socio;
    }
    
    public int getId_socio(){
        return id_socio;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void addMoto(Moto moto){
        
        moto.setSocio(id_socio);
        mis_motos.add(moto);
        money = money + moto.getPrecio();
    }
    
    public float getMoney(){
        return money;
    }
    
    public void deleteMoto(String matricula){
        
        int i = 0;
        while(!matricula.equals(mis_motos.get(i).getMatricula())){
            i++;
        }
        mis_motos.remove(i);
    }
    
    public void deleteMoto(int i){
        mis_motos.remove(i);
        
    }
    public Moto getMoto(int i){
        return mis_motos.get(i);
    }
    
    /* No aplicar, porque no hay forma de saber si ha llegado a encontrar el dato, y mandarlo, pues no se podría hcaer por referencia
    public Moto getMoto(String matricula){
        
        int i = 0;
        
        while(!matricula.equals(mis_motos.get(i).getMatricula()) && i < mis_motos.size())
            i++;
        
        return mis_motos.get(i);
    }
*/
    
    @Override
    public String toString(){
        return (String.format("%03d", id_socio)+"\t"+nombre+"\n");
    }
}
