package com.evbx.resource.layer.controller;

import com.evbx.resource.layer.service.SpecService;
import com.evbx.resource.model.domain.Specification;
import com.evbx.resource.model.data.ItemData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class SpecificationController {

    private SpecService specService;

    @Autowired
    public SpecificationController(SpecService specService) {
        this.specService = specService;
    }

    @GetMapping(value = "/specifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Specification> getSpecification(@PathVariable Long id) {
        return ResponseEntity.ok(specService.findById(id));
    }

    @GetMapping(value = "/specifications/ids", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Long>> getAllSpecificationsIds() {
        return ResponseEntity.ok(specService.getAllIds());
    }

    @GetMapping(value = "/specifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ItemData<Specification>> getAllSpecifications() {
        return ResponseEntity.ok(specService.findAllSpecifications());
    }

    @PostMapping(value = "/specifications", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity addSpecification(@Valid @RequestBody Specification specification) {
        return ResponseEntity.ok(specService.save(specification));
    }

    @PatchMapping(value = "/specifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Specification> updateSpecification(@PathVariable long id,
                                                             @RequestBody Specification specification) {
        return ResponseEntity.ok(specService.update(id, specification));
    }

    @DeleteMapping(value = "/specifications/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity deleteSpecification(@PathVariable long id) {
        specService.deleteById(id);
        return ResponseEntity.ok("Deleted item with id = " + id);
    }
}
