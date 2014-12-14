package org.sidhu.utility.imagehotspot.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.sidhu.utility.imagehotspot.entity.KeywordList;
import org.sidhu.utility.imagehotspot.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository("userDao")
public class UserDaoImpl implements UserDao {

	@PersistenceContext
	private EntityManager em;

	@Override
	@SuppressWarnings("unchecked")
	public User findByUserName(String username) {
		List<User> users = new ArrayList<User>();

		users = em.createQuery("from User where username=:username").setParameter("username",username).getResultList();

		if (users.size() > 0) {
			return users.get(0);
		} else {
			return null;
		}
	}

	@Override
	public void save(User user) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));
		em.persist(user);		
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean userExists(String username) {
		Query q=em.createQuery("from User u where u.username = :username", User.class);
		q.setParameter("username", username);
		List<KeywordList> result = q.getResultList();
		if (result.isEmpty())
			return false;
		else
			return true;
	}
}
