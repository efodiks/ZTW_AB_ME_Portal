package com.abme.portal.domain.post;


import com.abme.portal.domain.label.Label;
import com.abme.portal.domain.label.LabelRepository;
import com.abme.portal.domain.user.User;
import com.abme.portal.domain.user.UserRepository;
import com.abme.portal.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class PostFacade {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LabelRepository labelRepository;

    private void addNewLabels(Set<String> imageLabels)
    {
        var existingLabels = labelRepository.findAllByLabelNameIn(imageLabels);
        var labelsToAdd = new HashSet<String>(imageLabels);
        labelsToAdd.removeIf(labelString -> existingLabels.stream()
                                .anyMatch(x -> x.getLabelName().equals(labelString)));

        for (String labelString : labelsToAdd)
        {
            var label = new Label(labelString);
            labelRepository.save(label);
        }
    }

    private Set<Label> getMostPopularLabels(Set<Post> posts)
    {
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

    public PostDto addPostToUserWithEmail(AddPostDto addPostDto, String email) {
        var author = userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
        Post post = Post.from(addPostDto);
        post.setAuthor(author);
        postRepository.save(post);
        return PostDto.fromPost(post);
    }

    public PostDto addPostWithLabelsToUserWithEmail(AddPostDto addPostDto, String email, Set<String> imageLabels) {
        var author = userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
        Post post = Post.from(addPostDto);
        post.setAuthor(author);

        addNewLabels(imageLabels);
        var labelsToAddToPost = labelRepository.findAllByLabelNameIn(imageLabels);
        post.setLabels(labelsToAddToPost);

        postRepository.save(post);
        return PostDto.fromPost(post);
    }

    public List<PostDto> getPostsByAuthorEmailNot(String email) {
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(email);
        return postRepository
                .findByAuthorNot(author.orElseThrow(UserNotFoundException::new))
                .stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }

    public List<PostDto> getPostsByAuthorEmail(String email) {
        Optional<User> author = userRepository.findOneByEmailIgnoreCase(email);
        return postRepository
                .findByAuthor(author.orElseThrow(UserNotFoundException::new))
                .stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }

    public List<PostDto> getSuggestedPostsForUserWithEmail(String email) {
        var author = userRepository
                .findOneByEmailIgnoreCase(email)
                .orElseThrow(UserNotFoundException::new);
        var authorPosts = author.getPosts();
        var labels = getMostPopularLabels(authorPosts);
        var excludedUsers = new HashSet<User>(author.getFollowing());
        excludedUsers.add(author);

        return postRepository.findByLabelsInAndAuthorNotIn(labels, excludedUsers)
                .stream()
                .map(PostDto::fromPost)
                .collect(Collectors.toList());
    }
}
