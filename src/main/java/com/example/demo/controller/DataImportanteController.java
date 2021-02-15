package com.example.demo.controller;

import com.example.demo.model.DataImportante;
import com.example.demo.services.DataImportanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/dataImportante")
public class DataImportanteController {

    @Autowired
    DataImportanteService service;


    @GetMapping()
    public ResponseEntity<List<DataImportante>> listar(){
        List<DataImportante> datasImportantes = service.listarDatasImportantes();
        return ResponseEntity.ok().body(datasImportantes);
    }

    @PostMapping()
    public ResponseEntity<DataImportante> inserir(@RequestBody @Valid DataImportante entity){
        DataImportante dataImportante = service.insert(entity);
        return ResponseEntity.ok().body(dataImportante);
    }

    @PutMapping()
    public ResponseEntity<Void> atualizar(@RequestBody @Valid DataImportante obj, @RequestPart Long id){
        service.atualizar(obj, id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping()
    public ResponseEntity<Void> deletar(@RequestParam Long id){
        service.remover(id);
        return ResponseEntity.noContent().build();
    }


}
