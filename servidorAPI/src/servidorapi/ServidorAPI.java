/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorapi;

import apiAlmacenamiento.Almacenamiento;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.TTransportFactory;

/**
 *
 * @author osvaldo
 */
public class ServidorAPI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            try {
 
            //manejador
            ManejadorAlmacenamiento manejador = new ManejadorAlmacenamiento();
            //Servicio
            Almacenamiento.Processor procesador = new Almacenamiento.Processor(manejador);
            //capa de transporte
            TServerTransport servidorTransporte = new TServerSocket(15000);
            
            //servidor de hilos
            TThreadPoolServer.Args argumentos = new TThreadPoolServer.Args(servidorTransporte);
            
            //argumentos del pool de hilos
            argumentos.processor(procesador);
            //factory
            argumentos.transportFactory(new TTransportFactory());
            //protocolo
            argumentos.protocolFactory(new TBinaryProtocol.Factory());
            
            //cantidad de hilos
            argumentos.minWorkerThreads(10);
            argumentos.maxWorkerThreads(50);
            
            //servidor
            TServer servidor = new TThreadPoolServer(argumentos);
            
            System.out.println("Iniciando el servidor ...");
            servidor.serve();
            
            
        } catch (TTransportException ex) {
            Logger.getLogger(ServidorAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       else if (args.length == 2){
           String ip = args[0];
           int pto = Integer.parseInt(args[1]);
           try {
 
            //manejador
            ManejadorReenvio manejador = new ManejadorReenvio(ip,pto);
            //Servicio
            Almacenamiento.Processor procesador = new Almacenamiento.Processor(manejador);
            //capa de transporte
            TServerTransport servidorTransporte = new TServerSocket(15000);
            
            //servidor de hilos
            TThreadPoolServer.Args argumentos = new TThreadPoolServer.Args(servidorTransporte);
            
            //argumentos del pool de hilos
            argumentos.processor(procesador);
            //factory
            argumentos.transportFactory(new TTransportFactory());
            //protocolo
            argumentos.protocolFactory(new TBinaryProtocol.Factory());
            
            //cantidad de hilos
            argumentos.minWorkerThreads(10);
            argumentos.maxWorkerThreads(50);
            
            //servidor
            TServer servidor = new TThreadPoolServer(argumentos);
            
            System.out.println("Iniciando el servidor ...");
            servidor.serve();
            
            
        } catch (TTransportException ex) {
            Logger.getLogger(ServidorAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
         }    
        }
        
    
}
