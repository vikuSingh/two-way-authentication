package com.vikas.twowayauthentication.repoistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vikas.twowayauthentication.model.User;

@Repository
public interface TwoWayUserRepository extends JpaRepository<User, Long> {

	public User findUserByEmail(String username);
}
