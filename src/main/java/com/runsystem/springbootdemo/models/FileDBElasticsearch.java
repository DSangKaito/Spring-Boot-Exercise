package com.runsystem.springbootdemo.models;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.Id;

@Data
@Document(indexName = "filedb")
public class FileDBElasticsearch {
    /** id of FileDBElasticsearch */
    @Id
    private Long id;

    /** data of FileDBElasticsearch */
    private String content;
}
