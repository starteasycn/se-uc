package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.ClientDetailsService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.ClientDetailsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing ClientDetails.
 */
@RestController
@RequestMapping("/api")
public class ClientDetailsResource {

    private final Logger log = LoggerFactory.getLogger(ClientDetailsResource.class);
        
    @Inject
    private ClientDetailsService clientDetailsService;

    /**
     * POST  /client-details : Create a new clientDetails.
     *
     * @param clientDetailsDTO the clientDetailsDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new clientDetailsDTO, or with status 400 (Bad Request) if the clientDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/client-details",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientDetailsDTO> createClientDetails(@RequestBody ClientDetailsDTO clientDetailsDTO) throws URISyntaxException {
        log.debug("REST request to save ClientDetails : {}", clientDetailsDTO);
        if (clientDetailsDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("clientDetails", "idexists", "A new clientDetails cannot already have an ID")).body(null);
        }
        ClientDetailsDTO result = clientDetailsService.save(clientDetailsDTO);
        return ResponseEntity.created(new URI("/api/client-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("clientDetails", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /client-details : Updates an existing clientDetails.
     *
     * @param clientDetailsDTO the clientDetailsDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated clientDetailsDTO,
     * or with status 400 (Bad Request) if the clientDetailsDTO is not valid,
     * or with status 500 (Internal Server Error) if the clientDetailsDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/client-details",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientDetailsDTO> updateClientDetails(@RequestBody ClientDetailsDTO clientDetailsDTO) throws URISyntaxException {
        log.debug("REST request to update ClientDetails : {}", clientDetailsDTO);
        if (clientDetailsDTO.getId() == null) {
            return createClientDetails(clientDetailsDTO);
        }
        ClientDetailsDTO result = clientDetailsService.save(clientDetailsDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("clientDetails", clientDetailsDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /client-details : get all the clientDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of clientDetails in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/client-details",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ClientDetailsDTO>> getAllClientDetails(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ClientDetails");
        Page<ClientDetailsDTO> page = clientDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/client-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /client-details/:id : get the "id" clientDetails.
     *
     * @param id the id of the clientDetailsDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the clientDetailsDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/client-details/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ClientDetailsDTO> getClientDetails(@PathVariable Long id) {
        log.debug("REST request to get ClientDetails : {}", id);
        ClientDetailsDTO clientDetailsDTO = clientDetailsService.findOne(id);
        return Optional.ofNullable(clientDetailsDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /client-details/:id : delete the "id" clientDetails.
     *
     * @param id the id of the clientDetailsDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/client-details/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteClientDetails(@PathVariable Long id) {
        log.debug("REST request to delete ClientDetails : {}", id);
        clientDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("clientDetails", id.toString())).build();
    }

}
