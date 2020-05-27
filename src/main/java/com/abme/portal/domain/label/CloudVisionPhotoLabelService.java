package com.abme.portal.domain.label;

import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CloudVisionPhotoLabelService {

    private final CloudVisionTemplate cloudVisionTemplate;
    private final ResourceLoader resourceLoader;

    public Set<String> GetImageLabelStrings(String URL)
    {
        AnnotateImageResponse response =
                this.cloudVisionTemplate.analyzeImage(
                        this.resourceLoader.getResource(URL), Feature.Type.LABEL_DETECTION);

        Map<String, Float> imageLabels =
                response
                        .getLabelAnnotationsList()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        EntityAnnotation::getDescription,
                                        EntityAnnotation::getScore,
                                        (u, v) -> {
                                            throw new IllegalStateException(String.format("Duplicate key %s", u));
                                        },
                                        LinkedHashMap::new));

        return imageLabels.keySet();
    }
}
