package ru.domclick.cockpit.plugin.log.mapper.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.elasticsearch.search.SearchHit;

@Data
@AllArgsConstructor
public final class SearchHitWrapper {
    private SearchHit hit;
}
