/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gestor_Motos;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.System.exit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


/**
 *
 * @author Cristhian Torrico Castellón
 */
public class Principal {
    
    
    private static final String[] TIPOS_MOTOS = { "Scooters", "Maxi Scooters", "Motos GT – Custom",
        "Naked", "Racing", "Trail", "Chopper", "Motocross", "Custom", "Ciclomotores" };
    
    private ArrayList<Socio> socios;
    private ArrayList<Moto> motos;
    private ArrayList<Cesion> cesiones;
    
    private int valor_maximo = 0;
    
    public Principal(){
        
        socios = new ArrayList();
        motos = new ArrayList();
        cesiones = new ArrayList();
        
        // Inicialización por si los pelos
        socios.add(new Socio(1, "Pedro"));
        socios.add(new Socio(2, "Oscar"));
        socios.add(new Socio(3, "Carls"));
        socios.add(new Socio(4, "Juan"));
        socios.add(new Socio(5, "Coco"));
        socios.add(new Socio(6, "Perl"));
        
        motos.add(new Moto("aa1", 123, 1, "Perros", 1,0));
        motos.add(new Moto("aa2", 123, 2, "Ptoss", 2, 0));
        motos.add(new Moto("aa3", 166, 3, "Tres", 3, 0));
        motos.add(new Moto("aa4", 1000, 4, "Jaguar", 4, 0));
        motos.add(new Moto("aa5", 500, 5, "Chatarra", 5, 0));
        motos.add(new Moto("aa6", 999, 6, "algo", 6, 0));
        
        for(int i = 0; i < 6; i++){
            socios.get(i).addMoto(motos.get(i));
        }
        /*
        socios.get(0).addMoto(motos.get(0));
        socios.get(1).addMoto(motos.get(1));
        socios.get(2).addMoto(motos.get(2));
        socios.get(3).addMoto(motos.get(3));
        */
    }
    
    public void registrarMoto(){
        
        ArrayList<Integer> ids_correctos = new ArrayList();
        
        Boolean socio_disp = false;
        float precio;
        
        Moto moto = new Moto();
        int id = -1;
        Boolean correcto = false;
        int pos;
        
        if(socios.isEmpty()){
            System.out.println("\tNo hay ningún socio al que se le pueda adjuntar la motocicleta!\n");
            return;
        }

        //int i = 0; //Contador de socios a recorrer
        
        //Añadirmos una motocicleta
        System.out.println("Dame los datos de la moto:\n");
        System.out.println("--------------------------------------------------");
        
        System.out.println("Matrícula: ");
        Scanner matri = new Scanner(System.in);
        moto.setMatricula(matri.nextLine());
        
        System.out.println("Precio: ");
        
        while((precio = pedirDecimal()) > 6000){
            System.out.println("El precio supera el presupuesto de cualquier socio. Vuelva a Intentarlo\n");
        }
        
        moto.setPrecio(precio);
        
        System.out.println("Tipo: ");
        moto.setTipo(pedirEntero());
        
        System.out.println("Descripción: ");
        Scanner descp = new Scanner(System.in);
        moto.setDescripcion(descp.nextLine());
        
        System.out.println("Gastos: ");
        moto.setGastos(pedirDecimal());
        
        //Escogermos un socio dispoble
        System.out.println("Esta es la opción de socios disponibles. Selecciona su id");
        System.out.println("---------------------------------------------------------------------\n");
        for(int i = 0; i < socios.size(); i++){
            if((socios.get(i).getMoney()+moto.getPrecio()) <= valor_maximo){
                System.out.println(socios.get(i).toString());
                ids_correctos.add(socios.get(i).getId_socio());
                socio_disp = true;
            }
        }
        
        if(socio_disp == false){
            System.out.println("\nLo siento, no hay socios con suficientes recursos para esa motocicleta\n");
            return;
        }
        
        //Se pide el id al usuario y se mira si es correcto con los disponibles
        while(correcto == false){
            id = pedirEntero();
            if(!(correcto = ids_correctos.contains(id)))
                System.out.println("Error: Id no correspondido con los candidaos disponibles.\nVuelva a intentarlo\n");
        }
        
        pos = VerificarSocio(id);
        
        socios.get(pos).addMoto(moto);
        motos.add(moto);
        System.out.println("Moto con matrícula " + moto.getMatricula()+" registrada!\n");
        //matricula = matri.nextInt();//toInteger(matri);
        
    }
    
