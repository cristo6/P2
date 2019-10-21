/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Motos;

/**
 *
 * @author Cristhian Torrico Castellón
 */
public class Cesion {
 
    private Socio emisor;           //Socio que da la motocicleta
    private Socio receptor;         //Socio que recibe la motocicleta
    private String matricula;       //Matrícula de la motocicleta para buscarla
    private Boolean transferido;    //Información sobre si se ha podido transferir la motocicleta
    
    
    /**
        Ponemos la transferencia a falso por posibles problemas de consulta antes de la transferencia
    */
    public Cesion(){
        transferido = false;
    }
    
    /*
        Metemos los valores para una ejecución diirecta para la transferencia
    */
    public boolean transferencia(Socio emisor, Socio receptor, String matrícula){
       
        int i = 0;                      //Contador para buscar la motocilceta en el vector de Socio emisor
        Boolean resultado = false;      //Resultado de si se ha podido hacer la transferencia
        
        //Nos aseguramos que, una vez transferido, no se haga modificaciones, pues es un registro.
        if(transferido == false){
            this.emisor = emisor;
            this.receptor = receptor;
            this.matricula = matrícula;

            //Buscar la otocicleta con la matrícula que tenemos
            while(!(resultado = matricula.equals(emisor.mis_motos.get(i).getMatricula()))){
                i++;
            }
            
            //Una vez encontrada, sé su posición 'i'
            if(resultado){
                if(receptor.getMoney()+emisor.mis_motos.get(i).getPrecio() <= 6000){

                    resultado = true; 
                    emisor.getMoto(i).setSocio(receptor.getId_socio());
                    receptor.addMoto(emisor.getMoto(i));
                    emisor.deleteMoto(i);
                }
                else
                    System.out.println("Error: El socio receptor sobresale de los límites monetarios para adquirir la moto.\n");


            }
            else
                System.out.println("Error: El socio emisor no tiene asignada una moto con esa matrícula descrita.\n");

            transferido = resultado;
        }
        
        else
            System.out.println("Error: Se ha establecido una transferencia correcta anteriormente. Ya no se pude modificar el registro.\n");
        
        return resultado;
    }

    //Se consulta el socio que da la moto
    public Socio getEmisor() {
        return emisor;
    }

    //Se consulta el socio que recibe la moto
    public Socio getReceptor() {
        return receptor;
    }

    //Se consulta la matrícula
    public String getMatricula() {
        return matricula;
    }
    
    public Boolean getTransferido(){
        return transferido;
    }
    
    @Override
    public String toString(){
        
        return ("Socio emisor: " + emisor + "\n Socio receptor: "+receptor + "\t Matrícula: "+matricula+"\n");
    }
}
