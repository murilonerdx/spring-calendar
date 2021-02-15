package com.example.demo.services;

import com.example.demo.model.DataImportante;
import com.example.demo.model.Tipo;
import com.example.demo.model.repository.DataImportanteRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DataImportanteService {
    @Autowired
    DataImportanteRepository repository;


    public List<DataImportante> listarDatasImportantes() {
        return repository.findAll();
    }

    public DataImportante insert(DataImportante entity) {
        repository.save(entity);
        if (entity.getTipo() == Tipo.Aniversario) {
            aniversario(entity);
        }
        if (entity.getTipo() == Tipo.Namoro) {
            aniversarioNamoro(entity);
        }
        return repository.save(entity);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public void atualizar(DataImportante obj, Long id) {
        DataImportante dataDesatualizada = repository.getOne(id);
        DataImportante dataAtualizada = dataAtualizar(dataDesatualizada, obj);
        repository.save(dataAtualizada);
    }

    public DataImportante dataAtualizar(DataImportante entity, DataImportante obj) {
        entity.setDescricao(obj.getDescricao());
        entity.setData(obj.getData());
        entity.setTipo(obj.getTipo());
        return entity;
    }

    public void aniversario(DataImportante entity) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataSomada = entity.getData().plus(Period.ofDays(366));
        String s = String.valueOf(entity.getData());
        if (entity.getData().getYear() != LocalDate.now().getYear()) {
            entity.setData(dataSomada);
            System.out.println(entity.getData());
            entity.setAlerta("Aniversario de " + entity.getDescricao() + " não chegou");
            entity.setFaltaQtdDias(366 - entity.getData().getDayOfYear());
            atualizar(entity, entity.getId());
        } else if (LocalDate.now().equals(entity.getData())) {
            entity.setFaltaQtdDias(0);
            entity.setAlerta("Hoje é o aniverario do " + entity.getDescricao());
            entity.setData(dataSomada);
            atualizar(entity, entity.getId());
        } else if (entity.getData().getYear() == LocalDate.now().getYear()) {
            entity.setData(dataSomada);
            entity.setAlerta("Aniversario de " + entity.getDescricao() + " não chegou");
            entity.setFaltaQtdDias(366 - entity.getData().getDayOfYear());
            atualizar(entity, entity.getId());
        } else {
            entity.setAlerta("Aniversario de " + entity.getDescricao() + " não chegou");
            entity.setFaltaQtdDias(366 - entity.getData().getDayOfYear());
            atualizar(entity, entity.getId());
        }
    }

    public void aniversarioNamoro(DataImportante entity) {
        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dataSomada = entity.getData().plus(Period.ofDays(28));
        String s = String.valueOf(entity.getData());
        if (entity.getData().getDayOfMonth() != LocalDate.now().getDayOfMonth()) {
            entity.setData(dataSomada);
            System.out.println(entity.getData());
            entity.setAlerta("Ainda não chegou aniversario de namoro");
            entity.setFaltaQtdDias((entity.getData().getDayOfYear()) - (LocalDate.now().getDayOfMonth() + 31));
            atualizar(entity, entity.getId());
        } else {
            entity.setAlerta("Aniversario de namoro não chegou");
            entity.setFaltaQtdDias((entity.getData().getDayOfYear()) - (LocalDate.now().getDayOfMonth() + 31));
            atualizar(entity, entity.getId());
        }

    }
}
