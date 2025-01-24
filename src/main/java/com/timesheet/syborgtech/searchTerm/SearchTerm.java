package com.timesheet.syborgtech.searchTerm;

import com.timesheet.syborgtech.model.Role;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public class SearchTerm {
    public static Specification<Role> containsText(String searchTerm, Long roleId) {

//        if (StringUtils.hasLength(searchTerm) && !searchTerm.contains("%")) {
//            searchTerm = "%" + searchTerm.toLowerCase() + "%";
//        }
//        String finalSearchTerm = searchTerm;

        boolean hasSearchTerm = StringUtils.hasLength(searchTerm);
        String finalSearchTerm = hasSearchTerm && !searchTerm.contains("%")
                ? "%" + searchTerm.toLowerCase() + "%"
                : searchTerm;

        Specification<Role> resultSpec = ((root1, query1, criteriaBuilder1) -> criteriaBuilder1.and(
                criteriaBuilder1.equal(root1.get("Status"), Role.RoleStatus.ACTIVE)
        ));

        if (Objects.nonNull(roleId)) {
            Specification<Role> roleIdSpec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.equal(root.get("id"), roleId);
            resultSpec = resultSpec.and(roleIdSpec);
        }

        if (hasSearchTerm) {
            Specification<Role> searchSpec = (root, query, criteriaBuilder) ->
                    criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("roleName")), finalSearchTerm),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), finalSearchTerm)
                    );
            resultSpec = resultSpec.and(searchSpec);
        }

        return resultSpec;
    }
}
