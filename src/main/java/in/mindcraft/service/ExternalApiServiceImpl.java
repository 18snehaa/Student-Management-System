package in.mindcraft.service;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import in.mindcraft.dto.ExternalUserDto;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalApiServiceImpl.class);

    private final RestTemplate restTemplate; // rest template inject 

    @Value("${external.api.base-url}")
    private String externalApiBaseUrl;
 
    //constructor 
    public ExternalApiServiceImpl(RestTemplate restTemplate) 
    {
        this.restTemplate = restTemplate;
    }

    // TASK 1 - SIMPLE REST CALL
    
    @Override
    public ExternalUserDto getUserSimple() 
    {
        String url = externalApiBaseUrl + "/users/1";

        try {
            logger.info( "Calling external API: {}", url );

            ResponseEntity<ExternalUserDto> response = restTemplate.getForEntity(url, ExternalUserDto.class );
            logger.info("External API response status: {}", response.getStatusCode());

            return response.getBody();

        } catch (RestClientException e) {

            logger.error("Error while calling external API", e);

            throw new ResponseStatusException( HttpStatus.SERVICE_UNAVAILABLE, "External API is unavailable");
        }
    }

    // TASK 2 - REST CALL WITH HEADERS

    @Override
    public ExternalUserDto getUserWithHeaders() {

        String url = externalApiBaseUrl + "/users/1";

        try { HttpHeaders headers = new HttpHeaders();

            headers.setContentType( MediaType.APPLICATION_JSON );

            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON ));

            logger.info(
                    "Outgoing headers: Content-Type={}, Accept={}",
                    headers.getContentType(), headers.getAccept() );

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<ExternalUserDto> response = restTemplate.exchange(url, HttpMethod.GET, entity,ExternalUserDto.class);

            logger.info("External API response status: {}",response.getStatusCode());

            return response.getBody();

        } catch (RestClientException e) {

            logger.error( "Error while calling external API", e);

            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,"External API is unavailable" );
        }
    }

    // TASK 3 - REST CALL WITH JWT

    @Override
    public ExternalUserDto getUserWithJwt( String authorizationHeader)
    {
        if (authorizationHeader == null || authorizationHeader.isBlank()) {

            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Authorization header is required"
            );
        }

        String url = "http://localhost:8080/external-api/user";

        try {

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType( MediaType.APPLICATION_JSON );

            headers.setAccept( Arrays.asList( MediaType.APPLICATION_JSON ));

            // Forward JWT token
            headers.set( "Authorization",authorizationHeader);

            logger.info("Authorization header added to outgoing request");

            HttpEntity<Void> entity = new HttpEntity<>(headers);

            ResponseEntity<ExternalUserDto> response = restTemplate.exchange( url, HttpMethod.GET, entity, ExternalUserDto.class );

            logger.info("JWT external API response status: {}", response.getStatusCode());

            return response.getBody();

        } catch (HttpClientErrorException.Unauthorized e) {

            logger.error ("JWT is invalid or expired" );

            throw new ResponseStatusException( HttpStatus.UNAUTHORIZED, "Invalid or expired JWT token");
            
        } catch (RestClientException e)  {
            logger.error("Error while calling JWT external API", e);
            throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE, "External API is unavailable");
        }
    }
}