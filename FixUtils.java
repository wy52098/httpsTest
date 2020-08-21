package com.techmahindra.rmgsystem20.controller;

import com.techmahindra.rmgsystem20.entity.DeptAndEmp;
import com.techmahindra.rmgsystem20.response.Response;
import com.techmahindra.rmgsystem20.service.EmpOTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/ot")
public class FixUtils {

    @Autowired
    private EmpOTService empOTService;


    @GetMapping("/getOrgAndEmpTree")
    public Response<List<DeptAndEmp>> getOrgAndEmpTree(){
        List<DeptAndEmp> deptAndEmps =  empOTService.listTree();
        return Response.success(deptAndEmps);
    }


}
