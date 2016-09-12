package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.CityService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.CityDTO;
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
 * REST controller for managing City.
 */
@RestController
@RequestMapping("/api")
public class CityResource {

    private final Logger log = LoggerFactory.getLogger(CityResource.class);
        
    @Inject
    private CityService cityService;

    /**
     * POST  /cities : Create a new city.
     *
     * @param cityDTO the cityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cityDTO, or with status 400 (Bad Request) if the city has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cities",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CityDTO> createCity(@RequestBody CityDTO cityDTO) throws URISyntaxException {
        log.debug("REST request to save City : {}", cityDTO);
        if (cityDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("city", "idexists", "A new city cannot already have an ID")).body(null);
        }
        CityDTO result = cityService.save(cityDTO);
        return ResponseEntity.created(new URI("/api/cities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("city", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cities : Updates an existing city.
     *
     * @param cityDTO the cityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cityDTO,
     * or with status 400 (Bad Request) if the cityDTO is not valid,
     * or with status 500 (Internal Server Error) if the cityDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/cities",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CityDTO> updateCity(@RequestBody CityDTO cityDTO) throws URISyntaxException {
        log.debug("REST request to update City : {}", cityDTO);
        if (cityDTO.getId() == null) {
            return createCity(cityDTO);
        }
        CityDTO result = cityService.save(cityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("city", cityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cities : get all the cities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of cities in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/cities",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CityDTO>> getAllCities(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Cities");
        Page<CityDTO> page = cityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cities/:id : get the "id" city.
     *
     * @param id the id of the cityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cityDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/cities/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CityDTO> getCity(@PathVariable Long id) {
        log.debug("REST request to get City : {}", id);
        CityDTO cityDTO = cityService.findOne(id);
        return Optional.ofNullable(cityDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cities/:id : delete the "id" city.
     *
     * @param id the id of the cityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/cities/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        log.debug("REST request to delete City : {}", id);
        cityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("city", id.toString())).build();
    }

}
