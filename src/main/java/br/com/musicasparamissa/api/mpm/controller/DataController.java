package br.com.musicasparamissa.api.mpm.controller;

import br.com.musicasparamissa.api.mpm.entity.Data;
import br.com.musicasparamissa.api.exception.InvalidEntityException;
import br.com.musicasparamissa.api.mpm.repository.DataRepository;
import br.com.musicasparamissa.api.mpm.service.ClearCacheService;
import br.com.musicasparamissa.api.mpm.service.DataService;
import br.com.musicasparamissa.api.mpm.service.SiteGenerateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/datas")
public class DataController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private SiteGenerateService siteGenerateService;

    @Autowired
    private ClearCacheService clearCacheService;


    @GetMapping
    public Page<Data> listDatas(Pageable pageable){

        return dataService.listDatas(pageable);

    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody Data data) throws ParseException {

        try {
            dataService.create(data);

            refreshDatas();

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

            refreshDatas();

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (InvalidEntityException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    private void refreshDatas() {
        Calendar tenDaysAgo = Calendar.getInstance();
        tenDaysAgo.add(Calendar.DATE, -10);
        Iterable<Data> datas = dataRepository.findAllByDataGreaterThanOrderByDataDesc(tenDaysAgo.getTime());
        siteGenerateService.generateDatas(datas);
        clearCacheService.one("https://musicasparamissa.com.br/datas.json");
    }

    @DeleteMapping("/{date}")
    public ResponseEntity<String> delete(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) throws ParseException {

        dataService.delete(date);

        refreshDatas();

        return new ResponseEntity<>(HttpStatus.OK);

    }

    @GetMapping(path = "/{date}/exists")
    public ResponseEntity<String> exists(@PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        if(dataService.exists(date))
            return new ResponseEntity<>("1", HttpStatus.OK);

        return new ResponseEntity<>("0", HttpStatus.OK);

    }

}
