/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication3;


/**
 *
 * @author Cristhian Torrico Castellón
 */
public class Moto {
     
    private String matricula;       //Matrícula la moto
    private float precio;           //Precio de la moto
    private int tipo;               //Aquí clasifico como quiero la moto
    private String descripcion;
    private int id_socio;           //Referencia al id del socio al que peretecene
    private float gastos;
    
    /*
        Creación de la moto. El socio no es imprescindible, pues en la clase Principal ya se pide
    */
    public Moto(String matricula,float precio, int tipo, String descripcion, int id_socio, float gastos){
        
        this.matricula = matricula;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        
        this.id_socio = id_socio;
        this.gastos = gastos;
    }
    //Creación cuando no queremos meterlo toda en una, sino poco a pooc (ya tú sabe;))
    public Moto(){
        gastos = 0;
    }
    
    //Creación de una moto con matrícula
    public Moto(String matricula){
        gastos = 0;
        this.matricula = matricula;
    }
    
    //Vamos consultando la matrícula
    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }
    
    //Vamos consulando la matrícula
    public String getMatricula() {
        return matricula;
    }
    
    //Vamos metiendo el precio
    public void setPrecio(float precio) {
        this.precio = precio;
    }
    
    //Vamos sacando el precio
    public float getPrecio() {
        return precio;
    }
    
    //Vamos metiendo el tipo
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    //Vamos consultando el tipo
    public int getTipo() {
        return tipo;
    }
    
    //Vamos metiendo la descripción
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    //Vamos consultando la descripción
    public String getDescripcion() {
        return descripcion;
    }
    
    //Vamos metiendo el socio
    public void setSocio(int id_socio){
        this.id_socio = id_socio;
    }
    
    public int getSocio(){
        return id_socio;
    }
    
    public void setGastos(float gastos){
        
        this.gastos = this.gastos + gastos;
    }
    
    @Override
    public String toString(){
        
        return "Matrícula: "+ matricula + "\t Precio: " + precio + "Tipo: "+ tipo + "\tDescripción: " + descripcion + "\tSocio: " + id_socio + "\tGastos: "+gastos+"\n";
    }
}