    // Se registra un socio
    public void registrarSocio(){
        
        Socio socio = new Socio();
        int id;
        
        System.out.println("Dame los datos del socio:\n");
        System.out.println("--------------------------------------------------");
        
        System.out.println("Id del socio: ");
        
        while(VerificarSocio((id = pedirEntero())) >= 0){
            System.out.println("Error: El socio ya está registrado\n");
        }
        socio.setId_socio(id);
        
        System.out.println("Nombre del socio: ");
        Scanner name = new Scanner(System.in);
        socio.setNombre(name.nextLine());
        socios.add(socio);

    }
    
    //Se registra una cesión
    public void RegistrarCesion(){
        
        String matricula;
        
        if(socios.size() < 2){
            System.out.println("\tNo hay suficientes socios para realizar la cesión\n");
            return;
        }
        
        Cesion cesion = new Cesion();
        int pos_1 = -1;                 //Posición del vector de Socios del socio emisor
        int pos_2 = -1;                 //Posición del vector de Socios del socio receptor

        int id1;
        int id2;
        Boolean salida_emisor = false, salida_receptor = false;
        System.out.println("Todos los usuarios son estos:\n");
        System.out.println("-------------------------------------------------\n");
        
        for(int i = 0; i < socios.size(); i++){
                System.out.println(socios.get(i).toString());
        }
        
        System.out.println("Dame los identificadores de los socios:\n");
        
        //Pido el id del socio que dará la moto
        do{
            System.out.println("Socio emisor: ");
            id1 = pedirEntero();
            
            if((pos_1 = VerificarSocio(id1))== -1){
               System.out.println("\n\tEl socio no existe\n");
            }
            
            else if(socios.get(pos_1).mis_motos.isEmpty()){
                System.out.println("\n\tEl socio no no tiene motocicletas en su poder\n");
            }
            else
                salida_emisor = true;
        }while(salida_emisor == false);
        
        //Pido el id del socio que recibirá la moto
        do{
            System.out.println("Socio receptos: ");
            id2 = pedirEntero();
            if((pos_2 = VerificarSocio(id2))== -1){
               System.out.println("\n\tEl socio no existe\n");
            }
            else
                salida_receptor = true;
        }while(salida_receptor == false);
        
        System.out.println("\n\tÉstos son las motocicletas del socio emisor\n");
        for(int i = 0; i < socios.get(pos_1).mis_motos.size();i++)
            System.out.println("\t"+socios.get(pos_1).mis_motos.get(i).getMatricula()+"\n");
        
        System.out.println("Matrícula de la moto a transferir: \n");
        matricula = pedirMatricula(socios.get(pos_1));

        cesion.transferencia(socios.get(pos_1), socios.get(pos_2), matricula);        
        cesiones.add(cesion);

    }
    
    
    public void ListaSocioMotos(){
        
        Boolean socio_moto = false;
        if(socios.isEmpty()){
            System.out.println("Error: No hay socios regitrados\n");
            return;
        }
    
        System.out.println("Estos son los socios con motos en posesión\n");
        System.out.println("----------------------------------------------------------\n");
        for(int i = 0; i < socios.size(); i++){
            if(!socios.get(i).mis_motos.isEmpty()){
                System.out.println(socios.get(i).toString());
                socio_moto = true;
            }
        }
        
        if(socio_moto == false)
            System.out.println("\tNingún socio tiene asociado una motocicleta\n");
    }
    
    
    public void ListarMotos(){
        
        if(motos.isEmpty()){
            System.out.println("Error: No se ha registrado ninguna moto\n");
            return;
        }
        for(int i = 0; i < motos.size(); i++){
            System.out.println(motos.get(i).toString());
        }
    }
    
