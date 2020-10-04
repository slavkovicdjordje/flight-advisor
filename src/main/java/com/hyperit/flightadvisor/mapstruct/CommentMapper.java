package com.hyperit.flightadvisor.mapstruct;

import com.hyperit.flightadvisor.bean.CommentDto;
import com.hyperit.flightadvisor.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mappings({
            @Mapping(target = "city.id", source = "cityId")
    })
    Comment commentDtoToComment(CommentDto commentDto);

    @Mappings({
            @Mapping(target = "cityId", source = "city.id")
    })
    CommentDto commentToCommentDto(Comment comment);

}
