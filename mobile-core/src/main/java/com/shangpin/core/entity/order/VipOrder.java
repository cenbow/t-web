package com.shangpin.core.entity.order;
import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


/** 
 * vip购买订单<br/>
 * @date    2016年8月8日 <br/> 
 * @author   陈小峰
 * @since    JDK 7
 */
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name="t_m_vip_order")
@ToString
public class VipOrder implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 224333358998255694L;
	@Id
	@GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
	private Long id;
	@Column(length=100,nullable=false)
	private String userId;
	@Column(length=32,nullable=false,unique=true)
	private String orderNo;
	/**
	 * 支付网关，
	 * ALIWAP,WEIXINWAP
	 */
	@Column(length=20,nullable=false)
	private String gateway;
	/**
	 * 下单金额
	 */
	@Column(nullable=false)
	private Float amount;
	/**
	 * 下单购买用户等级<br/>
	 * 0普通，1vip,2eip
	 */
	@Column
	private int level;
	/**
	 * 购买下单平台<br/>
	 * 1app,2m,3pc
	 */
	@Column
	private Integer plantform;
	/**
	 * 订单状态<br/>
	 * 0待支付，1成功
	 */
	@Column(nullable=false)
	private int status;
	/**
	 * 记录更新权益的日志
	 * 0为更新，1已更新
	 */
	@Column(nullable=true)
	private Byte logLevel=0;
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createDate;
	@Column
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDate;
	
	public boolean isPayed(){
		return 1==status;
	}
	/**
	 * 权益等级是否已经更新 
	 * <br/>
	 * @return true 已经更新
	 */
	public boolean levelLogd(){
		if(logLevel!=null && logLevel==LOGED){
			return true;
		}
		return false;
	}
	public static byte LOGED=1;
	public static byte UNLOGED=0;
}
