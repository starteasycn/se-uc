package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.CountyService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.CountyDTO;
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
 * REST controller for managing County.
 */
@RestController
@RequestMapping("/api")
public class CountyResource {

    private final Logger log = LoggerFactory.getLogger(CountyResource.class);
        
    @Inject
    private CountyService countyService;

    /**
     * POST  /counties : Create a new county.
     *
     * @param countyDTO the countyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new countyDTO, or with status 400 (Bad Request) if the county has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/counties",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountyDTO> createCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to save County : {}", countyDTO);
        if (countyDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("county", "idexists", "A new county cannot already have an ID")).body(null);
        }
        CountyDTO result = countyService.save(countyDTO);
        return ResponseEntity.created(new URI("/api/counties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("county", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /counties : Updates an existing county.
     *
     * @param countyDTO the countyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated countyDTO,
     * or with status 400 (Bad Request) if the countyDTO is not valid,
     * or with status 500 (Internal Server Error) if the countyDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/counties",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountyDTO> updateCounty(@RequestBody CountyDTO countyDTO) throws URISyntaxException {
        log.debug("REST request to update County : {}", countyDTO);
        if (countyDTO.getId() == null) {
            return createCounty(countyDTO);
        }
        CountyDTO result = countyService.save(countyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("county", countyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /counties : get all the counties.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of counties in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/counties",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CountyDTO>> getAllCounties(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Counties");
        Page<CountyDTO> page = countyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/counties");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /counties/:id : get the "id" county.
     *
     * @param id the id of the countyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the countyDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/counties/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CountyDTO> getCounty(@PathVariable Long id) {
        log.debug("REST request to get County : {}", id);
        CountyDTO countyDTO = countyService.findOne(id);
        return Optional.ofNullable(countyDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /counties/:id : delete the "id" county.
     *
     * @param id the id of the countyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/counties/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCounty(@PathVariable Long id) {
        log.debug("REST request to delete County : {}", id);
        countyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("county", id.toString())).build();
    }

}
