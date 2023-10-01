package com.epam.esm.common.util;

import lombok.experimental.UtilityClass;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Utility class, that helps to enrich your {@link PagedModel} object with pagination links in HATEOAS style,
 * when Data Jpa dependencies are not available.<br/>
 * <strong>The pagination must be zero-based (offset - size)<strong/>
 */
@UtilityClass
public class PaginationHelper {

    /**
     * Method generates pagination links if: <br/>
     * total pages > 2, - {@code  first, prev, self, next, last} <br/>
     * total pages = 2 - {@code  prev, self} or {@code  (self, next)} <br/>
     * otherwise nothing changes
     *
     * @param pagedModel {@link PagedModel} object with inserted self link
     */
    public static void insertPageLinks(PagedModel<?> pagedModel) {
        long totalPages = Optional.ofNullable(pagedModel.getMetadata())
                .orElseThrow(() -> new IllegalArgumentException("Pageable metadata is null"))
                .getTotalPages();
        if (totalPages < 2)
            return;
        long currentNumber = pagedModel.getMetadata().getNumber();
        List<Link> links = new ArrayList<>();
        Link link = pagedModel.getLinks().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("SelfLink should be inserted"));
        if (totalPages > 2) {
            links.add(changeParamValue(link, currentNumber, 0)
                    .withRel("first"));
        }
        if (currentNumber != 0) {
            links.add(changeParamValue(link, currentNumber, currentNumber - 1)
                    .withRel("prev"));
        }
        links.add(link);
        if ((totalPages - 1) != currentNumber) {
            links.add(changeParamValue(link, currentNumber, currentNumber + 1)
                    .withRel("next"));
        }
        if (totalPages > 2) {
            links.add(changeParamValue(link, currentNumber, totalPages - 1)
                    .withRel("last"));
        }
        pagedModel.removeLinks();
        pagedModel.add(links);
    }

    private static Link changeParamValue(Link oldLink, long oldValue, long newValue) {
        return Link.of(oldLink.getHref()
                .replace("offset=" + oldValue,
                        "offset=" + newValue));
    }

    public static void insertPageLinks(PagedModel<?> result, Link selfLink) {
        result.add(selfLink);
        insertPageLinks(result);
    }
}