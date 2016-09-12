package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.DistrictService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.DistrictDTO;
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
 * REST controller for managing District.
 */
@RestController
@RequestMapping("/api")
public class DistrictResource {

    private final Logger log = LoggerFactory.getLogger(DistrictResource.class);
        
    @Inject
    private DistrictService districtService;

    /**
     * POST  /districts : Create a new district.
     *
     * @param districtDTO the districtDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new districtDTO, or with status 400 (Bad Request) if the district has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/districts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DistrictDTO> createDistrict(@RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to save District : {}", districtDTO);
        if (districtDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("district", "idexists", "A new district cannot already have an ID")).body(null);
        }
        DistrictDTO result = districtService.save(districtDTO);
        return ResponseEntity.created(new URI("/api/districts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("district", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /districts : Updates an existing district.
     *
     * @param districtDTO the districtDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated districtDTO,
     * or with status 400 (Bad Request) if the districtDTO is not valid,
     * or with status 500 (Internal Server Error) if the districtDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/districts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DistrictDTO> updateDistrict(@RequestBody DistrictDTO districtDTO) throws URISyntaxException {
        log.debug("REST request to update District : {}", districtDTO);
        if (districtDTO.getId() == null) {
            return createDistrict(districtDTO);
        }
        DistrictDTO result = districtService.save(districtDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("district", districtDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /districts : get all the districts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of districts in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/districts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DistrictDTO>> getAllDistricts(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Districts");
        Page<DistrictDTO> page = districtService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/districts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /districts/:id : get the "id" district.
     *
     * @param id the id of the districtDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the districtDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/districts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DistrictDTO> getDistrict(@PathVariable Long id) {
        log.debug("REST request to get District : {}", id);
        DistrictDTO districtDTO = districtService.findOne(id);
        return Optional.ofNullable(districtDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /districts/:id : delete the "id" district.
     *
     * @param id the id of the districtDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/districts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        log.debug("REST request to delete District : {}", id);
        districtService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("district", id.toString())).build();
    }

}