    public void MostrarCesiones(){
        if(cesiones.isEmpty()){
            System.out.println("Error: No se ha registrado ninguna cesión\n");
            return;
        }
        
        for(int i = 0; i < cesiones.size(); i++){
            System.out.println(cesiones.get(i).toString());
        }
    }
    
    
    public void Salir(){
        
        
        Boolean exist = false;
        int eleccion = -1;
        File archivo;
        FileWriter writer;
        BufferedWriter buffer;
        String cadena = "";
        String aux_salida;
        System.out.println("¿Quiere guardar el archivo? [Y/N]");
        
        Scanner salida = new Scanner(System.in);
        aux_salida = salida.nextLine();
        if(aux_salida.equals("N")|| aux_salida.toUpperCase().equals("NO")){
         
            System.out.println("Salida exitosa");
            exit(0);
        }
        
        System.out.println("\n\tDeme el nombre que quiere adjuntarle al fichero: ");
        Scanner name = new Scanner(System.in);
        
        
        archivo = new File(name.nextLine() + ".txt");
        
        if(archivo.exists()){
            System.out.println("\nEl archivo ya existe. Si quiere sobreescribirlo pulse 1.\nEn caso de querer continuar pulse 0. Salir sin guardar -1\n");
            
            while((eleccion = pedirEntero()) != 0 && eleccion != 1){
                
                if(eleccion == -1)
                    exit(0);
                System.out.println("Error: Eleeción incorrecta. Vuelva a poner un 1 o un 0\n");
            }
            exist = true;
        }
        
        
        
        
        //Escribimos en cadena todo lo que nos hace falta
        for(int i = 0; i < socios.size(); i++){
            if(!socios.get(i).mis_motos.isEmpty()){
                cadena = cadena + socios.get(i).toString()+"\n";
            }
        }
        
        for(int i = 0; i < cesiones.size(); i++){
            cadena = cadena +cesiones.get(i).toString()+"\n";
        }
        
        System.out.println(cadena+"\n");
        //cadena = "--------------------------------------------";
        
        if(eleccion == 1 || exist == false){
            try{
                writer = new FileWriter(archivo);

            buffer = new BufferedWriter(writer);

            buffer.write(cadena);
            buffer.close();
            }catch(IOException ioe){
                Salir();
            }
        }
        else{
            try{
                writer = new FileWriter(archivo, true);

                buffer = new BufferedWriter(writer);

                buffer.write(cadena);
                buffer.close();
            }catch(IOException ioe){
                Salir();
            }
        }

        System.out.println("Fue un éxito el fichero");
        exit(0);
    }
    
    
    void Valor_Maximo(){
        valor_maximo = pedirEntero();
    }
    
    void IncrementarGastos(){
        
        if(motos.isEmpty()){
            System.out.println("Error: No hay motocicletas asignadas\n");
            return;
        }
        String matricula;
        float gastos;
        Boolean encontrado = false;
        int i = 0;
        System.out.println("Dame la matrícula: ");
        Scanner matri = new Scanner(System.in);
        
        matricula = matri.nextLine();
        
        System.out.println("Gastos extras: ");
        gastos = pedirDecimal();
        
        System.out.println("Estas son las matrículas: ");
        for(int j = 0; j < motos.size(); j++)
            System.out.println(motos.get(j).getMatricula() +"\n");
        
        while(encontrado == false){
            
            if(matricula.equals(motos.get(i).getMatricula()))
            {
                motos.get(i).setGastos(gastos);
                encontrado = true;
            }
            else
                i++;
        }
    }
    
    
    public void EliminarSocio(){
        
        int pos = -1;            //Posición del socio en la lista socio
        int pos_s = -1;          // Posición de usuarios a dar la motocicleta
        Boolean encontrado = false;
        Boolean socio_correcto = false;
        System.out.println("Dame el id del socio a dar de baja.\nEstos son los socios que hay:");

        
        //Mostramos por pantalla los socios
        for(int i = 0; i < socios.size(); i++){
            System.out.println(socios.get(i).toString());
        }
        
        //Pedimos a la fuerza un entero y que este entero sea una id de algún socio. Luego se guarda en pos la posición del socio
        while(encontrado == false){
            
            if((pos = VerificarSocio(pedirEntero())) != -1)
                encontrado = true;
        }
        
        //Si el socio tiene motocicletas, se las repartimos a los otros socios
        if(socios.get(pos).mis_motos.isEmpty() == false){
            System.out.println("El socio tiene motocicletas en su poder. Se han de resolver\n");
            for(int i = 0; i < socios.get(pos).mis_motos.size(); i++){
                System.out.println("Dime a qué socio quieres meter la matrícula " + socios.get(pos).mis_motos.get(i).getMatricula());
                
               while(socio_correcto == false){
                   
                   if((socios.get(i).getMoney()+socios.get(pos).mis_motos.get(i).getPrecio()) <= valor_maximo)
                       System.out.println("Socio con pocos recursos");
                   else if((pos_s = VerificarSocio(pedirEntero())) != -1)
                       socio_correcto = true;
               }
               socio_correcto = false;
               
               socios.get(pos_s).addMoto(socios.get(pos).mis_motos.get(i));
            }
        }
            
        socios.remove(pos);
    }
    
