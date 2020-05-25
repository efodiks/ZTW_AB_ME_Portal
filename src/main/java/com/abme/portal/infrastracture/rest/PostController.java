package com.abme.portal.infrastracture.rest;

import com.abme.portal.domain.post.AddPostDto;
import com.abme.portal.domain.post.PostDto;
import com.abme.portal.domain.post.PostFacade;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

    private final PostFacade postFacade;

    private Set<String> GetImageLabels(String URL)
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

    @PostMapping("/posts/create")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PostDto> addPost(@Valid @RequestBody AddPostDto addPostDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var imageLabels = GetImageLabels(addPostDto.getURL());
        var postDto = postFacade.addPostWithLabelsToUserWithEmail(addPostDto, authentication.getName(), imageLabels);
        log.info(String.format("Creating post with uuid %s for user with email %s", addPostDto.getUuid(), authentication.getName()));
        return ResponseEntity.status(HttpStatus.CREATED).body(postDto);
    }

    @GetMapping("/posts")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(String.format("Getting posts feed for user with email %s", authentication.getName()));
        return postFacade.getPostsByAuthorEmailNot(authentication.getName());
    }

    @GetMapping("/posts/me")
    @PreAuthorize("isAuthenticated()")
    public Iterable<PostDto> getUsersPosts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(String.format("Getting user with email %s posts", authentication.getName()));
        return postFacade.getPostsByAuthorEmail(authentication.getName());
    }
}
