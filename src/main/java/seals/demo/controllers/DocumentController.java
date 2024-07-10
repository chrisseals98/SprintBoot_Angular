package seals.demo.controllers;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import seals.demo.models.document;
import seals.demo.repositories.documentRepository;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class DocumentController {
    @Autowired
    private documentRepository documentRepository;

    @GetMapping("/documents")
    public List<document> getUsers() {
        return (List<document>) this.documentRepository.findAll();
    }

    @GetMapping("/documents/application")
    public List<document> getDocumentsForApplication(@RequestParam String id) {
        Stream<document> stream = StreamSupport.stream(documentRepository.findAll().spliterator(), false);
        Stream<document> filteredStream = stream.filter(document -> document.getApplication().getId().toString().equals(id));
        List<document> list = filteredStream.toList();
        return list;
    }
}
