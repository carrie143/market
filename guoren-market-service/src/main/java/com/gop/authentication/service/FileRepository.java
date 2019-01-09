package com.gop.authentication.service;

import java.io.File;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepository extends MongoRepository<File, String>  {

}
