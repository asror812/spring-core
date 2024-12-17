package com.example.demo.dao;

import com.example.demo.model.Training;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public interface TrainingDAO  extends GenericDAO<Training ,UUID>{
  
}
