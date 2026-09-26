package com.ebac.modulo59.model;

import java.sql.SQLException;

public interface OperacionesCRUD<T> {

    T guardar(T t) throws SQLException;

    T actualizarPorId(T t) throws SQLException;

    int eliminarPorId(int id) throws SQLException;

    T obtenerPorId(int id) throws SQLException;
}
