package com.shangpin.biz.bo.addr;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AddrNew implements Serializable{
    private static final long serialVersionUID = 1L;
    private String areaId;
    private String areaChName;
    private String areaEnName;
    private String layer;//级别
    private String patch;
    private String parentID;
    private String parentIDs;

}
