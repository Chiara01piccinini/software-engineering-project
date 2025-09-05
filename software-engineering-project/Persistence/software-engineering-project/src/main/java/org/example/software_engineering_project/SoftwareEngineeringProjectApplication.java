package org.example.software_engineering_project;

import org.example.software_engineering_project.controls.*;
import org.example.software_engineering_project.model.*;
import org.example.software_engineering_project.model.JsonPersistence.PersistenceManager;
import org.example.software_engineering_project.view.AuthService;
import org.example.software_engineering_project.view.EmailSystem;
import org.example.software_engineering_project.view.SistemaSponsorizzazioni;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class SoftwareEngineeringProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoftwareEngineeringProjectApplication.class, args);
	}

	// ---------------------- Bean dei servizi ----------------------
	@Bean
	public Marketplace marketplace() {
		return new Marketplace();
	}

	@Bean
	@Scope("prototype") // Ogni sessione è indipendente
	public Session session() {
		return new Session();
	}

	@Bean
	public Curatore curatore(Marketplace marketplace, Session session, EmailSystem emailSystem) {
		return new Curatore(
				new Account("curatore1", "pass", tipoAccount.CURATORE),
				1,
				"mrzpccnn@gmail.com",
				emailSystem,
				marketplace,
				session
		);
	}

	@Bean
	public EmailSystem emailSystem() {
		return new EmailSystem();
	}

	@Bean
	public SistemaSponsorizzazioni sistemaSponsorizzazioni() {
		return new SistemaSponsorizzazioni();
	}

	@Bean
	public GestorePubblicazioni gestorePubblicazioni(Curatore curatore, EmailSystem emailSystem) {
		return new GestorePubblicazioni(curatore, emailSystem);
	}

	@Bean
	public GestoreCreazioni gestoreCreazioni(GestorePubblicazioni gestorePubblicazioni,
											 Curatore curatore,
											 Marketplace marketplace) {
		GestoreCreazioni gc = new GestoreCreazioni(gestorePubblicazioni, curatore, marketplace);
		gestorePubblicazioni.setGestoreCreazioni(gc);
		return gc;
	}

	@Bean
	public GestoreSponsorizzazioni gestoreSponsorizzazioni(SistemaSponsorizzazioni sistemaSponsorizzazioni,
														   EmailSystem emailSystem,
														   Marketplace marketplace) {
		return new GestoreSponsorizzazioni(sistemaSponsorizzazioni, emailSystem, marketplace);
	}

	@Bean
	public AuthService authService(List<AuthHandler> handlers, Session session, Marketplace marketplace) {
		return new AuthService(handlers, session, marketplace);
	}

	// ---------------------- Runner per test e inizializzazione ----------------------
	@Bean
	public CommandLineRunner startupRunner(
			GestoreCreazioni gestoreCreazioni,
			GestorePubblicazioni gestorePubblicazioni,
			PersistenceManager persistenceManager,
			Marketplace marketplace,
			Session session,
			AuthService authService
	) {
		return args -> {
			System.out.println(">>> Avvio Spring Boot Marketplace");

			// Caricamento dati
			try {
				persistenceManager.carica();
			} catch (IOException e) {
				e.printStackTrace();
			}

			// Esegui test
			TestRunner testRunner = new TestRunner(
					gestoreCreazioni,
					gestorePubblicazioni,
					marketplace,
					persistenceManager,
					session,
					authService
			);
			//testRunner.testCreazioneAccounteautenticazione();
			//testRunner.testCreazioneContenuti();
			//testRunner.testAcquisto();
			//testRunner.testPrenotazioneEvento();
			//testRunner.testSponsorizzazioneProdotto();

			// Salvataggio dati
			try {
				persistenceManager.salva();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
			System.out.println(">>> Fine avvio Spring Boot Marketplace");
		};
	}
}
