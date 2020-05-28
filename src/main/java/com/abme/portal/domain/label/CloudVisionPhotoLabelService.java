package com.abme.portal.domain.label;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CloudVisionPhotoLabelService {

    private final CloudVisionTemplate cloudVisionTemplate;
    private final ResourceLoader resourceLoader;

    public Set<String> GetImageLabelStrings(String URL) {
        var resource = this.resourceLoader.getResource(URL);
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(resource, Feature.Type.LABEL_DETECTION);

        return response
                .getLabelAnnotationsList()
                .stream()
                .map(EntityAnnotation::getDescription)
                .collect(Collectors.toSet());
    }
}
