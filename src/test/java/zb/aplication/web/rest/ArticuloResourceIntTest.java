package zb.aplication.web.rest;

import zb.aplication.PruebaAplicationApp;

import zb.aplication.domain.Articulo;
import zb.aplication.repository.ArticuloRepository;
import zb.aplication.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static zb.aplication.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ArticuloResource REST controller.
 *
 * @see ArticuloResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PruebaAplicationApp.class)
public class ArticuloResourceIntTest {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_UBICACION = "AAAAAAAAAA";
    private static final String UPDATED_UBICACION = "BBBBBBBBBB";

    private static final String DEFAULT_UNIDAD = "AAAAAAAAAA";
    private static final String UPDATED_UNIDAD = "BBBBBBBBBB";

    private static final Integer DEFAULT_VALOR_UNIDAD = 1;
    private static final Integer UPDATED_VALOR_UNIDAD = 2;

    private static final String DEFAULT_ID_PROVEEDOR = "AAAAAAAAAA";
    private static final String UPDATED_ID_PROVEEDOR = "BBBBBBBBBB";

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restArticuloMockMvc;

    private Articulo articulo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ArticuloResource articuloResource = new ArticuloResource(articuloRepository);
        this.restArticuloMockMvc = MockMvcBuilders.standaloneSetup(articuloResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Articulo createEntity(EntityManager em) {
        Articulo articulo = new Articulo()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION)
            .ubicacion(DEFAULT_UBICACION)
            .unidad(DEFAULT_UNIDAD)
            .valorUnidad(DEFAULT_VALOR_UNIDAD)
            .idProveedor(DEFAULT_ID_PROVEEDOR);
        return articulo;
    }

    @Before
    public void initTest() {
        articulo = createEntity(em);
    }

    @Test
    @Transactional
    public void createArticulo() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isCreated());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate + 1);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testArticulo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testArticulo.getUbicacion()).isEqualTo(DEFAULT_UBICACION);
        assertThat(testArticulo.getUnidad()).isEqualTo(DEFAULT_UNIDAD);
        assertThat(testArticulo.getValorUnidad()).isEqualTo(DEFAULT_VALOR_UNIDAD);
        assertThat(testArticulo.getIdProveedor()).isEqualTo(DEFAULT_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void createArticuloWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = articuloRepository.findAll().size();

        // Create the Articulo with an existing ID
        articulo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArticuloMockMvc.perform(post("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllArticulos() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get all the articuloList
        restArticuloMockMvc.perform(get("/api/articulos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(articulo.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].ubicacion").value(hasItem(DEFAULT_UBICACION.toString())))
            .andExpect(jsonPath("$.[*].unidad").value(hasItem(DEFAULT_UNIDAD.toString())))
            .andExpect(jsonPath("$.[*].valorUnidad").value(hasItem(DEFAULT_VALOR_UNIDAD)))
            .andExpect(jsonPath("$.[*].idProveedor").value(hasItem(DEFAULT_ID_PROVEEDOR.toString())));
    }
    
    @Test
    @Transactional
    public void getArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", articulo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(articulo.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.ubicacion").value(DEFAULT_UBICACION.toString()))
            .andExpect(jsonPath("$.unidad").value(DEFAULT_UNIDAD.toString()))
            .andExpect(jsonPath("$.valorUnidad").value(DEFAULT_VALOR_UNIDAD))
            .andExpect(jsonPath("$.idProveedor").value(DEFAULT_ID_PROVEEDOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArticulo() throws Exception {
        // Get the articulo
        restArticuloMockMvc.perform(get("/api/articulos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Update the articulo
        Articulo updatedArticulo = articuloRepository.findById(articulo.getId()).get();
        // Disconnect from session so that the updates on updatedArticulo are not directly saved in db
        em.detach(updatedArticulo);
        updatedArticulo
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION)
            .ubicacion(UPDATED_UBICACION)
            .unidad(UPDATED_UNIDAD)
            .valorUnidad(UPDATED_VALOR_UNIDAD)
            .idProveedor(UPDATED_ID_PROVEEDOR);

        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedArticulo)))
            .andExpect(status().isOk());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
        Articulo testArticulo = articuloList.get(articuloList.size() - 1);
        assertThat(testArticulo.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testArticulo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testArticulo.getUbicacion()).isEqualTo(UPDATED_UBICACION);
        assertThat(testArticulo.getUnidad()).isEqualTo(UPDATED_UNIDAD);
        assertThat(testArticulo.getValorUnidad()).isEqualTo(UPDATED_VALOR_UNIDAD);
        assertThat(testArticulo.getIdProveedor()).isEqualTo(UPDATED_ID_PROVEEDOR);
    }

    @Test
    @Transactional
    public void updateNonExistingArticulo() throws Exception {
        int databaseSizeBeforeUpdate = articuloRepository.findAll().size();

        // Create the Articulo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArticuloMockMvc.perform(put("/api/articulos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(articulo)))
            .andExpect(status().isBadRequest());

        // Validate the Articulo in the database
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteArticulo() throws Exception {
        // Initialize the database
        articuloRepository.saveAndFlush(articulo);

        int databaseSizeBeforeDelete = articuloRepository.findAll().size();

        // Delete the articulo
        restArticuloMockMvc.perform(delete("/api/articulos/{id}", articulo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Articulo> articuloList = articuloRepository.findAll();
        assertThat(articuloList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Articulo.class);
        Articulo articulo1 = new Articulo();
        articulo1.setId(1L);
        Articulo articulo2 = new Articulo();
        articulo2.setId(articulo1.getId());
        assertThat(articulo1).isEqualTo(articulo2);
        articulo2.setId(2L);
        assertThat(articulo1).isNotEqualTo(articulo2);
        articulo1.setId(null);
        assertThat(articulo1).isNotEqualTo(articulo2);
    }
}
