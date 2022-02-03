package com.kosmo.fitnesstogether.service;


import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class row2 {
    public String CLSBIZ_DT;
    public String SITE_ADDR;
    public String PRDLST_REPORT_NO;
    public String PRMS_DT;
    public String PRDLST_NM;
    public String BAR_CD;
    public String POG_DAYCNT;
    public String PRDLST_DCNM;
    public String BSSH_NM;
    public String END_DT;
    public String INDUTY_NM;
    public String id;
    public String text;

}
