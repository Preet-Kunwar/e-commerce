package com.learn.electronic.store;


import java.util.List;
import java.util.UUID;

import com.learn.electronic.store.config.AppConstant;
import com.learn.electronic.store.entities.Role;
import com.learn.electronic.store.entities.User;
import com.learn.electronic.store.repositories.RoleRepository;
import com.learn.electronic.store.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication
@EnableWebMvc
public class ElectronicStoreApplication implements CommandLineRunner  {



	@Autowired
private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ElectronicStoreApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {



		Role roleAdmin=roleRepository.findByName(AppConstant.ROLE_ADMIN).orElse(null);

		System.out.println(roleAdmin);

		if (roleAdmin==null){
			Role role1=new Role();
			role1.setRoleId(UUID.randomUUID().toString());
			role1.setName(AppConstant.ROLE_ADMIN);
			roleAdmin=role1;
			roleRepository.save(role1);
		}

		Role roleNormal=roleRepository.findByName(AppConstant.ROLE_NORMAL).orElse(null);

		if (roleNormal==null){
			Role role2=new Role();
			role2.setRoleId(UUID.randomUUID().toString());
			role2.setName(AppConstant.ROLE_NORMAL);
			roleNormal=role2;
			roleRepository.save(role2);
		}


		// one Admin user
		User user0 = userRepository.findByEmail("preet@gmail.com").orElse(null);
		if (user0==null){
			User user=new User();
			user.setName("Preet");
			user.setEmail("preet@gmail.com");
			user.setPassword(passwordEncoder.encode("preet123"));
			user.setRoles(List.of(roleAdmin));
			user.setUserId(UUID.randomUUID().toString());

			userRepository.save(user);

		}

	}
}
