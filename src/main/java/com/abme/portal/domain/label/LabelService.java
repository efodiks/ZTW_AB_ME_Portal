package com.abme.portal.domain.label;

import com.abme.portal.domain.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class LabelService {

    private final LabelRepository labelRepository;

    private final CloudVisionPhotoLabelService cloudVisionPhotoLabelService;

    public Set<Label> addPostLabels(Post post) {
        var labelStrings =
                cloudVisionPhotoLabelService.GetImageLabelStrings(post.getURL());

        var existingLabels = labelRepository.findAllByLabelNameIn(labelStrings);

        var labelStringsToAdd = new HashSet<>(labelStrings);
        labelStringsToAdd.removeIf(labelString -> existingLabels.stream()
                .anyMatch(x -> x.getLabelName().equals(labelString)));

        var newLabels = labelStringsToAdd
                .stream()
                .map(Label::new)
                .collect(Collectors.toList());
        newLabels = labelRepository.saveAll(newLabels);
        var allLabels = new HashSet<>(existingLabels);
        allLabels.addAll(newLabels);
        return allLabels;
    }

    public Set<Label> getMostPopularLabels(Set<Post> posts) {
        var labelsMap = new HashMap<Label, Integer>();

        for (Post post : posts) {
            for (Label label : post.getLabels()) {
                var labelOccurrences = labelsMap.get(label);
                labelsMap.put(label, labelOccurrences == null ? 1 : labelOccurrences + 1);
            }
        }

        return labelsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}