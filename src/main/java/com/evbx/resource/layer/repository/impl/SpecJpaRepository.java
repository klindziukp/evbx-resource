package com.evbx.resource.layer.repository.impl;

import com.evbx.resource.layer.repository.SpecRepository;
import com.evbx.resource.model.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecJpaRepository extends JpaRepository<Specification, Long>, SpecRepository {}
