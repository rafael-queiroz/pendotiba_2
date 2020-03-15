package br.com.logic.pendotiba.logicbus.service;


import br.com.logic.pendotiba.logicbus.domain.response.ProgramacaoStatusResponse;
import feign.Client;
import feign.Feign;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImportacaoProgramacaoService {
/*
	public ProgramacaoStatusResponse getProgramacaoStatusResponse(){

		try {
			return Feign.builder()
					.client(new Client.Default(new BlindSSLSocketFactory().buildSocketFactory(),
							new NoopHostnameVerifier()))
					.logger(feignClientLoggingHandler)
					.logLevel(Logger.Level.FULL)
					.encoder(new JacksonEncoder())
					.decoder(new JacksonDecoder())
					.target(OAuthClient.class, gatewayUrl)
					.getOAuthToken(oauthTokenGrantType, generateEncodedAuthorizationString(oauthType, keyPair));

		} catch (FeignException ex) {
			log.error(ex.getMessage(), ex.getCause());

			if (HttpStatus.UNAUTHORIZED.value() == ex.status()){
				throw GenericApiException
						.builder()
						.statusCode(HttpStatus.UNAUTHORIZED)
						.reason("The given client credentials were not valid.")
						.build();
			}

			throw GenericApiException
					.builder()
					.statusCode(HttpStatus.BAD_GATEWAY)
					.reason("Error calling OAuth external service.")
					.build();
		} catch (NoSuchAlgorithmException e) {
			throw GenericApiException
					.builder()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
					.reason("Error while building SSL socket factory for client certificate validation (NoSuchAlgorithm)")
					.build();
		} catch (KeyManagementException e) {
			throw GenericApiException
					.builder()
					.statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
					.reason("Error while building SSL socket factory for client certificate validation (KeyManagement)")
					.build();
		}
	}


 */
}