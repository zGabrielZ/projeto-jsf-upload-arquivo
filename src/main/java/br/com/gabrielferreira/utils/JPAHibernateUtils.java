package br.com.gabrielferreira.utils;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@ApplicationScoped
public class JPAHibernateUtils implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private EntityManagerFactory emf;
	
	public JPAHibernateUtils() {
		emf = Persistence.createEntityManagerFactory("projeto-jsf-upload");
	}

	// Produzir esse entitymanager pra nao ser chamado manualmente
	@Produces
	@RequestScoped
	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}
}
