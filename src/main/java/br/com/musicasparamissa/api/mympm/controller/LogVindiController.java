package br.com.musicasparamissa.api.mympm.controller;

import br.com.musicasparamissa.api.mympm.entity.LogVindi;
import br.com.musicasparamissa.api.mympm.service.LogVindiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController("MyMpMLogVindiController")
@RequestMapping("/mympm/logs-vindi")
public class LogVindiController {

    @Autowired
    private LogVindiService logVindiService;

    @GetMapping("/search")
    public Page<LogVindi> search(@RequestParam("filter") String filter,
                                 @RequestParam("processed") Boolean processed,
                                 @PageableDefault(value = Integer.MAX_VALUE) Pageable pageable) {

        return logVindiService.search(filter, processed, pageable);

    }

    @PutMapping("/{id}/processed/{processed}")
    public void setProcessed(
            @PathVariable("id") Long id,
            @PathVariable("processed") Boolean processed) {

        logVindiService.updateProcessed(id, processed);

    }

}
