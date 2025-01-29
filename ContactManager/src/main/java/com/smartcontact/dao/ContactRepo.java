package com.smartcontact.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.smartcontact.entities.Contact;
import com.smartcontact.entities.User;

public interface ContactRepo extends JpaRepository<Contact, Integer> {
	
//pageable will have two variable for current page and contact per page
	
	@Query("from Contact as c where c.user.uid=:id")
	public Page<Contact> findByUserId(@Param("id") int id ,Pageable pageable );

	public List<Contact> findByNameContainingAndUser(String name,User user);
}
