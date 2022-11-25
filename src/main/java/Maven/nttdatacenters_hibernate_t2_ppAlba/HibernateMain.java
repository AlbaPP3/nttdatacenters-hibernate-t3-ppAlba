package Maven.nttdatacenters_hibernate_t2_ppAlba;


import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Maven.nttdatacenters_hibernate_t2_ppAlba.persistencia.Client;
import Maven.nttdatacenters_hibernate_t2_ppAlba.persistencia.Contract;
import Maven.nttdatacenters_hibernate_t2_ppAlba.servicios.ClientManagementServiceI;
import Maven.nttdatacenters_hibernate_t2_ppAlba.servicios.ClientManagementServiceImpl;
import Maven.nttdatacenters_hibernate_t2_ppAlba.servicios.ContractManagementServiceI;
import Maven.nttdatacenters_hibernate_t2_ppAlba.servicios.ContractManagementServiceImpl;

/**
 * 
 * @author Alba
 *
 */
public class HibernateMain {
	
	private static final Logger LOG = LoggerFactory.getLogger(HibernateMain.class);
		
	public static void main( String[] args ) {	
		
		Session session ;
		Configuration con = new Configuration();
		SessionFactory sf = con.configure().buildSessionFactory();
		
		LOG.info("Configuración de la sesión generada correctamente");
		
		session = sf.openSession();
		
		LOG.info("Sesión abierta correctamente");
		
		final ClientManagementServiceI clientService = new ClientManagementServiceImpl(session);
		final ContractManagementServiceI contractService = new ContractManagementServiceImpl(session);
		
		final StringBuilder busqueda = new StringBuilder();
		
		/*Inserción del usuario que está utilizando la BBDD y la fecha en lo que lo hace*/
		final String updateUser = "ALBA";
		final Date updateDate = new Date();
		
		/*Inserción de los datos del contrato 1*/
		final Contract contrato1 = new Contract();
		contrato1.setPrice("Rango1");
		contrato1.setUpdatedDate(updateDate);
		contrato1.setCreatedUser(updateUser);
		contrato1.setExpiration(new Date(16/05/2023));
		contrato1.setValidity(updateDate);
		
		LOG.debug("Generación de contrato1");
		
		/*Inserción de los datos del contrato 2*/
		final Contract contrato2 = new Contract();
		contrato2.setPrice("Rango2");
		contrato2.setUpdatedDate(updateDate);
		contrato2.setCreatedUser(updateUser);
		
		LOG.debug("Generación de contrato2");
		
		/*Inserción de datos del cliente 1*/
		final Client cliente1 = new Client();
		cliente1.setClientDni("7865425Z");
		cliente1.setName("Alejandro");
		cliente1.setFirstSurName("Tellez");
		cliente1.setSecondSurName("Rubio");
		cliente1.setUpdatedDate(updateDate);
		cliente1.setCreatedUser(updateUser);
		contrato1.setClient(cliente1);
		
		LOG.debug("Generación de cliente1");
		/*Inserción en la BBDD del cliente 1*/
		clientService.insertNewClient(cliente1);
		
		LOG.debug("Inserción de cliente1");
		
		/*Inserción de datos del cliente 2*/
		final Client cliente2 = new Client();
		cliente2.setClientDni("78567945Z");
		cliente2.setName("Manuel");
		cliente2.setFirstSurName("Gutierrez");
		cliente2.setSecondSurName("Moreno");
		cliente2.setUpdatedDate(updateDate);
		cliente2.setCreatedUser(updateUser);
		contrato2.setClient(cliente2);
		
		LOG.debug("Generación de cliente2");
		
		/*Inserción en la BBDD del cliente 2*/
		clientService.insertNewClient(cliente2);
		
		LOG.debug("Inserción de cliente2");
		
		/*Inserción de los contratos en la BBDD*/
		/*contractService.insertNewContract(contrato1);
		contractService.insertNewContract(contrato2);*/
		
		LOG.debug("Inserción de contratos");
		
		/*Busqueda de cliente por ID*/
		Client searchById = clientService.searchById(1L);
		busqueda.append(searchById.getName());
		busqueda.append(searchById.getFirstSurName());
		busqueda.append(searchById.getClientDni());

		
		/*Busqueda de cliente por Nombre*/
		List<Client> searchByName = clientService.searchByName("Alejandro");
		for(Client client : searchByName) {
			busqueda.append(client.getClientID());
			busqueda.append(client.getClientDni());
			busqueda.append(client.getFirstSurName());
			busqueda.append(client.getSecondSurName());
		}
		
		/*Busqueda de todos los contratos*/
		List<Contract> searchAll = contractService.searchAll();
		for(Contract contract : searchAll) {
			busqueda.append(contract.getClient());
			busqueda.append(contract.getContractID());
			
		}
		
		
		
		/**Consultas JPA*/
		
		/*Busqueda por nombre de cliente y contrato*/
		List<Client> results = clientService.searchByNameAndContract("Manuel", contrato2);
		for(final Client client  : results) {
			System.out.println (client.getName()+ " " + client.getContract());
		}
		
		session.close();
		LOG.info("Sesión cerrada correctamente");
		
		
	}
}
