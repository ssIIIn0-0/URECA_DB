package com.mycom.myapp;

import java.util.HashMap;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.mycom.myapp.config.MyPersistenceUnitInfo;
import com.mycom.myapp.entity.Product;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Test {

	public static void main(String[] args) {

		// xml 대신 자바 설정 객체
		EntityManagerFactory emf = new HibernatePersistenceProvider()
				.createContainerEntityManagerFactory(new MyPersistenceUnitInfo(), new HashMap<>());
		EntityManager em = emf.createEntityManager();
		
		// persistence context
		// em.getTransaction().begin() -- 작업 -- ....commit(), close()

		try {
			em.getTransaction().begin();
			Product p = new Product();
			p.setId(1L);
			p.setName("Phone");

			// Entity 객체가 persistence context 안으로 들어온다.
			em.persist(p);

			em.getTransaction().commit();
		} finally {
			em.close();
		}

	}

}