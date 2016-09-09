package com.jspxcms.core.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import com.jspxcms.core.domain.ProductRecord;

/**
 * RecordDao
 * 
 * @author liufang
 * 
 */
public interface RecordDao extends Repository<ProductRecord, Integer>, RecordDaoPlus  {
	
	public Page<ProductRecord> findAll(Specification<ProductRecord> spec, Pageable pageable);
	
	public  ProductRecord save(ProductRecord bean);
	@Query("select count(*) from ProductRecord bean where bean.infoId=?1")
	public int findCountByInfoId(Integer id);


	
	@Query("select max(infoPeriod) from ProductRecord bean where bean.infoId=?1")
	public int findPeriodByInfoId(Integer id);
	
	@Query("select count(*) from ProductRecord bean where bean.infoId=?1 and bean.infoPeriod=?2")
	public int findCountByInfoIdAndPeriod(Integer id , Integer infoPeriod);

	@Query("select userId from ProductRecord bean where bean.periodNo=?1 and bean.infoId=?2 and bean.infoPeriod=?3")
	public int findByProInFo(int orderprono, Integer id, Integer infoPeriod);

	
}
