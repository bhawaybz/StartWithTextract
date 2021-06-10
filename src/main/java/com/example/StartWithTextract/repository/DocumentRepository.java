package com.example.StartWithTextract.repository;

import com.example.StartWithTextract.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends MongoRepository<DocumentModel,String> {

}
