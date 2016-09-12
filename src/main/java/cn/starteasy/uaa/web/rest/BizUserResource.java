package cn.starteasy.uaa.web.rest;

import com.codahale.metrics.annotation.Timed;
import cn.starteasy.uaa.service.BizUserService;
import cn.starteasy.uaa.web.rest.util.HeaderUtil;
import cn.starteasy.uaa.web.rest.util.PaginationUtil;
import cn.starteasy.uaa.service.dto.BizUserDTO;
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
 * REST controller for managing BizUser.
 */
@RestController
@RequestMapping("/api")
public class BizUserResource {

    private final Logger log = LoggerFactory.getLogger(BizUserResource.class);
        
    @Inject
    private BizUserService bizUserService;

    /**
     * POST  /biz-users : Create a new bizUser.
     *
     * @param bizUserDTO the bizUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new bizUserDTO, or with status 400 (Bad Request) if the bizUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/biz-users",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BizUserDTO> createBizUser(@RequestBody BizUserDTO bizUserDTO) throws URISyntaxException {
        log.debug("REST request to save BizUser : {}", bizUserDTO);
        if (bizUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("bizUser", "idexists", "A new bizUser cannot already have an ID")).body(null);
        }
        BizUserDTO result = bizUserService.save(bizUserDTO);
        return ResponseEntity.created(new URI("/api/biz-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bizUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /biz-users : Updates an existing bizUser.
     *
     * @param bizUserDTO the bizUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated bizUserDTO,
     * or with status 400 (Bad Request) if the bizUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the bizUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/biz-users",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BizUserDTO> updateBizUser(@RequestBody BizUserDTO bizUserDTO) throws URISyntaxException {
        log.debug("REST request to update BizUser : {}", bizUserDTO);
        if (bizUserDTO.getId() == null) {
            return createBizUser(bizUserDTO);
        }
        BizUserDTO result = bizUserService.save(bizUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bizUser", bizUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /biz-users : get all the bizUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of bizUsers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @RequestMapping(value = "/biz-users",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BizUserDTO>> getAllBizUsers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of BizUsers");
        Page<BizUserDTO> page = bizUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/biz-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /biz-users/:id : get the "id" bizUser.
     *
     * @param id the id of the bizUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the bizUserDTO, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/biz-users/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BizUserDTO> getBizUser(@PathVariable Long id) {
        log.debug("REST request to get BizUser : {}", id);
        BizUserDTO bizUserDTO = bizUserService.findOne(id);
        return Optional.ofNullable(bizUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /biz-users/:id : delete the "id" bizUser.
     *
     * @param id the id of the bizUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/biz-users/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBizUser(@PathVariable Long id) {
        log.debug("REST request to delete BizUser : {}", id);
        bizUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bizUser", id.toString())).build();
    }

}
