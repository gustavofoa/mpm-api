/* 
 * @(#)CategoriaController.java 1.0 08/2017
 */
package br.com.musicasparamissa.mpmjadmin.backend.controller;

import br.com.musicasparamissa.mpmjadmin.backend.entity.Categoria;
import br.com.musicasparamissa.mpmjadmin.backend.entity.Data;
import br.com.musicasparamissa.mpmjadmin.backend.exception.InvalidEntityException;
import br.com.musicasparamissa.mpmjadmin.backend.exception.UnableToRemoveException;
import br.com.musicasparamissa.mpmjadmin.backend.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/datas")
public class DataController {

    @Autowired
    private DataService dataService;


    @GetMapping
    public Page<Data> listDatas(Pageable pageable){

        return dataService.listDatas(pageable);

    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Data data) throws ParseException {

        try {
            dataService.create(data);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PutMapping("/{oldDate}")
    public ResponseEntity<String> update(@PathVariable("oldDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date oldDate,
                                       @RequestBody Data data) throws ParseException {

        try {
            dataService.update(data, oldDate);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @DeleteMapping("/{date}")
    public ResponseEntity<String> delete(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ParseException {

        dataService.delete(date);
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
