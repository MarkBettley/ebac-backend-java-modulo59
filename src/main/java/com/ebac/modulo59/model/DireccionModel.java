package com.ebac.modulo59.model;

import com.ebac.modulo59.dto.Direccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DireccionModel implements OperacionesCRUD<Direccion> {

    private final Connection connection;

    public DireccionModel(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Direccion guardar(Direccion direccion) throws SQLException {
        String sql = "INSERT INTO direcciones(idUsuario, calle, numero, estado) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, direccion.getIdUsuario());
        statement.setString(2, direccion.getCalle());
        statement.setInt(3, direccion.getNumero());
        statement.setString(4, direccion.getEstado());

        int elementosInsertados = statement.executeUpdate();

        if (elementosInsertados == 1) {
            return direccion;
        }

        throw new SQLException("Algo salio mal al ejecutar el insert");
    }

    @Override
    public Direccion actualizarPorId(Direccion direccion) throws SQLException {
        String sql = "UPDATE direcciones SET idUsuario = ?, calle = ?, numero = ?, estado = ? WHERE idDireccion = ?";
        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, direccion.getIdUsuario());
        statement.setString(2, direccion.getCalle());
        statement.setInt(3, direccion.getNumero());
        statement.setString(4, direccion.getEstado());
        statement.setInt(5, direccion.getIdDireccion());

        int elementosActualizados = statement.executeUpdate();

        if (elementosActualizados == 1) {
            return direccion;
        }

        throw new SQLException("Algo salio mal al ejecutar el update");
    }

    @Override
    public int eliminarPorId(int id) throws SQLException {
        String sql = "DELETE FROM direcciones WHERE idDireccion = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        return statement.executeUpdate();
    }

    @Override
    public Direccion obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM direcciones WHERE idDireccion = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);

        ResultSet resultSet = statement.executeQuery();

        Direccion direccion = new Direccion();

        while (resultSet.next()) {
            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
            direccion.setIdUsuario(resultSet.getInt("idUsuario"));
            direccion.setCalle(resultSet.getString("calle"));
            direccion.setNumero(resultSet.getInt("numero"));
            direccion.setEstado(resultSet.getString("estado"));
        }

        return direccion;
    }
}
