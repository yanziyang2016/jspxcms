package com.jspxcms.core.service.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jspxcms.common.orm.SearchFilter;
import com.jspxcms.core.domain.Info;
import com.jspxcms.core.domain.Order;
import com.jspxcms.core.domain.Product;
import com.jspxcms.core.domain.User;
import com.jspxcms.core.repository.InfoDao;
import com.jspxcms.core.repository.OrderDao;
import com.jspxcms.core.repository.ProductDao;
import com.jspxcms.core.repository.UserDao;
import com.jspxcms.core.service.OrderService;

/**
 * UserServiceImpl
 * 
 * @author liufang
 * 
 */
@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService{
	private static final int SALT_SIZE = 8;

	public Page<Order> findPage(Map<String, String[]> params,
			Pageable pageable) {
		Page<Order> orderPage=dao.findAll(spec( params), pageable);
		if(orderPage.getContent().size()>0){
			for(int i=0;i<orderPage.getContent().size();i++){
				User usertemp = userdao.findOne(orderPage.getContent().get(i).getUserId());
//				Info infotemp = infodao.findOne(orderPage.getContent().get(i).getInfoId());
				Product product = productDao.findOne(orderPage.getContent().get(i).getInfoId());
				orderPage.getContent().get(i).setUserAddress(usertemp.getHomeAddress());
				orderPage.getContent().get(i).setUserName(usertemp.getRealName());
				orderPage.getContent().get(i).setMobile(usertemp.getMobile());
				orderPage.getContent().get(i).setInfoName(product.getTitle());
				orderPage.getContent().get(i).setProductPro(product.getProductpro()==1?"虚拟":"实物");
			}
			
		}
		
		return orderPage;
	}
	
	private Specification<Order> spec( Map<String, String[]> params) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Order> fsp = SearchFilter.spec(filters, Order.class);
		Specification<Order> sp = new Specification<Order>() {
			public Predicate toPredicate(Root<Order> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
							
				return pred;
			}
		};
		return sp;
	}

	
	public Page<Order> findPage(Integer id, 
			Pageable pageable){
		Map<String, String[]> params = new HashMap<String, String[]>();
		Page<Order> orderPage=dao.findAll(spec1( params ,id), pageable);
		if(orderPage.getContent().size()>0){
			for(int i=0;i<orderPage.getContent().size();i++){
				User usertemp = userdao.findOne(orderPage.getContent().get(i).getUserId());
				Product product = productDao.findOne(orderPage.getContent().get(i).getInfoId());
				orderPage.getContent().get(i).setUserAddress(usertemp.getHomeAddress());
				orderPage.getContent().get(i).setUserName(usertemp.getRealName());
				orderPage.getContent().get(i).setMobile(usertemp.getMobile());
				orderPage.getContent().get(i).setInfoName(product.getTitle());
				orderPage.getContent().get(i).setProductPro(product.getProductpro()==1?"虚拟":"实物");
				
			}
			
		}
		return orderPage;
//		return dao.findPage(id, 
//				pageable);
	}

	private Specification<Order> spec1( Map<String, String[]> params, final Integer id) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Order> fsp = SearchFilter.spec(filters, Order.class);
		Specification<Order> sp = new Specification<Order>() {
			public Predicate toPredicate(Root<Order> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				pred = cb.and(pred, cb.equal(root.<Integer> get("userId"), id));			
				return pred;
			}
		};
		return sp;
	}
	
	
	public Page<Order> findPage2(Integer infoId, 
			Pageable pageable){
		Map<String, String[]> params = new HashMap<String, String[]>();
		Page<Order> orderPage=dao.findAll(spec2( params ,infoId), pageable);
		if(orderPage.getContent().size()>0){
			for(int i=0;i<orderPage.getContent().size();i++){
				User usertemp = userdao.findOne(orderPage.getContent().get(i).getUserId());
//				Info infotemp = infodao.findOne(orderPage.getContent().get(i).getInfoId());
				Product product = productDao.findOne(orderPage.getContent().get(i).getInfoId());
				orderPage.getContent().get(i).setInfoName(product.getTitle());
				orderPage.getContent().get(i).setProductPro(product.getProductpro()==1?"虚拟":"实物");
				orderPage.getContent().get(i).setUserAddress(usertemp.getHomeAddress());
				orderPage.getContent().get(i).setUserName(usertemp.getRealName());
				orderPage.getContent().get(i).setMobile(usertemp.getMobile());
			
			}
			
		}
		return orderPage;
//		return dao.findPage(id, 
//				pageable);
	}

	private Specification<Order> spec2( Map<String, String[]> params, final Integer infoId) {
		Collection<SearchFilter> filters = SearchFilter.parse(params).values();
		final Specification<Order> fsp = SearchFilter.spec(filters, Order.class);
		Specification<Order> sp = new Specification<Order>() {
			public Predicate toPredicate(Root<Order> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate pred = fsp.toPredicate(root, query, cb);
				pred = cb.and(pred, cb.equal(root.<Integer> get("infoId"), infoId));			
				return pred;
			}
		};
		return sp;
	}


	

	private UserDao userdao;
	@Autowired
	public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}
	

	@Autowired
	private ProductDao productDao;

	private OrderDao dao;
	@Autowired
	public void setDao(OrderDao dao) {
		this.dao = dao;
	}
	@Transactional
	public Order save(Order order) {
		return dao.save(order);
	}

	@Override
	public Order get(Integer id) {
		Order order = dao.findOne(id);
		User usertemp = userdao.findOne(order.getUserId());
//		Info infotemp = infodao.findOne(order.getInfoId());
		Product product = productDao.findOne(order.getInfoId());
		order.setUserAddress(usertemp.getHomeAddress());
		order.setUserName(usertemp.getRealName());
		order.setMobile(usertemp.getMobile());
		order.setInfoName(product.getTitle());
		order.setProductPro(product.getProductpro()==1?"虚拟":"实物");
		return order;
	}

	@Transactional
	public void update(Order bean) {
		dao.updateOrder(bean.getOrderNo(),bean.getLogisticsNo(),bean.getLogisticsName(),bean.getStatus());
		
	}

	@Transactional
	public void delete(Integer id) {
		Order bean = dao.findOne(id);
		dao.delete(bean);
	}

}
