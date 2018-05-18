package com.hunter.sagittarius.rest.controller;

import com.hunter.sagittarius.service.bean.IdType;
import com.hunter.sagittarius.service.provider.impl.IpConfigurableMachineIdProvider;
import com.hunter.sagittarius.service.service.IdService;
import com.hunter.sagittarius.service.service.impl.AbstractIdServiceImpl;
import com.hunter.sagittarius.service.service.impl.IdServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SagittariusRestController {



    @GetMapping(value = "/genId")
    public String genId(){
        AbstractIdServiceImpl idService = new IdServiceImpl(IdType.SECONDS.value());
        idService.setMachineIdProvider(new IpConfigurableMachineIdProvider());
        idService.init();

        return String.valueOf(idService.genId());
    }

    /*@PostMapping(value = "/expId/{id}")
    public String expId(long id){


        return "hello-expId";
    }*/

   /* public String makeId() {

        return "hello-makeID";
    }*/

}
