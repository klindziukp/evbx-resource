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
    public ItemData<Specification> findAllSpecifications() {
        return new ItemData<>(specRepository.findAll());
    }

    @Override
    public Specification findById(long id) {
        return specRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(Item.SPECIFICATION, id));
    }

    @Override
    public Specification save(Specification specification) {
        verifySpecPresence(specification);
        specification.setCreatedBy(AuthUtil.getUserName());
        Specification savedSpec = specRepository.save(specification);
        LOGGER.info("Report with id = '{}' updated successfully", savedSpec.getId());
        return savedSpec;
    }

    @Override
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
    public void deleteById(long id) {
        specRepository.deleteById(id);
    }

    private void verifySpecPresence(Specification specification) {
        String specificationName = specification.getSpecificationName();
        if (Objects.nonNull(specificationName)) {
            ValidationUtil.validateResourceName(specificationName, specRepository.getNames());
        }
    }
}
