package com.example.TeamWork.Specification;

import com.example.TeamWork.entity.Project;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ProjectSpecification implements Specification<Project> {
  private Project filter;

  public ProjectSpecification(Project filter) {
    super();
    this.filter = filter;
  }

  @Override
  public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> cq,
                               CriteriaBuilder cb) {

    Predicate p = cb.disjunction();

    if (filter.getProjectName() != null) {
      p.getExpressions().add(cb.like(root.get("name"), "%" + filter.getProjectName() + "%"));
    }

    return p;
  }
}
