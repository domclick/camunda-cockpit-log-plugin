package ru.domclick.cockpit.plugin.log.mapper;

import ru.domclick.cockpit.plugin.log.dto.LogDto;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils.ActivityName;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils.BusinessKey;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils.Level;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils.Message;
import ru.domclick.cockpit.plugin.log.mapper.util.LogMapperUtils.Timestamp;
import ru.domclick.cockpit.plugin.log.mapper.util.SearchHitWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = LogMapperUtils.class)
public interface LogMapper {

    LogMapper MAPPER = Mappers.getMapper(LogMapper.class);

    @Mapping(source = "hit", target = "level", qualifiedBy = Level.class)
    @Mapping(source = "hit", target = "businessKey", qualifiedBy = BusinessKey.class)
    @Mapping(source = "hit", target = "message", qualifiedBy = Message.class)
    @Mapping(source = "hit", target = "activityName", qualifiedBy = ActivityName.class)
    @Mapping(source = "hit", target = "time", qualifiedBy = Timestamp.class)
    LogDto toLogDto(SearchHitWrapper hitWrapper);
}
