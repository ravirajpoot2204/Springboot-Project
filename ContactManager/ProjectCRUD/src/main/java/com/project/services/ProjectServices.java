package com.project.services;

import java.util.List;
import javax.transaction.Transactional;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;
import com.project.entity.ProjectEntity;

@Component
public class ProjectServices {
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/* Create Product */
	@Transactional
	public void addProduct(ProjectEntity product) {
		this.hibernateTemplate.saveOrUpdate(product);
		System.out.println("new product added");
	}

	/* Get All Product */
	public List<ProjectEntity> getAllProducts(){
	    List<ProjectEntity> list = this.hibernateTemplate.loadAll(ProjectEntity.class);
	    System.out.println("Selected All Product: " + list.size());
	    return list;
	}

	/* Delete Product */
	@Transactional
	public void deleteProduct(int productID) {
		ProjectEntity p = this.hibernateTemplate.load(ProjectEntity.class, productID);
		this.hibernateTemplate.delete(p);
		System.out.println("product Deleted");

	}
	/* get one product */
	public ProjectEntity getSingleProduct(int productID) {
		ProjectEntity p = this.hibernateTemplate.load(ProjectEntity.class, productID);
		return p;
	}
	@PreDestroy
	public void destroy() {
	    if (hibernateTemplate != null) {
	        hibernateTemplate.getSessionFactory().close();
	    }
	}
}
