package com.abme.portal.bootstrap;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageUrlProvider {
    public static final String PICSUM_PHOTOS_V_2_LIST = "https://picsum.photos/v2/list";
    private final Random random = new Random();
    private final String template = "https://picsum.photos/id/%d/%d/%d";
    private final int height = 300;
    private final int width = 400;
    private final int ids_per_page = 100;
    private final List<Integer> ids = new ArrayList<>();
    private final RestTemplate restTemplate;
    private int currentPage = 0;

    public String randomUrl() {
        if (ids.isEmpty()) {
            ids.addAll(getIds(++currentPage));
        }
        var randomIndex = random.nextInt(ids.size());
        var randomId = ids.get(randomIndex);
        ids.remove(randomIndex);
        return randomUrl(randomId);
    }

    private String randomUrl(int id) {
        return String.format(template, id, height, width);
    }

    private List<Integer> getIds(int page) {
        String uriString = UriComponentsBuilder
                .fromHttpUrl(PICSUM_PHOTOS_V_2_LIST)
                .queryParam("page", page)
                .queryParam("limit", ids_per_page)
                .toUriString();

        PicsumImage[] images = restTemplate.getForObject(
                uriString,
                PicsumImage[].class);
        if (images == null) {
            throw new IllegalStateException("No ids returned from picsum");
        }
        return Stream
                .of(images)
                .map(PicsumImage::getId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
