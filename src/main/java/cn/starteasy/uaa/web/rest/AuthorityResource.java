package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.AuthorityService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.AuthorityDTO;
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
 * REST controller for managing Authority.
 */
@RestController
@RequestMapping("/api")
public class AuthorityResource {

    private final Logger log = LoggerFactory.getLogger(AuthorityResource.class);
        
    @Inject
    private AuthorityService authorityService;

    /**
     * POST  /authorities : Create a new authority.
     *
     * @param authorityDTO the authorityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new authorityDTO, or with status 400 (Bad Request) if the authority has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/authorities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorityDTO> createAuthority(@RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to save Authority : {}", authorityDTO);
        if (authorityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("authority", "idexists", "A new authority cannot already have an ID")).body(null);
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity.created(new URI("/api/authorities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("authority", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /authorities : Updates an existing authority.
     *
     * @param authorityDTO the authorityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated authorityDTO,
     * or with status 400 (Bad Request) if the authorityDTO is not valid,
     * or with status 500 (Internal Server Error) if the authorityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/authorities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorityDTO> updateAuthority(@RequestBody AuthorityDTO authorityDTO) throws URISyntaxException {
        log.debug("REST request to update Authority : {}", authorityDTO);
        if (authorityDTO.getId() == null) {
            return createAuthority(authorityDTO);
        }
        AuthorityDTO result = authorityService.save(authorityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("authority", authorityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /authorities : get all the authorities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of authorities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/authorities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AuthorityDTO>> getAllAuthorities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Authorities");
        Page<AuthorityDTO> page = authorityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/authorities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /authorities/:id : get the "id" authority.
     *
     * @param id the id of the authorityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the authorityDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/authorities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuthorityDTO> getAuthority(@PathVariable Long id) {
        log.debug("REST request to get Authority : {}", id);
        AuthorityDTO authorityDTO = authorityService.findOne(id);
        return Optional.ofNullable(authorityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /authorities/:id : delete the "id" authority.
     *
     * @param id the id of the authorityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/authorities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuthority(@PathVariable Long id) {
        log.debug("REST request to delete Authority : {}", id);
        authorityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("authority", id.toString())).build();
    }

}
