package com.jspxcms.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.ProductRecord;

/**
 * RecordService
 * 
 * @author liufang
 * 
 */
public interface RecordService {
	public Page<ProductRecord> findPage( Map<String, String[]> params,
			Pageable pageable);
	//查找商品的领取总记录数
	public int findCountByInfoId(Integer id);
	//查找商品的当前期数
	public int findPeriodByInfoId(Integer id);

	public ProductRecord save(ProductRecord bean);
	//查找商品的当前期的领取记录数
	public int findCountByInfoIdAndPeriod(Integer id, Integer infoPeriod);
	public int findByProInFo(int orderprono, Integer id, Integer infoPeriod);
	public Page<ProductRecord> findPage(Integer infoId, Integer infoPeriod, Pageable pageable);
	public List<ProductRecord> getByInfoId(Integer id);
	
	
}
