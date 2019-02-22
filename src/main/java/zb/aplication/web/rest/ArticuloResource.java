package zb.aplication.web.rest;
import zb.aplication.domain.Articulo;
import zb.aplication.repository.ArticuloRepository;
import zb.aplication.web.rest.errors.BadRequestAlertException;
import zb.aplication.web.rest.util.HeaderUtil;
import zb.aplication.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Articulo.
 */
@RestController
@RequestMapping("/api")
public class ArticuloResource {

    private final Logger log = LoggerFactory.getLogger(ArticuloResource.class);

    private static final String ENTITY_NAME = "articulo";

    private final ArticuloRepository articuloRepository;

    public ArticuloResource(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    /**
     * POST  /articulos : Create a new articulo.
     *
     * @param articulo the articulo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new articulo, or with status 400 (Bad Request) if the articulo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/articulos")
    public ResponseEntity<Articulo> createArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to save Articulo : {}", articulo);
        if (articulo.getId() != null) {
            throw new BadRequestAlertException("A new articulo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.created(new URI("/api/articulos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /articulos : Updates an existing articulo.
     *
     * @param articulo the articulo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated articulo,
     * or with status 400 (Bad Request) if the articulo is not valid,
     * or with status 500 (Internal Server Error) if the articulo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/articulos")
    public ResponseEntity<Articulo> updateArticulo(@RequestBody Articulo articulo) throws URISyntaxException {
        log.debug("REST request to update Articulo : {}", articulo);
        if (articulo.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Articulo result = articuloRepository.save(articulo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, articulo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /articulos : get all the articulos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of articulos in body
     */
    @GetMapping("/articulos")
    public ResponseEntity<List<Articulo>> getAllArticulos(Pageable pageable) {
        log.debug("REST request to get a page of Articulos");
        Page<Articulo> page = articuloRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/articulos");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /articulos/:id : get the "id" articulo.
     *
     * @param id the id of the articulo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the articulo, or with status 404 (Not Found)
     */
    @GetMapping("/articulos/{id}")
    public ResponseEntity<Articulo> getArticulo(@PathVariable Long id) {
        log.debug("REST request to get Articulo : {}", id);
        Optional<Articulo> articulo = articuloRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(articulo);
    }

    /**
     * DELETE  /articulos/:id : delete the "id" articulo.
     *
     * @param id the id of the articulo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/articulos/{id}")
    public ResponseEntity<Void> deleteArticulo(@PathVariable Long id) {
        log.debug("REST request to delete Articulo : {}", id);
        articuloRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
