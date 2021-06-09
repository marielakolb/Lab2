/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteapi;

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
 * @author osvaldo
 */
public class ClienteAPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            //capa transporte
            TTransport transporte = new TSocket("localhost",15001);
            //protocolo de comunicación
            TProtocol protocolo = new TBinaryProtocol(transporte);
            
            Almacenamiento.Client almacen = new Almacenamiento.Client(protocolo);
            
            //conexion
            transporte.open();
            
            //usar servicio
            try {
                System.out.println(almacen.guardar("key1", "value"));
            } catch (Except e) {
                System.out.println(e.getId()+" "+e.getDetalle());
            }
            
            try {
                System.out.println(almacen.obtener("key1"));
            } catch (Except e) {
                System.out.println(e.getId()+" "+e.getDetalle());
            }
            
            try {
                System.out.println(almacen.obtener("key2"));
            } catch (Except e) {
                System.out.println(e.getId()+" "+e.getDetalle());
            }
            
            try {
                System.out.println(almacen.eliminar("key1"));
            } catch (Except e) {
                System.out.println(e.getId()+" "+e.getDetalle());
            }
            
            try {
                System.out.println(almacen.obtener("key1"));
            } catch (Except e) {
                System.out.println(e.getId()+" "+e.getDetalle());
            }
            
            //cerrar conexion
            transporte.close();
            
        } catch (TTransportException ex) {
            Logger.getLogger(ClienteAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TException ex) {
            Logger.getLogger(ClienteAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
