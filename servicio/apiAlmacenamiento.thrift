namespace java apiAlmacenamiento

#manejo de excepciones

exception Except {
  1: i32 id,
  2: string detalle
}

#servicio de almacenamiento

service Almacenamiento {
  /*
  formato guardar("clave", "valor")
  retorno: 
    0 creo nueva clave-valor, 
    1 modifico valor para la clave asociada,
    error id y detalle, cuando clave o valor no cumplen validaciones
  */
  i32 guardar(1: string clave, 2: string valor) throws (1: Except error),
  /*
  formato obtener("clave")
  retorno:
    "valor" asociado a clave
    error id y detalle, cuando no existe clave buscada
  */
  string obtener(1: string clave) throws (1: Except error),
  /*
  formato eliminar("clave")
  retorno:
    "valor" asociado a la clave, ELIMINA
    error id y detalle, cuando no existe clave buscada
  */
  string eliminar(1: string clave) throws (1: Except error)
}