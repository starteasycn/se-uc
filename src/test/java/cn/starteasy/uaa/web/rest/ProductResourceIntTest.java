package cn.starteasy.uaa.web.rest;

import cn.starteasy.uaa.SeuaaApp;
import cn.starteasy.uaa.domain.Product;
import cn.starteasy.uaa.repository.ProductRepository;
import cn.starteasy.uaa.service.ProductService;
import cn.starteasy.uaa.service.dto.ProductDTO;
import cn.starteasy.uaa.service.mapper.ProductMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ProductResource REST controller.
 *
 * @see ProductResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SeuaaApp.class)
public class ProductResourceIntTest {
    private static final String DEFAULT_PRODUCT_NAME = "AAAAA";
    private static final String UPDATED_PRODUCT_NAME = "BBBBB";
    private static final String DEFAULT_PRODUCT_CODE = "AAAAA";
    private static final String UPDATED_PRODUCT_CODE = "BBBBB";
    private static final String DEFAULT_WEB_SITE = "AAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBB";
    private static final String DEFAULT_PRODUCT_DESC = "AAAAA";
    private static final String UPDATED_PRODUCT_DESC = "BBBBB";

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductMapper productMapper;

    @Inject
    private ProductService productService;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restProductMockMvc;

    private Product product;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProductResource productResource = new ProductResource();
        ReflectionTestUtils.setField(productResource, "productService", productService);
        this.restProductMockMvc = MockMvcBuilders.standaloneSetup(productResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product();
        product = new Product()
                .productName(DEFAULT_PRODUCT_NAME)
                .productCode(DEFAULT_PRODUCT_CODE)
                .webSite(DEFAULT_WEB_SITE)
                .productDesc(DEFAULT_PRODUCT_DESC);
        return product;
    }

    @Before
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    public void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // Create the Product
        ProductDTO productDTO = productMapper.productToProductDTO(product);

        restProductMockMvc.perform(post("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(DEFAULT_PRODUCT_NAME);
        assertThat(testProduct.getProductCode()).isEqualTo(DEFAULT_PRODUCT_CODE);
        assertThat(testProduct.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testProduct.getProductDesc()).isEqualTo(DEFAULT_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the products
        restProductMockMvc.perform(get("/api/products?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
                .andExpect(jsonPath("$.[*].productName").value(hasItem(DEFAULT_PRODUCT_NAME.toString())))
                .andExpect(jsonPath("$.[*].productCode").value(hasItem(DEFAULT_PRODUCT_CODE.toString())))
                .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE.toString())))
                .andExpect(jsonPath("$.[*].productDesc").value(hasItem(DEFAULT_PRODUCT_DESC.toString())));
    }

    @Test
    @Transactional
    public void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.productName").value(DEFAULT_PRODUCT_NAME.toString()))
            .andExpect(jsonPath("$.productCode").value(DEFAULT_PRODUCT_CODE.toString()))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE.toString()))
            .andExpect(jsonPath("$.productDesc").value(DEFAULT_PRODUCT_DESC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get("/api/products/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findOne(product.getId());
        updatedProduct
                .productName(UPDATED_PRODUCT_NAME)
                .productCode(UPDATED_PRODUCT_CODE)
                .webSite(UPDATED_WEB_SITE)
                .productDesc(UPDATED_PRODUCT_DESC);
        ProductDTO productDTO = productMapper.productToProductDTO(updatedProduct);

        restProductMockMvc.perform(put("/api/products")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(productDTO)))
                .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = products.get(products.size() - 1);
        assertThat(testProduct.getProductName()).isEqualTo(UPDATED_PRODUCT_NAME);
        assertThat(testProduct.getProductCode()).isEqualTo(UPDATED_PRODUCT_CODE);
        assertThat(testProduct.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testProduct.getProductDesc()).isEqualTo(UPDATED_PRODUCT_DESC);
    }

    @Test
    @Transactional
    public void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Get the product
        restProductMockMvc.perform(delete("/api/products/{id}", product.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Product> products = productRepository.findAll();
        assertThat(products).hasSize(databaseSizeBeforeDelete - 1);
    }
}
