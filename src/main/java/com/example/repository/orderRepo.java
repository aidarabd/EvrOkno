package com.example.repository;

import com.example.model.Role;
import com.example.model.order;
import com.example.model.wishes;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.Query;
import javax.persistence.criteria.Order;
import java.util.List;

@Repository("order")
public interface orderRepo extends JpaRepository<order, Integer> {
    List<order> findFirst7ByOrderByDateDesc();
    order deleteById(int id);
    List<order> findAllByOrderByQuantityAsc();
    List<order> findAllByOrderByDateAsc();

}
