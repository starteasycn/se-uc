package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.ProvinceService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.ProvinceDTO;
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
 * REST controller for managing Province.
 */
@RestController
@RequestMapping("/api")
public class ProvinceResource {

    private final Logger log = LoggerFactory.getLogger(ProvinceResource.class);
        
    @Inject
    private ProvinceService provinceService;

    /**
     * POST  /provinces : Create a new province.
     *
     * @param provinceDTO the provinceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new provinceDTO, or with status 400 (Bad Request) if the province has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/provinces",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvinceDTO> createProvince(@RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        log.debug("REST request to save Province : {}", provinceDTO);
        if (provinceDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("province", "idexists", "A new province cannot already have an ID")).body(null);
        }
        ProvinceDTO result = provinceService.save(provinceDTO);
        return ResponseEntity.created(new URI("/api/provinces/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("province", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /provinces : Updates an existing province.
     *
     * @param provinceDTO the provinceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated provinceDTO,
     * or with status 400 (Bad Request) if the provinceDTO is not valid,
     * or with status 500 (Internal Server Error) if the provinceDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/provinces",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvinceDTO> updateProvince(@RequestBody ProvinceDTO provinceDTO) throws URISyntaxException {
        log.debug("REST request to update Province : {}", provinceDTO);
        if (provinceDTO.getId() == null) {
            return createProvince(provinceDTO);
        }
        ProvinceDTO result = provinceService.save(provinceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("province", provinceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /provinces : get all the provinces.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of provinces in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/provinces",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProvinceDTO>> getAllProvinces(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Provinces");
        Page<ProvinceDTO> page = provinceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/provinces");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /provinces/:id : get the "id" province.
     *
     * @param id the id of the provinceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the provinceDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/provinces/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProvinceDTO> getProvince(@PathVariable Long id) {
        log.debug("REST request to get Province : {}", id);
        ProvinceDTO provinceDTO = provinceService.findOne(id);
        return Optional.ofNullable(provinceDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /provinces/:id : delete the "id" province.
     *
     * @param id the id of the provinceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/provinces/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProvince(@PathVariable Long id) {
        log.debug("REST request to delete Province : {}", id);
        provinceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("province", id.toString())).build();
    }

}
