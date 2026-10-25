package edu.ufg.aulas.controladores;

import edu.ufg.aulas.entidades.Usuario;
import edu.ufg.aulas.util.HibernateUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class UsuarioController {

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtCorreo;

    @FXML
    private PasswordField txtClave;

    @FXML
    private void guardarUsuario() {
        String nombre = txtNombre.getText();
        String correo = txtCorreo.getText();
        String clave = txtClave.getText();

        if (nombre.isEmpty() || correo.isEmpty() || clave.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, llena todos los campos.");
            return;
        }

        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaccion = session.beginTransaction();
            
            Usuario nuevoUsuario = new Usuario(nombre, correo, clave);
            session.persist(nuevoUsuario);
            
            transaccion.commit();
            mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario registrado correctamente.");
            
            txtNombre.clear();
            txtCorreo.clear();
            txtClave.clear();

        } catch (Exception e) {
            if (transaccion != null) transaccion.rollback();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Fallo al guardar: " + e.getMessage());
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}