package net.whgkswo.excuse_bundle.entities.posts.tags.dto;

import net.whgkswo.excuse_bundle.entities.posts.tags.entity.Tag;

public record TagSearchResult(
        Tag tag,
        double score
) {
}
