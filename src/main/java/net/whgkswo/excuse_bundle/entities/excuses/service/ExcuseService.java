package net.whgkswo.excuse_bundle.entities.excuses.service;

import lombok.RequiredArgsConstructor;
import net.whgkswo.excuse_bundle.entities.excuses.Excuse;
import net.whgkswo.excuse_bundle.entities.posts.tags.entity.Tag;
import net.whgkswo.excuse_bundle.entities.posts.tags.repository.TagRepository;
import net.whgkswo.excuse_bundle.exceptions.BadRequestException;
import net.whgkswo.excuse_bundle.exceptions.BusinessLogicException;
import net.whgkswo.excuse_bundle.exceptions.ExceptionType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExcuseService {
    private final TagRepository tagRepository;

    public Excuse createExcuse(String situation, String excuseStr, Set<String> tagKeys){
        Excuse excuse = new Excuse();

        excuse.setSituation(situation);
        excuse.setExcuse(excuseStr);

        Set<Tag> tags = tagKeys.stream()
                        .map(key -> {
                            String[] splitKeys = key.split(":", 2);
                            Tag.Category category = Tag.Category.valueOf(splitKeys[0]);
                            Optional<Tag> tag = tagRepository.findByCategoryAndValue(category, splitKeys[1]);
                            return tag.orElseThrow(() -> new BusinessLogicException(ExceptionType.tagNotFound(key)));
                        })
                        .collect(Collectors.toSet());

        excuse.setTags(tags);

        // post 등록하며 함께 등록되기 때문에 저장 없이 반환
        return excuse;
    }
}
