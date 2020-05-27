package com.abme.portal.domain.label;

import com.abme.portal.domain.post.Post;
import com.abme.portal.extensions.SetExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LabelServiceTest {

    @Mock
    CloudVisionPhotoLabelService cloudVisionPhotoLabelService;

    @Mock
    LabelRepository labelRepository;

    private LabelService labelService;

    private static final UUID POST_UUID = UUID.fromString("06bdb428-f71b-456b-b132-1f95903a8548");

    private static final String POST_URL = "POST_URL";

    private static Set<String> LABEL_STRINGS = Set.of(
            "label1", "label2", "label3", "label4"
    );

    private static Set<Label> LABELS_ALREADY_IN = Set.of(
            new Label(1L, "label1"),
            new Label(2L, "label2")
    );

    private static Post post = new Post(
            1L,
            POST_UUID,
            null,
            POST_URL,
            "POST_DESCRIPTION",
            new HashSet<>()
    );

    @Captor
    ArgumentCaptor<Iterable<Label>> labelCaptor;

    @BeforeEach
    void setUp() {
        labelService =
                new LabelService(labelRepository, cloudVisionPhotoLabelService);
    }

    @Test
    void addPostLabels() {
        //given
        when(cloudVisionPhotoLabelService.GetImageLabelStrings(POST_URL))
                .thenReturn(LABEL_STRINGS);
        when(labelRepository.findAllByLabelNameIn(LABEL_STRINGS))
                .thenReturn(LABELS_ALREADY_IN);
        when(labelRepository.saveAll(
                List.of(
                        new Label("label3"),
                        new Label("label4"))))
                .thenAnswer(i -> {
                    var arguments = ((List<Label>) i.getArgument(0));
                    arguments.get(0).setId(3L);
                    arguments.get(1).setId(4L);
                    return arguments;
                });

        //when
        var labels = labelService.addPostLabels(post);
        //then
        assertEquals(
                LABEL_STRINGS,
                SetExtension.map(labels, Label::getLabelName)
        );
        verify(labelRepository, times(1)).saveAll(labelCaptor.capture());

        var saveAllArgumentSet = StreamSupport
                .stream(labelCaptor
                        .getValue()
                        .spliterator(), false)
                .collect(Collectors.toSet());

        assertEquals(Set.of("label3", "label4"),
                SetExtension.map(saveAllArgumentSet, Label::getLabelName)
        );
    }

    @Test
    void getMostPopularLabels() {
        //TODO[ME] I don't really know how does this algorithm work, can't test it xd
    }
}