package com.abme.portal.domain.label;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Set<Label> findAllByLabelNameIn(Set<String> imageLabels);
}
