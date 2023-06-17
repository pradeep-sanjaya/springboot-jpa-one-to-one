package com.cake.jointable;

import com.cake.jointable.entity.Address;
import com.cake.jointable.entity.User;
import com.cake.jointable.repository.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan
public class JointableApplication implements ApplicationRunner {

	@Autowired
	private final SessionFactory sessionFactory;

	public JointableApplication(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public static void main(String[] args) {
		SpringApplication.run(JointableApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		long startTime, endTime, durationMillis;

		try (Session session = sessionFactory.openSession()) {

			startTime = System.currentTimeMillis();

			// read 1,000,000
			for (int i = 1; i <= 10000; i++) {
				User user = session.get(User.class, RandomUtils.nextInt(0, 1000000));
				//System.out.printf("user" + user.toString());
			}

			logTime(startTime, "Total time taken for 10K read:");


			// save 100
			startTime = System.currentTimeMillis();

			for (int i = 1; i <= 10000; i++) {

				Address address = new Address();
				address.setAddress(RandomStringUtils.random(10, true, false));

				User user = new User();
				user.setFirstname(RandomStringUtils.random(10, true, false));
				user.setAddress(address);

				session.save(user);
			}

			logTime(startTime, "Total time taken for 10K write:");
		}
	}

	public static void  logTime(long startTime, String message) {
		long endTime, durationMillis;
		endTime = System.currentTimeMillis();
		durationMillis = endTime - startTime;
		System.out.println(message + durationMillis + " milliseconds");
	}
}
