package com.example.TeamWork.Specification;

import com.example.TeamWork.entity.Customer;
import org.springframework.data.jpa.domain.Specification;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class CustomerSpecification implements Specification<Customer> {
  private Customer filter;

  public CustomerSpecification(Customer filter) {
    super();
    this.filter = filter;
  }

  @Override
  public Predicate toPredicate(Root<Customer> root, CriteriaQuery<?> cq,
                               CriteriaBuilder cb) {

    Predicate p = cb.disjunction();

    if (filter.getName() != null) {
      p.getExpressions().add(cb.like(root.get("name"), "%" + filter.getName() + "%"));
    }

    if (filter.getPhoneNumber()!= null) {
      p.getExpressions().add(cb.like(root.get("phone"), "%" + filter.getPhoneNumber() + "%"));
    }

    return p;
  }
}
