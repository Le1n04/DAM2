package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("deprecation")
public class CantanteDAO {

    private SessionFactory sessionFactory;

    public CantanteDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

	public void altaCantante(Cantante cantante) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(cantante);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<Cantante> obtenerTodos() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Cantante", Cantante.class).list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Cantante obtenerPorCodigo(int codigo) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Cantante.class, codigo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void actualizarCantante(Cantante cantante) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.update(cantante);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void borrarCantante(Cantante cantante) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(cantante);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
}
