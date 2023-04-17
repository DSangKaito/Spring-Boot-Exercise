package com.runsystem.springbootdemo.elasticsearch.repositories;

import com.runsystem.springbootdemo.models.FileDBElasticsearch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FileDBElasticsearchRepository extends ElasticsearchRepository<FileDBElasticsearch, Long> {

    List<FileDBElasticsearch> findFileDBElasticsearchByContentContainsIgnoreCase(String contentContainsIgnoreCase);
}
