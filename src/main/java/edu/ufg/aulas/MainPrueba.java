package edu.ufg.aulas;

import edu.ufg.aulas.entidades.Usuario;
import edu.ufg.aulas.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MainPrueba {
    public static void main(String[] args) {
        System.out.println("Iniciando prueba de conexión con Hibernate...");

        Transaction transaccion = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            
            transaccion = session.beginTransaction();

            // Creamos un usuario de prueba
            Usuario usuarioPrueba = new Usuario("Líder de Proyecto", "lider@ufg.edu.sv", "12345");

            // Guardamos el objeto (Hibernate creará la tabla automáticamente)
            session.persist(usuarioPrueba);

            transaccion.commit();
            System.out.println("¡Éxito! Usuario guardado en MySQL.");

        } catch (Exception e) {
            if (transaccion != null) {
                transaccion.rollback();
            }
            System.err.println("Error al intentar guardar en la base de datos:");
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }
}
