package com.shangpin.biz.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @ClassName: DefaultIndex
 * @Description:商品默认选择索引实体类
 * @author wangfeng
 * @date 2015年08月22日
 * @version 1.0
 */
@Getter
@Setter
public class DefaultIndex implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 753562769386897707L;
    private String firstPropIndex;
    private String secondPropIndex;

    
    

}
