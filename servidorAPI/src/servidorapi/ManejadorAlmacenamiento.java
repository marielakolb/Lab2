/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorapi;

import apiAlmacenamiento.Almacenamiento;
import apiAlmacenamiento.Except;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.thrift.TException;

/**
 *
 * @author osvaldo
 */
public class ManejadorAlmacenamiento implements Almacenamiento.Iface {

    //definir el almacen
    public Map<String, String> almacen = new ConcurrentHashMap<>();
    //definir el regex como formato valido de clave
    String regex = "^([a-zA-Z0-9_]){1,50}$";
    //definir tamanio maximo del valor (en mb)
    int maximo = 1500000;

    /**
     * Validar formato de clave.
     * @param clave
     * @return 
     */
    public boolean validarClave(String clave) {
        //crear patron
        Pattern patron = Pattern.compile(this.regex);
        //comparar
        Matcher m = patron.matcher(clave);
        return m.find();
    }
    
    /**
     * Validar tamanio del valor.
     * @param valor
     * @return 
     */
    public boolean validarTamanioValor(String valor) {
        int largoBytesValor = valor.getBytes(StandardCharsets.UTF_16).length;
        return largoBytesValor <= this.maximo;
    }

    /**
     * guardar clave-valor
     * @param clave
     * @param valor
     * @return int
     * @throws Except
     * @throws TException 
     */
    @Override
    public int guardar(String clave, String valor) throws Except, TException {
        //falta validar tamaño del valor, menor a 1.5mb
        if (validarClave(clave) && validarTamanioValor(valor)) {
            //si no existe la clave
            if (!almacen.containsKey(clave)) {
                //creo clave valor
                almacen.putIfAbsent(clave, valor);
                //retorno 0
                return 0;
            } else {
                //si clave existe
                almacen.replace(clave, valor);
                //retornar 1
                return 1;
            }
        } else {
            Except e = new Except();
            e.setId(0);
            e.setDetalle("guardar - error en formato");
            throw e;
        }
    }

    /**
     * obtener clave-valor
     * @param clave
     * @return string
     * @throws Except
     * @throws TException 
     */
    @Override
    public String obtener(String clave) throws Except, TException {
        if(almacen.containsKey(clave)){
            //obtengo el valor
            return almacen.get(clave);
        } else {
            Except e = new Except();
            e.setId(1);
            e.setDetalle("obtener - clave no existe");
            throw e;
        }
    }

    /**
     * eliminar clave-valor
     * @param clave
     * @return string
     * @throws Except
     * @throws TException 
     */
    @Override
    public String eliminar(String clave) throws Except, TException {
        if(almacen.containsKey(clave)){
            //elimino clave-valor
            return almacen.remove(clave);
        } else {
            Except e = new Except();
            e.setId(2);
            e.setDetalle("eliminar - clave no existe");
            throw e;
        }
    }

}
