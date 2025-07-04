package net.whgkswo.excuse_bundle.entities.posts.comments;

import net.whgkswo.excuse_bundle.entities.members.core.mapper.MemberMapper;
import net.whgkswo.excuse_bundle.entities.posts.comments.dto.CommentResponseDto;
import net.whgkswo.excuse_bundle.entities.posts.comments.entity.Comment;
import net.whgkswo.excuse_bundle.entities.posts.comments.entity.CommentVoteDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {MemberMapper.class})
public interface CommentMapper {

    @Mapping(target = "isReply", expression = "java(comment.getPost() == null)")
    CommentResponseDto commentToCommentResponseDto(Comment comment, CommentVoteDto myVote);

}
