package com.example.StartWithTextract.repository;

import com.example.StartWithTextract.model.DocumentModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DocumentRepository extends MongoRepository<DocumentModel, String> {

    @Query("{'urlModel':{'url1':?0,'url2':?1}}")
    DocumentModel findbyUrl(String u1, String u2);
}
