package com.evbx.resource.layer.service.impl;

import com.evbx.resource.constant.Item;
import com.evbx.resource.exception.ItemNotFoundException;
import com.evbx.resource.layer.repository.SpecRepository;
import com.evbx.resource.layer.service.SpecService;
import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.model.data.ItemData;
import com.evbx.resource.util.AuthUtil;
import com.evbx.resource.util.UpdateUtil;
import com.evbx.resource.util.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class SpecServiceImpl implements SpecService {

    private static final Logger LOGGER = LogManager.getLogger(SpecServiceImpl.class);

    private SpecRepository specRepository;

    @Autowired
    public SpecServiceImpl(SpecRepository specRepository) {
        this.specRepository = specRepository;
    }

    @Override
    @Cacheable(value= "allSpecsCache", unless= "#result.getTotal() == 0")
    public ItemData<Specification> findAllSpecifications() {
        LOGGER.info("Get all books");
        return new ItemData<>(specRepository.findAll());
    }

    @Override
    @Cacheable(value= "specCache", key= "'spec'+#id")
    public Specification findById(long id) {
        LOGGER.info("Get book with id = '{}'", id);
        return specRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.SPECIFICATION, id));
    }

    @Override
    @Caching(put = { @CachePut(value = "specCache", key = "'specCache'+#specification.id") },
             evict = { @CacheEvict(value = "allSpecsCache", allEntries = true) })
    public Specification save(Specification specification) {
        verifySpecPresence(specification);
        specification.setCreatedBy(AuthUtil.getUserName());
        Specification savedSpec = specRepository.save(specification);
        LOGGER.info("Specification with id = '{}' updated successfully", savedSpec.getId());
        return savedSpec;
    }

    @Override
    @Caching(put = { @CachePut(value = "specCache", key = "'spec'+#specification.id") },
             evict = { @CacheEvict(value = "allSpecsCache", allEntries = true) })
    public Specification update(long id, Specification specification) {
        Specification persistedSpecification = specRepository.findById(id).orElseThrow(
                () -> new ItemNotFoundException(Item.SPECIFICATION, id));
        verifySpecPresence(specification);
        UpdateUtil.updateItem(specification, persistedSpecification);
        Specification updatedSpec = specRepository.save(persistedSpecification);
        LOGGER.info("Report with id = '{}' updated successfully", updatedSpec.getId());
        return updatedSpec;
    }

    @Override
    public List<Long> getAllIds() {
        return specRepository.getAllIds();
    }

    @Override
    @Caching(evict = { @CacheEvict(value = "specCache", key = "'specCache'+#id"),
            @CacheEvict(value = "allSpecsCache", allEntries = true) })
    public void deleteById(long id) {
        if (!specRepository.existsById(id)) {
            throw new ItemNotFoundException(Item.SPECIFICATION, id);
        }
        specRepository.softDelete(id, AuthUtil.getUserName());
        LOGGER.info("Specification with id = '{}' deleted successfully", id);
    }

    private void verifySpecPresence(Specification specification) {
        String specificationName = specification.getSpecificationName();
        if (Objects.nonNull(specificationName)) {
            ValidationUtil.validateResourceName(specificationName, specRepository.getNames());
        }
    }
}
