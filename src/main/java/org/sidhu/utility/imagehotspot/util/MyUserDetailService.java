package org.sidhu.utility.imagehotspot.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sidhu.utility.imagehotspot.dao.UserDao;
import org.sidhu.utility.imagehotspot.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
public class MyUserDetailService implements UserDetailsService {
 
	//get user from the database, via Hibernate
	@Autowired
	private UserDao userDao;
 
	@Transactional(readOnly=true)
	@Override
	public UserDetails loadUserByUsername(final String username) 
		throws UsernameNotFoundException {
 
		org.sidhu.utility.imagehotspot.entity.User user = userDao.findByUserName(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRole());
 
		return buildUserForAuthentication(user, authorities);
 
	}
 
	/**
	 * Converts org.sidhu.utility.imagehotspot.entity.User user to org.springframework.security.core.userdetails.User
	 * @param user User Object.
	 * @param authorities List of GrantedAuthority Objects.
	 * @return User Object.
	 */
	private User buildUserForAuthentication(org.sidhu.utility.imagehotspot.entity.User user, List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), 
			user.isEnabled(), true, true, true, authorities);
	}
 
	/**
	 * Build user's authorities.
	 * @param userRoles Set of UserRole Objects
	 * @return List of GrantedAuthority Objects.
	 */
	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {
 
		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
 
		for (UserRole userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
		}
 
		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
 
		return Result;
	}
 
}