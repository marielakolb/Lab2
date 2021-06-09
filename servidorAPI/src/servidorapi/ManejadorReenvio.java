/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorapi;

import apiAlmacenamiento.Almacenamiento;
import apiAlmacenamiento.Except;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

/**
 *
 * @author Mariela
 */
public class ManejadorReenvio implements Almacenamiento.Iface {
    private Almacenamiento.Client almacen;
    
    
    public ManejadorReenvio(String ip, int puerto) {
        try {
            TTransport transporte = new TSocket(ip,puerto);
            //protocolo de comunicación
            TProtocol protocolo = new TBinaryProtocol(transporte);
            
            this.almacen = new Almacenamiento.Client(protocolo);
            
            //conexion
            transporte.open();
        } catch (TTransportException ex) {
            Logger.getLogger(ManejadorReenvio.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
     
    @Override
    public int guardar(String clave, String valor) throws Except, TException {
        try{
          return almacen.guardar(clave, valor);          
        } catch (Except err){        
            throw err;
        } catch (TException err){
            Except e = new Except();
            e.setId(3);
            e.setDetalle("Instancia pricipal caída");
            throw e;
       }
       } 
    @Override
    public String obtener(String clave) throws Except, TException {
        try{
            return almacen.obtener(clave);
            } catch (Except err){
             throw err;
            } catch (TException err){
              Except e = new Except();
              e.setId(3);
              e.setDetalle("Instancia principal caída");
              throw e;
              }  
            }
    

    @Override
    public String eliminar(String clave) throws Except, TException {
        try{
         return almacen.eliminar(clave);        
         } catch (Except err){
          throw err;
          } catch (TException err){
          Except e = new Except();
          e.setId(3);
          e.setDetalle("Instancia principal caída");      
          throw e;
          }
    }
    
}
