package com.probase.potzr.SmartBanking;

import com.github.ulisesbocchio.jar.resources.JarResourceLoader;
import com.probase.potzr.SmartBanking.models.bridge.BridgeFundsTransfer;
import com.probase.potzr.SmartBanking.models.core.Client;
import com.probase.potzr.SmartBanking.models.core.ClientSetting;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.SpringVersion;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan(basePackages = { "com.probase.potzr.SmartBanking" })
@EntityScan(basePackageClasses = {Client.class, ClientSetting.class, BridgeFundsTransfer.class})
@EnableJpaRepositories//("com.probase.potzr.SmartBanking.repositories")
public class SmartBankingApplication implements ApplicationContextAware {

	private static ApplicationContext context;

	public static void main(String[] args) {
		StandardEnvironment environment = new StandardEnvironment();
		System.out.println(SpringVersion.getVersion());
		new SpringApplicationBuilder()
				.sources(SmartBankingApplication.class)
				.environment(environment)
				.resourceLoader(new JarResourceLoader(environment, "resources.extract.dir"))
				.build()
				.run(args);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;

	}

	public static ApplicationContext getContext() {
		return context;
	}
}
