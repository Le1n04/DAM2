package com.ejemplo.pruebas;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.ejemplo.modelo.Cantante;
import com.ejemplo.util.HibernateUtil;

public class TestCrear {
    public static void main(String[] args) {
        // 1) Objeto a persistir
        Cantante nuevo = new Cantante(1, "The Beatles", 1960, "Abbey Road");
        
        // 2) Obtener sesión
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            // 3) Iniciar transacción
            tx = session.beginTransaction();
            
            // 4) Guardar objeto
            session.persist(nuevo);
            
            // 5) Confirmar
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            // 6) Cerrar sesión
            session.close();
        }
        
        HibernateUtil.getSessionFactory().close();
    }
}