    //Se listarán los 3 usuarios con más cesiones recibidas
    public void miembrosMasCesiones(){
        
        Map<Socio, Integer> socios_cesiones = new HashMap();
        Socio socio_actual;
        
        if(cesiones.isEmpty()){
            System.out.println("\nError: No ha habido ninguna transferencia\n");
            return;
        }
        
        //Obtengo en una lista map los socios no repidos, y además, cuántas  veces aparecieron
        for(int i = 0; i < cesiones.size(); i++){
            
            socio_actual = cesiones.get(i).getReceptor();
            if(socios_cesiones.containsKey(socio_actual) == false)
                socios_cesiones.put(socio_actual, 1);
            else
                socios_cesiones.put(socio_actual, socios_cesiones.get(socio_actual)+1);
                
        }
        

        System.out.println("Socios\t\tNº cesiones\n");
        for(Map.Entry<Socio, Integer> it : socios_cesiones.entrySet()){
            System.out.println(it.getKey().getId_socio() + "\t\t" + it.getValue()+"\n");
        }


    }
    /*
            FUNCIONES AUXILIARES
    */
    public float pedirDecimal(){
        
        Boolean decimal = false;
        float exito = -1;
        
        while(decimal == false){
            Scanner aux = new Scanner(System.in);
            try {

                    exito = Float.parseFloat(aux.nextLine());
                    decimal = true;
            } catch (NumberFormatException nfe){
                    decimal = false;
                    System.out.println("Eror. No es un número decimal:\n"+nfe+"\n");
            }
        }
        
        return exito;
    }
    
    
    public int pedirEntero(){
        
        
        Boolean entero = false;
        int exito = -1;
        
        while(entero == false){
                Scanner aux = new Scanner(System.in);
                //exito = Integer.parseInt(aux.nextLine());
            try {
                    exito = Integer.parseInt(aux.nextLine());
                    entero = true;
            } catch (NumberFormatException nfe){
                    entero = false;
                    aux.reset();
                    System.out.println("Error: No es un número entero:\n"+nfe+"\n");
            }
        }
        
        return exito;
    }
    //Pide una matrícula y mira si está en el array de motocicletas del socio
    String pedirMatricula(Socio socio){
        
        String matri_final;
        
        Scanner matri = new Scanner(System.in);
        matri_final = matri.nextLine();
         /*No sé por qué, pero el código original no me funciona, asi que me adapto para que compile
        int i = 0;
        int max = socio.mis_motos.size();
        Boolean hecho = false;
        
        while(i < max && hecho == false){
            
            Scanner mat = new Scanner(System.in);
            String matricula = mat.nextLine();
            for(int j = 0; j < max; j++){
                if(socio.mis_motos.get(j).getMatricula().equals(matricula)){
                    hecho = true;
                    matri_final = matricula;
                }
            }
            i++;
        }
        */
        return matri_final;
    }
    //Si el socio existe, devuelve su posición; sino, devuelve -1
    public int VerificarSocio(int id){
        
        int i = 0;
        int respuesta = -1;
        Boolean encontrado = false;
        
        //Busca al socio
        
        while(encontrado == false && i < socios.size()){
                if(id != socios.get(i).getId_socio())
                    i++;
                else{
                    encontrado = true;
                    respuesta = i;
                }
        
        }
        return respuesta;
    }
        
    
    public static void main(String[] args){
        
        Principal prin = new Principal();
        
        System.out.println("Deme el coste máximo de los socios");
        prin.Valor_Maximo();
        
        while(true){
            System.out.println("1. Registrar un nuevo miembro\n");
            System.out.println("2. Registrar una nueva motocicleta\n");
            System.out.println("3. Registrar una cesión\n");
            System.out.println("4. Listar en pantalla los miembros con motos en posesión\n");
            System.out.println("5. Listar todas las motos\n");
            System.out.println("6. Mostrar las cesiones realizadas\n");
            System.out.println("7. Incrementar gastos\n");
            System.out.println("8. Miembros con más cesiones\n");
            System.out.println("9. Salir del programa\n");
            System.out.println("------------------------------------------------------------------------------\n");
            int caso = prin.pedirEntero();
         
            switch(caso){
                
                case 1: prin.registrarSocio();
                    break;
                case 2: prin.registrarMoto();
                    break;
                case 3: prin.RegistrarCesion();
                    break;
                case 4: prin.ListaSocioMotos();
                    break;
                case 5: prin.ListarMotos();
                    break;
                case 6: prin.MostrarCesiones();
                    break;
                case 7: prin.IncrementarGastos();
                    break;
                case 8: prin.miembrosMasCesiones();
                    break;
                case 9: prin.Salir();
                    break;
                default: System.out.println("Elección. Pruebe otra vez.\n");
            }
        }
    }
}
