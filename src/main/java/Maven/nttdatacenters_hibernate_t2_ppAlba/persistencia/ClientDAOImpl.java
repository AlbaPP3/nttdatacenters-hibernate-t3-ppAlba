package Maven.nttdatacenters_hibernate_t2_ppAlba.persistencia;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Implementación DAO de la entidad/tabla Cliente
 * 
 * @author Alba
 *
 */
public class ClientDAOImpl extends CommonDAOImpl<Client> implements ClientDAOI{		
	
	private static final Logger LOG = LoggerFactory.getLogger(ClientDAOImpl.class);
	
	/**Sesión de conexión a la BBDD*/
	private Session session;
	
	/**Constructor*/
	public ClientDAOImpl(Session session) {
		super(session);
		this.session = session;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Client> searchByName(final String name) {
		
		LOG.debug("Busqueda de cliente por Nombre");
		
		/**Verificacion de que la sesion está abierta*/
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		/**Busca los clientes por el nombre*/
		final List<Client> clientsList = session.createQuery("FROM Client WHERE name=" + name).list();
		return clientsList;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Client> searchBySurName(final String firstSurName, final String secondSurName){
		
		LOG.debug("Busqueda de cliente por Apellidos");
		
		/**Verificacion de que la sesion está abierta*/
		if (!session.getTransaction().isActive()) {
			session.getTransaction().begin();
		}
		/**Busca los clientes por los apellidos*/
		final List<Client> results = session.createQuery("FROM Customer WHERE firstLastName='"+firstSurName +"'AND secondLastName='"+ secondSurName+"'").list();
		return results;
}
	
	@Override
	public List<Client> searchByNameAndContract(final String name, final Contract contract ){
		final CriteriaBuilder cb = session.getCriteriaBuilder();
		final CriteriaQuery<Client> cquery = cb.createQuery(Client.class);
		final Root<Client> rootP = cquery.from(Client.class);
		/*final Join<Client, Contract> pJoinT = rootP.join("contract");*/
		
		final Predicate pr1 = cb.like(rootP.<String> get("name"), name);
		/*final Predicate pr2 = cb.equal(pJoinT.getParent().<String> get("contract"), contract);*/
		
		cquery.select(rootP).where(pr1);
		/*cquery.orderBy(cb.desc(pJoinT.getParent().get("contract")));*/
		final List<Client> results = session.createQuery(cquery).getResultList();
		return results;
	}
	}
	



