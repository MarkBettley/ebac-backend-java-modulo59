package com.ebac.modulo59;

import com.ebac.modulo59.dto.Direccion;
import com.ebac.modulo59.model.DireccionModel;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Contexto {

    static Connection connection;

    public static void main(String[] args) throws SQLException {
        String url = "jdbc:mysql://localhost:3306/modulo59";
        String user = "ebac";
        String password = "ebac123";

        MysqlConnection mysqlConnection = new MysqlConnection();
        connection = mysqlConnection.getConnection(url, user, password);

        consultaConStatement();
        operacionConDirecciones();

        connection.close();
    }

    private static void consultaConStatement() throws SQLException {
        System.out.println("===== USUARIOS CON STATEMENT =====");

        String sql = "SELECT * FROM usuarios";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println(
                    "ID: " + resultSet.getInt("idUsuario")
                    + " | Nombre: " + resultSet.getString("nombre")
                    + " | Edad: " + resultSet.getInt("edad")
            );
        }

        resultSet.close();
        statement.close();
    }

    private static void operacionConDirecciones() throws SQLException {
        DireccionModel direccionModel = new DireccionModel(connection);

        System.out.println("\n===== GUARDAR DIRECCION =====");

        Direccion direccion = new Direccion();
        direccion.setIdUsuario(1);
        direccion.setCalle("Avenida Revolucion");
        direccion.setNumero(150);
        direccion.setEstado("Hidalgo");

        direccionModel.guardar(direccion);
        System.out.println("Direccion guardada correctamente");

        /*
         * Obtenemos el ID generado mediante una consulta para poder
         * continuar con las operaciones del ejercicio.
         */
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(
                "SELECT MAX(idDireccion) AS idDireccion FROM direcciones"
        );

        if (resultSet.next()) {
            direccion.setIdDireccion(resultSet.getInt("idDireccion"));
        }

        resultSet.close();
        statement.close();

        System.out.println("\n===== OBTENER DIRECCION =====");
        Direccion direccionObtenida =
                direccionModel.obtenerPorId(direccion.getIdDireccion());
        System.out.println(direccionObtenida);

        System.out.println("\n===== ACTUALIZAR DIRECCION =====");
        direccionObtenida.setCalle("Boulevard Felipe Angeles");
        direccionObtenida.setNumero(250);
        direccionObtenida.setEstado("Hidalgo");

        direccionModel.actualizarPorId(direccionObtenida);

        Direccion direccionActualizada =
                direccionModel.obtenerPorId(direccion.getIdDireccion());
        System.out.println(direccionActualizada);

        System.out.println("\n===== ELIMINAR DIRECCION =====");
        int eliminadas =
                direccionModel.eliminarPorId(direccion.getIdDireccion());

        System.out.println("Direcciones eliminadas: " + eliminadas);
    }
}
